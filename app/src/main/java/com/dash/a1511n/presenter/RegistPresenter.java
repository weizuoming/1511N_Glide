package com.dash.a1511n.presenter;

import com.dash.a1511n.model.RegistModel;
import com.dash.a1511n.model.bean.RegistBean;
import com.dash.a1511n.presenter.inter.RegistPresenterInter;
import com.dash.a1511n.view.Iview.RegistActivityInter;
import com.dash.a1511n.view.activity.RegistActivity;

/**
 * Created by Dash on 2018/2/2.
 */
public class RegistPresenter implements RegistPresenterInter {

    private RegistActivityInter registActivityInter;
    private RegistModel registModel;

    public RegistPresenter(RegistActivityInter registActivityInter) {
        this.registActivityInter = registActivityInter;
        registModel = new RegistModel(this);
    }

    public void registUser(String registUrl, String name, String pwd) {

        registModel.registUser(registUrl,name,pwd);
    }

    @Override
    public void onRegistSuccess(RegistBean registBean) {
        registActivityInter.onRegistSuccess(registBean);
    }
}
