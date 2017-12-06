package com.xdroid.loanbox.module.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xdroid.loanbox.R;
import com.xdroid.loanbox.module.bean.NewsBean;
import com.xdroid.loanbox.utils.ImageManager;

import java.util.List;

public class NewsAdapter extends BaseAdapter {
	private Context context;
	private List<NewsBean> listDatas;
	public LayoutInflater layoutInflater;

	public NewsAdapter(Context context, List<NewsBean> listFiles) {
		this.context = context;
		this.listDatas = listFiles;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return listDatas.size();
	}

	@Override
	public Object getItem(int arg0) {
		return listDatas.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.item_list_news, null);
			holder.headimg = (ImageView) convertView.findViewById(R.id.iv_head);
			holder.title = (TextView) convertView
					.findViewById(R.id.tv_news_title);
			holder.content = (TextView) convertView
					.findViewById(R.id.tv_news_info);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		/*ImageLoader.getInstance().displayImage(listDatas.get(position).getImg_url(),
				holder.headimg, ImageManager.getDefaultOptions());
		holder.title.setText(listDatas.get(position).getTitle());
		holder.content.setText(listDatas.get(position).getZhaiyao());*/
		return convertView;
	}

	/**
	 * class ViewHolder
	 */
	private class ViewHolder {
		ImageView headimg;
		TextView title, content;
	}

}
