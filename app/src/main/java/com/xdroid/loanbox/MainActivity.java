package com.xdroid.loanbox;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.umeng.comm.ui.fragments.AllFeedsFragment;
import com.xdroid.loanbox.app.APP;
import com.xdroid.loanbox.base.BaseActivity;
import com.xdroid.loanbox.base.BaseFragment;
import com.xdroid.loanbox.module.adapter.MyViewPagerAdapter;
import com.xdroid.loanbox.utils.FragmentFactory;
import com.xdroid.loanbox.utils.ToastUtil;
import com.xdroid.loanbox.utils.UIUtils;
import com.xdroid.loanbox.widgets.ControlScrollViewPager;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.vp_content)
    ControlScrollViewPager mVpContent;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    private BaseFragment mHomeFragment,mCardFragment,mMyFragment;
    private AllFeedsFragment mFoundFragment;
    private MyViewPagerAdapter mFragmentAdapter;
    private List<Fragment> mFragmentList;
    private long mExitTime;


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        SetStatusBarColor(UIUtils.getColor(R.color.bottom_tip_text_color));
        mToolbar.setTitle("首页");
        initNavigationBar();
        initFragments();

    }

    private void initNavigationBar() {
        bottomNavigationBar.setAutoHideEnabled(false);
        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar
                .setActiveColor(R.color.bottom_tip_text_color)//选中
                .setInActiveColor(R.color.bottom_black_color)//没有选中
                .setBarBackgroundColor(R.color.white);//设置背景颜色
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.icon_loan_press, "首页").setInactiveIcon(ContextCompat.getDrawable(this, R.drawable.icon_loan_normal)))
                .addItem(new BottomNavigationItem(R.drawable.icon_news_press, "资讯").setInactiveIcon(ContextCompat.getDrawable(this, R.drawable.icon_news_normal)))
                .addItem(new BottomNavigationItem(R.drawable.icon_circle_press, "圈子").setInactiveIcon(ContextCompat.getDrawable(this, R.drawable.icon_circle_normal)))
                .addItem(new BottomNavigationItem(R.drawable.icon_my_press, "我的").setInactiveIcon(ContextCompat.getDrawable(this, R.drawable.icon_my_normal)))
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
            mFoundFragment = new AllFeedsFragment();
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
                mToolbar.setTitle("首页");
                mVpContent.setCurrentItem(0);
                break;
            case 1:
                mToolbar.setTitle("资讯");
                mVpContent.setCurrentItem(1);
                break;
            case 2:
                mToolbar.setTitle("圈子");
                mVpContent.setCurrentItem(2);
                break;
            case 3:
                mToolbar.setTitle("我的");
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

    /**
     * 连续点击退出
     *
     * @Title: onKeyDown
     * @Description: TODO
     * @Calls: TODO
     * @CalledBy: TODO
     * @Input:@param keyCode
     * @Input:@param event
     * @Input:@return
     * @Date: 下午3:20:51
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtil.show(this, "再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                APP.mInstance.exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
