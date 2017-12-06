package com.xdroid.loanbox.utils;

import com.xdroid.loanbox.R;
import com.xdroid.loanbox.module.bean.BannerBean;
import com.xdroid.loanbox.module.bean.BaseBean;
import com.xdroid.loanbox.module.bean.NewsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/20 0020.
 */

public class DataControl {
    public static List<BannerBean> getBannerData(){
        List<BannerBean> data = new ArrayList<>();
        BannerBean  bean = new BannerBean();
        bean.setResource(R.drawable.ka_rong360);
        data.add(bean);
        BannerBean  bean1 = new BannerBean();
        bean1.setResource(R.drawable.ka_haodai);
        data.add(bean1);
        BannerBean  bean2 = new BannerBean();
        bean2.setResource(R.drawable.ka_rong360);
        data.add(bean2);
        return data;
    }
    public static List<BaseBean> getTestData(){
        List<BaseBean> data = new ArrayList<>();
        BaseBean bean = null;
        for(int i=0;i<6;i++){
            bean = new BaseBean();
            data.add(bean);
        }
        return data;
    }

    public static List<NewsBean> getNewsData(){
        List<NewsBean> data = new ArrayList<>();
        NewsBean bean = null;
        for(int i=0;i<6;i++){
            bean = new NewsBean();
            data.add(bean);
        }
        return data;
    }
}
