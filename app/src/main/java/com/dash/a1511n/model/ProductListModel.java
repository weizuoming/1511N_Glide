package com.dash.a1511n.model;

import com.dash.a1511n.model.bean.ProductListBean;
import com.dash.a1511n.presenter.ProductListPresenter;
import com.dash.a1511n.presenter.inter.ProductListPresenterInter;
import com.dash.a1511n.util.CommonUtils;
import com.dash.a1511n.util.OkHttp3Util_03;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Dash on 2018/1/26.
 */
public class ProductListModel {
    private ProductListPresenterInter productListPresenterInter;

    public ProductListModel(ProductListPresenterInter productListPresenterInter) {
        this.productListPresenterInter = productListPresenterInter;
    }

    public void getProductData(String seartchUrl, String keywords, int page) {

        Map<String, String> params = new HashMap<>();
        params.put("keywords",keywords);
        params.put("page", String.valueOf(page));

        OkHttp3Util_03.doPost(seartchUrl, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();

                    //解析
                    final ProductListBean productListBean = new Gson().fromJson(json,ProductListBean.class);

                    CommonUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            productListPresenterInter.onSuccess(productListBean);
                        }
                    });

                }
            }
        });

    }
}
