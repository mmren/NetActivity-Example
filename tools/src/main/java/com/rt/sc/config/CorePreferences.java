package com.rt.sc.config;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.rt.sc.tools.DirUtils;
import com.rt.sc.tools.PackageUtils;

import java.io.File;

/**
 * Created by renmingming on 15/9/21.
 */
public class CorePreferences
{

    private static CorePreferences corePreferences = null;

    private static CoreConfig coreConfig;

    public static final String CACHEPATH = "cache";

    public static final String IMAGEPATH = "image";

    public static final String IMAGEPATH1 = "ImgCach";

    public static final String TEMPPATH = "temp";

    public static final String DOWNLOADPATH = "download";

    public static int avgpage = 20;

    public static Context ctx;

    public CorePreferences(Context context)
    {
        coreConfig = initConfig(context);
    }

    public static CorePreferences getInstance(Context context)
    {
        if (corePreferences == null)
        {
            return new CorePreferences(context);
        }
        return corePreferences;
    }

    public CoreConfig getCoreConfig()
    {
        return coreConfig;
    }

    /***
     * 获取Manifest.xml中配置的meta
     *
     * @param context
     * @return
     */
    public CoreConfig initConfig(Context context)
    {
        ctx = context;
        if (coreConfig == null)
        {
            Bundle metaBundle = PackageUtils.getAppMetaData(context);
            if (metaBundle != null)
            {
                coreConfig = new CoreConfig();
                coreConfig.setAppName(metaBundle.getString("app_name"));
                coreConfig.setAppTag(metaBundle.getString("app_tag"));
                coreConfig.setOpenBaiduStat(metaBundle.getBoolean("app_baidustat", false));

                coreConfig.setAppBaiduMapKey(metaBundle.getString("app_baidumapkey"));
                coreConfig.setDebug(metaBundle.getBoolean("app_isdebug", true));
                coreConfig.setAnalyse(metaBundle.getBoolean("app_isanalyse",false));
                if (coreConfig.isAnalyse())
                {
                    coreConfig.setAnalyseChannel(String.valueOf(metaBundle.get("TD_CHANNEL_ID")));
                }
                coreConfig.setDefaultCity(metaBundle.getString("app_default_city"));
            }
        }
        return coreConfig;
    }

    public static final String getAppSDPath(String name)
    {
        File file = DirUtils.getDiskCacheDir(ctx, name);
        if (!file.exists())
        {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public static final String getAppSDPath()
    {
        File file = DirUtils.getDiskCacheDir(ctx);
        if (!file.exists())
        {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /***
     * @return
     */
    public static final String getAppCacheSDPath()
    {
        File file = new File(getAppSDPath(), CACHEPATH);
        if (!file.exists())
        {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /***
     * @return
     */
    public static final String getAppTmpSDPath()
    {
        File file = new File(getAppSDPath(), TEMPPATH);
        if (!file.exists())
        {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /***
     * @return
     */
    public static final String getAppDownloadSDPath()
    {
        File file = new File(getAppSDPath(), DOWNLOADPATH);
        if (!file.exists())
        {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /***
     * @return
     */
    public static final String getAppImageSDPath()
    {
        File file = new File(getAppSDPath(), IMAGEPATH);
        if (!file.exists())
        {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public static final String getAppImageSDPath1()
    {
        File file = new File(getAppSDPath(), IMAGEPATH1);
        if (!file.exists())
        {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public static final String getAppApkFile()
    {
        return getAppSDPath() + "/" + coreConfig.getAppTag() + "_update.apk";
    }

    /**
     * 获取app自定义子目录路径
     *
     * @param subDir
     * @return
     */
    public static final String getAppSDPathWithSubDir(String subDir)
    {
        File file = new File(getAppSDPath(), subDir);
        if (!file.exists())
        {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /***
     * @param s
     */
    public static final void DEBUG(String s)
    {
        if (coreConfig.isDebug())
        {
            Log.i(coreConfig.getAppTag(), s);
        }
    }

    public static final void ERROR(Throwable e)
    {
        if (coreConfig.isDebug())
        {
            Log.e(coreConfig.getAppTag(), e.getMessage(), e);
        }
    }

    public static final void ERROR(String s)
    {
        if (coreConfig.isDebug())
        {
            Log.e(coreConfig.getAppTag(), s);
        }
    }

    public static final void ERROR(String s, Throwable e)
    {
        if (coreConfig.isDebug())
        {
            Log.e(coreConfig.getAppTag(), s, e);
        }
    }

    public static int getAvgpage()
    {
        return avgpage;
    }

    public static void setAvgpage(int avgpage)
    {
        CorePreferences.avgpage = avgpage;
    }

    public static boolean isDebug()
    {
        return coreConfig.isDebug();
    }

    public static String getAnalyseChannel()
    {
        return coreConfig.getAnalyseChannel();
    }
}
