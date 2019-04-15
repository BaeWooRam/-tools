package com.example.bwtools.android.tools.imp;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.MotionEvent;


import com.example.bwtools.android.tools.interfaces.RecyclerTouchImp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public abstract class FactoryRecyclerTouch implements RecyclerTouchImp {
    private final String TAG ="FactoryRecyclerTouch";
    private RecyclerView targetRecyclerView;
    private GestureDetector GestureDetector;
    private Activity targetActivity;

    public FactoryRecyclerTouch(RecyclerView thisRecyclerView, Activity thisActivity) {
        this.targetRecyclerView = thisRecyclerView;
        this.targetActivity = thisActivity;
    }

    @Override
    public void setupTouchListener() {
        setupGestureDetetor();
        setupRecyclerItemTouchListener();
    }


    private void setupGestureDetetor(){
        GestureDetector = new GestureDetector(targetActivity,new GestureDetector.SimpleOnGestureListener() {

            //누르고 뗄 때 한번만 인식하도록 하기위해서
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    private void setupRecyclerItemTouchListener(){
        targetRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                return setupHandleItemTouch(recyclerView, motionEvent, GestureDetector);
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });
    }

    protected abstract boolean setupHandleItemTouch(RecyclerView targetRecycler, MotionEvent motionEvent, GestureDetector gestureDetector);


    /**
     * @param layoutOrientation VERTICAL = 1,  HORIZONTAL = 0;
     */
    @Override
    public void setupRecyclerGridLayoutOrientation(int span, int layoutOrientation) {
        StaggeredGridLayoutManager LayoutManager = new StaggeredGridLayoutManager(span, layoutOrientation);
        LayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        targetRecyclerView.setLayoutManager(LayoutManager);
    }

    @Override
    @Deprecated
    public void setupRecyclerGridLayoutOrientation(int span, int layoutOrientation, RecyclerView.ItemDecoration itemDecoration) {
        StaggeredGridLayoutManager LayoutManager = new StaggeredGridLayoutManager(span, layoutOrientation);
        LayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        targetRecyclerView.addItemDecoration(itemDecoration);
        targetRecyclerView.setLayoutManager(LayoutManager);
    }

    @Override
    public void setupRecyclerAnimator(RecyclerView.ItemAnimator recyclerAnimator) {
        targetRecyclerView.setItemAnimator(recyclerAnimator);

    }

    /**
     * @param layoutOrientation VERTICAL = 1,  HORIZONTAL = 0;
     */
    @Override
    public void setupRecyclerLinearLayoutOrientation(int layoutOrientation) {
        LinearLayoutManager LayoutManager = new LinearLayoutManager(targetActivity);
        LayoutManager.setOrientation(layoutOrientation);
        targetRecyclerView.setLayoutManager(LayoutManager);
    }


    @Override
    @Deprecated
    public void setupRecyclerLinearLayoutOrientation(int layoutOrientation, RecyclerView.ItemDecoration itemDecoration) {
        LinearLayoutManager LayoutManager = new LinearLayoutManager(targetActivity);
        LayoutManager.setOrientation(layoutOrientation);
        targetRecyclerView.addItemDecoration(itemDecoration);
        targetRecyclerView.setLayoutManager(LayoutManager);
    }

    @Override
    public void setupRecyclerAdapter(RecyclerView.Adapter recyclerAdapter) {
        targetRecyclerView.setAdapter(recyclerAdapter);
    }

}
