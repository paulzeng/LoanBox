/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Umeng, Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.umeng.comm.ui.fragments;

import java.util.List;

import android.content.ComponentName;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.umeng.comm.core.beans.Topic;
import com.umeng.comm.core.constants.Constants;
import com.umeng.comm.core.utils.ResFinder;

import com.umeng.comm.ui.activities.TopicDetailActivity;
import com.umeng.comm.ui.imagepicker.adapters.RecommendTopicAdapter;

import com.umeng.comm.ui.imagepicker.fragments.RecommendTopicBaseFragment;
import com.umeng.comm.ui.imagepicker.listener.TopicToTopicDetail;


/**
 * 推荐的话题
 */
public class RecommendTopicFragment extends RecommendTopicBaseFragment {




    public static RecommendTopicFragment newRecommendTopicFragment() {
        return new RecommendTopicFragment();
    }

    @Override
    protected int getFragmentLayout() {
        return ResFinder.getLayout("umeng_comm_topic_recommend");
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();
    }

    @Override
    protected void initAdapter() {
        super.initAdapter();
    }

    @Override
    protected void setAdapterGotoDetail() {
        ((RecommendTopicAdapter)mAdapter).setTtt(new TopicToTopicDetail() {
            @Override
            public void gotoTopicDetail(Topic topic) {
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName(getActivity(), TopicDetailActivity.class);
                intent.setComponent(componentName);
                intent.putExtra(Constants.TAG_TOPIC, topic);
                getActivity().startActivity(intent);
            }
        });
    }



}
