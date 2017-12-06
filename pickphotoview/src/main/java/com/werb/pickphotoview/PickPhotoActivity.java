package com.werb.pickphotoview;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.werb.pickphotoview.adapter.PickGridAdapter;
import com.werb.pickphotoview.adapter.SpaceItemDecoration;
import com.werb.pickphotoview.model.GroupImage;
import com.werb.pickphotoview.model.PickData;
import com.werb.pickphotoview.util.PickConfig;
import com.werb.pickphotoview.util.PickPhotoHelper;
import com.werb.pickphotoview.util.PickPreferences;
import com.werb.pickphotoview.util.PickUtils;
import com.werb.pickphotoview.util.RxBus;
import com.werb.pickphotoview.util.event.ImageLoadOkEvent;
import com.werb.pickphotoview.widget.MyToolbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;


/**
 * Created by wanbo on 2016/12/30.
 */

public class PickPhotoActivity extends AppCompatActivity {

    private PickData pickData;
    private RecyclerView photoList;
    private PickGridAdapter pickGridAdapter;
    private MyToolbar myToolbar;
    private TextView selectText, previewText;
    private List<String> allPhotos;
    private RequestManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_activity_pick_photo);
        manager = Glide.with(this);
        pickData = (PickData) getIntent().getSerializableExtra(PickConfig.INTENT_PICK_DATA);
        if (pickData != null) {
            PickPreferences.getInstance(PickPhotoActivity.this).savePickData(pickData);
        } else {
            pickData = PickPreferences.getInstance(PickPhotoActivity.this).getPickData();
        }
        initToolbar();
        initRecyclerView();
        initSelectLayout();
        intiEvent();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.pick_finish_slide_out_bottom);
    }

    private void initToolbar() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.pick_black));
        }
        selectText = (TextView) findViewById(R.id.tv_pick_photo);
        previewText = (TextView) findViewById(R.id.tv_preview_photo);
        myToolbar = (MyToolbar) findViewById(R.id.toolbar);
        myToolbar.setLeftIcon(R.mipmap.pick_ic_open);
        myToolbar.setRightIcon(R.mipmap.pick_ic_close);
        myToolbar.setPhotoDirName(getString(R.string.pick_all_photo));
        myToolbar.setLeftLayoutOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickPhotoActivity.this.startPhotoListActivity();
            }
        });
        myToolbar.setRightLayoutOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickPhotoActivity.this.finish();
            }
        });

        previewText.setOnClickListener(preClick);
        selectText.setOnClickListener(selectClick);
    }

    private void initRecyclerView() {
        photoList = (RecyclerView) findViewById(R.id.photo_list);
        photoList.setItemAnimator(new DefaultItemAnimator());
        GridLayoutManager layoutManager = new GridLayoutManager(this, pickData.getSpanCount());
        photoList.setLayoutManager(layoutManager);
        photoList.addItemDecoration(new SpaceItemDecoration(PickUtils.getInstance(PickPhotoActivity.this).dp2px(PickConfig.ITEM_SPACE), pickData.getSpanCount()));
        photoList.addOnScrollListener(scrollListener);
        PickPhotoHelper helper = new PickPhotoHelper(PickPhotoActivity.this);
        helper.getImages();
    }

    private void initSelectLayout() {
        LinearLayout selectLayout = (LinearLayout) findViewById(R.id.select_layout);
        selectLayout.setVisibility(View.VISIBLE);
    }

    private void intiEvent() {
        RxBus.getInstance().toObservable(ImageLoadOkEvent.class).subscribe(new Action1<ImageLoadOkEvent>() {
            @Override
            public void call(ImageLoadOkEvent imageLoadOkEvent) {
                GroupImage groupImage = PickPreferences.getInstance(PickPhotoActivity.this).getListImage();
                allPhotos = groupImage.mGroupMap.get(PickConfig.ALL_PHOTOS);
                Log.d("All photos size:", allPhotos.size() + "");
                if (allPhotos != null && !allPhotos.isEmpty()) {
                    pickGridAdapter = new PickGridAdapter(PickPhotoActivity.this, manager, allPhotos, pickData.isShowCamera(), pickData.getSpanCount(), pickData.getPickPhotoSize(), imageClick);
                    photoList.setAdapter(pickGridAdapter);
                }
            }
        });
    }

    public void updateSelectText(String selectSize) {
        if (selectSize.equals("0")) {
            selectText.setText(getString(R.string.pick_pick));
            selectText.setTextColor(getResources().getColor(R.color.pick_gray));
            previewText.setTextColor(getResources().getColor(R.color.pick_gray));
            previewText.setEnabled(false);
            selectText.setEnabled(false);
        } else {
            previewText.setEnabled(true);
            selectText.setEnabled(true);
            selectText.setText(String.format(getString(R.string.pick_pick_size), selectSize));
            selectText.setTextColor(getResources().getColor(R.color.pick_green));
            previewText.setTextColor(getResources().getColor(R.color.pick_black));
        }
    }

    private void startPhotoListActivity() {
        Intent intent = new Intent();
        intent.setClass(PickPhotoActivity.this, PickListActivity.class);
        startActivityForResult(intent, PickConfig.LIST_PHOTO_DATA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            return;
        }
        if (requestCode == PickConfig.LIST_PHOTO_DATA) {
            if (data != null) {
                String dirName = data.getStringExtra(PickConfig.INTENT_DIR_NAME);
                GroupImage listImage = PickPreferences.getInstance(PickPhotoActivity.this).getListImage();
                allPhotos = listImage.mGroupMap.get(dirName);
                pickGridAdapter.updateData(allPhotos);
                myToolbar.setPhotoDirName(dirName);
                selectText.setText(getString(R.string.pick_pick));
                selectText.setTextColor(getResources().getColor(R.color.pick_black));
            }
        } else if (requestCode == PickConfig.CAMERA_PHOTO_DATA) {
            String path;
            if (data != null) {
                path = data.getData().getPath();
                if (path.contains("/pick_camera")) {
                    path = path.replace("/pick_camera", "/storage/emulated/0/DCIM/Camera");
                }
            } else {
                path = PickUtils.getInstance(PickPhotoActivity.this).getFilePath();
            }
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
            Intent intent = new Intent();
            List<String> list = new ArrayList<>();
            list.add(path);
            intent.putExtra(PickConfig.INTENT_IMG_LIST_SELECT, (Serializable) list);
            setResult(PickConfig.PICK_PHOTO_DATA, intent);
            finish();
        }
    }

    View.OnClickListener imageClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String imgPath = (String) v.getTag(R.id.pick_image_path);
            Intent intent = new Intent();
            intent.setClass(PickPhotoActivity.this, PickPhotoPreviewActivity.class);
            intent.putExtra(PickConfig.INTENT_IMG_PATH, imgPath);
            intent.putExtra(PickConfig.INTENT_IMG_LIST, (Serializable) allPhotos);
            startActivity(intent);
        }
    };

    View.OnClickListener preClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!pickGridAdapter.getSelectPath().isEmpty()) {
                Intent intent = new Intent();
                intent.setClass(PickPhotoActivity.this, PickPhotoPreviewActivity.class);
                intent.putExtra(PickConfig.INTENT_IMG_PATH, pickGridAdapter.getSelectPath().get(0));
                intent.putExtra(PickConfig.INTENT_IMG_LIST, (Serializable) allPhotos);
                startActivity(intent);
            }
        }
    };

    View.OnClickListener selectClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!pickGridAdapter.getSelectPath().isEmpty()) {
                Intent intent = new Intent();
                intent.putExtra(PickConfig.INTENT_IMG_LIST_SELECT, (Serializable) pickGridAdapter.getSelectPath());
                setResult(PickConfig.PICK_PHOTO_DATA, intent);
                finish();
            }
        }
    };

    RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (Math.abs(dy) > PickConfig.SCROLL_THRESHOLD) {
                manager.pauseRequests();
            } else {
                manager.resumeRequests();
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                manager.resumeRequests();
            }
        }
    };
}
