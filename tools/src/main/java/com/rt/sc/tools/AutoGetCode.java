package com.rt.sc.tools;

import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hanweipeng on 2016/5/13.
 */
public class AutoGetCode extends ContentObserver {
    private Cursor cursor = null;
    private Activity activity;

//    private String smsContent = "";
    private EditText editText = null;
    private String patternCoder = "(?<!\\d)\\d{6}(?!\\d)";

    public AutoGetCode(Activity activity, Handler handler, EditText edittext) {
        super(handler);
        this.activity = activity;
        this.editText = edittext;
    }
    public AutoGetCode(Activity activity, Handler handler){
        super(handler);
        this.activity = activity;
    }

    public void setCodeEdt(EditText edittext)
    {
        this.editText = edittext;
    }
    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        // 读取收件箱中指定号码的短信
        if (editText==null){
            return;
        }
        try {
            cursor = activity.managedQuery(Uri.parse("content://sms/inbox"),
                    new String[]{"_id", "address", "read", "body"}, "read=?",
                    new String[]{"0"}, "_id desc");
            // 按短信id排序，如果按date排序的话，修改手机时间后，读取的短信就不准了
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                if (cursor.moveToFirst()) {
                    String smsbody = cursor
                            .getString(cursor.getColumnIndex("body"));
//                String regEx = "(?<![0-9])([0-9]{" + 6 + "})(?![0-9])";
//                Pattern p = Pattern.compile(regEx);
//                Matcher m = p.matcher(smsbody.toString());
                    String code = patternCode(smsbody);
                    if (editText!=null){
                        editText.setText(code);
                    }
//                while (m.find()) {
//                    smsContent = m.group();
//                    editText.setText(smsContent);
//                }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (cursor!=null){
                cursor.close();
            }
        }
    }

    /**
     * 匹配短信中间的6个数字（验证码等）
     *
     * @param patternContent
     * @return
     */
    private String patternCode(String patternContent) {
        if (StringUtils.isEmpty(patternContent)) {
            return null;
        }
        Pattern p = Pattern.compile(patternCoder);
        Matcher matcher = p.matcher(patternContent);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
}
