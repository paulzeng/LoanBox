package com.xdroid.loanbox.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by thomas on 2017/12/5.
 * {
 * "id": "2",
 * "weight": "49",
 * "subtitle": "满18周岁有芝麻分即可申请",
 * "title": "向钱贷",
 * "imgurl": "http://dk2.shenyingyong.com/daikuan_logo_zc/logo_xiangqiandai.png",
 * "info_fangkuannum": "5",
 * "info_lilv": "日利率0.03%",
 * "info_qixianfanwei": "7-60天",
 * "info_shenpishichang": "2小时内",
 * "lighttitle": "热门",
 * "targeturl": "https://m.xiangqd.cn/homeWeb?channel=H5_BD_jlm_06",
 * "edu_info": "1千 - 5千"
 * }
 */

public class AppListResult extends NetworkResult {
    public List<AppModel> data;

    public class AppModel implements Serializable {
        public String id, weight, subtitle, title, imgurl, info_fangkuannum, info_lilv;
        public String info_qixianfanwei, info_shenpishichang, lighttitle, targeturl, edu_info;
    }
}
