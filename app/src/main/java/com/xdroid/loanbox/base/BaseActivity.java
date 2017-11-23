package com.xdroid.loanbox.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.xdroid.loanbox.R;
import com.xdroid.loanbox.app.AppManager;
import com.xdroid.loanbox.utils.SerMap;
import com.xdroid.loanbox.utils.StatusBarSetting;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by thomas on 2017/7/28.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public Context mContext;
    private Unbinder mUnbinder;
    // 用于禁止用户重复点击
    private boolean clickable = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeSetcontentView();
        setContentView(getLayoutId());
        // 默认着色状态栏
        SetStatusBarColor();
        mUnbinder = ButterKnife.bind(this);
        mContext = this;
        this.initPresenter();
        this.initView();

    }

    /**
     * 设置layout前配置
     */
    private void doBeforeSetcontentView() {
        // 把actvity放到application栈中管理
        AppManager.getAppManager().addActivity(this);
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /*********************
     * 子类实现
     *****************************/
    //获取布局文件
    public abstract int getLayoutId();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    //初始化view
    public abstract void initView() ;

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor() {
        StatusBarSetting.setColor(this, getResources().getColor(R.color.colorPrimary));
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor(int color) {
        StatusBarSetting.setColor(this, color);
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void SetTranslanteBar() {
        StatusBarSetting.setTranslucent(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        clickable = true;
    }

    /**页面跳转**/
    /**
     * 锁定点击
     */
    protected void lockClick() {
        clickable = false;
    }
    /**
     * 当前是否可以点击
     *
     * @return
     */
    protected boolean isClickable() {
        return clickable;
    }
    /**
     * @param packageContext from,一般传XXXActivity.this
     * @param cls            to,一般传XXXActivity.class
     * @Description: 跳转
     */
    public void startActivity(Context packageContext, Class<?> cls) {
        if (isClickable()) {
            lockClick();
            Intent i = new Intent(packageContext, cls);
            packageContext.startActivity(i);
        }
    }
    /**
     * @param packageContext from,一般传XXXActivity.this
     * @param cls            to,一般传XXXActivity.class
     * @Description: 跳转
     */
    public void startActivityWithResult(Context packageContext, Class<?> cls, int requstCode) {
        if (isClickable()) {
            lockClick();
            Intent i = new Intent(packageContext, cls);
            ((Activity) packageContext).startActivityForResult(i, requstCode);
        }
    }

    /**
     * @param packageContext from,一般传XXXActivity.this
     * @param cls            to,一般传XXXActivity.class
     * @Description: 跳转
     */
    public void startActivityAndFinish(Context packageContext, Class<?> cls) {
        if (isClickable()) {
            lockClick();
            Intent i = new Intent(packageContext, cls);
            packageContext.startActivity(i);
            ((Activity) packageContext).finish();
        }
    }

    /**
     * 跳转activity，并传递参数
     *
     * @param packageContext
     * @param cls
     * @param params
     */
    public void startActivityWithParam(Context packageContext, Class<?> cls, HashMap<String, Object> params) {
        if (isClickable()) {
            lockClick();
            Intent i = new Intent(packageContext, cls);
            SerMap serMap = new SerMap();
            //传递map到SerMap 中的map，这样数据就会传递到SerMap 中的map中。
            serMap.setMap(params);
            //创建Bundle对象，存放实现可序列化的SerMap
            Bundle bundle = new Bundle();
            bundle.putSerializable("KEY", serMap);
            //意图放置bundle变量
            i.putExtras(bundle);
            packageContext.startActivity(i);
        }
    }

    /**
     * 获取传递的参数
     *
     * @param bundle
     * @return
     */
    public HashMap<String, Object> getIntentParams(Bundle bundle) {
        if (bundle != null) {
            SerMap serializableMap = (SerMap) bundle
                    .get("KEY");
            return serializableMap.getMap();
        } else {
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        AppManager.getAppManager().finishActivity(this);
    }

}
