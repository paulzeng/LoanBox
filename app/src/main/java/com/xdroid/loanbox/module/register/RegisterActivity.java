package com.xdroid.loanbox.module.register;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.umeng.community.login.LoginActivity;
import com.xdroid.loanbox.MainActivity;
import com.xdroid.loanbox.R;
import com.xdroid.loanbox.base.BaseActivity;
import com.xdroid.loanbox.module.bean.UserBean;
import com.xdroid.loanbox.utils.StringUtils;
import com.xdroid.loanbox.utils.ToastUtil;
import com.xdroid.loanbox.utils.UIUtils;
import com.xdroid.loanbox.widgets.DeletableEditText;
import com.xdroid.loanbox.widgets.ShowHidePasswordEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by thomas on 2017/11/24.
 */

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.register_mobile)
    DeletableEditText registerMobile;
    @BindView(R.id.register_pwd)
    ShowHidePasswordEditText registerPwd;
    @BindView(R.id.register_username)
    DeletableEditText registerUsername;

    private String nickname, mobile, password;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        SetStatusBarColor(UIUtils.getColor(R.color.bottom_tip_text_color));
        mToolbar.setTitle("注册");
    }

    @OnClick({R.id.register_get_codes, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_get_codes:
                break;
            case R.id.btn_register:
                nickname = registerUsername.getText().toString().trim();
                mobile = registerMobile.getText().toString().trim();
                password = registerPwd.getText().toString().trim();
                if (mobile.isEmpty()) {
                    ToastUtil.show(this, "手机号码不能为空");
                    return;
                }
                if (!StringUtils.isMobileNO(mobile)) {
                    ToastUtil.show(this, "请输入正确的手机号码");
                    return;
                }
                if (password.isEmpty()) {
                    ToastUtil.show(this, "密码设置不能为空");
                    return;
                }
                if (nickname.isEmpty()) {
                    ToastUtil.show(this, "用户昵称不能为空");
                    return;
                }
                doRegister(mobile, password, nickname);
                break;
        }
    }

    private void doRegister(String mobile, String password, String nickname) {
        showDialog();
        UserBean user = new UserBean();
        user.setUsername(mobile);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setUserHead("https://b-ssl.duitang.com/uploads/item/201506/29/20150629140736_5JR8c.thumb.700_0.jpeg");
        user.signUp(new SaveListener<UserBean>() {
            @Override
            public void done(UserBean s, BmobException e) {
                cancelDialog();
                if (e == null) {
                    ToastUtil.show(RegisterActivity.this, "注册成功！");
                    finish();
                } else {
                    ToastUtil.show(RegisterActivity.this, "注册失败！" + e.getMessage());
                }
            }
        });
    }
}
