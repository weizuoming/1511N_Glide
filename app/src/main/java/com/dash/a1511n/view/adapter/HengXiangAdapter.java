package com.dash.a1511n.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.dash.a1511n.R;
import com.dash.a1511n.model.bean.FenLeiBean;
import com.dash.a1511n.view.Iview.OnItemListner;
import com.dash.a1511n.view.hodler.HengXiangHolder;

/**
 * Created by Dash on 2018/1/24.
 */
public class HengXiangAdapter extends RecyclerView.Adapter<HengXiangHolder> {
    private FenLeiBean fenLeiBean;
    private Context context;
    private OnItemListner onItemListner;

    public HengXiangAdapter(Context context, FenLeiBean fenLeiBean) {
        this.context = context;
        this.fenLeiBean = fenLeiBean;
    }

    //创建一个viewHolder用的
    @Override
    public HengXiangHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //通过条目的视图进行创建holder的操作
        View view = View.inflate(context, R.layout.heng_xiang_item_layout,null);
        HengXiangHolder hengXiangHolder = new HengXiangHolder(view);

        return hengXiangHolder;
    }

    //绑定了holder以后的操作....赋值(textView设置文本,,imageView加载图片,,,buttom设置点击事件)
    @Override
    public void onBindViewHolder(HengXiangHolder holder, final int position) {
        FenLeiBean.DataBean dataBean = fenLeiBean.getData().get(position);

        //赋值
        holder.heng_item_text.setText(dataBean.getName());
        //加载图片
        Glide.with(context).load(dataBean.getIcon()).into(holder.heng_item_image);

        //点击
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //不直接进行操作...传回去
                onItemListner.onItemClick(position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onItemListner.onItemLongClick(position);

                //返回true,,,避免和点击事件的冲突
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {

        return fenLeiBean.getData().size();
    }

    /**
     * 设置条目事件的方法
     * @param onItemListner
     */
    public void setOnItemListner(OnItemListner onItemListner) {
        this.onItemListner = onItemListner;
    }
}
