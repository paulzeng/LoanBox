package com.xdroid.loanbox.network;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.xdroid.loanbox.BuildConfig;
import com.xdroid.loanbox.base.BaseActivity;
import com.xdroid.loanbox.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtil {
    private static final Handler mMainHandler = new Handler(Looper.getMainLooper());
    private static final OkHttpClient mOkHttpClient = new OkHttpClient();

    public static <T> void request(final String url, final boolean cache, final Request request, final Class<T> t, final NetworkListener<T> networkListener) {

        LogUtil.d("HTTP","Request Url=" + request.url().toString());

        Call requestCall = mOkHttpClient.newCall(request);
        requestCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e("HTTP","Failed IOException=="+e.getMessage());
                if (BuildConfig.DEBUG) LogUtil.e(e.getMessage());

                if (networkListener != null && BaseActivity.getCurrentActivity() != null) {
                    mMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            networkListener.onError(0, null, null);
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                LogUtil.d("Http Header: ============================== Begin.");
                for (int i = 0; i < responseHeaders.size(); i++) {
                    LogUtil.d(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }
                LogUtil.d("Http Header: ============================== End.");

                final String responseBody = response.body().string();

                LogUtil.e("HTTP","Response=="+responseBody.toString());

                if (!TextUtils.isEmpty(responseBody) && cache) {
                    CacheUtils.save(url, responseBody);
                }

                if (BaseActivity.getCurrentActivity() != null) {
                    invokeSuccessCallback(false, responseBody, t, networkListener);
                }
            }
        });
    }

    public static <T> void invokeSuccessCallback(final boolean cache, final String responseBody, final Class<T> t, final NetworkListener<T> networkListener) {
        LogUtil.d(String.format("ResponseBody[%s]: %s.", cache ? "cache" : "network", responseBody));
        if (networkListener != null) {

            T gsonResult = null;
            try {
                Gson gson = new Gson();
                gsonResult = gson.fromJson(responseBody, t);
            } catch (Exception e) {
                e.printStackTrace();
            }

            final T result = gsonResult;

            mMainHandler.post(new Runnable() {
                @Override
                public void run() {
                    int error = 0;
                    String msg = null;
                    try {
                        JSONObject json = new JSONObject(responseBody);
                        error = json.optInt("status_code");
                        msg = json.optString("status_msg");
                    } catch (JSONException e) {
                    }

                    if (error == 0 && result != null) {
                        networkListener.onSuccess(result);
                    } else if (!cache) {
                        networkListener.onError(error, msg, result);
                    }
                }
            });
        }
    }
}
