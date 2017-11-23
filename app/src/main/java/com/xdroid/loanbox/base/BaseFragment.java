package com.xdroid.loanbox.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.xdroid.loanbox.utils.SerMap;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by thomas on 2017/7/28.
 */

public abstract class BaseFragment extends Fragment {
    //根视图
    protected View rootView;
    //视图绑定
    private Unbinder mUnbinder;
    //当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
    private boolean isFragmentVisible;
    //是否是第一次开启网络加载
    public boolean isFirst;
    // 用于禁止用户重复点击
    private boolean clickable = true;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(getLayoutResource(), container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        initPresenter();
        initView();
        //可见，但是并没有加载过
        if (isFragmentVisible && !isFirst) {
            onFragmentVisibleChange(true);
        }
        return rootView;
    }

    /**
     * viewpager监听切换tab事件，tab切换一次，执行一次setUserVisibleHint()方法
     setUserVisibleHint() 在 上图所示fragment所有生命周期之前，无论viewpager是在activity哪个生命周期里初始化。
     activity生命周期 和 fragment生命周期 时序并不是按序来的，也就是说fragment的oncreate方法时序并不一定在activity的oncreate方法之后。
     **/
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isFragmentVisible = true;
        }
        if (rootView == null) {
            return;
        }
        //可见，并且没有加载过
        if (!isFirst&&isFragmentVisible) {
            onFragmentVisibleChange(true);
            return;
        }
        //由可见——>不可见 已经加载过
        if (isFragmentVisible) {
            onFragmentVisibleChange(false);
            isFragmentVisible = false;
        }
    }


    /**
     * 当前fragment可见状态发生变化时会回调该方法
     * 如果当前fragment是第一次加载，等待onCreateView后才会回调该方法，其它情况回调时机跟 {@link #setUserVisibleHint(boolean)}一致
     * 在该回调方法中你可以做一些加载数据操作，甚至是控件的操作.
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {

    }

    //获取布局文件
    protected abstract int getLayoutResource();


    //初始化view
    protected abstract void initView();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    @Override
    public void onResume() {
        super.onResume();
        clickable = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    /**
     * 锁定点击
     */
    protected void lockClick() {
        clickable = false;
    }

    /**页面跳转**/

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
}
