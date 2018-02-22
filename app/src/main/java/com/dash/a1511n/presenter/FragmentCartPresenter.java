package com.dash.a1511n.presenter;

import com.dash.a1511n.model.CartModel;
import com.dash.a1511n.model.bean.CartBean;
import com.dash.a1511n.presenter.inter.CartPresenterInter;
import com.dash.a1511n.view.Iview.FragmentCartInter;
import com.dash.a1511n.view.fragment.FragmentShoppingCart;

/**
 * Created by Dash on 2018/1/30.
 */
public class FragmentCartPresenter implements CartPresenterInter {

    private FragmentCartInter fragmentCartInter;
    private CartModel cartModel;

    public FragmentCartPresenter(FragmentCartInter fragmentCartInter) {
        this.fragmentCartInter = fragmentCartInter;

        cartModel = new CartModel(this);
    }

    public void getCartData(String selectCart, String uid) {

        cartModel.getCartData(selectCart,uid);

    }

    @Override
    public void getCartDataNull() {
        fragmentCartInter.getCartDataNull();
    }

    @Override
    public void getCartDataSuccess(CartBean cartBean) {
        fragmentCartInter.getCartDataSuccess(cartBean);
    }
}
