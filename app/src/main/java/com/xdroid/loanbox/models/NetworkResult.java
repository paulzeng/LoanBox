package com.xdroid.loanbox.models;

import com.google.gson.Gson;


public class NetworkResult{
    public String status_msg;
    public int status_code;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
