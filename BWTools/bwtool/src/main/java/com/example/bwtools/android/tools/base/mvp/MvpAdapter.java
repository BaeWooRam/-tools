package com.example.bwtools.android.tools.base.mvp;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public interface MvpAdapter<Type> {
    ArrayList<Type> getList();
    void removeList();
    void setList(@NonNull ArrayList<Type> list);
    void addList(@NonNull ArrayList<Type> list);
}
