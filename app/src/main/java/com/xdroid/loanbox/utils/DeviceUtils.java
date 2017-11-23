package com.xdroid.loanbox.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by thomas on 2017/7/28.
 * 用于获取设备的信息
 */

public class DeviceUtils {
    /**
     * 获取屏幕宽度
     * @return
     */
    public static int getWindowWidth(){
        WindowManager wm = (WindowManager) UIUtils.getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;     // 屏幕宽度（像素）
    }

    /**
     * 获取屏幕高度
     * @return
     */
    public static int getWindowHeight(){
        WindowManager wm = (WindowManager) UIUtils.getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;   // 屏幕高度（像素）
    }
}
