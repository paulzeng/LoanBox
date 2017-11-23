package com.xdroid.loanbox;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.xdroid.loanbox.base.BaseActivity;
import com.xdroid.loanbox.base.BaseFragment;
import com.xdroid.loanbox.module.adapter.MyViewPagerAdapter;
import com.xdroid.loanbox.utils.FragmentFactory;
import com.xdroid.loanbox.utils.UIUtils;
import com.xdroid.loanbox.widgets.ControlScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    @BindView(R.id.vp_content)
    ControlScrollViewPager mVpContent;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    private BaseFragment mHomeFragment,mCardFragment,mFoundFragment,mMyFragment;
    private MyViewPagerAdapter mFragmentAdapter;
    private List<BaseFragment> mFragmentList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        SetStatusBarColor(UIUtils.getColor(R.color.color_bottom_green));
        initNavigationBar();
        initFragments();
    }



    private void initNavigationBar() {
        bottomNavigationBar.setAutoHideEnabled(false);
        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar
                .setActiveColor(R.color.color_bottom_green)//选中
                .setInActiveColor(R.color.bottom_black_color)//没有选中
                .setBarBackgroundColor(R.color.white);//设置背景颜色
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.icon_loan_pressed, "办贷款").setInactiveIcon(ContextCompat.getDrawable(this, R.drawable.icon_loan_normal)))
                .addItem(new BottomNavigationItem(R.drawable.icon_card_pressed, "办信用卡").setInactiveIcon(ContextCompat.getDrawable(this, R.drawable.icon_card__normal)))
                .addItem(new BottomNavigationItem(R.drawable.icon_found_pressed, "发现").setInactiveIcon(ContextCompat.getDrawable(this, R.drawable.icon_found_normal)))
                .addItem(new BottomNavigationItem(R.drawable.icon_me_pressed, "我的").setInactiveIcon(ContextCompat.getDrawable(this, R.drawable.icon_me_normal)))
                .setFirstSelectedPosition(0)
                .initialise();
        mVpContent.setCurrentItem(0);
    }

    private void initFragments() {
        mFragmentList = new ArrayList<>();
        if(mHomeFragment==null){
            mHomeFragment = FragmentFactory.create(FragmentFactory.TYPE_HOME);
            mFragmentList.add(mHomeFragment);
        }
        if(mCardFragment==null){
            mCardFragment = FragmentFactory.create(FragmentFactory.TYPE_CARD);
            mFragmentList.add(mCardFragment);
        }
        if(mFoundFragment==null){
            mFoundFragment = FragmentFactory.create(FragmentFactory.TYPE_FOUND);
            mFragmentList.add(mFoundFragment);
        }
        if(mMyFragment==null){
            mMyFragment = FragmentFactory.create(FragmentFactory.TYPE_ME);
            mFragmentList.add(mMyFragment);
        }
        if (mFragmentAdapter == null) {
            mFragmentAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), mFragmentList);
        } else {
            //刷新fragment
            mFragmentAdapter.setFragments(getSupportFragmentManager(), mFragmentList, null);
        }
        mVpContent.setAdapter(mFragmentAdapter);
        mVpContent.setNoScroll(true);
    }


    @Override
    public void onTabSelected(int position) {
        switch (position) {
            case 0:
                mVpContent.setCurrentItem(0);
                break;
            case 1:
                mVpContent.setCurrentItem(1);
                break;
            case 2:
                mVpContent.setCurrentItem(2);
                break;
            case 3:
                mVpContent.setCurrentItem(3);
                break;
        }

    }


    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
