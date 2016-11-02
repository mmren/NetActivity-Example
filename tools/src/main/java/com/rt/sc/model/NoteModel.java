package com.rt.sc.model;

import java.io.Serializable;

/**
 * 
 * 短信
 * 
 * @author  renmingming
 * @version  [版本号, 2015年8月4日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */

public class NoteModel implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 2844658929256516715L;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 姓名
     */
    private String person;
    
    /**
     * 内容
     */
    private String body;
    
    /**
     * 日期
     */
    private String date;
    
    /**
     * 类型
     */
    private TYPE type;
    
    
    
    public enum TYPE
    {
        receive(1), send(2), draft(3), sendbox(4), sendfaild(5), waitsend(6), all(0);
        @SuppressWarnings("unused")
        public int code;
        TYPE(int code)
        {
            this.code = code;
        }
        
    };
    
    public NoteModel(String phone, String person, String body, String date, TYPE type)
    {
        super();
        this.phone = phone;
        this.person = person;
        this.body = body;
        this.date = date;
        this.type = type;
    }

    public NoteModel()
    {
        super();
    }
    
    public String getPhone()
    {
        return phone;
    }
    
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    public String getPerson()
    {
        return person;
    }
    
    public void setPerson(String person)
    {
        this.person = person;
    }
    
    public String getBody()
    {
        return body;
    }
    
    public void setBody(String body)
    {
        this.body = body;
    }
    
    public String getDate()
    {
        return date;
    }
    
    public void setDate(String date)
    {
        this.date = date;
    }

    public TYPE getType()
    {
        return type;
    }

    public void setType(TYPE type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "NoteModel [phone=" + phone + ", person=" + person + ", body=" + body + ", date=" + date + ", type="
            + type + "]";
    }
    
}
