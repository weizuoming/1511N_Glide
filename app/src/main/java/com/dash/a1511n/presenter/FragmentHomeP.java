package com.dash.a1511n.presenter;

import com.dash.a1511n.model.FragmentHomeModel;
import com.dash.a1511n.model.bean.FenLeiBean;
import com.dash.a1511n.model.bean.HomeBean;
import com.dash.a1511n.presenter.inter.FragmentHomePInter;
import com.dash.a1511n.view.Iview.InterFragmentHome;
import com.dash.a1511n.view.fragment.FragmentHome;

/**
 * Created by Dash on 2018/1/23.
 */
public class FragmentHomeP implements FragmentHomePInter{

    private FragmentHomeModel fragmentHomeModel;
    private InterFragmentHome interFragmentHome;

    //创建构造方法
    public FragmentHomeP(InterFragmentHome interFragmentHome) {
        this.interFragmentHome = interFragmentHome;

        //创建model的引用
        fragmentHomeModel = new FragmentHomeModel(this);
    }

    public void getNetData(String homeUrl) {

        //让model获取数据
        fragmentHomeModel.getData(homeUrl);

    }

    @Override
    public void onSuccess(HomeBean homeBean) {

        //此时的数据回调到p层,,,把数据从p层传到view层进行使用
        interFragmentHome.onSuccess(homeBean);
    }

    @Override
    public void onFenLeiDataSuccess(FenLeiBean fenLeiBean) {
        interFragmentHome.onFenLeiDataSuccess(fenLeiBean);
    }

    public void getFenLeiData(String fenLeiUrl) {

        fragmentHomeModel.getFenLeiData(fenLeiUrl);
    }
}
