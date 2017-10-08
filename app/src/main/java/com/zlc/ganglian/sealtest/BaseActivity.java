package com.zlc.ganglian.sealtest;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;


/**
 * Created by Administrator on 2017/10/3.
 *
 */
public class BaseActivity extends FragmentActivity {

    public static Intent createIntent(Context context, Class clazz){
        return new Intent(context, clazz);
    }

}
