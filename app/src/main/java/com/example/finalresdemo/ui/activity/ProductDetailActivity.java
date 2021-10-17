package com.example.finalresdemo.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalresdemo.R;
import com.example.finalresdemo.bean.Product;
import com.example.finalresdemo.config.Config;
import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends BaseActivity {

    private TextView productPrice,productTitle,productDesc;
    private ImageView productImage;
    private static final String KEY_PRODUCT = "key_product";
    private Product product;

    public static void launch(Context context, Product product) {
        Intent intent = new Intent(context,ProductDetailActivity.class);
        intent.putExtra(KEY_PRODUCT,product);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        setUpToolBar();
        setTitle("详情");

       Intent intent = getIntent();
       if (intent != null) {
           product = (Product)intent.getSerializableExtra(KEY_PRODUCT);
       }

       if (product == null) {
           Toast.makeText(this,"数据传递失败",Toast.LENGTH_SHORT).show();
           return;
       }

        initView();
    }

    private void initView() {
        productImage = findViewById(R.id.product_detail_image);
        productPrice = findViewById(R.id.product_detail_price);
        productTitle = findViewById(R.id.product_detail_title);
        productDesc = findViewById(R.id.product_detail_desc);

        Picasso.with(this)
                .load(Config.baseUrl + product.getIcon())
                .placeholder(R.drawable.pictures_no)
                .into(productImage);

        productPrice.setText(product.getPrice() + "");
        productTitle.setText(product.getName());
        productPrice.setText(product.getPrice() + "元/份");
    }
}