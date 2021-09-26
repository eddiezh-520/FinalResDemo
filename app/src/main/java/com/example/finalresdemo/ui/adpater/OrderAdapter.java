package com.example.finalresdemo.ui.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalresdemo.bean.Order;

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
        return null;
    }

    @Override
    public void onBindViewHolder(OrderAdapter.OrderAdapterItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class OrderAdapterItemHolder extends RecyclerView.ViewHolder {

        public OrderAdapterItemHolder(View itemView) {
            super(itemView);
        }
    }
}
