package com.example.finalresdemo.biz;

import com.example.finalresdemo.bean.Order;

import com.example.finalresdemo.bean.Product;
import com.example.finalresdemo.config.Config;
import com.example.finalresdemo.net.CommonCallback;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;
import java.util.Map;

public class OrderBiz {

    public void listByPage(int currentPage, CommonCallback<List<Order>> commonCallback) {

        OkHttpUtils.post()
                .url(Config.baseUrl + "order_find")
                .tag(this)
                .addParams("currentPage",currentPage + "")
                .build()
                .execute(commonCallback);
    }


    public void add(Order order, CommonCallback<String> commonCallback) {

        StringBuilder sb = new StringBuilder();
        Map<Product,Integer> productMap = order.productMap;
        for (Product p : productMap.keySet()) {
            sb.append(p.getId()+"_"+productMap.get(p));
            sb.append("|");
        }
        sb = sb.deleteCharAt(sb.length() -1 );
        OkHttpUtils.post()
                .url(Config.baseUrl + "order_add")
                .tag(this)
                .addParams("res_id",order.getRestaurant().getId() + "")
                .addParams("product_str",sb.toString())
                .addParams("count",order.getCount() + "")
                .addParams("price",order.getPrice() + "")
                .build()
                .execute(commonCallback);
    }

}
