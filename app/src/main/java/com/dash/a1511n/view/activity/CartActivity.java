package com.dash.a1511n.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dash.a1511n.R;
import com.dash.a1511n.model.bean.CartBean;
import com.dash.a1511n.model.bean.CountPriceBean;
import com.dash.a1511n.model.bean.FenLeiBean;
import com.dash.a1511n.model.bean.HomeBean;
import com.dash.a1511n.presenter.FragmentCartPresenter;
import com.dash.a1511n.presenter.FragmentHomeP;
import com.dash.a1511n.util.ApiUtil;
import com.dash.a1511n.util.CommonUtils;
import com.dash.a1511n.view.Iview.FragmentCartInter;
import com.dash.a1511n.view.Iview.InterFragmentHome;
import com.dash.a1511n.view.Iview.OnItemListner;
import com.dash.a1511n.view.adapter.MyExpanableAdapter;
import com.dash.a1511n.view.adapter.TuiJianAdapter;
import com.dash.a1511n.view.custom.MyExpanableView;

import java.util.List;

public class CartActivity extends AppCompatActivity implements InterFragmentHome,FragmentCartInter, View.OnClickListener{

    private LinearLayout linear_login;
    private Button cart_login;
    private RecyclerView tui_jian_recycler;
    private MyExpanableView my_expanable_view;
    private FragmentHomeP fragmentHomeP;
    private RelativeLayout relative_progress;
    private FragmentCartPresenter fragmentCartPresenter;
    private CheckBox cart_check_all;
    private TextView text_total;
    private TextView text_buy;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                CountPriceBean countPriceBean = (CountPriceBean) msg.obj;

                //设置显示
                text_total.setText("合计:¥"+countPriceBean.getPriceString());
                text_buy.setText("去结算("+countPriceBean.getCount()+")");
            }
        }
    };
    private MyExpanableAdapter myExpanableAdapter;
    private ImageView empty_cart_image;
    private ImageView detail_image_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frament_cart_layout);


        //找到控件
        linear_login = findViewById(R.id.linear_login);
        cart_login = findViewById(R.id.cart_login);
        tui_jian_recycler = findViewById(R.id.tui_jian_recycler);
        my_expanable_view = findViewById(R.id.my_expanable_view);
        relative_progress = findViewById(R.id.relative_progress);
        cart_check_all = findViewById(R.id.cart_check_all);
        text_total = findViewById(R.id.text_total);
        text_buy = findViewById(R.id.text_buy);
        empty_cart_image = findViewById(R.id.empty_cart_image);
        detail_image_back = findViewById(R.id.detail_image_back);


        detail_image_back.setVisibility(View.VISIBLE);

        //去掉默认的指示器
        my_expanable_view.setGroupIndicator(null);

        //失去焦点....界面不是从recyclerView开始显示
        tui_jian_recycler.setFocusable(false);

        //2.为你推荐,,,只需要获取一次
        fragmentHomeP = new FragmentHomeP(this);
        //调用p层里面的方法....https://www.zhaoapi.cn/ad/getAd
        fragmentHomeP.getNetData(ApiUtil.HOME_URL);

        fragmentCartPresenter = new FragmentCartPresenter(this);


        //全选 设置点击事件
        cart_check_all.setOnClickListener(this);
        detail_image_back.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();

        initData();

        //为你推荐的数据请求一次....其实为你推荐的数据也是随着登录和不登录改变
    }

    private void initData() {
        //判断是否登录...没有,则显示登录按钮....已经登录显示购物车数据
        if (CommonUtils.getBoolean("isLogin")) {
            //请求购物车的数据...显示购物车
            my_expanable_view.setVisibility(View.VISIBLE);
            linear_login.setVisibility(View.GONE);

            //请求购物车的数据
            getCartData();


        }else {
            //登录按钮的显示
            linear_login.setVisibility(View.VISIBLE);
            my_expanable_view.setVisibility(View.GONE);

            //登录的点击事件
            cart_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CartActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            });

        }

    }

    /**
     * 获取购物车数据
     */
    private void getCartData() {
        //显示进度条
        relative_progress.setVisibility(View.VISIBLE);

        //查询购物车的数据
        fragmentCartPresenter.getCartData(ApiUtil.SELECT_CART,CommonUtils.getString("uid"));

    }

    @Override
    public void onSuccess(final HomeBean homeBean) {
        //瀑布流
        tui_jian_recycler.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        //为你推荐设置适配器
        TuiJianAdapter tuiJianAdapter = new TuiJianAdapter(CartActivity.this, homeBean.getTuijian());
        tui_jian_recycler.setAdapter(tuiJianAdapter);

        //为你推荐的点击事件
        tuiJianAdapter.setOnItemListner(new OnItemListner() {
            @Override
            public void onItemClick(int position) {

                //跳转的是自己的详情页面
                Intent intent = new Intent(CartActivity.this, DetailActivity.class);
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
    public void getCartDataNull() {
        relative_progress.setVisibility(View.GONE);
        empty_cart_image.setVisibility(View.VISIBLE);
        Toast.makeText(CartActivity.this,"购物车为空,先去逛逛吧",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getCartDataSuccess(final CartBean cartBean) {
        //进度条隐藏
        relative_progress.setVisibility(View.GONE);
        empty_cart_image.setVisibility(View.GONE);

        //显示购物车的数据....二级列表设置适配器

        //1.根据某一组里面所有子孩子是否选中,决定当前组是否选中
        for (int i =0;i<cartBean.getData().size();i++) {

            //遍历每一组的数据,,,设置是否选中...有所有的子孩子决定
            CartBean.DataBean dataBean = cartBean.getData().get(i);
            dataBean.setGroupChecked(isAllChildInGroupChecked(dataBean.getList()));
        }

        //2.根据所有的组是否选中,,,决定全选是否选中
        cart_check_all.setChecked(isAllGroupChecked(cartBean));

        //3.
        myExpanableAdapter = new MyExpanableAdapter(CartActivity.this, cartBean,handler,relative_progress,fragmentCartPresenter);
        my_expanable_view.setAdapter(myExpanableAdapter);

        //展开所有的每一组
        for (int i = 0;i<cartBean.getData().size();i++) {
            my_expanable_view.expandGroup(i);
        }

        //4.计算商品的总价和数量
        myExpanableAdapter.sendPriceAndCount();

        //子条目的点击事件
        my_expanable_view.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {

                //Toast.makeText(getActivity(),"点击了",Toast.LENGTH_SHORT).show();

                //跳转的是自己的详情页面
                Intent intent = new Intent(CartActivity.this, DetailActivity.class);
                //传递pid
                intent.putExtra("pid",cartBean.getData().get(groupPosition).getList().get(childPosition).getPid());
                startActivity(intent);

                return false;
            }
        });

    }

    /**
     * 是否所有的组选中
     * @param cartBean
     * @return
     */
    private boolean isAllGroupChecked(CartBean cartBean) {

        for (int i=0;i<cartBean.getData().size();i++) {
            //组没有选中
            if (! cartBean.getData().get(i).isGroupChecked()) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断组中所有的子孩子是否全部选中
     * @param list
     * @return
     */
    private boolean isAllChildInGroupChecked(List<CartBean.DataBean.ListBean> list) {

        for (int i =0;i<list.size();i++) {
            if (list.get(i).getSelected() == 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cart_check_all://全选
                //点击全选的时候,,,,根据全选的状态 改变购物车所有商品的选中状态

                myExpanableAdapter.setAllChildChecked(cart_check_all.isChecked());
                break;
            case R.id.detail_image_back:

                finish();
                break;
        }
    }
}
