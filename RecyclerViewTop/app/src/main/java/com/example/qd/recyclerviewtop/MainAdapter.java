package com.example.qd.recyclerviewtop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by qd on 2018/2/2.
 * 发现的adapter嵌套很多内容，wu
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private final int type_zero = 0;
    private final int type_one = 1;
    private final int type_two = 2;
    private List<String> mDatas;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public MainAdapter.OnItemClickListener mOnItemClickListerer;

    public void setmOnItemClickListerer(MainAdapter.OnItemClickListener listerer) {
        this.mOnItemClickListerer = listerer;
    }

    public MainAdapter(Context context, List<String> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == type_zero) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_zero, parent, false);
            return new ZeroViewHolder(view);
        } else if (viewType == type_one) {
            //排序
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_one, parent, false);
            return new OneViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_other, parent, false);
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case type_zero:
                ZeroViewHolder zeroViewHolder = (ZeroViewHolder) holder;
                //写逻辑

                break;
            case type_one:
                OneViewHolder oneViewHolder = (OneViewHolder) holder;
                //adapter点击置顶
                oneViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickListerer.onItemClick(view,position);
                    }
                });
                break;
            default:
                MyViewHolder myViewHolder = (MyViewHolder) holder;
                //写逻辑
                myViewHolder.tv_text.setText(mDatas.get(position));
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return type_zero;
        } else if (position == 1) {
            return type_one;
        } else {
            return type_two;
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ZeroViewHolder extends RecyclerView.ViewHolder {

        public ZeroViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class OneViewHolder extends RecyclerView.ViewHolder {

        public OneViewHolder(View itemView) {
            super(itemView);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_text;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_text = itemView.findViewById(R.id.tv_text);
        }
    }
}
