package com.example.bwtools.android.builder.naverapi;

import java.util.ArrayList;

public interface MvpAdapter<T,B> {
    ArrayList<T> getList();

    void setList(ArrayList<T> list);
    void addList(ArrayList<B> list);
}
