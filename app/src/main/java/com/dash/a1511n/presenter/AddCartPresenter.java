package com.dash.a1511n.presenter;

import com.dash.a1511n.model.AddCartModel;
import com.dash.a1511n.model.bean.AddCartBean;
import com.dash.a1511n.presenter.inter.AddCartPresenterInter;
import com.dash.a1511n.view.Iview.ActivityAddCartInter;
import com.dash.a1511n.view.activity.DetailActivity;

/**
 * Created by Dash on 2018/2/1.
 */
public class AddCartPresenter implements AddCartPresenterInter {

    private ActivityAddCartInter activityAddCartInter;
    private AddCartModel addCartModel;

    public AddCartPresenter(ActivityAddCartInter activityAddCartInter) {
        this.activityAddCartInter = activityAddCartInter;

        addCartModel = new AddCartModel(this);
    }

    public void addToCart(String addCart, String uid, int pid) {

        addCartModel.addToCart(addCart,uid,pid);

    }

    @Override
    public void onCartAddSuccess(AddCartBean addCartBean) {
        activityAddCartInter.onCartAddSuccess(addCartBean);
    }
}
