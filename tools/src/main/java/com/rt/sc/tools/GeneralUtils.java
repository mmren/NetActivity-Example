package com.rt.sc.tools;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.DisplayMetrics;

/**
 * 工具类
 */
public class GeneralUtils
{
    /**
     * <打电话>
     */
    public static void setTel(Context context, String tel)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + tel));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     *
     * <获取屏幕尺寸>
     * <功能详细描述>
     * @param context
     * @return DisplayMetrics  displayMetrics.heightPixels 高度  displayMetrics.widthPixels 宽度
     * @see [类、类#方法、类#成员]
     */
    public static DisplayMetrics getScreenDisplay(Context context)
    {
        return context.getResources().getDisplayMetrics();
    }
}
