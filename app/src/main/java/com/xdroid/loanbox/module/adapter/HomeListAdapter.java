package com.xdroid.loanbox.module.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xdroid.loanbox.R;
import com.xdroid.loanbox.models.AppListResult;
import com.xdroid.loanbox.module.bean.BaseBean;
import com.xdroid.loanbox.module.bean.LoanBean;

import java.util.List;


/**
 * Created by thomas on 2017/8/3.
 */

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ItemViewHolder>
{
    private Context mContext;
    private List<AppListResult.AppModel> mData;
    private onItemClickListenr mOnItemClickListenr;
    private final LayoutInflater inflater;

    public HomeListAdapter(Context context, List<AppListResult.AppModel> list) {
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
        AppListResult.AppModel bean = mData.get(position);
        holder.tv_title.setText(bean.title);
        holder.tv_content.setText(bean.subtitle);
        holder.tv_money.setText(bean.edu_info);
        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListenr.onItemClick(position);
            }
        });
        Glide.with(mContext).load(bean.imgurl).into(holder.iv_image);
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
