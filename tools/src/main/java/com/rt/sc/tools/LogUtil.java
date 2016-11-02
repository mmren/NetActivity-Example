package com.rt.sc.tools;

import android.util.Log;

import com.rt.sc.config.CorePreferences;

/**
 * Created by renmingming on 15/9/30.
 */
public class LogUtil
{

    public static void i(String tag, String content)
    {
        if (CorePreferences.isDebug())
        {
            Log.i(tag, content);
        }
    }

    public static void d(String tag, String content)
    {
        if (CorePreferences.isDebug())
        {
            Log.d(tag, content);
        }
    }

    public static void v(String tag, String content)
    {
        if (CorePreferences.isDebug())
        {
            Log.v(tag, content);
        }
    }
    public static void e(String tag, String content)
    {
        if (CorePreferences.isDebug())
        {
            Log.e(tag, content);
        }
    }

    public static void w(String tag, String content)
    {
        if (CorePreferences.isDebug())
        {
            Log.w(tag, content);
        }
    }
}
