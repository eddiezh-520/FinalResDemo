package com.example.finalresdemo.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalresdemo.R;
import com.example.finalresdemo.UserHolder;
import com.example.finalresdemo.bean.User;
import com.example.finalresdemo.ui.adpater.OrderAdapter;
import com.example.finalresdemo.ui.view.CircleTransform;
import com.example.finalresdemo.ui.view.refresh.SwipeRefresh;
import com.example.finalresdemo.ui.view.refresh.SwipeRefreshLayout;
import com.squareup.picasso.Picasso;

public class OrderActivity extends BaseActivity {

    public static final String TAG = "OrderActivity";
    private ImageView mIvIcon;
    private TextView mTvUsername;
    private Button mBtnOrder;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        initView();
        initEvent();
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

        //set icon
        Picasso.with(this)
                .load(R.drawable.icon)
                .placeholder(R.drawable.pictures_no)
                .transform(new CircleTransform())
                .into(mIvIcon);
    }

    private void initEvent() {

    }
}