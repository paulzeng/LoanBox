package com.xdroid.loanbox.module.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xdroid.loanbox.R;
import com.xdroid.loanbox.module.bean.BaseBean;

import java.util.List;


/**
 * Created by thomas on 2017/8/3.
 */

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ItemViewHolder>
{
    private Context mContext;
    private List<BaseBean> mData;
    private onItemClickListenr mOnItemClickListenr;
    private final LayoutInflater inflater;

    public HomeListAdapter(Context context, List<BaseBean> list) {
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.mData = list;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(inflater.inflate(R.layout.item_list_home,parent,false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout rootLayout;
        private ImageView iv_image;
        private TextView tv_title,tv_content,tv_money;
        public ItemViewHolder(View itemView) {
            super(itemView);
            rootLayout = (LinearLayout)itemView.findViewById(R.id.root);
            iv_image = (ImageView)itemView.findViewById(R.id.iv_image);
            tv_title = (TextView)itemView.findViewById(R.id.tv_title);
            tv_content = (TextView)itemView.findViewById(R.id.tv_content);
            tv_money = (TextView)itemView.findViewById(R.id.tv_money);
        }
    }

    /**定义点击事件**/
    public interface onItemClickListenr{
        void onItemClick(int position);
    }

    public void setOnItemClickListenr(onItemClickListenr onItemClickListenr){
        this.mOnItemClickListenr = onItemClickListenr;
    }
}
