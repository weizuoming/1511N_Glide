package com.dash.a1511n.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dash.a1511n.R;
import com.dash.a1511n.model.bean.FenLeiBean;
import com.dash.a1511n.model.bean.HomeBean;
import com.dash.a1511n.presenter.FragmentHomeP;
import com.dash.a1511n.util.ApiUtil;
import com.dash.a1511n.util.CommonUtils;
import com.dash.a1511n.util.GlideImgManager;
import com.dash.a1511n.view.Iview.InterFragmentHome;
import com.dash.a1511n.view.Iview.OnItemListner;
import com.dash.a1511n.view.activity.DetailActivity;
import com.dash.a1511n.view.activity.LoginActivity;
import com.dash.a1511n.view.activity.MyOrderActivity;
import com.dash.a1511n.view.activity.UserSettingActivity;
import com.dash.a1511n.view.adapter.TuiJianAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * Created by Dash on 2018/1/23.
 *
 * 当前页面对用户可见的时候...判断是否登录
 */
public class FragmentMy extends Fragment implements InterFragmentHome, View.OnClickListener {

    private RecyclerView tui_jian_recycler;
    private FragmentHomeP fragmentHomeP;
    private LinearLayout my_linear_login;
    private ImageView my_user_icon;
    private TextView my_user_name;
    private LinearLayout my_order_dfk;
    private LinearLayout my_order_dpj;
    private LinearLayout my_order_dsh;
    private LinearLayout my_order_th;
    private LinearLayout my_order_all;
    private ScrollView fragment_my_scroll;
    private RelativeLayout login_back_pic;
    private SmartRefreshLayout smart_refresh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_my_layout,container,false);

        tui_jian_recycler = view.findViewById(R.id.tui_jian_recycler);
        my_linear_login = view.findViewById(R.id.my_linear_login);
        my_user_icon = view.findViewById(R.id.my_user_icon);
        my_user_name = view.findViewById(R.id.my_user_name);
        my_order_dfk = view.findViewById(R.id.my_order_dfk);
        my_order_dpj = view.findViewById(R.id.my_order_dpj);
        my_order_dsh = view.findViewById(R.id.my_order_dsh);
        my_order_th = view.findViewById(R.id.my_order_th);
        my_order_all = view.findViewById(R.id.my_order_all);
        fragment_my_scroll = view.findViewById(R.id.fragment_my_scroll);
        login_back_pic = view.findViewById(R.id.login_back_pic);
        smart_refresh = view.findViewById(R.id.smart_refresh);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tui_jian_recycler.setFocusable(false);

        //2.为你推荐,,,只需要获取一次
        fragmentHomeP = new FragmentHomeP(this);
        //调用p层里面的方法....https://www.zhaoapi.cn/ad/getAd
        fragmentHomeP.getNetData(ApiUtil.HOME_URL);

        //设置点击事件
        my_linear_login.setOnClickListener(this);
        my_order_dfk.setOnClickListener(this);
        my_order_dpj.setOnClickListener(this);
        my_order_dsh.setOnClickListener(this);
        my_order_th.setOnClickListener(this);

        smart_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //延时多久结束刷新
                smart_refresh.finishRefresh(3000);
            }
        });
        smart_refresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                smart_refresh.finishLoadmore(3000);
            }
        });
    }

    /**
     * 只要对用户可见,,,就要调用
     */
    private void initData() {
        //判断一下是否登录..,..当登录成功之后,先存一下boolean值,,,在这里取出来判断
        boolean isLogin = CommonUtils.getBoolean("isLogin");

        if (isLogin) {
            if ( "".equals(CommonUtils.getString("iconUrl"))  || "null".equals(CommonUtils.getString("iconUrl"))){
                //显示默认头像
                my_user_icon.setImageResource(R.drawable.user);
            }else {

                //1.加载一下头像显示...判断一下是否有头像的路径,,,没有则显示默认的头像
                //Glide.with(getActivity()).load(CommonUtils.getString("iconUrl")).into(my_user_icon);

                //圆形
                GlideImgManager.glideLoader(getActivity(), CommonUtils.getString("iconUrl"), R.drawable.user, R.drawable.user, my_user_icon, 0);
            }
            //2.先试一下用户名
            my_user_name.setText(CommonUtils.getString("name"));

            //背景图片的切换
            login_back_pic.setBackgroundResource(R.drawable.reg_bg);
        }else {
            //显示默认头像
            my_user_icon.setImageResource(R.drawable.user);
            //用户名显示 登录/注册 >
            my_user_name.setText("登录/注册 >");

            //背景图片的切换
            login_back_pic.setBackgroundResource(R.drawable.normal_regbg);

        }

    }

    @Override
    public void onResume() {
        super.onResume();

        //scrollView滚动到最上面
        //fragment_my_scroll.smoothScrollTo(0,0);

        //1.是否登录的一些操作
        initData();

    }

    /**
     * 隐藏改变的回调...是否隐藏  true表示隐藏
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //Toast.makeText(getActivity(),hidden+"---",Toast.LENGTH_SHORT).show();

        if (! hidden) {//可见
            //scrollView滚动到最上面
            //fragment_my_scroll.smoothScrollTo(0,0);

            initData();
        }

    }

    @Override
    public void onSuccess(final HomeBean homeBean) {

        //瀑布流
        tui_jian_recycler.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        //为你推荐设置适配器
        TuiJianAdapter tuiJianAdapter = new TuiJianAdapter(getActivity(), homeBean.getTuijian());
        tui_jian_recycler.setAdapter(tuiJianAdapter);

        //为你推荐的点击事件
        tuiJianAdapter.setOnItemListner(new OnItemListner() {
            @Override
            public void onItemClick(int position) {

                //跳转的是自己的详情页面
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                //传递pid
                intent.putExtra("pid",homeBean.getTuijian().getList().get(position).getPid());
                startActivity(intent);

            }

            @Override
            public void onItemLongClick(int position) {

            }
        });

    }

    @Override
    public void onFenLeiDataSuccess(FenLeiBean fenLeiBean) {

    }

    @Override
    public void onClick(View view) {
        boolean isLogin = CommonUtils.getBoolean("isLogin");

        Intent intent = new Intent();

        if (! isLogin) {
            //跳转登录注册页面
            intent.setClass(getActivity(),LoginActivity.class);

        }else {
            switch (view.getId()) {

                case R.id.my_linear_login://跳转账户设置页面
                    intent.setClass(getActivity(), UserSettingActivity.class);

                    break;
                case R.id.my_order_dfk://我的订单...代付款
                    //可以传值过去...显示哪一个tablayout
                    intent.setClass(getActivity(), MyOrderActivity.class);

                    break;
                case R.id.my_order_dpj://待评价

                    intent.setClass(getActivity(), MyOrderActivity.class);


                    break;
                case R.id.my_order_dsh://待收货

                    intent.setClass(getActivity(), MyOrderActivity.class);

                    break;
                case R.id.my_order_th://退化

                    intent.setClass(getActivity(), MyOrderActivity.class);

                    break;

            }
        }

        //开启
        startActivity(intent);

    }
}
