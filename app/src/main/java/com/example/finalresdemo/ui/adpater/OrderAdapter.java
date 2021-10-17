package com.example.finalresdemo.ui.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.finalresdemo.R;
import com.example.finalresdemo.bean.Order;
import com.example.finalresdemo.config.Config;
import com.example.finalresdemo.ui.activity.OrderDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderAdapterItemHolder> {

    private List<Order> mDatas;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public OrderAdapter(Context context, List<Order> datas) {
        mDatas = datas;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public OrderAdapterItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View itemView =  mLayoutInflater.inflate(R.layout.item_order_list,parent,false);
        return new OrderAdapterItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderAdapter.OrderAdapterItemHolder holder, int position) {
        Order order = mDatas.get(position);

        Picasso.with(mContext)
                .load(Config.baseUrl + order.getRestaurant().getIcon())
                .placeholder(R.drawable.pictures_no)
                .into(holder.image);

        if (order.ps.size() > 0) {
            holder.resDesc.setText(order.ps.get(0).product.getName() + "等" + order.getCount() + "件商品");
        }else {
            holder.resDesc.setText("无消费");
        }

        holder.resName.setText(order.getRestaurant().getName());
        holder.itemPrice.setText("共消费" + (int) order.getPrice() + "元");
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class OrderAdapterItemHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView resName, resDesc, itemPrice;

        public OrderAdapterItemHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.id_iv_image);
            resName = itemView.findViewById(R.id.id_tv_name);
            resDesc = itemView.findViewById(R.id.id_tv_desc);
            itemPrice = itemView.findViewById(R.id.id_tv_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderDetailActivity.launch(mContext,mDatas.get(getLayoutPosition()));
                }
            });
        }
    }
}
