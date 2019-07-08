package com.example.bwtools.android.tools.imp;

import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseAdapter<viewHolder extends RecyclerView.ViewHolder,ItemType> extends RecyclerView.Adapter<viewHolder> {
    protected ArrayList<ItemType> ItemList;

    private final int VIEWTYPE_PAGEITEM =1;
    private final int VIEWTYPE_NONE=2;

    private int currentViewType;
    private boolean isDataSet = false;

    protected BaseAdapter() {
        this.ItemList = new ArrayList<>();
        currentViewType = VIEWTYPE_NONE;
    }

    public ArrayList<ItemType> getList() {
        return ItemList;
    }

    public void removeList() {
        isDataSet = false;
        this.ItemList.clear();
    }

    public void setItemList(@NonNull ArrayList<ItemType> list) {
        isDataSet = true;

        if(ItemList == null)
            this.ItemList = new ArrayList<>();

        this.ItemList = list;

        notifyDataSetChanged();
    }

    public void addItemList(@NonNull ArrayList<ItemType> list) {
        isDataSet = true;

        if(list == null)
            list = new ArrayList<>();

        if(ItemList == null)
            this.ItemList = new ArrayList<>();

        for(ItemType pageItemType : list){
            this.ItemList.add(pageItemType);
        }

        notifyItemInserted(getItemCount()-list.size());
    }


    @Override
    public int getItemViewType(int position) {
        if(position<getVeiwTypePageItemRange()){
            return VIEWTYPE_PAGEITEM;
        }else
            return VIEWTYPE_NONE;
    }

    private int getVeiwTypePageItemRange(){
        return ItemList.size() > 0 ? ItemList.size() : 0;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEWTYPE_PAGEITEM){
            currentViewType = VIEWTYPE_PAGEITEM;
            return onCreateViewHolderItem(parent);
        }else{
            return onCreateViewHolderNoneList(parent);
        }
    }
    abstract protected viewHolder onCreateViewHolderItem(@NonNull ViewGroup parent);
    abstract protected viewHolder onCreateViewHolderNoneList(@NonNull ViewGroup parent);

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        if(currentViewType == VIEWTYPE_PAGEITEM){
            onBindItem(holder,position);
        }

        currentViewType = VIEWTYPE_NONE;
    }

    abstract protected void onBindItem(@NonNull viewHolder holder,int position);

    @Override
    public int getItemCount() {

        if(ItemList.size() > 0){
            return ItemList.size();
        }else{
            return isDataSet == false ? 0 : 1;
        }
    }
}
