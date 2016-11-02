package com.rt.sc.tools;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;

/**
 * Created by renmingming on 15/9/21.
 */
public class CompatUtils
{

    private static final boolean DEBUG = false;
    private static final String TAG = "CompatUtils";

    public static boolean hasFroyo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * Workaround for bug pre-Froyo, see here for more info:
     * http://android-developers.blogspot.com/2011/09/androids-http-clients.html
     */
    public static void disableConnectionReuseIfNecessary() {
        // HTTP connection reuse which was buggy pre-froyo
        if (hasHttpConnectionBug()) {
            System.setProperty("http.keepAlive", "false");
        }
    }

    /**
     * Check if OS version has a http URLConnection bug. See here for more
     * information:
     * http://android-developers.blogspot.com/2011/09/androids-http-clients.html
     *
     * @return
     */
    public static boolean hasHttpConnectionBug() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO;
    }

    /**
     * Get the size in bytes of a bitmap.
     *
     * @param bitmap
     * @return size in bytes
     */
    @TargetApi(12)
    public static int getBitmapSize(Bitmap bitmap) {
        // if (Utils.hasHoneycombMR1()) {
        // return bitmap.getByteCount();
        // }
        // // Pre HC-MR1
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    /**
     * Get the size in bytes of a bitmap in a BitmapDrawable.
     *
     * @param value
     * @return size in bytes
     */
    @TargetApi(12)
    public static int getBitmapSize(BitmapDrawable value) {
        Bitmap bitmap = value.getBitmap();

        if (CompatUtils.hasHoneycombMR1()) {
            return bitmap.getByteCount();
        }
        // Pre HC-MR1
        return bitmap.getRowBytes() * bitmap.getHeight();
    }
}
