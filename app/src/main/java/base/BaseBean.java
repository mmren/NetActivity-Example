package base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.netactivity.net.NetBean;
import com.rt.sc.tools.LogUtil;

import java.io.Serializable;

/**
 * Created by renmingming on 2016/10/24.
 */

public class BaseBean extends NetBean implements Serializable
{

    private static final long serialVersionUID = 1L;

    /**
     * 服务器返回成功,且处理正常.
     */
    public static final String OK = "000000";
//
    /**
     * 服务器返回错误.
     */
    public static final String ERROR = "000001";
    /**
     * 还款中
     */
    public static final int REFUNDFAILED =000003;

    /**
     * token 被挤下线
     */
    public static final String PASTDUE = "000002";

    /**
     * token 过期
     */
    public static final String TOAKENEXPIRE="000004";



    @SerializedName("retcode")
    @Expose
    private String retcode;

    @SerializedName("retinfo")
    @Expose
    private String retinfo;

    @SerializedName("token")
    @Expose
    private String token;

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    @Override
    public String getRetinfo() {
        return retinfo;
    }

    public void setRetinfo(String retinfo) {
        this.retinfo = retinfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void saveToke()
    {
        // TODO: 2016/11/1
//        if (isSuccess())
//        {
//            if (this instanceof CreditInfoBean)
//            {
//                UserProfile.setUserAccount((CreditInfoBean) this);
//            }
//            if (!StringUtils.isEmpty(token))
//                UserProfile.setmToken(token);
//        }
    }

    @Override
    public boolean isJumpLogin()
    {
        try
        {
            if (retcode.equals(TOAKENEXPIRE))
            {
                return true;
            }
        } catch (Exception e)
        {
            LogUtil.e("BaseBean", e.toString());
        }
        return false;
    }

    @Override
    public boolean isExpire() {
        try
        {
            if (retcode.equals(PASTDUE))
            {
                return true;
            }
        } catch (Exception e)
        {
            LogUtil.e("BaseBean", e.toString());
        }
        return false;
    }

    @Override
    public String toString() {

        return "BaseBean{" +
                "retcode='" + retcode + '\'' +
                ", retinfo='" + retinfo + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    @Override
    public boolean isSuccess() {
        // TODO: 2016/11/1
//        try
//        {
//            if ("JXL0000".equals(retcode)||"JXL1000".equals(retcode)||retcode.equals(OK))
//            {
//                if (this instanceof CreditInfoBean)
//                {
//                    UserProfile.setUserAccount((CreditInfoBean) this);
//                }
//                if (!StringUtils.isEmpty(token))
//                    UserProfile.setmToken(token);
//                return true;
//            }
//        } catch (Exception e)
//        {
//            LogUtil.e("BaseBean", e.toString());
//        }
        return false;
    }

    @Override
    public boolean isFailed() {
        try
        {
            if (!retcode.equals(OK)&&!retcode.equals("JXL0000")&&!retcode.equals("JXL1000")
                    &&!retcode.equals("000002")&&!retcode.equals("000003")&&!retcode.equals("000004"))
            {
                return true;
            }
        } catch (Exception e)
        {
            LogUtil.e("BaseBean", e.toString());
        }
        return false;
    }
}
