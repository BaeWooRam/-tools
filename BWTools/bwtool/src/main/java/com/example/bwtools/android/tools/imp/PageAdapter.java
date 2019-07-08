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

    private final int PAGEITEM_HEAD =1 ;
    private final int PAGEITEM_FOOTER =1;
    private int currentViewType;
    private boolean isDataSet = false;

    protected PageAdapter(PageManager pageManager) {
        this.pageManager = pageManager;
        this.pageItemList = new ArrayList<>();
        currentViewType = VIEWTYPE_NONE;
    }

    public ArrayList<PageItemType> getList() {
        return pageItemList;
    }

    public PageItemType getPageItem(int position) {
        int itemPosition = getPositionPageItem(position);

        if(itemPosition >= 0)
            return  pageItemList.get(itemPosition);
        else
            return null;
    }

    public void removePageList() {

        isDataSet = false;
        this.pageItemList.clear();
    }

    public void setPageItemList(@NonNull ArrayList<PageItemType> list) {
        isDataSet = true;

        if(pageItemList == null)
            this.pageItemList = new ArrayList<>();

        this.pageItemList = list == null ? new ArrayList<PageItemType>() : list;

        notifyDataSetChanged();
    }

    public void addPageItemList(@NonNull ArrayList<PageItemType> list) {
        isDataSet = true;

        if(list == null)
            list = new ArrayList<>();

        if(pageItemList == null)
            this.pageItemList = new ArrayList<>();

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

    private int getVeiwTypeHeadRange(){
        return pageItemList.size() > 0 ? PAGEITEM_HEAD : 0;
    }

    private int getVeiwTypePageItemRange(){
        return pageItemList.size() > 0 ? getVeiwTypeHeadRange()+pageItemList.size() : 0;
    }

    private int getVeiwTypeFooterRange(){
        return pageItemList.size() > 0 ? getVeiwTypePageItemRange()+PAGEITEM_FOOTER : 0;
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
            return onCreateViewHolderNoneList(parent);
        }
    }
    abstract protected viewHolder onCreateViewHolderPageHeader(@NonNull ViewGroup parent);
    abstract protected viewHolder onCreateViewHolderPageItem(@NonNull ViewGroup parent);
    abstract protected viewHolder onCreateViewHolderPageFooter(@NonNull ViewGroup parent);
    abstract protected viewHolder onCreateViewHolderNoneList(@NonNull ViewGroup parent);

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

    public int getPositionPageItem(int positionRecyclerview){
        return pageItemList.size() > 0 ? positionRecyclerview-getVeiwTypeHeadRange() : -1;
    }

    abstract protected void onBindPageHeader(@NonNull viewHolder holder,int position);
    abstract protected void onBindPageItem(@NonNull viewHolder holder,int position);
    abstract protected void onBindPageFooter(@NonNull viewHolder holder,int position);

    @Override
    public int getItemCount() {
        boolean isPageEnd =  pageManager.isPageEnd();

        if(pageItemList.size() > 0){
            if(isPageEnd){
                return pageItemList.size()+PAGEITEM_FOOTER+PAGEITEM_HEAD;
            }else{
                return pageItemList.size()+PAGEITEM_HEAD;
            }
        }else{
            return isDataSet == false ? 0 : 1;
        }
    }
}
