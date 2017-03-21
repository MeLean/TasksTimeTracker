package com.melean.taskstimetracker.record_tasks.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<T> items;
    private OnRecyclerItemClicked onRecyclerItemClicked;
    private View lastSelectedView = null;
    private int currentSelectedPosition = -1;

    public abstract RecyclerView.ViewHolder setViewHolder(ViewGroup parent, OnRecyclerItemClicked onRecyclerItemClicked);

    public abstract void onBindData(RecyclerView.ViewHolder holder, T val);

    //public abstract OnRecyclerItemClicked onGetRecyclerItemClickListener();

    public BaseRecyclerAdapter(Context context, List<T> items) {
        this.mContext = context;
        this.items = items;
        onRecyclerItemClicked = onGetRecyclerItemClickListener();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return setViewHolder(parent, onRecyclerItemClicked);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindData(holder, items.get(position));
        holder.itemView.setSelected(position == currentSelectedPosition);
        holder.itemView.setTag(new Integer(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<T> incomingItems) {
        items = incomingItems;
        this.notifyDataSetChanged();
    }

    public T getItem(int position) {
        return items.get(position);
    }

    public interface OnRecyclerItemClicked {
        void onItemClicked(View view, int position);
    }


    private OnRecyclerItemClicked onGetRecyclerItemClickListener() {
        return new OnRecyclerItemClicked() {
            @Override
            public void onItemClicked(View view, int position) {
                currentSelectedPosition = position;

                if(lastSelectedView != null){
                    int lastSelectedPosition = (Integer) lastSelectedView.getTag();
                    lastSelectedView.setSelected(false);

                    if (position != lastSelectedPosition){
                        setViewSelected(view);
                    }else{
                        view.setSelected(false);
                    }
                } else{
                    setViewSelected(view);
                }
            }
        };
    }

    private void setViewSelected(View view) {
        view.setSelected(true);
        lastSelectedView = view;
    }
}