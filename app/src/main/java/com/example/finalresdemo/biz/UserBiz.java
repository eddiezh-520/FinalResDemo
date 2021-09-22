package com.example.finalresdemo.biz;

import com.example.finalresdemo.bean.User;
import com.example.finalresdemo.config.Config;
import com.example.finalresdemo.net.CommonCallback;
import com.zhy.http.okhttp.OkHttpUtils;

public class UserBiz {

    public void login(String username, String password,
                      CommonCallback<User> commonCallback) {

        OkHttpUtils.post()
                    .url(Config.baseUrl + "user_login")
                    .tag(this)
                    .addParams("username",username)
                    .addParams("password",password)
                    .build()
                    .execute(commonCallback);
    }


    public void register(String username, String password,
                      CommonCallback<User> commonCallback) {

        OkHttpUtils.post()
                .url(Config.baseUrl + "user_register")
                .tag(this)
                .addParams("username",username)
                .addParams("password",password)
                .build()
                .execute(commonCallback);
    }
}
