package com.example.finalresdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalresdemo.bean.User;
import com.example.finalresdemo.biz.UserBiz;
import com.example.finalresdemo.net.CommonCallback;
import com.example.finalresdemo.utils.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;

public class LoginActivity extends BaseActivity {

    private final String TAG = "LoginActivity";
    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mbtLogin;
    private TextView mTvRegister;
    private UserBiz userBiz =new UserBiz();
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    @Override
    protected void onResume() {
        super.onResume();
        CookieJarImpl cookieJar = (CookieJarImpl)OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
        cookieJar.getCookieStore().removeAll();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initEvent();
        initIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        initIntent(intent);
    }

    private void initIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        String username = intent.getStringExtra(USERNAME);
        String password = intent.getStringExtra(PASSWORD);

        mEtUsername.setText(username);
        mEtPassword.setText(password);
    }

    private void initView() {
        mEtUsername = findViewById(R.id.id_et_username);
        mEtPassword = findViewById(R.id.id_et_password);
        mbtLogin = findViewById(R.id.id_btn_login);
        mTvRegister = findViewById(R.id.id_tv_register);
    }

    private void initEvent() {

        mbtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mEtUsername.getText().toString();
                String password = mEtPassword.getText().toString();
                Log.d(TAG,"test username:" + username + "\t password:" + password);
                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    displayToast(Constant.ACCOUNT_OR_PWD_EMPTY);
                    //不能继续往下执行
                    return;
                }

                startLoadingProgress();

                userBiz.login(username, password, new CommonCallback<User>() {
                    @Override
                    public void onError(Exception e) {
                        stopLoadingProgress();
                        displayToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(User response) {
                        stopLoadingProgress();
                        displayToast(Constant.LOGIN_SUCCESS);
                        //保存用户信息
                        UserHolder.getInstance().setUser(response);
                        toOrderActivity();
                    }
                });

            }
        });

        mTvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               toRegisterActivity();
            }
        });
    }

    private void toRegisterActivity() {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    private void toOrderActivity() {
        Intent intent = new Intent(this,OrderActivity.class);
        startActivity(intent);
        finish();
    }

    public static void launch(Context context, String username, String password) {
        Intent intent = new Intent(context,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(USERNAME,username);
        intent.putExtra(PASSWORD,password);
        context.startActivity(intent);
    }

    private void displayToast(String text) {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
}