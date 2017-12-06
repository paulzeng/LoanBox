package com.xdroid.loanbox.module.home;

import android.accounts.AccountManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.google.gson.Gson;
import com.xdroid.loanbox.R;
import com.xdroid.loanbox.base.BaseFragment;
import com.xdroid.loanbox.models.AppListResult;
import com.xdroid.loanbox.module.adapter.HomeListAdapter;
import com.xdroid.loanbox.module.bean.LoanBean;
import com.xdroid.loanbox.module.bean.ResponseResult;
import com.xdroid.loanbox.module.webview.CustomWebviewActivity;
import com.xdroid.loanbox.network.NetworkListener;
import com.xdroid.loanbox.network.NetworkServer;
import com.xdroid.loanbox.utils.Constants;
import com.xdroid.loanbox.utils.DataControl;
import com.xdroid.loanbox.utils.GlideLoader;
import com.xdroid.loanbox.utils.LogUtil;
import com.xdroid.loanbox.utils.StringUtils;
import com.xdroid.loanbox.utils.ToastUtil;
import com.xdroid.loanbox.utils.UIUtils;
import com.xdroid.loanbox.widgets.TitleBar;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/22 0022.
 */

public class HomeFragment extends BaseFragment implements OnRefreshListener,OnLoadMoreListener,HomeListAdapter.onItemClickListenr {

    Banner mBanner;
    private View headerView;
    private HomeListAdapter homeAdapter;
    @BindView(R.id.irv_home)
    IRecyclerView irvHome;
    private String dataStr;
    private List<AppListResult.AppModel> data = new ArrayList<>();
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        setTitle();
    }

    @Override
    public void initPresenter() {

    }

    private void setTitle(){
        /*dataStr = StringUtils.getJsonOnFile(this.getContext(),"data.json");
        Gson gson = new Gson();
        ResponseResult result = gson.fromJson(dataStr,ResponseResult.class);
        LogUtil.e("TAG","读取的JSON=="+result.toString());
        data = result.getData();*/
    }

    private void initListView(){
        headerView = UIUtils.inflate(R.layout.layout_header_view);
        irvHome.addHeaderView(headerView);
        mBanner = (Banner) headerView.findViewById(R.id.home_banner);
        homeAdapter = new HomeListAdapter(this.getContext(), data);
        homeAdapter.setOnItemClickListenr(this);
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        irvHome.setLayoutManager(manager);
        irvHome.setIAdapter(homeAdapter);
        getData("0","50000");
        //initBanner();
    }

    private void getData(String min,String max){
        showDialog();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("min", min);
        params.put("max", max);
        NetworkServer.request(Constants.URL_BASE + "api/list.json", params, AppListResult.class, new NetworkListener<AppListResult>() {
            @Override
            public void onSuccess(AppListResult response) {
                cancelDialog();
                LogUtil.e("TAG", "获取贷款APP列表信息==" + response.toString());
                data.addAll(response.data);
                homeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int errorCode, String errorMsg, AppListResult response) {
                cancelDialog();
                LogUtil.e("TAG", "获取贷款APP列表失败==" + errorCode + "||" + errorMsg);
                ToastUtil.show(HomeFragment.this.getContext(), errorMsg);
            }
        });
    }

    private void initBanner() {
        if (mBanner != null) {
            mBanner.setLayoutParams(new LinearLayout.LayoutParams(
                    UIUtils.getWindowWidth(), UIUtils.getWindowWidth() * 1 / 3));
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
            initListView();
        } else {
            //取消加载
        }
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onItemClick(int position) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("model",data.get(position));
        startActivityWithParam(this.getContext(), CustomWebviewActivity.class,map);
    }
}
