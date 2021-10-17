package com.example.finalresdemo.ui.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalresdemo.R;
import com.example.finalresdemo.config.Config;
import com.example.finalresdemo.ui.vo.ProductItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<ProductItem> mProductItemList;

    public ProductListAdapter(Context context,List<ProductItem> productItemList) {
            this.mContext = context;
            this.mProductItemList = productItemList;
            mLayoutInflater = LayoutInflater.from(context);
    }

    public interface OnProductListener {
        void onProductAdd(ProductItem productItem);
        void onProductSub(ProductItem productItem);
    }

    private OnProductListener mOnProductListener;

    public void setOnProductListener(OnProductListener onProductListener) {
        this.mOnProductListener = onProductListener;
    }


    @Override
    public ProductListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_product_list,parent,false);
        return new ProductListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductListAdapter.ProductListHolder holder, int position) {
        ProductItem productItem = mProductItemList.get(position);
        Picasso.with(mContext)
                .load(Config.baseUrl + productItem.getIcon())
                .placeholder(R.drawable.pictures_no)
                .into(holder.image);
        holder.itemPrice.setText(productItem.getPrice() + "");
        holder.mTvCount.setText(productItem.count+ "");
        holder.resDesc.setText(productItem.getLabel());
        holder.resName.setText(productItem.getName());
    }

    @Override
    public int getItemCount() {
        return mProductItemList.size();
    }

    class ProductListHolder extends RecyclerView.ViewHolder {


        private ImageView image;
        private TextView resName, resDesc, itemPrice, mTvCount;
        private ImageView mIvAdd,mIvSub;


        public ProductListHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.id_iv_image);
            resName = itemView.findViewById(R.id.id_tv_name);
            resDesc = itemView.findViewById(R.id.id_tv_desc);
            itemPrice = itemView.findViewById(R.id.id_tv_price);
            mTvCount = itemView.findViewById(R.id.id_tv_single_count);
            mIvAdd = itemView.findViewById(R.id.id_iv_add);
            mIvSub = itemView.findViewById(R.id.id_iv_sub);

            // to do
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            mIvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    ProductItem productItem = mProductItemList.get(position);
                    productItem.count += 1;
                    mTvCount.setText(productItem.count + "");
                    //callback to change main ui
                    if (mOnProductListener != null) {
                        mOnProductListener.onProductAdd(productItem);
                    }
                }
            });

            mIvSub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    ProductItem productItem = mProductItemList.get(position);
                    if (productItem.count <= 0) {
                        Toast.makeText(mContext,"不能再减少了",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    productItem.count -= 1;
                    mTvCount.setText(productItem.count + "");
                    //callback to change main ui
                    if (mOnProductListener != null) {
                        mOnProductListener.onProductSub(productItem);
                    }
                }
            });
        }
    }
}
