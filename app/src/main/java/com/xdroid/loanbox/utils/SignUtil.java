package com.xdroid.loanbox.utils;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class SignUtil {

    /**
     * 新的md5签名，首尾放secret。
     *
     * @param secret APP_SECRET
     */

    public static String md5Signature(Map<String, String> params, String secret) {
        StringBuffer origin = getBeforeSign(params, new StringBuffer(secret));
        if (origin == null) {
            return null;
        }
        origin.append(secret);

        return md5(origin.toString());
    }

    public static String md5(String origin) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance(Constants.SIGN_METHOD);
            result = byte2hex(md.digest(origin.getBytes(Constants.CHARSET_UTF8)));
            result = result.toLowerCase();
        } catch (Exception e) {
            //if (BuildConfig.DEBUG) e.printStackTrace();
        }
        return result;
    }
    /**
     * 二行制转字符串
     */
    private static String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer();
        String stmp = "";

        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
        }
        return hs.toString().toUpperCase();
    }

    /**
     * 添加参数的封装方法
     */
    private static StringBuffer getBeforeSign(Map<String, String> params, StringBuffer orgin) {
        if (params == null) {
            return null;
        }

        Map<String, String> treeMap = new TreeMap<String, String>();
        treeMap.putAll(params);

        Iterator<String> iter = treeMap.keySet().iterator();
        while (iter.hasNext()) {
            String name = (String) iter.next();
            orgin.append(name).append(params.get(name));
        }

        return orgin;
    }


    public static String getSignature(Map<String, String> map, String secretKey) {
        try {
            if (map.isEmpty() || TextUtils.isEmpty(secretKey)) {
                return null;
            }

            map.remove(Constants.KEY_SIGN);
            return md5Signature(map, secretKey.trim());
        } catch (Exception e) {
            return null;
        }
    }

}
