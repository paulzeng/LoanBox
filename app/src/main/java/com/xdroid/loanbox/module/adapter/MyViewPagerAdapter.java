package com.xdroid.loanbox.module.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


import com.xdroid.loanbox.base.BaseFragment;
import com.xdroid.loanbox.base.BaseFragmentAdapter;

import java.util.List;

/**
 * Created by lvr on 2017/2/6.
 */

public class MyViewPagerAdapter extends BaseFragmentAdapter
{
    public MyViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> mTitles) {
        super(fm, fragmentList, mTitles);
    }

    public MyViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm, fragmentList);
    }
}
