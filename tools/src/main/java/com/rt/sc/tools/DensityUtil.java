package com.rt.sc.tools;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

public class DensityUtil
{

    private static TypedValue mTmpValue = new TypedValue();


    public static int getXmlDef(Context context, int id){
        synchronized (mTmpValue) {
            TypedValue value = mTmpValue;
            context.getResources().getValue(id, value, true);
            return (int)TypedValue.complexToFloat(value.data);
        }
    }
    
    /**
     * 
     * <将px值转换为dip或dp值，保证尺寸大小不变>
     * <功能详细描述>
     * @param context
     * @param pxValue   DisplayMetrics类中属性density
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int px2dip(Context context, float pxValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }
    
    /**
     * 
     * <将dip或dp值转换为px值，保证尺寸大小不变>
     * <功能详细描述>
     * @param context
     * @param dipValue  DisplayMetrics类中属性density
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int dip2px(Context context, float dipValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }
    
    /**
     * 
     * <将px值转换为sp值，保证文字大小不变>
     * <功能详细描述>
     * @param context
     * @param pxValue   DisplayMetrics类中属性scaledDensity
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int px2sp(Context context, float pxValue)
    {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(pxValue / fontScale + 0.5f);
    }
    
    /**
     * 
     * <将sp值转换为px值，保证文字大小不变>
     * <功能详细描述>
     * @param context
     * @param spValue   DisplayMetrics类中属性scaledDensity
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int sp2px(Context context, float spValue)
    {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(spValue * fontScale + 0.5f);
    }
    
    public static int getWidth(Context context)
    {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }
    
    public static int getHeight(Context context)
    {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }
    
    /**
     * 
     * <获取屏幕像素密度比>
     * <功能详细描述>
     * @param context
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static float getDensityRatio(Context context)
    {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        float density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）  
        return density;
    }
    
}
