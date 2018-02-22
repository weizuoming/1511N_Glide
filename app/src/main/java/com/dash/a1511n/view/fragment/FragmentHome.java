package com.dash.a1511n.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dash.a1511n.R;
import com.dash.a1511n.model.bean.FenLeiBean;
import com.dash.a1511n.model.bean.HomeBean;
import com.dash.a1511n.presenter.FragmentHomeP;
import com.dash.a1511n.util.ApiUtil;
import com.dash.a1511n.util.GlideImageLoader;
import com.dash.a1511n.view.Iview.InterFragmentHome;
import com.dash.a1511n.view.Iview.OnItemListner;
import com.dash.a1511n.view.activity.DetailActivity;
import com.dash.a1511n.view.activity.WebViewActivity;
import com.dash.a1511n.view.adapter.HengXiangAdapter;
import com.dash.a1511n.view.adapter.MiaoShaAdapter;
import com.dash.a1511n.view.adapter.TuiJianAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sunfusheng.marqueeview.MarqueeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dash on 2018/1/23.
 *
 * targetSdkVersion 22....6.0---23新特性,,,y运行时权限
 *
 */
public class FragmentHome extends Fragment implements InterFragmentHome{

    private FragmentHomeP fragmentHomeP;
    private Banner banner;
    private RecyclerView heng_xiang;
    private RecyclerView tui_jian_recycler;
    private MarqueeView marqueeView;
    private RecyclerView miao_sha_recycler;
    private SmartRefreshLayout smart_refresh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //主要在oncreateView中做的操作是找到控件...
        View view = inflater.inflate(R.layout.fragment_home_layout,container,false);

        banner = view.findViewById(R.id.banner);
        heng_xiang = view.findViewById(R.id.heng_xiang_recycler);
        tui_jian_recycler = view.findViewById(R.id.tui_jian_recycler);
        marqueeView = (MarqueeView) view.findViewById(R.id.marqueeView);
        miao_sha_recycler = view.findViewById(R.id.miao_sha_recycler);
        smart_refresh = view.findViewById(R.id.smart_refresh);

       /* try {
            int i = 1/0;//此处存在异常,,,但是没有捕获,,,运行起来之后程序会崩溃
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //在这里可以做其他操作....
        fragmentHomeP = new FragmentHomeP(this);

        //调用p层里面的方法....https://www.zhaoapi.cn/ad/getAd
        fragmentHomeP.getNetData(ApiUtil.HOME_URL);

        //一个view一般是对应着一个presenter和一个model;;;;逻辑比较复杂的页面可能会对应多个presenter和多个model
        fragmentHomeP.getFenLeiData(ApiUtil.FEN_LEI_URL);


        //初始化banner
        initBanner();

        //初始化跑马灯
        initMarqueeView();

        smart_refresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                smart_refresh.finishLoadmore(2000);
            }
        });
        smart_refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                smart_refresh.finishRefresh(2000);
            }
        });

    }

    private void initMarqueeView() {
        List<String> info = new ArrayList<>();
        info.add("欢迎访问京东app");
        info.add("大家有没有在 听课");
        info.add("是不是还有人在睡觉");
        info.add("你妈妈在旁边看着呢");
        info.add("赶紧的好好学习吧 马上毕业了");
        info.add("你没有事件睡觉了");
        marqueeView.startWithList(info);
    }

    private void initBanner() {

        //设置banner样式...CIRCLE_INDICATOR_TITLE包含标题
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);

    }

    //此时已经获取导数据,,,,并且此时也在主线程中
    @Override
    public void onSuccess(final HomeBean homeBean) {

        //设置显示bannner
        List<HomeBean.DataBean> datas = homeBean.getData();

        List<String> imageUrls = new ArrayList<>();
        for (int i = 0;i<datas.size();i++){
            imageUrls.add(datas.get(i).getIcon());
        }

        banner.setImages(imageUrls);
        banner.start();

        //banner的点击事件
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

                HomeBean.DataBean dataBean = homeBean.getData().get(position);
                if (dataBean.getType() == 0) {
                    //跳转webView
                    Intent intent = new Intent(getActivity(),WebViewActivity.class);

                    intent.putExtra("detailUrl",dataBean.getUrl());
                    startActivity(intent);

                }else {
                    Toast.makeText(getActivity(),"即将跳转商品详情",Toast.LENGTH_SHORT).show();
                }





            }
        });


        //秒杀的数据
        miao_sha_recycler.setLayoutManager(new LinearLayoutManager(getActivity(),OrientationHelper.HORIZONTAL,false));

        MiaoShaAdapter miaoShaAdapter = new MiaoShaAdapter(getActivity(), homeBean.getMiaosha());
        miao_sha_recycler.setAdapter(miaoShaAdapter);
        //条目的点击事件
        miaoShaAdapter.setOnItemListner(new OnItemListner() {
            @Override
            public void onItemClick(int position) {
                //跳转到下一个页面....传值过去...webView页面
                Intent intent = new Intent(getActivity(), WebViewActivity.class);

                String detailUrl = homeBean.getMiaosha().getList().get(position).getDetailUrl();
                intent.putExtra("detailUrl",detailUrl);
                startActivity(intent);

            }

            @Override
            public void onItemLongClick(int position) {

            }
        });

        //瀑布流
        tui_jian_recycler.setLayoutManager(new StaggeredGridLayoutManager(2,OrientationHelper.VERTICAL));
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
        //在这里拿到了分类的数据

        //如果要展示数据的话必须先设置布局管理器....上下文,,,表示几行或者几列,,,表示方向,水平或者竖直,,,是否反转布局展示
        heng_xiang.setLayoutManager(new GridLayoutManager(getActivity(),2, OrientationHelper.HORIZONTAL,false));

        HengXiangAdapter hengXiangAdapter = new HengXiangAdapter(getActivity(), fenLeiBean);
        //设置适配器
        heng_xiang.setAdapter(hengXiangAdapter);

        //设置条目的点击事件...实际是适配器的点击事件
        hengXiangAdapter.setOnItemListner(new OnItemListner() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getActivity(),"点击事件执行",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(int position) {
                Toast.makeText(getActivity(),"长按事件执行",Toast.LENGTH_SHORT).show();

            }
        });


    }
}
