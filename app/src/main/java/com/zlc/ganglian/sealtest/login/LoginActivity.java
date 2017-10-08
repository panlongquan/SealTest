package com.zlc.ganglian.sealtest.login;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.zlc.ganglian.sealtest.BaseActivity;
import com.zlc.ganglian.sealtest.MainActivity;
import com.zlc.ganglian.sealtest.R;
import com.zlc.ganglian.sealtest.constant.Constant;
import com.zlc.ganglian.sealtest.tools.StrTool;
import com.zlc.ganglian.sealtest.tools.Tool;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText nameE;
    private EditText uidE;

    private String name;
    private String uid;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nameE = (EditText) findViewById(R.id.name);
        uidE = (EditText) findViewById(R.id.pwd);
        findViewById(R.id.login).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        name = nameE.getText().toString().trim();
        uid = uidE.getText().toString().trim();
        // 1. 调用app后台登录接口, 获取userinfo, 此处为模拟 TODO
        if ("panpan".equals(name) && "123456".equals(uid)) {
            token = Constant.TOKEN_PAN;
        } else if ("longlong".equals(name) && "654321".equals(uid)) {
            token = Constant.TOKEN_LONG;
        } else {
            return;
        }

        if (!StrTool.isEmpty(token)) {
            //保持token到本地
            getSharedPreferences("config", Context.MODE_PRIVATE)
                    .edit()
                    .putString(Constant.SP_TOKEN, token)
                    .apply();
            getSharedPreferences("config", Context.MODE_PRIVATE)
                    .edit()
                    .putString(Constant.SP_CURR_UID, uid)
                    .apply();
            getSharedPreferences("config", Context.MODE_PRIVATE)
                    .edit()
                    .putString(Constant.SP_CURR_NAME, name)
                    .apply();

            connect(token);
        }
    }

    private void connect(String token) {
        if (getApplicationInfo().packageName.equals(Tool.getCurProcessName(getApplicationContext()))) {
            //2. 拿到app后台登录接口返回的token后, 调用connect连接融云服务器，connect() 方法在整个应用只需要调用一次，且必须在主进程调用
            Log.i("plq", "主进程...");
            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {
                    Log.i("plq", "onTokenIncorrect");
                }

                @Override
                public void onSuccess(String uid) {
                    Log.i("plq", "融云登陆成功，uid: "+uid);
                    //3. 保存User对象到本地, 比如数据库 TODO

                    //4. 刷新这个userinfo给融云服务器
                    RongIM.getInstance().refreshUserInfoCache(new UserInfo(uid, name, null));
                    startActivity(createIntent(LoginActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.i("plq", "onError, "+errorCode);
                }
            });
        }
    }

}
