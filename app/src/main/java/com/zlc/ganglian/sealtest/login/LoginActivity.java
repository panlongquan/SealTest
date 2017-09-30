package com.zlc.ganglian.sealtest.login;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.zlc.ganglian.sealtest.R;
import com.zlc.ganglian.sealtest.constant.Constant;
import com.zlc.ganglian.sealtest.tools.StrTool;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nameE;
    private EditText pwdE;

    private String name;
    private String pwd;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nameE = (EditText) findViewById(R.id.name);
        pwdE = (EditText) findViewById(R.id.pwd);
        findViewById(R.id.login).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        name = nameE.getText().toString().trim();
        pwd = pwdE.getText().toString().trim();
        // 1. 调用app后台登录接口, 获取userinfo
        token = getSharedPreferences("config", Context.MODE_PRIVATE).getString(Constant.SP_TOKEN, "");

        if (StrTool.isEmpty(token)) {
            if ("panpan".equals(name) && "123456".equals(pwd)) {
                token = Constant.TOKEN;
                getSharedPreferences("config", Context.MODE_PRIVATE)
                        .edit()
                        .putString(Constant.SP_TOKEN, token)
                        .apply();

                connect();
            }
        }
    }

    private void connect() {
        //2. 拿到app后台登录接口返回的token后, 调用connect连接融云服务器
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
            }

            @Override
            public void onSuccess(String uid) {
                getSharedPreferences("config", Context.MODE_PRIVATE)
                        .edit()
                        .putString(Constant.SP_CURR_UID, uid)
                        .apply();

                //3. 保存User对象到本地, 比如数据库 TODO

                //4. 刷新这个userinfo给融云服务器
                RongIM.getInstance().refreshUserInfoCache(new UserInfo(uid, name, null));
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
            }
        });
    }
}
