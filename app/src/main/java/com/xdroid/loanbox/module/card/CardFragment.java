package com.xdroid.loanbox.module.card;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xdroid.loanbox.R;
import com.xdroid.loanbox.base.BaseFragment;
import com.xdroid.loanbox.module.adapter.NewsAdapter;
import com.xdroid.loanbox.module.bean.NewsBean;
import com.xdroid.loanbox.utils.DataControl;
import com.xdroid.loanbox.widgets.MyRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/22 0022.
 */

public class CardFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, MyRefreshLayout.OnLoadListener, AdapterView.OnItemClickListener{
    @BindView(R.id.lv_listview_data)
    ListView lvListviewData;
    @BindView(R.id.rl_listview_refresh)
    MyRefreshLayout mRefreshLayout;
    private NewsAdapter mAdapter;
    private List<NewsBean> listDatas;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_card;
    }

    @Override
    protected void initView() {
        initRefresh();
    }

    @Override
    public void initPresenter() {

    }

    private void initRefresh() {
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadListener(this);
        mRefreshLayout.setRefreshing(false);
        mRefreshLayout.setLoading(false);
        lvListviewData.setOnItemClickListener(this);
        listDatas = new ArrayList<>();
        listDatas.addAll(DataControl.getNewsData());
        mAdapter = new NewsAdapter(this.getContext(), listDatas);
        lvListviewData.setAdapter(mAdapter);
        lvListviewData.setOnItemClickListener(this);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onLoad() {

    }
}
