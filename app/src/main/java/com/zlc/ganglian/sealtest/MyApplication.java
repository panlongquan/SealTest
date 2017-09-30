package com.zlc.ganglian.sealtest;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.view.View;

import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;

/**
* Description: 
*
* Author panpan
* Created at 2017/9/30 10:33 
*/
public class MyApplication extends Application implements RongIM.OnSendMessageListener, RongIMClient.OnReceiveMessageListener, RongIMClient.ConnectionStatusListener, RongIM.ConversationListBehaviorListener, RongIM.UserInfoProvider {

    @Override
    public void onCreate() {
        super.onCreate();
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {
            //1.推送集成, 在init之前
            initPush();
            /**
             * 注意：
             *
             * IMKit SDK调用第一步 初始化
             *
             * context上下文
             *
             * 只有两个进程需要初始化，主进程和 push 进程
             */
            RongIM.setServerInfo("nav.cn.ronghub.com", "up.qbox.me");
            RongIM.init(this);
            //设置
            setListener();
        }
    }

    private void initPush() {
//        RongPushClient.registerHWPush(this);//华为推送
//        RongPushClient.registerMiPush(this, "2882303761517473625", "5451747338625");//小米推送
//        try {
//            RongPushClient.registerGCM(this);//Google推送
//        } catch (RongException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 获取当前进程名
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    private void setListener() {
        //设置发送消息监听
        RongIM.getInstance().setSendMessageListener(this);
        RongIM.setOnReceiveMessageListener(this);
        RongIM.setConnectionStatusListener(this);
        RongIM.setConversationListBehaviorListener(this);
        RongIM.setUserInfoProvider(this, true);
    }


    /******************
     * 发送消息监听
     * 消息发送失败可在 onSent 方法中根据 SentMessageErrorCode 返回的状态码实现自己的逻辑处理。
     * onSent 返回 true 表示走自己的处理方式，否则走融云默认处理方式。
     * *********************/
    @Override
    public Message onSend(Message message) {
        return null;
    }

    @Override
    public boolean onSent(Message message, RongIM.SentMessageErrorCode sentMessageErrorCode) {
        return false;
    }


    /***********************
     * 接受消息监听
     * 接收消息监听器的实现，所有接收到的消息、通知、状态都经由此处设置的监听器处理。
     * 包括私聊消息、讨论组消息、群组消息、聊天室消息以及各种状态。
     * ******************************/
    @Override
    public boolean onReceived(Message message, int i) {
        return false;
    }

    /*****************
     * 连接状态监听
     * ************************/
    @Override
    public void onChanged(ConnectionStatus connectionStatus) {
        switch (connectionStatus){
            case CONNECTED://连接成功。

                break;
            case DISCONNECTED://断开连接。

                break;
            case CONNECTING://连接中。

                break;
            case NETWORK_UNAVAILABLE://网络不可用。

                break;
            case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线

                break;
        }
    }

    /*********************
     * 会话列表监听
     *
     * *************************/
    @Override
    public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String s) {
        /**
         * 当点击会话头像后执行。
         *
         * @param context          上下文。
         * @param conversationType 会话类型。
         * @param targetId         被点击的用户id。
         * @return 如果用户自己处理了点击后的逻辑处理，则返回 true，否则返回 false，false 走融云默认处理方式。
         */
        return false;
    }

    @Override
    public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String s) {
        /**
         * 当长按会话头像后执行。
         *
         * @param context          上下文。
         * @param conversationType 会话类型。
         * @param targetId         被点击的用户id。
         * @return 如果用户自己处理了点击后的逻辑处理，则返回 true，否则返回 false，false 走融云默认处理方式。
         */
        return false;
    }

    @Override
    public boolean onConversationLongClick(Context context, View view, UIConversation uiConversation) {
        /**
         * 长按会话列表中的 item 时执行。
         *
         * @param context        上下文。
         * @param view           触发点击的 View。
         * @param uiConversation 长按时的会话条目。
         * @return 如果用户自己处理了长按会话后的逻辑处理，则返回 true， 否则返回 false，false 走融云默认处理方式。
         */
        return false;
    }

    @Override
    public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {
        /**
         * 点击会话列表中的 item 时执行。
         *
         * @param context        上下文。
         * @param view           触发点击的 View。
         * @param uiConversation 会话条目。
         * @return 如果用户自己处理了点击会话后的逻辑处理，则返回 true， 否则返回 false，false 走融云默认处理方式。
         */
        return false;
    }

    /*****************************************************
     * 用户信息提供者, WEIBO融云服务器提供最新userinfo
     */
    @Override
    public UserInfo getUserInfo(String s) {
        // TODO 从本地获取最新userinfo, loginActivity中保存了, 或者修改用户名等操作修改了userinfo的值
        return null;
    }

}