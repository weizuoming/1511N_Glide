package com.dash.a1511n.model;

import android.util.Log;

import com.dash.a1511n.model.bean.FenLeiBean;
import com.dash.a1511n.model.bean.HomeBean;
import com.dash.a1511n.presenter.FragmentHomeP;
import com.dash.a1511n.presenter.inter.FragmentHomePInter;
import com.dash.a1511n.util.CommonUtils;
import com.dash.a1511n.util.OkHttp3Util_03;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Dash on 2018/1/23.
 */
public class FragmentHomeModel {

    private FragmentHomePInter fragmentHomePInter;

    //接收p的时候需要使用接口形式进行接收....
    public FragmentHomeModel(FragmentHomePInter fragmentHomePInter) {
        this.fragmentHomePInter = fragmentHomePInter;
    }

    public void getData(String homeUrl) {

        //使用okhttp获取数据
        //第一个参数是post的路径..第二个参数params是需要传递的参数,如果没有只需要传一个map...第三个参数回调
        Map<String, String> params = new HashMap<>();

        OkHttp3Util_03.doPost(homeUrl, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            //onResponse回调的是在子线程...如果要设置数据的显示需要发送的主线程中
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //响应成功
                if (response.isSuccessful()){
                    //拿到响应的字符串...json
                    String json = response.body().string();//1.该方法是要在子线程中执行..2.该方法只能执行一次,,,再次获取是null(http协议特点就是一次请求一次响应)
                    //解析json
                    final HomeBean homeBean = new Gson().fromJson(json, HomeBean.class);

                    //onResponse回调的是在子线程...需要发送到主线程
                    CommonUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            //写在这里面的代码就已经运行到主线程中了...view层里面就不在需要写runOnUiThread方法了!!!!

                            //在这里进行数据的回调
                            fragmentHomePInter.onSuccess(homeBean);
                        }
                    });

                }
            }
        });

    }

    public void getFenLeiData(String fenLeiUrl) {

        Map<String, String> params = new HashMap<>();


        OkHttp3Util_03.doPost(fenLeiUrl, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    //3.当获取服务器的数据小于1M的时候,,,这个时候可以使用这种方式
                    //但是当大于1M的时候,,InputStream inputStream = response.body().byteStream();,,,,,因为这个方法是把数据直接加到内存中去,如果过大会内存溢出
                    String json = response.body().string();

                    //解析
                    final FenLeiBean fenLeiBean = new Gson().fromJson(json,FenLeiBean.class);
                    //回调给主线程
                    CommonUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            fragmentHomePInter.onFenLeiDataSuccess(fenLeiBean);
                        }
                    });

                }
            }
        });

    }
}
