package com.rt.sc.tools;

/**
 * Created by renmingming on 15/9/21.
 */
public class PermissionException extends Exception
{
    public PermissionException() {
        super();
        // TODO 自动生成的构造函数存根
    }

    /**
     * @param detailMessage
     * @param throwable
     */
    public PermissionException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    /**
     * @param detailMessage
     */
    public PermissionException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * @param throwable
     */
    public PermissionException(Throwable throwable) {
        super(throwable);
    }

    /**
     *
     */
    public PermissionException(SecurityException e) {
        this(e.getMessage(), e.getCause());
    }
}
