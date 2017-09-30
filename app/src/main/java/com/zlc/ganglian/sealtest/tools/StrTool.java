package com.zlc.ganglian.sealtest.tools;

import android.text.TextUtils;

/**
 * Created by panpan on 2017/9/30.
 */

public class StrTool {

    public static boolean isEmpty(String s){
        return TextUtils.isEmpty(s) || "null".equalsIgnoreCase(s);
    }
}
