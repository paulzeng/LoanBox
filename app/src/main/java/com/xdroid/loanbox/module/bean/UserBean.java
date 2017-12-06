package com.xdroid.loanbox.module.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by thomas on 2017/12/5.
 */

public class UserBean extends BmobUser {
    private String nickname;
    private String userHead;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }
}
