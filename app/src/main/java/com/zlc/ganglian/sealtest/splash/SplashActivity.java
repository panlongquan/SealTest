package com.zlc.ganglian.sealtest.splash;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.zlc.ganglian.sealtest.BaseActivity;
import com.zlc.ganglian.sealtest.MainActivity;
import com.zlc.ganglian.sealtest.R;
import com.zlc.ganglian.sealtest.constant.Constant;
import com.zlc.ganglian.sealtest.login.LoginActivity;
import com.zlc.ganglian.sealtest.tools.StrTool;

import java.util.Timer;
import java.util.TimerTask;

import io.rong.imkit.RongIM;
import io.rong.imkit.utils.StringUtils;
import io.rong.imlib.RongIMClient;

public class SplashActivity extends BaseActivity {

    private Timer mTimer;
    private int time = 0;
    private TextView tv_splash_time;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    int time = (int) msg.obj;
                    if (time >= 4) {
                        mTimer.cancel();
                        mTimer = null;
                        jump();
                    } else {
                        tv_splash_time.setText(getString(R.string.splash_time, time+""));
                    }
                    break;
            }
        }
    };

    private void jump() {
        SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        String token = sp.getString(Constant.SP_TOKEN, "");
        Log.i("plq", "token: "+token);
        if (StrTool.isEmpty(token)) {
            startActivity(createIntent(this, LoginActivity.class));
        } else {
            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {
                    Log.i("plq", "融云登陆成功，onTokenIncorrect: ");
                }

                @Override
                public void onSuccess(String s) {
                    Log.i("plq", "融云登陆成功，uid: "+s);
                    startActivity(createIntent(SplashActivity.this, MainActivity.class));
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.i("plq", "融云登陆成功，onError: ");
                }
            });
        }
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv_splash_time = findViewById(R.id.tv_splash_time);
        time = 0;

        if (mTimer == null) {
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                time++;
                Message message = mHandler.obtainMessage(1);
                message.obj = time;
                mHandler.sendMessage(message);
            }
        }, 1000, 1000);
    }
}
