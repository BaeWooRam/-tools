package com.example.bwtools.android.tools.imp;

import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PageAdapter<viewHolder extends RecyclerView.ViewHolder,PageItemType> extends RecyclerView.Adapter<viewHolder> {
    private PageManager pageManager;
    protected ArrayList<PageItemType> pageItemList;

    public final int VIEWTYPE_PAGEITEM_HEAD =0;
    public final int VIEWTYPE_PAGEITEM =1;
    public final int VIEWTYPE_PAGEITEM_FOOTER=2;
    public final int VIEWTYPE_NONE=3;

    public final int PAGEITEM_HEAD =1 ;
    public final int PAGEITEM_FOOTER =1;
    private int currentViewType;

    public PageAdapter(PageManager pageManager) {
        this.pageManager = pageManager;
        this.pageItemList = new ArrayList<>();
        currentViewType = VIEWTYPE_NONE;
    }

    public ArrayList<PageItemType> getList() {
        return null;
    }

    public void removePageList() {
        boolean isPageEnd =  pageManager.isPageEnd();

        int size;
        if(isPageEnd){
            size = pageItemList.size()+PAGEITEM_FOOTER+PAGEITEM_HEAD;
        }else{
            size = pageItemList.size()+PAGEITEM_HEAD;
        }

        this.pageItemList.clear();
    }

    public void setPageItemList(@NonNull ArrayList<PageItemType> list) {
        if(pageItemList == null)
            this.pageItemList = new ArrayList<PageItemType>();

        this.pageItemList = list == null ? new ArrayList<PageItemType>() : list;

        notifyDataSetChanged();
    }

    public void addPageItemList(@NonNull ArrayList<PageItemType> list) {
        if(list == null)
            list = new ArrayList<>();

        if(pageItemList == null)
            this.pageItemList = new ArrayList<PageItemType>();

        for(PageItemType pageItemType : list){
            this.pageItemList.add(pageItemType);
        }

        notifyItemInserted(getItemCount()-list.size());
    }


    @Override
    public int getItemViewType(int position) {
        if(position < getVeiwTypeHeadRange()) {
            return VIEWTYPE_PAGEITEM_HEAD;
        }else if(position<getVeiwTypePageItemRange()){
            return VIEWTYPE_PAGEITEM;
        }else if(position<getVeiwTypeFooterRange()){
            return VIEWTYPE_PAGEITEM_FOOTER;
        }else
            return VIEWTYPE_NONE;
    }

    protected int getVeiwTypeHeadRange(){
        return PAGEITEM_HEAD;
    }

    protected int getVeiwTypePageItemRange(){
        return getVeiwTypeHeadRange()+pageItemList.size();
    }

    protected int getVeiwTypeFooterRange(){
        return getVeiwTypePageItemRange()+PAGEITEM_FOOTER;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEWTYPE_PAGEITEM_HEAD){
            currentViewType = VIEWTYPE_PAGEITEM_HEAD;
            return onCreateViewHolderPageHeader(parent);
        }else if(viewType == VIEWTYPE_PAGEITEM){
            currentViewType = VIEWTYPE_PAGEITEM;
            return onCreateViewHolderPageItem(parent);
        }else if(viewType == VIEWTYPE_PAGEITEM_FOOTER){
            currentViewType = VIEWTYPE_PAGEITEM_FOOTER;
            return onCreateViewHolderPageFooter(parent);
        }else{
            return null;
        }
    }
    abstract public viewHolder onCreateViewHolderPageHeader(@NonNull ViewGroup parent);
    abstract public viewHolder onCreateViewHolderPageItem(@NonNull ViewGroup parent);
    abstract public viewHolder onCreateViewHolderPageFooter(@NonNull ViewGroup parent);

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        switch (currentViewType){
            case VIEWTYPE_PAGEITEM_HEAD:
                onBindPageHeader(holder,position);
                break;

            case VIEWTYPE_PAGEITEM:
                int itemPosition = getPositionPageItem(position);
                onBindPageItem(holder,itemPosition);
                break;

            case VIEWTYPE_PAGEITEM_FOOTER:
                onBindPageFooter(holder,position);
                break;

                default:
                    Log.e("PageAdapter","VIEWTYPE_NONE");
        }

        currentViewType = VIEWTYPE_NONE;
    }

    public int getPositionPageItem(int position){
        return position-getVeiwTypeHeadRange();
    }

    abstract public void onBindPageHeader(@NonNull viewHolder holder,int position);
    abstract public void onBindPageItem(@NonNull viewHolder holder,int position);
    abstract public void onBindPageFooter(@NonNull viewHolder holder,int position);

    @Override
    public int getItemCount() {
        boolean isPageEnd =  pageManager.isPageEnd();
        if(isPageEnd){
            return pageItemList.size()+PAGEITEM_FOOTER+PAGEITEM_HEAD;
        }else{
            return pageItemList.size()+PAGEITEM_HEAD;
        }
    }
}
