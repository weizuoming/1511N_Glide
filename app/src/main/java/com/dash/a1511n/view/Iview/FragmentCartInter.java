package com.dash.a1511n.view.Iview;

import com.dash.a1511n.model.bean.CartBean;

/**
 * Created by Dash on 2018/1/30.
 */
public interface FragmentCartInter {
    void getCartDataNull();

    void getCartDataSuccess(CartBean cartBean);
}
