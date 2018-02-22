package com.dash.a1511n.view.activity;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dash.a1511n.R;
import com.dash.a1511n.util.CommonUtils;
import com.dash.a1511n.util.GlideImgManager;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RESULT_LOAD_IMAGE = 1001;
    private ImageView detail_image_back;
    private ImageView setting_icon;
    private LinearLayout linear_upload;
    private PopupWindow popupWindow;
    private View parent;
    private RelativeLayout pop_up_layout;
    private TextView text_camera;
    private TextView text_local_pic;
    private TextView text_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);


        detail_image_back = findViewById(R.id.detail_image_back);
        setting_icon = findViewById(R.id.setting_icon);
        linear_upload = findViewById(R.id.linear_upload);


        detail_image_back.setOnClickListener(this);
        linear_upload.setOnClickListener(this);

        initPopupWindown();

        initData();

    }

    private void initPopupWindown() {
        View contentView = View.inflate(UserInfoActivity.this, R.layout.upload_pop_layout,null);
        parent = View.inflate(UserInfoActivity.this, R.layout.activity_user_info,null);
        //popWindown
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);

        //剩下两个在这里可以不用设置

        pop_up_layout = contentView.findViewById(R.id.pop_up_layout);
        text_camera = contentView.findViewById(R.id.text_camera);
        text_local_pic = contentView.findViewById(R.id.text_local_pic);
        text_cancel = contentView.findViewById(R.id.text_cancel);


        pop_up_layout.setOnClickListener(this);
        text_camera.setOnClickListener(this);
        text_local_pic.setOnClickListener(this);
        text_cancel.setOnClickListener(this);
    }

    private void initData() {

        //加载圆形
        GlideImgManager.glideLoader(UserInfoActivity.this, CommonUtils.getString("iconUrl"), R.drawable.user, R.drawable.user, setting_icon, 0);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_upload://弹出popupWindown

                popupWindow.showAtLocation(parent, Gravity.BOTTOM,0,0);

                break;

            case R.id.detail_image_back:

                finish();
                break;
            case R.id.pop_up_layout:

                popupWindow.dismiss();

                break;
            case R.id.text_camera:


                popupWindow.dismiss();
                break;
            case R.id.text_local_pic://相册
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);

                popupWindow.dismiss();
                break;
            case R.id.text_cancel:


                popupWindow.dismiss();
                break;
        }
    }
}
