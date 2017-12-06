package com.xdroid.loanbox.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import com.xdroid.loanbox.R;

/**
 * @author liuhai
 * @version $Rev$
 * @time 2016/4/18 14:10
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate 2016/4/18$
 * @updateDes ${TODO}
 */
public class DialogUtils {
    public static Dialog getLoadingDialog(Context context) {
        final Dialog dialog = new Dialog(context, R.style.netLoadingDialog);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.loading_layout);
        Window window = dialog.getWindow();
        /*WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = UIUtils.dip2px(120);*/
        window.setGravity(Gravity.CENTER_VERTICAL);
        //TextView titleTxtv = (TextView) dialog.findViewById(R.id.dialog_tv);
        return dialog;
    }



    /**
     * 弹出提示窗体
     *
     * @Title: getCommWarnDialog
     * @Description: TODO
     * @Calls: TODO
     * @CalledBy: TODO
     * @Input:@param context
     * @Input:@param l
     * @Input:@return
     * @Date: 上午10:04:21
     */
    public static Dialog getCommWarnDialog(Context context, View view) {
        final Dialog dialog = new Dialog(context, R.style.netLoadingDialog);
        //View view = View.inflate(context, R.layout.dialog_version, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER_VERTICAL);
        return dialog;
    }

}
