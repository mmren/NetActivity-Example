package com.rt.sc.tools;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.rt.sc.R;


/**
 * 倒计时
 */
public class MyDownChangeTime extends CountDownTimer
{
    private TextView tvCode;

    private Context context;

    private long surplusMillis;

    int left, right, top, bottom;

    public MyDownChangeTime(long millisInFuture, long countDownInterval, Context context, TextView tvCode)
    {
        super(millisInFuture, countDownInterval);
        this.tvCode = tvCode;
        this.context = context;
        surplusMillis=0L;
        left = tvCode.getPaddingLeft();
        right = tvCode.getPaddingRight();
        top = tvCode.getPaddingTop();
        bottom = tvCode.getCompoundPaddingBottom();
    }
    @Override
    public void onTick(long millisUntilFinished)
    {
        surplusMillis = millisUntilFinished;
        tvCode.setClickable(false);
        tvCode.setText(context.getResources().getString(R.string.register_get_again_later, (millisUntilFinished / 1000)));
//        tvCode.setBackgroundResource(R.drawable.register_code_grey_bg);
        tvCode.setPadding(left, top, right, bottom);
    }

    @Override
    public void onFinish()
    {
        tvCode.setClickable(true);
        tvCode.setText(context.getString(R.string.register_get_code));
//        tvCode.setBackgroundResource(R.drawable.selector_btn_register);
        tvCode.setPadding(left, top, right, bottom);
        cancel();
    }

    public long getsurplusMillis()
    {
        return surplusMillis;
    }

}
