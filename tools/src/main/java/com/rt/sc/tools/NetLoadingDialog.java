package com.rt.sc.tools;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.rt.sc.R;


/**
 * <只控制显示或者不显示对话框，或者单纯的显示对话框没有线程>
 * <功能详细描述>
 *
 * @author wangtao
 * @version [版本号, Apr 18, 2014]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class NetLoadingDialog {

    private Dialog mDialog;

    private boolean isLDShow = false;

    private Context context;

    private NetLoadingDialog() {

    }

    private static NetLoadingDialog netLoadingDialog;


    public static NetLoadingDialog getInstance() {

        if (netLoadingDialog == null) {
            netLoadingDialog = new NetLoadingDialog();
        }
        return netLoadingDialog;
    }

    public Dialog loading(Context context, View content){
        return loading(context,content,true);
    }
    /**
     * <显示Dialog>
     * <功能详细描述>
     *
     * @see [类、类#方法、类#成员]
     */
    public Dialog loading(Context context, View content, final boolean canBackDismiss) {
        this.context = context;
        try {
            if (isLDShow) {
                hideLoadingDialog();
            }
            createDialog(content);
            mDialog.show();
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0)
                    {
                        if (canBackDismiss)
                        {
                            hideLoadingDialog();
                        }
                    }
                    return true;

                }
            });
            isLDShow = true;
            return  mDialog;
        } catch (Exception e) {
            if (isLDShow && mDialog != null) {
                hideLoadingDialog();
            }
        }
        return  mDialog;
    }

    /**
     * <隐藏对话框>
     * <功能详细描述>
     *
     * @see [类、类#方法、类#成员]
     */
    private void hideLoadingDialog() {
        isLDShow = false;
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    /**
     * <创建对话框>
     * <功能详细描述>
     *
     * @see [类、类#方法、类#成员]
     */
    private void createDialog(View content) {
        mDialog = null;
        mDialog = new Dialog(context, R.style.loading_dialog);
        mDialog.setOnDismissListener(onDismissListener);
        if (content == null) {
            content = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null);
        }
        mDialog.setContentView(content);
    }

    /**
     * <dismiss Dialog>
     * <功能详细描述>
     *
     * @see [类、类#方法、类#成员]
     */
    public void dismissDialog() {
        hideLoadingDialog();
    }

    private OnDismissListener onDismissListener = new OnDismissListener() {

        @Override
        public void onDismiss(DialogInterface dialog) {


        }
    };

}
