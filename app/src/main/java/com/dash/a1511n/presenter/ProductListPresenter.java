package com.dash.a1511n.presenter;

import com.dash.a1511n.model.ProductListModel;
import com.dash.a1511n.model.bean.ProductListBean;
import com.dash.a1511n.presenter.inter.ProductListPresenterInter;
import com.dash.a1511n.view.Iview.ProductListActivityInter;
import com.dash.a1511n.view.activity.ProductListActivity;

/**
 * Created by Dash on 2018/1/26.
 */
public class ProductListPresenter implements ProductListPresenterInter {

    private ProductListModel productListModel;
    private ProductListActivityInter productListActivityInter;

    public ProductListPresenter(ProductListActivityInter productListActivityInter) {
        this.productListActivityInter = productListActivityInter;

        productListModel = new ProductListModel(this);
    }

    public void getProductData(String seartchUrl, String keywords, int page) {

        productListModel.getProductData(seartchUrl,keywords,page);
    }

    @Override
    public void onSuccess(ProductListBean productListBean) {
        productListActivityInter.getProductDataSuccess(productListBean);
    }
}
