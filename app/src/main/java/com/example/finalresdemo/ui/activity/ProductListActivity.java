package com.example.finalresdemo.ui.activity;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalresdemo.R;
import com.example.finalresdemo.bean.Product;
import com.example.finalresdemo.biz.ProductListBiz;
import com.example.finalresdemo.net.CommonCallback;
import com.example.finalresdemo.ui.adpater.ProductListAdapter;
import com.example.finalresdemo.ui.view.refresh.SwipeRefresh;
import com.example.finalresdemo.ui.view.refresh.SwipeRefreshLayout;
import com.example.finalresdemo.ui.vo.ProductItem;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends BaseActivity {

    private TextView mTvCount;
    private Button mBtnPay;
    private RecyclerView mRecyclerView;
    private ProductListAdapter mProductListAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProductListBiz mProductBiz = new ProductListBiz();
    private List<ProductItem> productItemList = new ArrayList<>();
    private int mCurrentPage = 0;
    private float mTotalPrice;
    private int mTotalCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        setUpToolBar();
        setTitle("订单");
        initView();
        initEvent();
        loadDatas();
    }

    private void initView() {
        mTvCount = findViewById(R.id.id_tv_count);
        mBtnPay = findViewById(R.id.id_btn_pay);
        mRecyclerView = findViewById(R.id.id_product_recyclerview);
        mSwipeRefreshLayout = findViewById(R.id.id_product_swipeRefresh);

        //set swiperefreshLayout
        mSwipeRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,Color.GREEN,Color.YELLOW);

        mProductListAdapter = new ProductListAdapter(this,productItemList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mProductListAdapter);
    }

    private void initEvent() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                    loadDatas();
            }
        });

        mSwipeRefreshLayout.setOnPullUpRefreshListener(new SwipeRefreshLayout.OnPullUpRefreshListener() {
            @Override
            public void onPullUpRefresh() {
                    loadMore();
            }
        });

        mProductListAdapter.setOnProductListener(new ProductListAdapter.OnProductListener() {
            @Override
            public void onProductAdd(ProductItem productItem) {
                mTotalCount ++;
                mTvCount.setText("数量：" + mTotalCount);
                mTotalPrice += productItem.getPrice();
                mBtnPay.setText(mTotalPrice +"元 立即支付");
            }

            @Override
            public void onProductSub(ProductItem productItem) {
                mTotalCount --;
                mTvCount.setText("数量：" + mTotalCount);
                mTotalPrice -= productItem.getPrice();
                mBtnPay.setText(mTotalPrice +"元 立即支付");
            }
        });

        // TO DO
        mBtnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    private void loadDatas() {
        startLoadingProgress();
        mProductBiz.listByPage(0, new CommonCallback<List<Product>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                Toast.makeText(ProductListActivity.this,e.toString(),Toast.LENGTH_SHORT);
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onSuccess(List<Product> response) {
                stopLoadingProgress();
                mSwipeRefreshLayout.setRefreshing(false);
                productItemList.clear();
//                Toast.makeText(ProductListActivity.this,"刷新成功",Toast.LENGTH_SHORT).show();
                for (Product p: response) {
                    productItemList.add(new ProductItem(p));
                }
                mCurrentPage = 0;
                mProductListAdapter.notifyDataSetChanged();
                //总数量，总价格变为0
                mTotalPrice = 0;
                mTotalCount = 0;
                mTvCount.setText("数量：" + mTotalCount);
                mBtnPay.setText(mTotalPrice +"元 立即支付");
            }
        });
    }

    private void loadMore() {
        startLoadingProgress();
        mProductBiz.listByPage(++mCurrentPage, new CommonCallback<List<Product>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                mCurrentPage--;
                Toast.makeText(ProductListActivity.this,e.toString(),Toast.LENGTH_SHORT);
                mSwipeRefreshLayout.setPullUpRefreshing(false);
            }

            @Override
            public void onSuccess(List<Product> response) {
                stopLoadingProgress();
                mSwipeRefreshLayout.setPullUpRefreshing(false);
                if (response.size() == 0) {
                    Toast.makeText(ProductListActivity.this,"没有更多了",Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(ProductListActivity.this,"还找到"+response.size()+"道菜",Toast.LENGTH_SHORT).show();
                for (Product p: response) {
                    productItemList.add(new ProductItem(p));
                }
                mProductListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProductBiz.onDestroy();
    }
}