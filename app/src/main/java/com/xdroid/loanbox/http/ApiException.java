package com.xdroid.loanbox.http;

/**
 * Created by liukun on 16/3/10.
 */
public class ApiException extends RuntimeException {

    public static final int ACTION_FAILED = 0;

    public static final int ACTION_NO_PERMISSION = -1;

    public static final int LOGIN_TIME_OUT = -2;

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     *
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code) {
        String message = "";
        switch (code) {
            case ACTION_FAILED:
                message = "操作失败";
                break;
            case ACTION_NO_PERMISSION:
                message = "没有权限操作";
                break;
            case LOGIN_TIME_OUT:
                message = "登录超时";
                break;
            default:
                message = "未知错误";

        }
        return message;
    }
}

