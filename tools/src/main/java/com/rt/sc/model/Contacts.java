package com.rt.sc.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * 通讯录
 * 
 * @author  renmingming
 * @version  [版本号, 2015年8月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Contacts implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 249697169066810508L;
    
    /**
     * 联系人姓名
     */
    private String name;
    /**
     * 联系人拼音
     */
    private String pinying;
    
    /**
     * 联系人邮箱
     */
    private final ArrayList<String> emailList = new ArrayList<String>();
    
    /**
     * 联系人手机号码
     */
    private final ArrayList<String> phoneList = new ArrayList<String>();
    
    public Contacts()
    {
        super();
    }

    public Contacts(String name, String pinying, String email)
    {
        super();
        this.name = name;
        this.pinying = pinying;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPinying()
    {
        return pinying;
    }

    public void setPinying(String pinying)
    {
        this.pinying = pinying;
    }

    public ArrayList<String> getEmailList()
    {
        return emailList;
    }


    public ArrayList<String> getPhoneList()
    {
        return phoneList;
    }

    @Override
    public String toString()
    {
        return "Contacts [name=" + name + ", pinying=" + pinying + ", emailList=" + emailList + ", phoneList="
            + phoneList + "]";
    }
    
    
    
}
