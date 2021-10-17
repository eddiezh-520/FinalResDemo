package com.example.finalresdemo.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalresdemo.R;
import com.example.finalresdemo.bean.Order;
import com.example.finalresdemo.bean.Product;
import com.example.finalresdemo.config.Config;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderDetailActivity extends BaseActivity {

    private TextView productPrice,productTitle,productDesc;
    private ImageView productImage;
    private static final String KEY_ORDER = "key_order";
    private Order mOrder;

    public static void launch(Context context, Order order) {
        Intent intent = new Intent(context,OrderDetailActivity.class);
        intent.putExtra(KEY_ORDER,order);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        setUpToolBar();
        setTitle("详情");

        Intent intent = getIntent();
        if (intent != null) {
            mOrder = (Order) intent.getSerializableExtra(KEY_ORDER);
        }

        if (mOrder == null) {
            Toast.makeText(this,"数据传递失败",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initView();
    }

    private void initView() {
        productImage = findViewById(R.id.order_detail_image);
        productPrice = findViewById(R.id.order_detail_price);
        productTitle = findViewById(R.id.order_detail_title);
        productDesc = findViewById(R.id.order_detail_desc);

        Picasso.with(this)
                .load(Config.baseUrl + mOrder.getRestaurant().getIcon())
                .placeholder(R.drawable.pictures_no)
                .into(productImage);

        List<Order.ProductVo> ps = mOrder.getPs();
        StringBuilder sb = new StringBuilder();
        for (Order.ProductVo productVo: ps) {
            sb.append(productVo.product.getName())
                .append("*")
                .append(productVo.count)
                .append("\n");
        }

        productDesc.setText(sb.toString());
        productTitle.setText(mOrder.getRestaurant().getName());
        productPrice.setText("共消费"+ mOrder.getPrice() + "元");
    }
}