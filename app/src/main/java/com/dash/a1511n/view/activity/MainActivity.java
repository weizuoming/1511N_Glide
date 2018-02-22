package com.dash.a1511n.view.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.dash.a1511n.R;
import com.dash.a1511n.view.fragment.FragmentFaXian;
import com.dash.a1511n.view.fragment.FragmentFenLei;
import com.dash.a1511n.view.fragment.FragmentHome;
import com.dash.a1511n.view.fragment.FragmentMy;
import com.dash.a1511n.view.fragment.FragmentShoppingCart;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup radioGroup;
    private FragmentHome fragmentHome;
    private FragmentFenLei fragmentFenLei;
    private FragmentFaXian fragmentFaXian;
    private FragmentShoppingCart fragmentShoppingCart;
    private FragmentMy fragmentMy;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radio_group);


        //管理者...开启事务(一个事务只能执行一次)....默认的是要显示第一个首页
        manager = getSupportFragmentManager();

        fragmentHome = new FragmentHome();

        //添加这个方法在使用的时候同一个fragment只能添加一次,否则会报错...结合着show和hide方法去使用
        manager.beginTransaction().add(R.id.frame, fragmentHome).commit();


        //监听事件
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

        //事务的对象
        FragmentTransaction transaction = manager.beginTransaction();

        //首先隐藏所有的fragment ...前提是不为空
        //在刚开始点击的时候,先判断fragment是否为空,,,如果不为空先让他隐藏
        if (fragmentHome != null) {
            transaction.hide(fragmentHome);
        }
        if (fragmentFenLei != null) {
            transaction.hide(fragmentFenLei);
        }
        if (fragmentFaXian != null) {
            transaction.hide(fragmentFaXian);
        }
        if (fragmentShoppingCart != null) {
            transaction.hide(fragmentShoppingCart);
        }
        if (fragmentMy != null) {
            transaction.hide(fragmentMy);
        }


        switch (checkedId) {//点击选中某个button的时候要么去显示要么去添加,,,没有去添加,,,有则显示出来
            case R.id.radio_01:
                //manager.beginTransaction().replace(arg0, arg1).commit()
                if (fragmentHome == null) {
                    fragmentHome = new FragmentHome();
                    transaction.add(R.id.frame, fragmentHome);
                }else {
                    transaction.show(fragmentHome);
                }
                break;
            case R.id.radio_02:
                if (fragmentFenLei == null) {
                    fragmentFenLei = new FragmentFenLei();
                    transaction.add(R.id.frame, fragmentFenLei);
                }else {
                    transaction.show(fragmentFenLei);
                }
                break;
            case R.id.radio_03:
                if (fragmentFaXian == null) {
                    fragmentFaXian = new FragmentFaXian();
                    transaction.add(R.id.frame, fragmentFaXian);
                }else {
                    transaction.show(fragmentFaXian);
                }
                break;
            case R.id.radio_04:
                if (fragmentShoppingCart == null) {
                    fragmentShoppingCart = new FragmentShoppingCart();
                    transaction.add(R.id.frame, fragmentShoppingCart);
                }else {
                    transaction.show(fragmentShoppingCart);
                }
                break;
            case R.id.radio_05:
                if (fragmentMy == null) {
                    fragmentMy = new FragmentMy();
                    transaction.add(R.id.frame, fragmentMy);
                }else {
                    transaction.show(fragmentMy);
                }
                break;

            default:
                break;
        }



        //只能提交一次
        transaction.commit();
    }
}
