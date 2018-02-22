package com.dash.a1511n.util;

/**
 * Created by Dash on 2018/1/23.
 *
 * 专门存放所有的路径的类,,,全都是静态变量,,,直接类名.调用
 */
public class ApiUtil {

    private static final String BASE_URL = "https://www.zhaoapi.cn/";
    //首页
    public static final String HOME_URL = BASE_URL+"ad/getAd";
    //分类
    public static final String FEN_LEI_URL = BASE_URL+"product/getCatagory";
    //详情
    public static final String DETAIL_URL = BASE_URL+"product/getProductDetail";
    //子分类
    public static final String CHILD_FEN_LEI_URL = BASE_URL+"product/getProductCatagory";
    //搜索
    public static final String SEARTCH_URL = BASE_URL+"product/searchProducts";

    //登录
    public static final String LOGIN_URL = BASE_URL+"user/login";
    //查询购物车
    public static final String SELECT_CART = BASE_URL+"product/getCarts";
    //更新购物车 product/updateCarts?uid=71&sellerid=1&pid=1&selected=0&num=10
    public static final String UPDATE_CART_URL = BASE_URL+"product/updateCarts";
    //删除购物车...product/deleteCart?uid=72&pid=1
    public static final String DELETE_CART_URL = BASE_URL+"product/deleteCart";
    //添加购物车...uid...pid
    public static final String ADD_CART_URL = BASE_URL+"product/addCart";
    //注册
    public static final String REGIST_URL = BASE_URL+"user/reg";

}
