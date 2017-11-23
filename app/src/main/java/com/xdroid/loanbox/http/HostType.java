package com.xdroid.loanbox.http;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author thomas
 * @version $Rev$
 * @time 2016/4/15 15:31
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate 2016/4/15$
 * @updateDes ${TODO}
 */
public class HostType {


    public static final int TYPE_COUNT = 4;


    @HostTypeChecker
    public static final int DEV_HOST = 1;


    @HostTypeChecker
    public static final int GAMMA_HOST = 2;

    @HostTypeChecker
    public static final int BETA_HOST = 3;


    @HostTypeChecker
    public static final int PRODUCT_HOST = 4;



    /**
     * 替代枚举的方案，使用IntDef保证类型安全
     */
    @IntDef({DEV_HOST, GAMMA_HOST, BETA_HOST,PRODUCT_HOST})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HostTypeChecker {

    }
}
