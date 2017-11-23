package com.xdroid.loanbox.module.home;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.aspsine.irecyclerview.IRecyclerView;
import com.xdroid.loanbox.R;
import com.xdroid.loanbox.base.BaseFragment;
import com.xdroid.loanbox.module.adapter.HomeListAdapter;
import com.xdroid.loanbox.utils.DataControl;
import com.xdroid.loanbox.utils.GlideLoader;
import com.xdroid.loanbox.utils.ToastUtil;
import com.xdroid.loanbox.utils.UIUtils;
import com.xdroid.loanbox.widgets.TitleBar;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/22 0022.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    Banner mBanner;
    private View headerView;
    private HomeListAdapter homeAdapter;
    @BindView(R.id.irv_home)
    IRecyclerView irvHome;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void initPresenter() {

    }

    private void setTitle(){
        titleBar.setTitle("优质贷款");
        titleBar.setTitleColor(R.color.white);
    }

    private void initListView(){
        headerView = UIUtils.inflate(R.layout.layout_header_view);
        irvHome.addHeaderView(headerView);
        mBanner = (Banner) headerView.findViewById(R.id.home_banner);
        homeAdapter = new HomeListAdapter(this.getContext(), DataControl.getTestData());
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        irvHome.setLayoutManager(manager);
        irvHome.setIAdapter(homeAdapter);
    }

    private void initBanner() {
        if (mBanner != null) {
            mBanner.setLayoutParams(new LinearLayout.LayoutParams(
                    UIUtils.getWindowWidth(), UIUtils.getWindowWidth() * 2 / 5));
            mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            mBanner.setImageLoader(new GlideLoader());
            mBanner.setImages(DataControl.getBannerData());
            mBanner.setBannerAnimation(Transformer.Default);
            mBanner.isAutoPlay(true);
            mBanner.setDelayTime(1000 * 10);
            mBanner.setIndicatorGravity(BannerConfig.CENTER);
            mBanner.start();
        } else {
            ToastUtil.show(this.getContext(), "null");
        }
    }
    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            //可见，并且是第一次加载
            if (!isFirst) {
                isFirst = true;
            }
            setTitle();
            initListView();
            initBanner();
        } else {
            //取消加载
        }
    }
}
