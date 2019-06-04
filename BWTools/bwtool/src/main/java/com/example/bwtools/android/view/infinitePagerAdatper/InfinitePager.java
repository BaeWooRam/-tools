package com.example.bwtools.android.view.infinitePagerAdatper;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.bwtools.android.tools.base.mvp.MvpAdapter;

import java.util.Timer;
import java.util.TimerTask;

import androidx.viewpager.widget.ViewPager;

public class InfinitePager extends ViewPager {
    private Handler autoScrollHandler;
    private Runnable autoScrollUpdate;
    private int currentPage = 0;
    private Timer timer;
    private long DELAY_MS = 500;
    private long PERIOD_MS = 4500;
    private boolean initFlag = true;
    private OnItemClickListener mOnItemClickListener;

    public int getCurrentPage() {
        return currentPage;
    }

    public InfinitePager(Context context) {
        super(context);
        setup();
    }

    public InfinitePager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    private void setup() {
        autoScrollHandler = new Handler();
        autoScrollUpdate = new Runnable() {
            public void run() {
                setCurrentItem(currentPage++, true);
            }
        };

        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int size = getAdapter().getCount();

                if(size > 0)
                    currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        final GestureDetector tapGestureDetector = new  GestureDetector(getContext(), new TapGestureListener());

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tapGestureDetector.onTouchEvent(event);

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.e("touch","move");
                        stopAutoScroll();
                        break;
                    case MotionEvent.ACTION_UP:
                        startAutoScroll();
                        break;
                }

                return false;
            }
        });

    }

    public void startAutoScroll(){
        stopAutoScroll();

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                autoScrollHandler.post(autoScrollUpdate);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    /**
     *
     * @param Interval Ms 단위
     */
    public void startAutoScroll(long Interval){
        this.PERIOD_MS = PERIOD_MS;
        startAutoScroll();
    }

    /**
     *
     * @param interval Ms 단위
     * @param delay Ms 단위
     */
    public void startAutoScroll(long interval, long delay){
        this.PERIOD_MS = PERIOD_MS;
        this.DELAY_MS = delay;

        startAutoScroll();
    }

    public void stopAutoScroll(){
        if(timer !=null) {
            timer.cancel();
            timer=null;
        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private class TapGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if(mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getCurrentItem());
            }
            return true;
       }
    }
}