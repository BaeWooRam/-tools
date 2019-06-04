package com.example.bwtools.android.view.itemdecoration;

import android.content.Context;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DecorationRvLinearVertical extends RecyclerView.ItemDecoration {
    private final short INPUT_PADDING4 = 0;
    private final short INPUT_PADDING2 = 1;

    private int paddingTop;
    private int paddingBottom;
    private int paddingLeft;
    private int paddingRight;

    private int paddingVertical;
    private int paddingHorizon;

    private final Context targetContext;
    private short State;

    public DecorationRvLinearVertical(Context targetContext, int paddingTop, int paddingBottom, int paddingLeft, int paddingRight) {
        this.targetContext = targetContext;
        this.paddingTop = dpToPx(paddingTop);
        this.paddingBottom = dpToPx(paddingBottom);
        this.paddingLeft = dpToPx(paddingLeft);
        this.paddingRight = dpToPx(paddingRight);
        this.State = INPUT_PADDING4;
    }

    public DecorationRvLinearVertical(Context targetContext, int paddingVertical, int paddingHorizon) {
        this.targetContext = targetContext;
        this.paddingVertical = dpToPx(paddingVertical);
        this.paddingHorizon = dpToPx(paddingHorizon);
        this.State = INPUT_PADDING2;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);
        int itemCount = state.getItemCount();

        switch (State){
            case INPUT_PADDING2:
                applayInputPadding2(position,itemCount,outRect);
                break;
            case INPUT_PADDING4:
                applayInputPadding4(position,itemCount,outRect);
                break;
        }


    }

    private void applayInputPadding4(int position, int itemCount, Rect outRect){
        int firstPosition = 0;
        int LastPosition = itemCount-1;

        if(position == firstPosition) {
            applayFirstItem(outRect,paddingTop,paddingBottom,paddingLeft,paddingRight);
        } else if(position == LastPosition){
            applayLastItem(outRect,paddingTop,paddingBottom,paddingLeft,paddingRight);
        }else{
            applayMiddleItem(outRect,paddingTop,paddingBottom,paddingLeft,paddingRight);
        }
    }

    private void applayInputPadding2(int position, int itemCount, Rect outRect){
        int firstPosition = 0;
        int LastPosition = itemCount-1;

        if(position == firstPosition) {
            applayFirstItem(outRect,paddingVertical,paddingVertical,paddingHorizon,paddingHorizon);
        } else if(position == LastPosition){
            applayLastItem(outRect,paddingVertical,paddingVertical,paddingHorizon,paddingHorizon);
        }else{
            applayMiddleItem(outRect,paddingVertical,paddingVertical,paddingHorizon,paddingHorizon);
        }
    }

    private void applayFirstItem(Rect outRect,int Top, int Bottom, int Left, int Right){
        outRect.top = Top;
        outRect.bottom = Bottom/2;
        outRect.left = Left;
        outRect.right = Right;
    }

    private void applayMiddleItem(Rect outRect,int Top, int Bottom, int Left, int Right){
        outRect.top = Top/2;
        outRect.bottom = Bottom/2;
        outRect.left = Left;
        outRect.right = Right;
    }

    private void applayLastItem(Rect outRect, int Top, int Bottom, int Left, int Right){
        outRect.top = Top/2;
        outRect.bottom = Bottom;
        outRect.left = Left;
        outRect.right = Right;
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension
                (TypedValue.COMPLEX_UNIT_DIP, dp, targetContext.getResources().getDisplayMetrics());
    }

}
