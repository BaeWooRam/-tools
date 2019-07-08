package com.example.bwtools.android.tools.interfaces;

import androidx.recyclerview.widget.RecyclerView;

public interface RecyclerImp {
    void setupRecyclerLinearLayoutOrientation(int layoutOrientation);
    void setupRecyclerLinearLayoutOrientation(int layoutOrientation, RecyclerView.ItemDecoration itemDecoration);
    void setupRecyclerGridLayoutOrientation(int span, int layoutOrientation);
    void setupRecyclerGridLayoutOrientation(int span, int layoutOrientation, RecyclerView.ItemDecoration itemDecoration);
    void setupRecyclerAnimator(RecyclerView.ItemAnimator recyclerAnimation);
    void setupRecyclerAdapter(RecyclerView.Adapter recyclerAdapter);
}
