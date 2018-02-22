package com.dash.a1511n.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dash.a1511n.R;
import com.dash.a1511n.model.bean.RegistBean;
import com.dash.a1511n.presenter.RegistPresenter;
import com.dash.a1511n.util.ApiUtil;
import com.dash.a1511n.view.Iview.RegistActivityInter;

public class RegistActivity extends AppCompatActivity implements RegistActivityInter, View.OnClickListener {

    private EditText edit_phone;
    private EditText edit_pwd;
    private RegistPresenter registPresenter;
    private ImageView cha_iamge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        edit_phone = findViewById(R.id.edit_phone);
        edit_pwd = findViewById(R.id.edit_pwd);
        cha_iamge = findViewById(R.id.cha_iamge);

        //中间者
        registPresenter = new RegistPresenter(this);

        cha_iamge.setOnClickListener(this);
    }

    /**
     * 注册的点击事件
     * @param view
     */
    public void regist(View view) {

        String name = edit_phone.getText().toString();
        String pwd = edit_pwd.getText().toString();

        registPresenter.registUser(ApiUtil.REGIST_URL,name,pwd);
    }

    @Override
    public void onRegistSuccess(RegistBean registBean) {
        String code = registBean.getCode();
        if ("1".equals(code)) {//注册失败
            Toast.makeText(RegistActivity.this,registBean.getMsg(),Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(RegistActivity.this,registBean.getMsg(),Toast.LENGTH_SHORT).show();
            //并且结束显示
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cha_iamge://叉

                finish();
                break;
        }
    }
}
