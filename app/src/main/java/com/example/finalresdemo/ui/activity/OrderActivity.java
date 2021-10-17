package com.example.finalresdemo.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalresdemo.R;
import com.example.finalresdemo.UserHolder;
import com.example.finalresdemo.bean.Order;
import com.example.finalresdemo.bean.User;
import com.example.finalresdemo.biz.OrderBiz;
import com.example.finalresdemo.net.CommonCallback;
import com.example.finalresdemo.ui.adpater.OrderAdapter;
import com.example.finalresdemo.ui.view.CircleTransform;
import com.example.finalresdemo.ui.view.refresh.SwipeRefresh;
import com.example.finalresdemo.ui.view.refresh.SwipeRefreshLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends BaseActivity {

    public static final String TAG = "OrderActivity";
    private ImageView mIvIcon;
    private TextView mTvUsername;
    private Button mBtnOrder;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private OrderAdapter orderAdapter;
    private List<Order> mDatas = new ArrayList<>();
    private OrderBiz mOrderBiz = new OrderBiz();
    private int mCurrentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        initView();
        initEvent();
        loadData();
    }

    private void initView() {
        mIvIcon = findViewById(R.id.id_iv_icon);
        mTvUsername = findViewById(R.id.id_tv_username);
        mBtnOrder = findViewById(R.id.id_btn_order);
        mSwipeRefreshLayout = findViewById(R.id.id_swipeRefresh);
        mRecyclerView = findViewById(R.id.id_recyclerview);

        //set username
        User user = UserHolder.getInstance().getUser();
        if (user != null) {
            mTvUsername.setText(user.getUsername());
        } else {
            toLoginActivity();
            finish();
            return;
        }

        //set swiperefreshLayout
        mSwipeRefreshLayout.setMode(SwipeRefresh.Mode.BOTH);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,Color.GREEN,Color.YELLOW);

        //set Recyclerview
        orderAdapter = new OrderAdapter(this,mDatas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(orderAdapter);
        //set icon
        Picasso.with(this)
                .load(R.drawable.icon)
                .placeholder(R.drawable.pictures_no)
                .transform(new CircleTransform())
                .into(mIvIcon);
    }

    private void initEvent() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefresh.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        mSwipeRefreshLayout.setOnPullUpRefreshListener(new SwipeRefreshLayout.OnPullUpRefreshListener() {
            @Override
            public void onPullUpRefresh() {
                loadMore();
            }
        });

        mBtnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this,ProductListActivity.class);
                startActivityForResult(intent,1001);
            }
        });
    }

    private void loadData() {
        startLoadingProgress();
        mOrderBiz.listByPage(0, new CommonCallback<List<Order>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                Toast.makeText(OrderActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onSuccess(List<Order> response) {
                stopLoadingProgress();
                Toast.makeText(OrderActivity.this,"订单更新成功",Toast.LENGTH_SHORT).show();
                mCurrentPage = 0;
                mDatas.clear();
                mDatas.addAll(response);
                Log.d(TAG,"test mDatas:" + mDatas.toString());
                orderAdapter.notifyDataSetChanged();
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void loadMore() {
        startLoadingProgress();
        mOrderBiz.listByPage(++mCurrentPage, new CommonCallback<List<Order>>() {
            @Override
            public void onError(Exception e) {
                stopLoadingProgress();
                Toast.makeText(OrderActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                mCurrentPage --;
                mSwipeRefreshLayout.setPullUpRefreshing(false);
            }

            @Override
            public void onSuccess(List<Order> response) {
                stopLoadingProgress();
                if (response.size() == 0) {
                    Toast.makeText(OrderActivity.this,"已经没有更多订单了",Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(OrderActivity.this,"订单加载成功",Toast.LENGTH_SHORT).show();
                mDatas.addAll(response);
                Log.d(TAG,"test mDatas:" + mDatas.toString());
                orderAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setPullUpRefreshing(false);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOrderBiz.onDestroy();
    }
}