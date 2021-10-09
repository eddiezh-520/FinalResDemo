package com.example.finalresdemo.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.finalresdemo.R;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.finalresdemo.bean.User;
import com.example.finalresdemo.biz.UserBiz;
import com.example.finalresdemo.net.CommonCallback;
import com.example.finalresdemo.utils.Constant;

public class RegisterActivity extends BaseActivity {

    private static final String TAG = "RegisterActivity";
    private EditText mEtUsername;
    private EditText mEtPassword;
    private EditText mEtRepassword;
    private Button mBtnRegister;
    private UserBiz userBiz = new UserBiz();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setUpToolBar();
        initView();
        initEvent();
        setTitle("注册");
    }



    private void initView() {
        mEtUsername = findViewById(R.id.id_et_username);
        mEtPassword = findViewById(R.id.id_et_password);
        mEtRepassword = findViewById(R.id.id_et_repassword);
        mBtnRegister = findViewById(R.id.id_btn_register);
    }

    private void initEvent() {
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mEtUsername.getText().toString();
                String password = mEtPassword.getText().toString();
                String repassword = mEtRepassword.getText().toString();

                Log.d(TAG,"test username:" + username + "\t password:" + password
                        + "\t repassword:" + repassword);
                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    displayToast(Constant.ACCOUNT_OR_PWD_EMPTY);
                    //不能继续往下执行
                    return;
                }

                if (!password.equals(repassword)) {
                    displayToast(Constant.REGISTER_PWD_NOT_MATCH);
                    return;
                }

                startLoadingProgress();

                userBiz.register(username, password, new CommonCallback<User>() {
                    @Override
                    public void onError(Exception e) {
                        stopLoadingProgress();
                        displayToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(User response) {
                        stopLoadingProgress();
                        displayToast(Constant.REGISTER_SUCCESS);
                        LoginActivity.launch(RegisterActivity.this,response.getUsername(),response.getPassword());
                    }
                });
            }
        });
    }


    private void displayToast(String text) {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

}