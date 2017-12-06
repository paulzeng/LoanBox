package com.xdroid.loanbox.module.bean;

import java.util.List;

/**
 * Created by thomas on 2017/11/23.
 */

public class ResponseResult extends BaseBean {
    private int status_code;
    private String status_msg;
    private List<LoanBean> data;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getStatus_msg() {
        return status_msg;
    }

    public void setStatus_msg(String status_msg) {
        this.status_msg = status_msg;
    }

    public List<LoanBean> getData() {
        return data;
    }

    public void setData(List<LoanBean> data) {
        this.data = data;
    }
}
