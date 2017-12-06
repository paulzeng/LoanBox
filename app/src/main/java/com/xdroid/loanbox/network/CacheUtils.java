package com.xdroid.loanbox.network;


import android.content.Context;
import android.content.SharedPreferences;


import com.xdroid.loanbox.BuildConfig;
import com.xdroid.loanbox.app.APP;
import com.xdroid.loanbox.utils.Constants;
import com.xdroid.loanbox.utils.LogUtil;
import com.xdroid.loanbox.utils.SignUtil;

import java.io.IOException;

public class CacheUtils {

    public static void save(String key, String cache) {

        try {
            DiskLruCache diskLruCache = getDiskLruCache(APP.getInstance().getApplicationContext());

            DiskLruCache.Editor editor = diskLruCache.edit(SignUtil.md5(key));
            editor.set(0, cache);
            editor.commit();

            diskLruCache.close();
        } catch (Exception e) {
            if (BuildConfig.DEBUG) LogUtil.d("save cache error.[" + e.getMessage() + "]");
        }

    }

    public static String get(String key) {
        String value = null;

        try {
            DiskLruCache diskLruCache = getDiskLruCache(APP.getInstance().getApplicationContext());

            DiskLruCache.Snapshot snapshot = diskLruCache.get(SignUtil.md5(key));
            value = snapshot.getString(0);

            diskLruCache.close();
        } catch (Exception e) {
            if (BuildConfig.DEBUG) LogUtil.d("get cache error.[" + e.getMessage() + "]");
        }

        return value;
    }


    private static DiskLruCache getDiskLruCache(Context context) {
        DiskLruCache diskLruCache = null;
        try {
            diskLruCache = DiskLruCache.open(context.getCacheDir(), 1, 1, 5 * 1024 * 1024);
        } catch (IOException e) {
        }
        return diskLruCache;
    }

    public static void initCache(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
        if (!preferences.getBoolean("init_cache", false)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("init_cache", true);
            editor.apply();
            //save(Constants.URL_BASE + "oss/company.ashx", Utils.getAssertsFile(context, "company.json"));
        }

    }
}
