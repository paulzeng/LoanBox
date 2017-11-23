package com.xdroid.loanbox.utils;

import android.util.SparseArray;

import com.xdroid.loanbox.base.BaseFragment;
import com.xdroid.loanbox.module.card.CardFragment;
import com.xdroid.loanbox.module.found.FoundFragment;
import com.xdroid.loanbox.module.home.HomeFragment;
import com.xdroid.loanbox.module.me.MeFragment;


/**
 * 使用工厂模式创建Fragment
 * Created by thomas on 17/3/22.
 */

public class FragmentFactory {
    public static final int TYPE_HOME = 0;
    public static final int TYPE_CARD = 1;
    public static final int TYPE_FOUND = 2;
    public static final int TYPE_ME = 3;


    private static SparseArray<BaseFragment> mFragments = new SparseArray<>();

    public static void clear(){
        mFragments.clear();
    }

    public static BaseFragment create(Integer type){
        BaseFragment fragment = mFragments.get(type);
        if(fragment == null) switch (type) {
            case TYPE_HOME:
                fragment = new HomeFragment();
                break;
            case TYPE_CARD:
                fragment = new CardFragment();
                break;
            case TYPE_FOUND:
                fragment = new FoundFragment();
                break;
            case TYPE_ME:
                fragment = new MeFragment();
                break;
        }
        if (fragment != null) {
            mFragments.put(type, fragment);
        }
        return fragment;
    }
}
