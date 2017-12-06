package com.xdroid.loanbox.module.bean;

import com.google.gson.Gson;

/**
 * @author 曾文韬
 * @version $Rev$
 * @time 2016/4/28 9:41
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class NewsBean {
    private int id;

    private int category_id;

    private String title;

    private String img_url;

    private String zhaiyao;

    private int click;

    private String add_time;

    private String city_id;

    private String market_price;

    private String sell_price;

    private int BmCount;

    private String author;

    private int app_index;

    private int app_list;

    private int app_top;

    private boolean app_url;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setCategory_id(int category_id){
        this.category_id = category_id;
    }
    public int getCategory_id(){
        return this.category_id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setImg_url(String img_url){
        this.img_url = img_url;
    }
    public String getImg_url(){
        return this.img_url;
    }
    public void setZhaiyao(String zhaiyao){
        this.zhaiyao = zhaiyao;
    }
    public String getZhaiyao(){
        return this.zhaiyao;
    }
    public void setClick(int click){
        this.click = click;
    }
    public int getClick(){
        return this.click;
    }
    public void setAdd_time(String add_time){
        this.add_time = add_time;
    }
    public String getAdd_time(){
        return this.add_time;
    }
    public void setCity_id(String city_id){
        this.city_id = city_id;
    }
    public String getCity_id(){
        return this.city_id;
    }
    public void setMarket_price(String market_price){
        this.market_price = market_price;
    }
    public String getMarket_price(){
        return this.market_price;
    }
    public void setSell_price(String sell_price){
        this.sell_price = sell_price;
    }
    public String getSell_price(){
        return this.sell_price;
    }
    public void setBmCount(int BmCount){
        this.BmCount = BmCount;
    }
    public int getBmCount(){
        return this.BmCount;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public String getAuthor(){
        return this.author;
    }
    public void setApp_index(int app_index){
        this.app_index = app_index;
    }
    public int getApp_index(){
        return this.app_index;
    }
    public void setApp_list(int app_list){
        this.app_list = app_list;
    }
    public int getApp_list(){
        return this.app_list;
    }
    public void setApp_top(int app_top){
        this.app_top = app_top;
    }
    public int getApp_top(){
        return this.app_top;
    }
    public void setApp_url(boolean app_url){
        this.app_url = app_url;
    }
    public boolean getApp_url(){
        return this.app_url;
    }


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
