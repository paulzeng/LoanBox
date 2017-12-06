package com.umeng.comm.ui.imagepicker.presenter.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.umeng.comm.core.beans.Topic;
import com.umeng.comm.core.constants.Constants;
import com.umeng.comm.core.listeners.Listeners;
import com.umeng.comm.core.nets.responses.TopicResponse;
import com.umeng.comm.core.nets.uitls.NetworkUtils;
import com.umeng.comm.core.utils.CommonUtils;
import com.umeng.comm.ui.imagepicker.mvpview.MvpRecommendTopicView;

import java.util.List;

/**
 * Created by wangfei on 15/12/1.
 */
public class FocusTopicFgPresenter extends TopicFgPresenter {


    private boolean mIsAttach;

    public FocusTopicFgPresenter(MvpRecommendTopicView recommendTopicView) {
        super(recommendTopicView);
    }

    @Override
    public void attach(Context context) {
        super.attach(context);
        if(!mIsAttach){
            registerLoginSuccessBroadcast();
            mIsAttach = true;
        }
    }

    @Override
    public void detach() {
        super.detach();
        if(mIsAttach){
            mContext.unregisterReceiver(mLoginReceiver);
        }
    }

    /**
     * 注册登录成功时的广播</br>
     */
    private void registerLoginSuccessBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ACTION_LOGIN_SUCCESS);
        mContext.registerReceiver(mLoginReceiver, filter);
    }

    private BroadcastReceiver mLoginReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            loadDataFromServer();
        }
    };

    @Override
    public void loadDataFromServer() {

        if (CommonUtils.getLoginUser(mContext) != null) {

            mCommunitySDK.fetchFollowedTopics(CommonUtils.getLoginUser(mContext).id, new Listeners.FetchListener<TopicResponse>() {

                @Override
                public void onStart() {

                    mRecommendTopicView.onRefreshStart();
                }

                @Override
                public void onComplete(final TopicResponse response) {

                    // 根据response进行Toast
                    if (NetworkUtils.handleResponseAll(response)) {
                        //  如果是网络错误，其结果可能快于DB查询
                        if (CommonUtils.isNetworkErr(response.errCode)) {
                            mRecommendTopicView.onRefreshEndNoOP();
                        } else {
                            mRecommendTopicView.onRefreshEnd();
                        }
                        return;
                    }

//                clearTopicCacheAfterFirstRefresh();
                    mDatabaseAPI.getTopicDBAPI().deleteAllTopics();
                    final List<Topic> results = response.result;
                    updateNextPageUrl(results.get(0).nextPage);
                    dealNextPageUrl(response.nextPageUrl, true);
                    fetchTopicComplete(results, true);
                    mRecommendTopicView.onRefreshEnd();
                }
            });
        } else {
//            Intent intent = new Intent(mContext, LoginActivity.class);
//            mContext.startActivity(intent);
        }
    }
}
