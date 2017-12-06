package com.xdroid.loanbox.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.L;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.sdkmanager.LocationSDKManager;
import com.umeng.community.location.DefaultLocationImpl;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;
import com.xdroid.loanbox.module.bean.UserBean;
import com.xdroid.loanbox.utils.Constants;
import com.xdroid.loanbox.utils.LogUtil;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

/**
 * Created by thomas on 2017/7/28.
 */

public class APP extends Application {
    public static APP mInstance;
    private static Context context;
    private static int mainThreadId;
    private static Handler handler;
    private static APP sApplication;
    public static List<Activity> allActivity = new LinkedList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context = getApplicationContext();
        sApplication = this;
        mainThreadId = android.os.Process.myTid();// 获取当前主线程id
        handler = new Handler();
        initImageLoader(context);
        initPush();
        initUmengCommunity();
        initBmob();
    }

    /**
     * 初始化Bmob
     */
    private void initBmob(){
        Bmob.initialize(this, Constants.BMOB_KEY);
    }

    /**
     * 获取当前用户
     * @return
     */
    public static UserBean getCurrentUser(){
        UserBean bmobUser = BmobUser.getCurrentUser(UserBean.class);
        if(bmobUser != null){
            // 允许用户使用应用
            return bmobUser;
        }else{
            //缓存用户对象为空时， 可打开用户注册界面…
            return null;
        }
    }

    public static APP getInstance() {
        return sApplication;
    }
    /**
     * 初始化图片加载
     *
     * @param context 上下文对象
     */
    public void initImageLoader(Context context) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(
                getApplicationContext(), "xuecheyi/Coach/Cache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .threadPoolSize(3).denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCacheFileCount(100)
                .diskCache(new UnlimitedDiskCache(cacheDir)).build();
        L.writeLogs(false);
        ImageLoader.getInstance().init(config);
    }


    private void initUmengCommunity(){
        CommunitySDK mCommSDK = CommunityFactory.getCommSDK(context);
        mCommSDK.initSDK(context);
        // 设置地理位置SDK
        LocationSDKManager.getInstance().addAndUse(new DefaultLocationImpl());
    }

    public void initPush() {
        PushAgent.getInstance(this).setDebugMode(true);
        PushAgent.getInstance(this).enable();
        //String device_token = UmengRegistrar.getRegistrationId(this);
        //LogUtil.e("####", "###device_token###" + device_token);
        //开启推送并设置注册的回调处理
        PushAgent.getInstance(this).enable(new IUmengRegisterCallback() {

            @Override
            public void onRegistered(final String registrationId) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        //onRegistered方法的参数registrationId即是device_token
                        LogUtil.e("####", "###device_token###" + registrationId);
                    }
                });
            }
        });
        PushAgent.getInstance(this).setMessageHandler(new UmengMessageHandler() {
            @Override
            public void dealWithNotificationMessage(Context arg0, UMessage msg) {
                // 调用父类方法,这里会在通知栏弹出提示信息
                super.dealWithNotificationMessage(arg0, msg);
                LogUtil.e("", "### 自行处理推送消息");
            }
        });
    }

    /**
     * 销毁所有activity，应用退出
     */
    public void exit() {
        for (Activity activity : allActivity) {
            activity.finish();
        }
        //MobclickAgent.onKillProcess(this);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static Context getContext() {
        return context;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }

    public static Handler getHandler() {
        return handler;
    }


}
