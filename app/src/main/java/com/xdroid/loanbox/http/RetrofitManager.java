package com.xdroid.loanbox.http;

import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xdroid.loanbox.app.C;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by laucherish on 16/3/15.
 */
public class RetrofitManager {

    private static OkHttpClient mOkHttpClient;
    public final ApiManagerService apiService;

    // 管理不同HostType的单例
    private static SparseArray<RetrofitManager> mInstanceManager = new SparseArray<>(
            HostType.TYPE_COUNT);


    public static RetrofitManager builder(int hostType) {
        RetrofitManager instance = mInstanceManager.get(hostType);
        if (instance == null) {
            instance = new RetrofitManager(hostType);
            mInstanceManager.put(hostType, instance);
            return instance;
        } else {
            return instance;
        }
    }


    private RetrofitManager(@HostType.HostTypeChecker int hostType) {
        initOkHttpClient();
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getHost(hostType))
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        apiService = retrofit.create(ApiManagerService.class);
    }


    private void initOkHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient.Builder()
                            .retryOnConnectionFailure(true)
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .addNetworkInterceptor(httpLoggingInterceptor)
                            .addInterceptor(httpLoggingInterceptor)
                            //.addInterceptor(interceptor)
                            .build();
                }

            }
        }
    }

    /**
     * 获取okhttp的实例
     * @return
     */
    public static OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient.Builder()
                            .retryOnConnectionFailure(true)
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .addNetworkInterceptor(httpLoggingInterceptor)
                            .addInterceptor(httpLoggingInterceptor)
                            //.addInterceptor(interceptor)
                            .build();
                }

            }
        }
        return mOkHttpClient;
    }



    /**
     * 获取对应的host
     *
     * @param hostType host类型
     * @return host
     */
    private String getHost(int hostType) {
        switch (hostType) {
            case HostType.DEV_HOST:
                return C.DEV_HOST_URL;
            case HostType.GAMMA_HOST:
                return C.GAMMA_HOST_URL;
            case HostType.PRODUCT_HOST:
                return C.PRODUCT_HOST_URL;
        }
        return "";
    }

}
