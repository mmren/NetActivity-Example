package base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.netactivity.app.CoreFragment;
import com.netactivity.app.WindowUtils;
import com.netactivity.net.NetBean;
import com.android.volley.Response;

/**
 * Created by renmingming on 2016/10/25.
 *
 */

public abstract class BaseFragment extends CoreFragment {
    public WindowUtils mWindowUtils;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FindFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static <T extends BaseFragment> BaseFragment newInstance(Class<T> t, String param1, String param2) throws IllegalAccessException, InstantiationException, java.lang.InstantiationException {
        T fragment = t.newInstance();
        Bundle args = new Bundle();
        if (param1 != null)
            args.putString(ARG_PARAM1, param1);
        if (param2 != null)
            args.putString(ARG_PARAM2, param2);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if(getActivity() instanceof BaseActivity)
        mWindowUtils = ((BaseActivity) getActivity()).getWindowUtils();

        super.onCreate(savedInstanceState);

    }


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    protected void getData() {

    }

    @Override
    public <D extends NetBean> boolean doAfterErrorImpl(String urlTag, D data) {
        if (data instanceof BaseBean)
        return doAfterErrorImplForBase(urlTag, (BaseBean) data);

        else
            return false;
    }

    @Override
    public <D extends NetBean> void doAfterSuccessImpl(String urlTag, D data) {
        if (data instanceof BaseBean)
        doAfterSuccessImplForBase(urlTag, (BaseBean) data);
    }

    public abstract <D extends BaseBean> boolean doAfterErrorImplForBase(String urlTag, D data);

    public abstract <D extends BaseBean> void doAfterSuccessImplForBase(String urlTag, D data);

    @Override
    public Response.ErrorListener errorListener(String urlTag, boolean isNeedShowDialog) {
        return null;
    }

    @Override
    public boolean doAfterError(String urlTag) {
        return false;
    }

    @Override
    public void showLoginConfirm(String content, int backtohome) {

    }

    @Override
    public void cancelRequests() {

    }

    @Override
    public void goToLogin() {

    }

    @Override
    public void goToHome() {

    }


}
