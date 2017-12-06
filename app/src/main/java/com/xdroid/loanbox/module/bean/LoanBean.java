package com.xdroid.loanbox.module.bean;


import java.io.Serializable;

/**
 * Created by thomas on 2017/11/23.
 */

public class LoanBean extends BaseBean implements Serializable {

    /**
     * info_switch : false
     * weight : 300
     * info_gonglve : 按照下述要求填写将有效提高成功率
     联系人信息填最近经常通电话或有来往短信的
     联系地址保持与淘宝的收货地址一致
     手机号实名且长期使用将增加成功率
     * info_jigoujieshao : 上海晓途网络科技有限公司，京东金融战略合作伙伴
     官方微信公众号：信用飞
     客服电话：400-862-5862
     * lighttitle : 热门新品
     * id : 100
     * info_shenpishichang : 快至5分钟
     * info_lilv : 日利率0.06%
     * info_qixianfanwei : 7天-1个月
     * title : 信用飞
     * info_edu : 500-1万
     * info_fangkuannum : 7万
     * targeturl : https://m.xinyongfei.cn/activity/guide?utm_source=QD_EV_BDQ01
     * subtitle : 18周岁以上有芝麻分和实名认证手机号即可,审批时间5分钟
     * edu : 500-1万
     * closetop : false
     * use_iosua : false
     * imgurl : http://dk2.shenyingyong.com/daikuan_logo_zc/logo_xinyongfei.png
     */

    private boolean info_switch;
    private int weight;
    private String info_gonglve;
    private String info_jigoujieshao;
    private String lighttitle;
    private int id;
    private String info_shenpishichang;
    private String info_lilv;
    private String info_qixianfanwei;
    private String title;
    private String info_edu;
    private String info_fangkuannum;
    private String targeturl;
    private String subtitle;
    private String edu;
    private boolean closetop;
    private boolean use_iosua;
    private String imgurl;

    public boolean isInfo_switch() {
        return info_switch;
    }

    public void setInfo_switch(boolean info_switch) {
        this.info_switch = info_switch;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getInfo_gonglve() {
        return info_gonglve;
    }

    public void setInfo_gonglve(String info_gonglve) {
        this.info_gonglve = info_gonglve;
    }

    public String getInfo_jigoujieshao() {
        return info_jigoujieshao;
    }

    public void setInfo_jigoujieshao(String info_jigoujieshao) {
        this.info_jigoujieshao = info_jigoujieshao;
    }

    public String getLighttitle() {
        return lighttitle;
    }

    public void setLighttitle(String lighttitle) {
        this.lighttitle = lighttitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfo_shenpishichang() {
        return info_shenpishichang;
    }

    public void setInfo_shenpishichang(String info_shenpishichang) {
        this.info_shenpishichang = info_shenpishichang;
    }

    public String getInfo_lilv() {
        return info_lilv;
    }

    public void setInfo_lilv(String info_lilv) {
        this.info_lilv = info_lilv;
    }

    public String getInfo_qixianfanwei() {
        return info_qixianfanwei;
    }

    public void setInfo_qixianfanwei(String info_qixianfanwei) {
        this.info_qixianfanwei = info_qixianfanwei;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo_edu() {
        return info_edu;
    }

    public void setInfo_edu(String info_edu) {
        this.info_edu = info_edu;
    }

    public String getInfo_fangkuannum() {
        return info_fangkuannum;
    }

    public void setInfo_fangkuannum(String info_fangkuannum) {
        this.info_fangkuannum = info_fangkuannum;
    }

    public String getTargeturl() {
        return targeturl;
    }

    public void setTargeturl(String targeturl) {
        this.targeturl = targeturl;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }

    public boolean isClosetop() {
        return closetop;
    }

    public void setClosetop(boolean closetop) {
        this.closetop = closetop;
    }

    public boolean isUse_iosua() {
        return use_iosua;
    }

    public void setUse_iosua(boolean use_iosua) {
        this.use_iosua = use_iosua;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
