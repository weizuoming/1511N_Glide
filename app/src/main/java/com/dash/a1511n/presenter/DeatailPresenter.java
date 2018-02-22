package com.dash.a1511n.presenter;

import com.dash.a1511n.model.DeatilModel;
import com.dash.a1511n.model.bean.DeatilBean;
import com.dash.a1511n.presenter.inter.DeatilPresenterInter;
import com.dash.a1511n.view.Iview.DetailActivityInter;
import com.dash.a1511n.view.activity.DetailActivity;

/**
 * Created by Dash on 2018/1/24.
 */
public class DeatailPresenter implements DeatilPresenterInter{

    private DeatilModel deatilModel;
    private DetailActivityInter detailActivityInter;

    public DeatailPresenter(DetailActivityInter detailActivityInter) {
        this.detailActivityInter = detailActivityInter;

        deatilModel = new DeatilModel(this);

    }

    public void getDetailData(String detailUrl, int pid) {

        deatilModel.getDetailData(detailUrl,pid);
    }

    @Override
    public void onSuccess(DeatilBean deatilBean) {
        //回调给view
        detailActivityInter.onSuccess(deatilBean);
    }
}
