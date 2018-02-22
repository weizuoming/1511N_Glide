package com.dash.a1511n.presenter;

import com.dash.a1511n.model.LoginModel;
import com.dash.a1511n.model.bean.LoginBean;
import com.dash.a1511n.presenter.inter.LoginPresenterInter;
import com.dash.a1511n.view.Iview.LoginActivityInter;
import com.dash.a1511n.view.activity.LoginActivity;

/**
 * Created by Dash on 2018/1/30.
 */
public class LoginPresenter implements LoginPresenterInter {

    private LoginModel loginModel;
    private LoginActivityInter loginActivityInter;

    public LoginPresenter(LoginActivityInter loginActivityInter) {
        this.loginActivityInter = loginActivityInter;

        loginModel = new LoginModel(this);
    }
    //处理逻辑用的...判断
    public void getLogin(String loginUrl, String phone, String pwd) {

        loginModel.getLogin(loginUrl,phone,pwd);

    }

    @Override
    public void onSuccess(LoginBean loginBean) {
        loginActivityInter.getLoginSuccess(loginBean);
    }

    @Override
    public void onSuccessByQQ(LoginBean loginBean, String ni_cheng, String iconurl) {
        loginActivityInter.getLoginSuccessByQQ(loginBean,ni_cheng,iconurl);
    }

    public void getLoginByQQ(String phone, String pwd, String ni_cheng, String iconurl) {

        loginModel.getLoginByQQ(phone,pwd,ni_cheng,iconurl);

    }
}
