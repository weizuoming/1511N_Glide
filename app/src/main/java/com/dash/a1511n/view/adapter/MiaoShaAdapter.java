package com.dash.a1511n.view.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.dash.a1511n.R;
import com.dash.a1511n.model.bean.HomeBean;
import com.dash.a1511n.view.Iview.OnItemListner;
import com.dash.a1511n.view.hodler.MiaoShaHolder;

/**
 * Created by Dash on 2018/1/24.
 */
public class MiaoShaAdapter extends RecyclerView.Adapter<MiaoShaHolder>{
    private HomeBean.MiaoshaBean miaosha;
    private Context context;
    private OnItemListner onItemListner;

    public MiaoShaAdapter(Context context, HomeBean.MiaoshaBean miaosha) {
        this.context = context;
        this.miaosha = miaosha;
    }

    @Override
    public MiaoShaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.miao_sha_item_layout,null);
        MiaoShaHolder miaoShaHolder = new MiaoShaHolder(view);

        return miaoShaHolder;
    }

    @Override
    public void onBindViewHolder(MiaoShaHolder holder, final int position) {

        String[] strings = miaosha.getList().get(position).getImages().split("\\|");

        //赋值的操作
        Glide.with(context).load(strings[0]).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onItemListner.onItemClick(position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return miaosha.getList().size();
    }

    public void setOnItemListner(OnItemListner onItemListner) {
        this.onItemListner = onItemListner;
    }
}
