package com.xdroid.loanbox.utils;

/**
 * Created by Administrator on 2017/6/5 0005.
 */

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/5/8.
 */
public class SerMap implements Serializable {
    public HashMap<String,Object> map;
    public SerMap(){

    }

    public HashMap<String, Object> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Object> map) {
        this.map = map;
    }
}