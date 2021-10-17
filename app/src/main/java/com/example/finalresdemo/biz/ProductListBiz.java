package com.example.finalresdemo.biz;

import com.example.finalresdemo.bean.Order;
import com.example.finalresdemo.bean.Product;
import com.example.finalresdemo.config.Config;
import com.example.finalresdemo.net.CommonCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

public class ProductListBiz {


    public void listByPage(int currentPage, CommonCallback<List<Product>> callback) {
        OkHttpUtils
                .post()
                .url(Config.baseUrl + "product_find")
                .tag(this)
                .addParams("currentPage", currentPage + "")
                .build()
                .execute(callback);
    }

    public void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(this);
    }

}
