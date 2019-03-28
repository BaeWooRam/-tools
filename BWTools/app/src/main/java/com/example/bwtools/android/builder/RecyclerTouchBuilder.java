package com.example.bwtools.android.builder;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class RecyclerTouchBuilder {
    private final String TAG ="RecyclerTouchBuilder";
    private RecyclerView mRecyclerView;
    private Activity mContext;

    public RecyclerTouchBuilder setRecyclerView(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
        return this;
    }

    public RecyclerTouchBuilder setContext(Activity mContext) {
        this.mContext = mContext;
        return this;
    }

    public RecyclerTouchBuilder build() {
        new RecyclerTouch();
        return this;
    }


    class RecyclerTouch{
        public RecyclerTouch() {
            if(mContext == null || mRecyclerView == null)
                new Error(TAG+" Error! Please input context and recyclerview.");

            final GestureDetector GestureDetector = new GestureDetector(mContext,new GestureDetector.SimpleOnGestureListener() {

                //누르고 뗄 때 한번만 인식하도록 하기위해서
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });

            mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                @Override
                public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                    return itemClickEvent(recyclerView,motionEvent,GestureDetector);
                }

                @Override
                public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

                }

                @Override
                public void onRequestDisallowInterceptTouchEvent(boolean b) {

                }
            });
        }
    }

    protected abstract boolean itemClickEvent(RecyclerView recyclerView,MotionEvent motionEvent, GestureDetector gestureDetector);
}
