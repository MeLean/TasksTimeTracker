package com.melean.taskstimetracker.record_tasks.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.melean.taskstimetracker.R;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<T> items;
    private RecyclerView mRecyclerView;
    private OnRecyclerItemClicked onRecyclerItemClicked;
    private int lastSelectedPosition = -1;


    public abstract RecyclerView.ViewHolder setViewHolder(ViewGroup parent , OnRecyclerItemClicked onRecyclerItemClicked);

    public abstract void onBindData(RecyclerView.ViewHolder holder, T val);

    //public abstract OnRecyclerItemClicked onGetRecyclerItemClickListener();

    public BaseRecyclerAdapter(Context context, List<T> items, RecyclerView recyclerView){
        this.mContext = context;
        this.items = items;
        onRecyclerItemClicked = onGetRecyclerItemClickListener();
        mRecyclerView = recyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return setViewHolder(parent, onRecyclerItemClicked);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindData(holder, items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems( ArrayList<T> incomingItems){
        items = incomingItems;
        this.notifyDataSetChanged();
    }

    public T getItem(int position){
        return items.get(position);
    }

    public int getLastSelectedPosition(){
        return lastSelectedPosition;
    }

    public interface OnRecyclerItemClicked{
        void onItemClicked(View view, int position);
    }

    private OnRecyclerItemClicked onGetRecyclerItemClickListener() {
        return new OnRecyclerItemClicked() {
            @Override
            public void onItemClicked(View view, int position) {
                if(lastSelectedPosition != -1){
                    //deselect previously clicked item
                    RecyclerView.ViewHolder previouslyClickedHolder =
                            mRecyclerView.findViewHolderForAdapterPosition(lastSelectedPosition);
                    previouslyClickedHolder.itemView.setBackgroundResource(R.drawable.shape_item_not_selected);
                } else if(lastSelectedPosition != position){
                    lastSelectedPosition = position;
                    view.setBackgroundResource(R.drawable.shape_item_selected);
                } else {
                    lastSelectedPosition = -1;
                    view.setBackgroundResource(R.drawable.shape_item_not_selected);
                }
            }
        };
    }
}