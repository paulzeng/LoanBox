package com.xdroid.loanbox.utils;

import android.content.Context;
import android.widget.ImageView;

import com.xdroid.loanbox.module.bean.BannerBean;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by lvr on 2017/4/24.
 */

public class GlideLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        ImageLoaderUtils.displaySmallPhoto(context,imageView,((BannerBean)path).getResource());
    }
}
