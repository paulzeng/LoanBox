package com.xdroid.loanbox.network;


public interface NetworkListener<T> {
    void onSuccess(T response);

    void onError(int errorCode, String errorMsg, T response);
}
