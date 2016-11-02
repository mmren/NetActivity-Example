package com.rt.sc.tools;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

/**
 * Created by renmingming on 15/9/15.
 */

public class PackageUtils
{
    private static final String TAG = "PackageUtils";

    private static final boolean DEBUG = false;

    private static PackageInfo info = null;

    private static PackageManager pm;

    public PackageUtils(Context context)
    {
        initialize(context);
    }

    private static void initialize(Context context)
    {
        pm = context.getPackageManager();
        try
        {
            info = pm.getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static int getLocalVersionCode(Context context)
    {
        if (info == null)
        {
            initialize(context);
        }
        return info != null ? info.versionCode : Integer.MAX_VALUE;
    }

    /**
     * 获取当前版本的名称
     *
     * @param context
     * @return
     */
    public static String getLocalVersionName(Context context)
    {
        if (info == null)
        {
            initialize(context);
        }
        return info != null ? info.versionName : "";
    }

    public static String getAppName(Context context)
    {
        if (info == null)
        {
            initialize(context);
        }
        return info != null ? (String) info.applicationInfo.loadLabel(pm) : "";
    }

    public static String getPackageName(Context context)
    {
        if (info == null)
        {
            initialize(context);
        }
        return info != null ? info.packageName : "";
    }

    public static int getAppIcon(Context context)
    {
        if (info == null)
        {
            initialize(context);
        }
        return info != null ? info.applicationInfo.icon : android.R.drawable.ic_dialog_info;
    }
    /***
     * 获取meta信息
     *
     * @param context
     * @return
     */
    public static Bundle getAppMetaData(Context context)
    {
        ApplicationInfo appInfo = null;
        try
        {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData;
        } catch (NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static ActivityInfo getActivityInfo(Activity activity)
    {
        try
        {
            PackageManager pm = activity.getPackageManager();
            ComponentName componentName = activity.getComponentName();
            return pm.getActivityInfo(componentName, 0);
        } catch (NameNotFoundException e)
        {
            return null;
        }
    }
}
