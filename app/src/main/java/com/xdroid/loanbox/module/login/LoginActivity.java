package com.xdroid.loanbox.module.login;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.xdroid.loanbox.MainActivity;
import com.xdroid.loanbox.R;
import com.xdroid.loanbox.base.BaseActivity;
import com.xdroid.loanbox.module.bean.UserBean;
import com.xdroid.loanbox.module.register.RegisterActivity;
import com.xdroid.loanbox.utils.StringUtils;
import com.xdroid.loanbox.utils.ToastUtil;
import com.xdroid.loanbox.utils.UIUtils;
import com.xdroid.loanbox.widgets.DeletableEditText;
import com.xdroid.loanbox.widgets.ShowHidePasswordEditText;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by thomas on 2017/11/24.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.edt_username)
    DeletableEditText edtUsername;
    @BindView(R.id.edt_password)
    ShowHidePasswordEditText edtPassword;

    private String mobile, password;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        SetStatusBarColor(UIUtils.getColor(R.color.bottom_tip_text_color));
        mToolbar.setTitle("登录");
    }


    @OnClick({R.id.btn_login, R.id.tv_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                mobile = edtUsername.getText().toString().trim();
                password = edtPassword.getText().toString().trim();
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
                doLogin(mobile, password);

                break;
            case R.id.tv_register:
                startActivity(this, RegisterActivity.class);
                break;
        }
    }

    private void doLogin(String mobile, String password) {
        showDialog();
        UserBean user = new UserBean();
        user.setUsername(mobile);
        user.setPassword(password);
        user.login(new SaveListener<UserBean>() {
            @Override
            public void done(UserBean bmobUser, BmobException e) {
                cancelDialog();
                if (e == null) {
                    ToastUtil.show(LoginActivity.this, "登录成功:)");
                    startActivity(LoginActivity.this, MainActivity.class);
                    //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                    //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
                } else {
                    ToastUtil.show(LoginActivity.this, "登录失败");
                }
            }
        });
    }

}
