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

package com.umeng.comm.ui.activities;

import android.support.v4.app.Fragment;
import android.view.View;

import com.umeng.comm.core.utils.ResFinder;
import com.umeng.comm.ui.fragments.AtMeFeedFragment;
import com.umeng.comm.ui.fragments.NotificationFragment;
import com.umeng.comm.ui.imagepicker.activities.NewMsgBaseActivity;
import com.umeng.comm.ui.imagepicker.fragments.CommentTabFragment;
import com.umeng.comm.ui.imagepicker.fragments.LikedMeFragment;
import com.umeng.comm.ui.imagepicker.fragments.MessageSessionFragment;


public class NewMsgActivity extends NewMsgBaseActivity {

    @Override
    public void initParams() {
        mDots = new View[5];
        mFragments = new Fragment[5];
    }

    @Override
    public void settingBtnViews() {
        initBtnView(ResFinder.getId("umeng_comm_my_msg_comment"), "评论",
                "my_comment_icon", 0, true);
        initBtnView(ResFinder.getId("umeng_comm_my_msg_like"), "赞",
                "my_like_icon", 1, true);
        initBtnView(ResFinder.getId("umeng_comm_my_msg_notice"), "通知",
                "umeng_comm_msg_notice", 2, true);
        initBtnView(ResFinder.getId("umeng_comm_my_msg_private_letter"), "管理员私信",
                "umeng_comm_msg_chat", 3, false);
        initBtnView(ResFinder.getId("umeng_comm_my_msg_at"), "@我",
                "my_about_icon", 4, true);
    }

    @Override
    public void onBtnViewClick(int position) {
        showFragment(position);
    }

    private void showFragment(int position) {
        if (mFragments[position] == null) {
            Fragment f = null;
            switch (position) {
                case 0:
                    f = new CommentTabFragment();
                    ((CommentTabFragment)f).setUserInfoClassName(UserInfoActivity.class);
                    ((CommentTabFragment)f).setTopicDetailClassName(TopicDetailActivity.class);
                    ((CommentTabFragment)f).setFeedDetailClassName(FeedDetailActivity.class);
                    break;

                case 1:
                    f = new LikedMeFragment();
                    ((LikedMeFragment)f).setUserInfoClassName(UserInfoActivity.class);
                    ((LikedMeFragment)f).setTopicDetailClassName(TopicDetailActivity.class);
                    ((LikedMeFragment)f).setFeedDetailClassName(FeedDetailActivity.class);
                    break;

                case 2:
                    f = new NotificationFragment();
                    break;

                case 3:
                    f = new MessageSessionFragment();
                    break;

                case 4:
                    f = new AtMeFeedFragment();
                    break;

                default: break;
            }
            mFragments[position] = f;
        }
        if (mFragments[position] != null) {
            showFragment(mFragments[position]);
        }
        mFragmentContainerView.setVisibility(View.VISIBLE);
        mBtnContainerView.setVisibility(View.GONE);
    }
}
