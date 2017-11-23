package com.xdroid.loanbox.http;

import android.util.Log;

import rx.functions.Func1;

/**
 * @author thomas
 * @version $Rev$
 * @time 2016/3/18 11:00
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */


/**
 * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
 *
 * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
 */
public class ResultFunc<T> implements Func1<HttpResult<T>, T> {

    @Override
    public T call(HttpResult<T> httpResult) {
        Log.e("HTTP-RESPONSE", httpResult.toString());
        if (!httpResult.isSuccess()) {
            throw new ApiException(httpResult.getStatus());
        }
        return httpResult.getPosts();
    }
}
