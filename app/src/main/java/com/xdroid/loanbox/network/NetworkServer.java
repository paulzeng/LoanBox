package com.xdroid.loanbox.network;

import android.text.TextUtils;


import com.xdroid.loanbox.BuildConfig;
import com.xdroid.loanbox.utils.Constants;
import com.xdroid.loanbox.utils.SignUtil;
import com.xdroid.loanbox.utils.Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class NetworkServer {
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");//mdiatype 这个需要和服务端保持一致

    public static <T> void request(String url, HashMap<String, String> params, Class<T> t, NetworkListener<T> networkListener) {
        if (params == null) {
            params = new HashMap<String, String>();
        }
        String cache = CacheUtils.get(url);
        boolean needCache = params.remove("cache") != null;
        if (!TextUtils.isEmpty(cache) && needCache) {
            OkHttpUtil.invokeSuccessCallback(true, cache, t, networkListener);
        }
        String realUrl = url + Constants.STRING_QUESTION + getQueryParams(params);
        Request request = new Request.Builder().url(realUrl).build();
        OkHttpUtil.request(url, needCache, request, t, networkListener);
    }

    private static String getQueryParams(HashMap<String, String> params) {
        StringBuilder sb = new StringBuilder("");
        if (params != null) {
            Iterator<String> keys = params.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = params.get(key);

                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                    try {
                        value = URLEncoder.encode(value, Constants.CHARSET_UTF8);
                    } catch (UnsupportedEncodingException e) {
                        if (BuildConfig.DEBUG) e.printStackTrace();
                    }
                    sb.append(String.format(Constants.STRING_EQUAL_EXPRESSION, key, value));

                    if (keys.hasNext()) {
                        sb.append(Constants.STRING_AND);
                    }
                }
            }
        }
        return sb.toString();
    }

    public static <T> void postRequest(String url, HashMap<String, String> params, Class<T> t, NetworkListener<T> networkListener) {
        if (params == null) {
            params = new HashMap<String, String>();
        }

        String cache = CacheUtils.get(url);
        boolean needCache = params.remove("cache") != null;
        if (!TextUtils.isEmpty(cache) && needCache) {
            OkHttpUtil.invokeSuccessCallback(true, cache, t, networkListener);
        }
        /*String realUrl = url + Constants.STRING_QUESTION + getQueryParams(params);
        Request request = new Request.Builder().url(realUrl).build();*/

        //创建一个请求实体对象 RequestBody
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, params.toString());
        //创建一个请求
        final Request request = new Request.Builder().url(url).post(body).build();
        OkHttpUtil.request(url, needCache, request, t, networkListener);
    }



}
