package com.example.finalresdemo;

import android.text.TextUtils;

import com.example.finalresdemo.bean.User;
import com.example.finalresdemo.utils.SPUtils;

public class UserHolder {
    private static UserHolder mInstance = new UserHolder();
    private User mUser;
    private static final String KEY_USERNAME = "key_username";

    public static UserHolder getInstance() {
        return mInstance;
    }

    public void setUser(User user) {
        mUser = user;
        if (mUser != null) {
            String username = mUser.getUsername();
            SPUtils.getInstance().put(KEY_USERNAME,username);
        }
    }

    public User getUser() {
        User u = mUser;
        if (u == null) {
            String username = (String) SPUtils.getInstance().get(KEY_USERNAME,"");
            if (!TextUtils.isEmpty(username)) {
                u = new User();
                u.setUsername(username);
            }
        }

        mUser = u;
        return u;
    }

}
