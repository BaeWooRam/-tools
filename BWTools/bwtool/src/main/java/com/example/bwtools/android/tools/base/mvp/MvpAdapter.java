package com.example.bwtools.android.tools.base.mvp;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public interface MvpAdapter<Set,Add> {
    ArrayList<Set> getList();

    void setList(@NonNull ArrayList<Set> list);
    void addList(@NonNull ArrayList<Add> list);
}
