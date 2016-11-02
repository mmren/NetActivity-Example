package com.rt.sc.tools;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.ContactsContract;
import android.util.Log;

import com.rt.sc.model.PhoneInfo;

import java.util.List;

/**
 * Created by renmingming on 15/9/21.
 */
public class AppUtils {

    private static final boolean DEBUG = true;
    private static final String TAG = "SystemUtils";

    /**
     * 获取设备号
     *
     * @return
     */
    public static String getDeviceId(Context context) {
        return DeviceUUID.getDeviceUUID(context);
    }

    /**
     * 获取版本信息
     *
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            String versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            return versionName;
        } catch (NameNotFoundException e) {
            return "1.0";
        }
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            int versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            return versionCode;
        } catch (NameNotFoundException e) {
            return 0;
        }
    }

    /**
     * 获取app名称
     *
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
            String name = (String) context.getPackageManager().getApplicationLabel(applicationInfo);
            return name;
        } catch (NameNotFoundException e) {
            return "";
        }
    }

    /**
     * 判断context指定的程序是否处于前台<br />
     * 注意:运行此方法需要<br />
     * &lt;uses-permission android:name="android.permission.GET_TASKS" /&gt;权限
     *
     * @param context
     * @return true:程序处于前台运行,false:Activity未处于前台状态
     */
    public static boolean isApplicationForeground(final Context context) throws PermissionException {
        return isApplicationForeground(context, context.getPackageName());
    }

    /**
     * 判断packageName指定的包名的程序是否处于前台<br />
     * 注意:运行此方法需要<br />
     * &lt;uses-permission android:name="android.permission.GET_TASKS" /&gt;权限
     *
     * @param context
     * @param packageName 要查询的包名
     * @return true:程序处于前台运行,false:Activity未处于前台状态
     */
    public static boolean isApplicationForeground(final Context context, String packageName)
            throws PermissionException {
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<RunningTaskInfo> tasks = am.getRunningTasks(1);
            if (!tasks.isEmpty()) {
                ComponentName topActivity = tasks.get(0).topActivity;
                if (topActivity.getPackageName().equals(packageName)) {
                    return true;
                }
            }
        } catch (SecurityException e) {
            Log.e(TAG, e.getMessage() + " Try to complete without permission");
            try {
                return isApplicationForegroundWithoutPermissions(context, packageName);
            } catch (UnsupportedOperationException e2) {
                throw new PermissionException(e);
            }
        }
        return false;
    }

    /**
     * 判断cls给定Activity的是否处于前台<br />
     * 注意:运行此方法需要<br />
     * &lt;uses-permission android:name="android.permission.GET_TASKS" /&gt;权限
     *
     * @param context
     * @return true:Activity处于前台运行,false:Activity未处于前台状态
     */
    public static boolean isActivityForeground(final Context context, Class<?> cls)
            throws PermissionException {
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<RunningTaskInfo> tasks = am.getRunningTasks(1);
            if (!tasks.isEmpty()) {
                ComponentName topActivity = tasks.get(0).topActivity;
                if (topActivity.getClassName().equals(cls.getName())) {
                    return true;
                }
            }
        } catch (SecurityException e) {
            Log.e(TAG, e.getMessage() + " Try to complete without permission");
            throw new PermissionException(e);
        }
        return false;
    }

    /**
     * 判断content给定的程序是否处于前台<br />
     * 注意:运行此方法不需要权限，但是比{@code isApplicationForeground}耗费更多时间
     *
     * @return true 在前台; false 在后台
     */
    public static boolean isApplicationForegroundWithoutPermissions(final Context context,
                                                                    final String packageName) throws UnsupportedOperationException {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        boolean isspecial = true;
        List<RunningAppProcessInfo> appProcesses = am.getRunningAppProcesses();
        if (appProcesses == null) return false;
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)) {
                if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                        || appProcess.importance == RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                    return true;
                }
                if (km.inKeyguardRestrictedInputMode()) return true;
            }
            if (isspecial) {
                /*
                 * http://blog.csdn.net/jw083411/article/details/7903038
                 * 经过测试，在一般情况下可行，在某些经过简洁版本的Android系统中
                 * RunningAppProcessInfo.importance的值一直为400，原因未找到，RunningAppProcessInfo
                 * 里面的变量importance其实是表示这个app进程的重要性，因为系统回收时候，会根据importance
                 * 的重要来杀进程的。具体可以去看文档。
                 */
                if (appProcess.importance != RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    isspecial = false;
                }
            }
        }
        if (isspecial) {
            Log.e(TAG,
                    "Utils.isApplicationForegroundWithoutPermissions():The system is unable to obtain accurate information and return false");
        }
        return false;
    }


    /**
     * 取得device的IP address
     */
    @SuppressLint("DefaultLocale")
    public static String getIp(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
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
    public static PhoneInfo backContact(Intent intent,Context mContext) throws SecurityException
    {
        PhoneInfo phoneInfo = new PhoneInfo();
        try
        {
            Uri uri = intent.getData();
            // 得到ContentResolver对象
            ContentResolver cr = mContext.getContentResolver();
            // 取得电话本中开始一项的光标
            Cursor cursor = cr.query(uri, null, null, null, null);
            // 向下移动光标
            while (cursor.moveToNext()) {
                // 取得联系人名字
                int nameFieldColumnIndex = cursor
                        .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                String contact = cursor.getString(nameFieldColumnIndex);
                phoneInfo.setContactName(contact);
                // 取得电话号码
                String ContactId = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.Contacts._ID));
                Cursor phone = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                + "=" + ContactId, null, null);
                //不只一个电话号码
                if (phone!=null) {
                    phone.moveToFirst();
                    String PhoneNumber = phone
                            .getString(phone
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    if (!StringUtils.isEmpty(PhoneNumber))
                    {
                        if (PhoneNumber.startsWith("+86"))
                        {
                            PhoneNumber=PhoneNumber.substring(3);
                        }
                        phoneInfo.setContactNumber(PhoneNumber.replaceAll(" ","").replaceAll("-",""));
                    }
                }
            }
            cursor.close();
        }
        catch (Exception e)
        {
            throw e;
        }

        return phoneInfo;
    }
}
