package com.xdroid.loanbox.module.me;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.werb.permissionschecker.PermissionChecker;
import com.werb.pickphotoview.PickPhotoView;
import com.werb.pickphotoview.util.PickConfig;
import com.xdroid.loanbox.R;
import com.xdroid.loanbox.app.APP;
import com.xdroid.loanbox.base.BaseFragment;
import com.xdroid.loanbox.utils.ImageManager;
import com.xdroid.loanbox.utils.LogUtil;
import com.xdroid.loanbox.utils.ToastUtil;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Administrator on 2017/11/22 0022.
 */

public class MeFragment extends BaseFragment {
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    Unbinder unbinder;
    // 检查运行时权限
    private PermissionChecker permissionChecker;
    private String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {
        permissionChecker = new PermissionChecker(this.getActivity());

        if(APP.getCurrentUser()!=null){
            tvNickname.setText(APP.getCurrentUser().getNickname());
            ImageManager.loadImage(this.getContext(),APP.getCurrentUser().getUserHead(),ivHead);
        }

    }

    @Override
    public void initPresenter() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_head, R.id.tv_nickname})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_head:
                if (permissionChecker.isLackPermissions(PERMISSIONS)) {
                    permissionChecker.requestPermissions();
                }else {
                    startPickPhoto();
                }
                break;
            case R.id.tv_nickname:

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ToastUtil.show(MeFragment.this.getContext(),"dddd");
        if (resultCode == 0) {
            return;
        }
        if (data == null) {
            return;
        }
        if (requestCode == PickConfig.PICK_PHOTO_DATA) {
            List<String> selectPaths = (List<String>) data.getSerializableExtra(PickConfig.INTENT_IMG_LIST_SELECT);
            LogUtil.e("TAG", selectPaths.toString());
            File imgFile = ImageManager.compressBiamp(selectPaths.get(0), 100);
            final BmobFile bmobFile = new BmobFile(imgFile);
            bmobFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){
                        ToastUtil.show(MeFragment.this.getContext(),"上传文件成功:" + bmobFile.getFileUrl());
                    }else{
                        ToastUtil.show(MeFragment.this.getContext(),"上传文件失败：" + e.getMessage());
                    }
                }
                @Override
                public void onProgress(Integer value) {
                    // 返回的上传进度（百分比）
                }
            });
        }
    }

    private void startPickPhoto() {
        new PickPhotoView.Builder(this.getActivity()).setPickPhotoSize(1).setShowCamera(true).setSpanCount(5).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionChecker.PERMISSION_REQUEST_CODE:
                if (permissionChecker.hasAllPermissionsGranted(grantResults)) {
                    startPickPhoto();
                } else {
                    permissionChecker.showDialog();
                }
                break;
        }
    }
}
