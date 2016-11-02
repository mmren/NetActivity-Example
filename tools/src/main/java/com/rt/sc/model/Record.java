package com.rt.sc.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 通话记录
 * <功能详细描述>
 * 
 * @author  renmingming
 * @version  [版本号, 2015年8月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Record implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 712163582093858811L;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 联系人
     */
    private String name;
    /**
     * 通话类型
     */
    private String type;
    /**
     * 通话时长
     */
    private long duration;
    /**
     * 通话时间
     */
    private Date date;
    
    public Record()
    {
        super();
    }
    public Record(String phone, String name, String type, long duration, Date date)
    {
        super();
        this.phone = phone;
        this.name = name;
        this.type = type;
        this.duration = duration;
        this.date = date;
    }
    public String getPhone()
    {
        return phone;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public long getDuration()
    {
        return duration;
    }
    public void setDuration(long duration)
    {
        this.duration = duration;
    }
    public Date getDate()
    {
        return date;
    }
    public void setDate(Date date)
    {
        this.date = date;
    }
    @Override
    public String toString()
    {
        return "Record [phone=" + phone + ", name=" + name + ", type=" + type + ", duration=" + duration + ", date="
            + date + "]";
    }
    
    
    
    
}
