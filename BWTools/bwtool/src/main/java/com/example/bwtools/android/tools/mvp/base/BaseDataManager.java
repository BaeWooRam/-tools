package com.example.bwtools.android.tools.mvp.base;

import android.util.Log;

import com.example.bwtools.android.tools.mvp.MvpAdapter;
import com.example.bwtools.android.tools.mvp.MvpNetworkData;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public abstract class BaseDataManager<Type> implements MvpNetworkData<Type> {
    public String TAG = "BaseDataManager";
    private ArrayList<Type> DataList;
    private MvpAdapter<Type> mvpAdapter;

    public BaseDataManager() {
        this.DataList = new ArrayList<>();
    }

    public BaseDataManager(MvpAdapter<Type> mvpAdapter) {
        this.DataList = new ArrayList<>();
        this.mvpAdapter =mvpAdapter;
    }

    @Override
    public ArrayList<Type> getList() {
        return DataList;
    }

    @Override
    public void removeList() {
        if(mvpAdapter != null && DataList != null){
            mvpAdapter.removeList();
        }


        if(DataList != null){
            DataList.clear();
            handleMangerAfterRemoveDataList();
        }
        else{
            Log.e(TAG,"DataList is Null!");
        }
    }

    @Override
    public void setList(@NonNull ArrayList<Type> list) {
        if(mvpAdapter != null && DataList != null){
            mvpAdapter.setList(DataList);
        }

        if(DataList != null){
            DataList = list;
            handleMangerAfterSetDataList(DataList);
        }
        else{
            Log.e(TAG,"DataList is Null!");
        }

    }

    @Override
    public void addList(@NonNull ArrayList<Type> list) {
        if(mvpAdapter != null && DataList != null){
            mvpAdapter.addList(DataList);
        }
        if(DataList != null){
            for(Type type : list){
                DataList.add(type);
            }
            handleMangerAfterAddDataList(DataList);
        }
        else{
            Log.e(TAG,"DataList is Null!");
        }
    }

    public abstract void handleMangerAfterRemoveDataList();
    public abstract void handleMangerAfterSetDataList(@NonNull ArrayList<Type> dataList);
    public abstract void handleMangerAfterAddDataList(@NonNull ArrayList<Type> dataList);
}
