package com.rt.sc.tools;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rt.sc.R;

/**
 * Created by renmingming on 15/9/10.
 *
 */
public class AddTitleBar {


    private View actionbarLayout;

    private Activity activity;

    public AddTitleBar(Activity activity) {
        this.activity = activity;
        inintTitleBar(activity);
    }



    public void inintTitleBar(Activity activity) {
        actionbarLayout = LayoutInflater.from(activity).inflate(R.layout.actionbar_layout, null);
        ViewGroup localViewGroup = (ViewGroup) activity.getWindow().getDecorView();
        localViewGroup.addView(actionbarLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void show() {
        actionbarLayout.setVisibility(View.VISIBLE);
    }

    public void hide() {
        actionbarLayout.setVisibility(View.GONE);
    }

    public void setHidOrShowRightBtn(boolean isShow) {
        if (isShow) {
            actionbarLayout.findViewById(R.id.right_imbt).setVisibility(View.VISIBLE);
        } else {
            actionbarLayout.findViewById(R.id.right_imbt).setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 设置右边按钮
     *
     * @param drawable
     * @param type        1,left 2 top 3 right 4 buttom
     * @param text
     * @param leftorRihgt 0 left 1 right
     */
    public void setTitleBtnDrawable(Drawable drawable, int type, String text, int leftorRihgt) {
        //去掉text

        TextView r_tv = null;
        if (0 == leftorRihgt) {
            text = "";
            r_tv = (TextView) actionbarLayout.findViewById(R.id.left_imbt);

        } else if (1 == leftorRihgt) {
            r_tv = (TextView) actionbarLayout.findViewById(R.id.right_imbt);
        }

        if (r_tv == null) {
            return;
        }

        r_tv.setVisibility(View.VISIBLE);

        if (text == null) {
            r_tv.setTextSize(0);
        } else if (drawable == null) {
            r_tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, activity.getResources().getDimensionPixelSize(R.dimen.actionBar_button_text_nomarl_size));
            r_tv.setText(text);
        } else {
            r_tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, activity.getResources().getDimensionPixelSize(R.dimen.actionBar_button_text_small_size));
            r_tv.setText(text);
        }

        if (drawable != null) {
//            int tv_W = actionBar.getHeight();
//            int tv_h = actionBar.getHeight();
            int d_w = drawable.getIntrinsicWidth();
            int d_h = drawable.getIntrinsicHeight();
            drawable.setBounds(0, 0, d_w, d_h);
            switch (type) {
                case 1:
                    r_tv.setCompoundDrawables(drawable, null, null, null);
                    break;
                case 2:
                    r_tv.setCompoundDrawables(null, drawable, null, null);
                    break;
                case 3:
                    r_tv.setCompoundDrawables(null, null, drawable, null);
                    break;
                case 4:
                    r_tv.setCompoundDrawables(null, null, null, drawable);
                    break;
            }
        }
        r_tv.setCompoundDrawablePadding(10);

    }


    /**
     * 隐藏显示返回键
     *
     * @param isShow 隐藏显示
     */
    public void setHidOrShowbackBtn(boolean isShow) {
        if (isShow) {
            actionbarLayout.findViewById(R.id.left_imbt).setVisibility(View.VISIBLE);
        } else {
            actionbarLayout.findViewById(R.id.left_imbt).setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        ((TextView) actionbarLayout.findViewById(R.id.title)).setText(title);
    }

    /**
     * 设置返回键监听
     */
    public void setBackUPListener(View.OnClickListener listener) {
        actionbarLayout.findViewById(R.id.left_imbt).setOnClickListener(listener);
    }

    public void setBackGroundRes(int resId) {
        actionbarLayout.setBackgroundResource(resId);
    }

    public void setTitleTextColor(int color) {
        ((TextView) (actionbarLayout.findViewById(R.id.title))).setTextColor(color);
    }

    /**
     * 设置右键点击事件
     */
    public void setLeftBtnClickListener(View.OnClickListener clickListener) {
        actionbarLayout.findViewById(R.id.left_imbt).setOnClickListener(clickListener);
    }

    /**
     * 设置右键是否可以点击
     */
    public void setRightBtnEnable(boolean isEnable) {
        if (isEnable) {
            actionbarLayout.findViewById(R.id.right_imbt).setEnabled(true);
        } else {
            actionbarLayout.findViewById(R.id.right_imbt).setEnabled(false);
        }
    }

    /**
     * @param clickListener 设置右键点击事件
     */
    public void setRightBtnClickListener(View.OnClickListener clickListener) {
        actionbarLayout.findViewById(R.id.right_imbt).setOnClickListener(clickListener);
    }

    public View getActionbarLayout() {
        return actionbarLayout;
    }


}
