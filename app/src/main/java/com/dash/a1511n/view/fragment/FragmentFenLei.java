package com.dash.a1511n.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dash.a1511n.R;
import com.dash.a1511n.model.bean.FenLeiBean;
import com.dash.a1511n.model.bean.HomeBean;
import com.dash.a1511n.presenter.FragmentHomeP;
import com.dash.a1511n.util.ApiUtil;
import com.dash.a1511n.view.Iview.InterFragmentHome;
import com.dash.a1511n.view.adapter.FenLeiAdapter;

/**
 * Created by Dash on 2018/1/23.
 */
public class FragmentFenLei extends Fragment implements InterFragmentHome {

    private ListView fen_lei_list_view;
    private FrameLayout fen_lei_frame;
    private FragmentHomeP fragmentHomeP;
    private FenLeiAdapter fenLeiAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fen_lei_layout,container,false);

        fen_lei_list_view = view.findViewById(R.id.fen_lei_list_view);
        fen_lei_frame = view.findViewById(R.id.fen_lei_frame);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //获取左边listView展示的数据
        fragmentHomeP = new FragmentHomeP(this);

        fragmentHomeP.getFenLeiData(ApiUtil.FEN_LEI_URL);

    }

    /**
     * 这个回调在fragment分类中没有用
     * @param homeBean
     */
    @Override
    public void onSuccess(HomeBean homeBean) {

    }

    @Override
    public void onFenLeiDataSuccess(final FenLeiBean fenLeiBean) {

        //给listView设置适配器
        fenLeiAdapter = new FenLeiAdapter(getActivity(), fenLeiBean);
        fen_lei_list_view.setAdapter(fenLeiAdapter);

        //默认显示京东超市右边对应的数据
        FragmentFenLeiRight fragmentFenLeiRight = FragmentFenLeiRight.getInstance(fenLeiBean.getData().get(0).getCid());

        getChildFragmentManager().beginTransaction().replace(R.id.fen_lei_frame,fragmentFenLeiRight).commit();


        //条目点击事件
        fen_lei_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //设置适配器当前位置的方法
                fenLeiAdapter.setCurPositon(i);
                //刷新适配器...getView重新执行
                fenLeiAdapter.notifyDataSetChanged();
                //滚动到指定的位置,,,第一个参数是滚动哪一个条目,,,滚动到哪个位置/偏移量
                fen_lei_list_view.smoothScrollToPositionFromTop(i,(adapterView.getHeight()-view.getHeight())/2);

                //使用fragment替换右边frameLayout....fragment之间的传值
                FragmentFenLeiRight fragmentFenLeiRight = FragmentFenLeiRight.getInstance(fenLeiBean.getData().get(i).getCid());


                getChildFragmentManager().beginTransaction().replace(R.id.fen_lei_frame,fragmentFenLeiRight).commit();


            }
        });

    }
}
