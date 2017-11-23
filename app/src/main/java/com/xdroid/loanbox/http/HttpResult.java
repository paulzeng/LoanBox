package com.xdroid.loanbox.http;


import com.google.gson.Gson;

/**
 * @author thomas
 * @version $Rev$
 * @time 2016/3/18 10:58
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class HttpResult<T> {
    private String status;
    private String count;
    //用来模仿Data
    private T posts;
    public boolean isSuccess() {
        if(status.equals("ok")){
            return true;
        }else{
            return false;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public T getPosts() {
        return posts;
    }





    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
