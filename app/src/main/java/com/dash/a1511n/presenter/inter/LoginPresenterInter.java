package com.dash.a1511n.presenter.inter;

import com.dash.a1511n.model.bean.LoginBean;

/**
 * Created by Dash on 2018/1/30.
 */
public interface LoginPresenterInter {

    void onSuccess(LoginBean loginBean);


    void onSuccessByQQ(LoginBean loginBean, String ni_cheng, String iconurl);
}
