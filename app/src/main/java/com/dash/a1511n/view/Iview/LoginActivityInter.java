package com.dash.a1511n.view.Iview;

import com.dash.a1511n.model.bean.LoginBean;

/**
 * Created by Dash on 2018/1/30.
 */
public interface LoginActivityInter {

    void getLoginSuccess(LoginBean loginBean);


    void getLoginSuccessByQQ(LoginBean loginBean, String ni_cheng, String iconurl);
}
