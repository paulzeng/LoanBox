package com.xdroid.loanbox.module.webview;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wang.avi.AVLoadingIndicatorView;
import com.xdroid.loanbox.R;
import com.xdroid.loanbox.base.BaseActivity;
import com.xdroid.loanbox.models.AppListResult;
import com.xdroid.loanbox.module.bean.LoanBean;
import com.xdroid.loanbox.widgets.TitleBar;

import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by thomas on 2017/11/23.
 */

public class CustomWebviewActivity extends BaseActivity {
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.mWebview)
    WebView mMWebview;
    private WebSettings settings;
    private String mWebUrl;
    private AppListResult.AppModel bean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_custom_webview;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        if (getIntent() != null) {
            HashMap<String, Object> map = getIntentParams(getIntent().getExtras());
            if (map.get("model") != null) {
                bean = (AppListResult.AppModel) map.get("model");
            }
        }
        mTitleBar.setTitle(bean.title);
        mTitleBar.setLeftImageResource(R.drawable.icon_back_white);
        settings = mMWebview.getSettings();
        setSettings(settings);
        mMWebview.setWebChromeClient(new WebChromeClient());
        mMWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                showDialog();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                cancelDialog();
            }
        });
        mWebUrl = bean.targeturl;
        mMWebview.loadUrl(mWebUrl);
    }

    @SuppressLint({"NewApi", "SetJavaScriptEnabled"})
    private void setSettings(WebSettings setting) {
        setting.setJavaScriptEnabled(true);
        setting.setBuiltInZoomControls(true);
        setting.setDisplayZoomControls(true);
        setting.setSupportZoom(true);
        setting.setDomStorageEnabled(true);
        setting.setDatabaseEnabled(true);
        // 全屏显示
        // setting.setLoadWithOverviewMode(true);
        // setting.setUseWideViewPort(true);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
