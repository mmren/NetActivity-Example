package com.rt.sc.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.UUID;

/**
 * Created by renmingming on 15/9/21.
 */
public class DeviceUUID
{

    private static final boolean DEBUG = true;

    private static final String TAG = "DeviceUUID";

    /**
     * 执行Adb Shell 命令
     *
     * @return
     */
    public static String execAdbShell(String[] args)
    {
        ProcessBuilder cmd;
        String result = null;
        try
        {
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            result = StringUtils.inputStreamToString(in);
            in.close();
            return result;
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Cpu信息
     *
     * @return
     */
    public static String getCpuFrequency()
    {
        try
        {
            String[] args = {"/system/bin/cat", "/proc/cpuinfo"};
            return execAdbShell(args);
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 获取CPU序列号
     *
     * @return CPU序列号(16位)
     */
    public static String getCPUSerial()
    {
        String serial = null;
        String cpuFrequency = getCpuFrequency();
        if (cpuFrequency == null)
        {
            return null;
        }
        String[] split = cpuFrequency.split("\n");
        for (String string : split)
        {
            if (string.contains("Serial"))
            {
                serial = string.substring(string.indexOf(":") + 1).trim();
            }
        }
        if (serial == null || serial.matches("^0+$"))
        {
            return null;
        } else
        {
            Log.i(TAG, "DeviceUtil.getCPUSerial()=" + serial);
            return serial;
        }
    }

    /**
     * 获取设备Mac地址
     *
     * @return
     */
    public static String getMacAddress()
    {
        String serial = null;
        try
        {
            String[] args = {"/system/bin/cat", "/sys/class/net/wlan0/address"};
            serial = execAdbShell(args);
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        String upperCase = serial.trim().toUpperCase();
        Log.i(TAG, "DeviceUtil.getMacAddress()=" + upperCase);
        if (upperCase
                .matches("^([A-Za-z0-9][A-Za-z0-9]:){5}[A-Za-z0-9][A-Za-z0-9]$"))
        {
            return upperCase;
        }
        return null;
    }

    /**
     * 获取设备唯一的DeviceId.
     *
     * @return CPU序列号(16位)
     */
    public static String getDeviceId(Context mContext)
    {
        String device_id;
        if (mContext == null)
        {
            Log.w(TAG, "DeviceUtil.getDeviceId() mContext = null");
            return null;
        }
        try
        {
            TelephonyManager phoneMgr = (TelephonyManager) mContext
                    .getSystemService(Context.TELEPHONY_SERVICE);
            device_id = phoneMgr.getDeviceId();
            if (device_id == null || device_id.matches("^0+$"))
            {
                return null;
            } else
            {
                Log.i(TAG, "DeviceUtil.getDeviceId()=" + device_id);
                return device_id;
            }
        } catch (SecurityException e)
        {
            Log.w(TAG, "DeviceUtil.getDeviceId() /n" + e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * 获取UUID
     *
     * @return
     */
    public static String getInstalltionUUID(Context mContext)
    {
        return Installtion.id(mContext);
    }

    /**
     * 获取设备唯一的DeviceId.
     * 首先尝试使用TelephonyManager.getDeviceId()
     * 如果以上值无效将尝试返回有效的CPUSerial
     * 如果以上值无效将尝试返回有效的MacAddress
     * 如果以上值无效将尝试生成唯一的UUID并存储
     *
     * @return DeviceId 或者 CPU序列号(16位)
     */
    public static String getDeviceUUID(Context mContext)
    {
        String serial = null;
        if (mContext == null)
        {
            throw new NullPointerException(
                    "DeviceUtil.getDeviceUUID():mContext = null");
        }
        SharedPreferences preferences = mContext.getSharedPreferences(
                "DeviceUUID", 0);
        serial = preferences.getString("DeviceUUID", null);
        if (serial == null)
        {
            Log.i(TAG, "DeviceUtil.getDeviceUUID() generate a new UUID");
            serial = getDeviceId(mContext);
            if (serial == null)
            {
                Log.w(TAG,
                        "DeviceUtil.getDeviceUUID() getDeviceId = null and return getCPUSerial");
                serial = getCPUSerial();
            }
            if (serial == null)
            {
                Log.w(TAG,
                        "DeviceUtil.getDeviceUUID() getCPUSerial = null and return getMacAddress");
                serial = getMacAddress();
            }
            if (serial == null)
            {
                Log.w(TAG,
                        "DeviceUtil.getDeviceUUID() getMacAddress = null and return Installtion UUID");
                serial = getInstalltionUUID(mContext);
            }
            preferences.edit().putString("DeviceUUID", serial).commit();
        }
        return serial;
    }

    /**
     * 生成UUID
     */
    private static class Installtion
    {

        private static final String TAG = "Installtion.java";

        private static String sID = null;

        private static final String INSTALLATION = "INSTALLATION";

        public synchronized static String id(Context context)
        {
            if (sID == null)
            {
                File installation = new File(
                        context.getFilesDir(), INSTALLATION);
                try
                {
                    if (!installation.exists())
                        writeInstallationFile(installation);
                    sID = readInstallationFile(installation);
                } catch (Exception e)
                {
                    throw new RuntimeException(e);
                }
            }
            return sID;
        }

        private static String readInstallationFile(File installation)
                throws IOException
        {
            RandomAccessFile f = new RandomAccessFile(installation, "r");
            byte[] bytes = new byte[(int) f.length()];
            f.readFully(bytes);
            f.close();
            return new String(bytes);
        }

        private static void writeInstallationFile(File installation)
                throws IOException
        {
            FileOutputStream out = new FileOutputStream(installation);
            String id = UUID.randomUUID().toString();
            out.write(id.getBytes());
            out.close();
        }

    }
}
