package com.dash.a1511n.view.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dash.a1511n.R;
import com.dash.a1511n.model.bean.AddCartBean;
import com.dash.a1511n.model.bean.DeatilBean;
import com.dash.a1511n.presenter.AddCartPresenter;
import com.dash.a1511n.presenter.DeatailPresenter;
import com.dash.a1511n.util.ApiUtil;
import com.dash.a1511n.util.CommonUtils;
import com.dash.a1511n.util.GlideImageLoader;
import com.dash.a1511n.util.ShareUtil;
import com.dash.a1511n.view.Iview.ActivityAddCartInter;
import com.dash.a1511n.view.Iview.DetailActivityInter;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements DetailActivityInter, View.OnClickListener,ActivityAddCartInter {

    private int pid;
    private DeatailPresenter deatailPresenter;
    private Banner banner;
    private TextView detail_title;
    private TextView detail_bargin_price;
    private TextView detail_yuan_price;
    private TextView detai_add_cart;
    private ImageView detail_image_back;
    private TextView watch_cart;
    private AddCartPresenter addCartPresenter;
    private ImageView detail_share;
    private DeatilBean deatilBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        banner = findViewById(R.id.banner);
        detail_title = findViewById(R.id.detail_title);
        detail_bargin_price = findViewById(R.id.detail_bargin_price);
        detail_yuan_price = findViewById(R.id.detail_yuan_price);
        detai_add_cart = findViewById(R.id.detai_add_cart);
        detail_image_back = findViewById(R.id.detail_image_back);
        watch_cart = findViewById(R.id.watch_cart);
        detail_share = findViewById(R.id.detail_share);

        //创建presenter
        deatailPresenter = new DeatailPresenter(this);
        addCartPresenter = new AddCartPresenter(this);

        //接收传递的pid
        pid = getIntent().getIntExtra("pid", -1);
        //如果不是默认值代表传递过来数据了
        if (pid != -1){

            //拿着传递的pid请求商品详情的接口,然后展示数据...MVP
            deatailPresenter.getDetailData(ApiUtil.DETAIL_URL,pid);
        }

        //初始化banner
        initBanner();

        //设置点击事件
        detai_add_cart.setOnClickListener(this);
        detail_image_back.setOnClickListener(this);
        watch_cart.setOnClickListener(this);
        detail_share.setOnClickListener(this);

    }

    @Override
    public void onSuccess(DeatilBean deatilBean) {

        this.deatilBean = deatilBean;

        //添加删除线
        detail_yuan_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        //设置数据显示
        detail_title.setText(deatilBean.getData().getTitle());
        detail_bargin_price.setText("优惠价:"+deatilBean.getData().getBargainPrice());
        detail_yuan_price.setText("原价:"+deatilBean.getData().getPrice());

        String[] strings = deatilBean.getData().getImages().split("\\|");

        final ArrayList<String> imageUrls = new ArrayList<>();
        for (int i = 0;i<strings.length;i++){
            imageUrls.add(strings[i]);
        }

        banner.setImages(imageUrls);
        banner.start();

        //bannner点击事件进行跳转
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(DetailActivity.this,ImageScaleActivity.class);
                //传递的数据...整个轮播图数据的集合需要传递,,,当前展示的图片的位置需要传递postion
                //intent传递可以传的数据...基本数据类型...引用数据类型(必须序列化,所有的类,包括内部类实现serilizable接口)...bundle
                intent.putStringArrayListExtra("imageUrls",imageUrls);
                intent.putExtra("position",position);

                startActivity(intent);
            }
        });


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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detai_add_cart://添加购物车...uid...pid

                addCartPresenter.addToCart(ApiUtil.ADD_CART_URL, CommonUtils.getString("uid"),pid);

                break;
            case R.id.detail_image_back://返回
                //finish() startActivity() setResult()...context.startActiivty()

                //DetailActivity.this.finish();
                finish();
                break;
            case R.id.watch_cart://查看购物车....跳转的是购物车的activity

                Intent intent = new Intent(DetailActivity.this,CartActivity.class);

                startActivity(intent);
                break;
            case R.id.detail_share://分享......分享的是商品链接

                if (deatilBean != null) {
                    //final Activity activity 分享的activity的上下文,
                    // String WebUrl 分享的商品的链接,
                    // String title 分享的商品的标题,
                    // String description 商品的描述,
                    // String imageUrl 商品的图片...如果没有图片传"",
                    // int imageID 本地商品的图片
                    DeatilBean.DataBean data = deatilBean.getData();
                    ShareUtil.shareWeb(DetailActivity.this,data.getDetailUrl(),data.getTitle(),"我在京东发现一个好的商品,赶紧来看看吧!",data.getImages().split("\\|")[0],R.mipmap.ic_launcher);
                }

                /*new ShareAction(DetailActivity.this)
                        .withText("hello")
                        .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                        .setCallback(shareListener)
                        .open();*/

                break;
        }
    }

    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Toast.makeText(DetailActivity.this,"分享开始",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Toast.makeText(DetailActivity.this,"分享成功",Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Log.e("----",throwable.getMessage());
            Toast.makeText(DetailActivity.this,"分享失败",Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.makeText(DetailActivity.this,"分享取消",Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    public void onCartAddSuccess(AddCartBean addCartBean) {
        Toast.makeText(DetailActivity.this,addCartBean.getMsg(),Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
