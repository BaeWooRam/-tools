package com.example.bwtools.android.view.infinitePagerAdatper;

<<<<<<< HEAD
import android.database.DataSetObserver;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

/**
 * A PagerAdapter that wraps around another PagerAdapter to handle paging wrap-around.
 */
public class InfinitePagerAdapter extends PagerAdapter {

    private static final String TAG = "InfinitePagerAdapter";
    private static final boolean DEBUG = false;

    private PagerAdapter adapter;

    public InfinitePagerAdapter(PagerAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getCount() {
        if (getRealCount() == 0) {
            return 0;
        }
        // warning: scrolling to very high values (1,000,000+) results in
        // strange drawing behaviour
        return Integer.MAX_VALUE;
    }

    /**
     * @return the {@link #getCount()} result of the wrapped adapter
     */
    public int getRealCount() {
        return adapter.getCount();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int virtualPosition = position % getRealCount();
        debug("instantiateItem: real position: " + position);
        debug("instantiateItem: virtual position: " + virtualPosition);

        // only expose virtual position to the inner adapter
        return adapter.instantiateItem(container, virtualPosition);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int virtualPosition = position % getRealCount();
        debug("destroyItem: real position: " + position);
        debug("destroyItem: virtual position: " + virtualPosition);

        // only expose virtual position to the inner adapter
        adapter.destroyItem(container, virtualPosition, object);
    }

    /*
     * Delegate rest of methods directly to the inner adapter.
     */

    @Override
    public void finishUpdate(ViewGroup container) {
        adapter.finishUpdate(container);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return adapter.isViewFromObject(view, object);
    }

    @Override
    public void restoreState(Parcelable bundle, ClassLoader classLoader) {
        adapter.restoreState(bundle, classLoader);
    }

    @Override
    public Parcelable saveState() {
        return adapter.saveState();
    }

    @Override
    public void startUpdate(ViewGroup container) {
        adapter.startUpdate(container);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int virtualPosition = position % getRealCount();
        return adapter.getPageTitle(virtualPosition);
    }

    @Override
    public float getPageWidth(int position) {
        return adapter.getPageWidth(position);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        adapter.setPrimaryItem(container, position, object);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        adapter.unregisterDataSetObserver(observer);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        adapter.registerDataSetObserver(observer);
    }

    @Override
    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return adapter.getItemPosition(object);
    }

    public int getLoopStartPosition(){
        return (Integer.MAX_VALUE/getCount()/2)*getCount();
    }

    /*
     * End delegation
     */

    private void debug(String message) {
        if (DEBUG) {
            Log.d(TAG, message);
        }
    }

}
=======
import android.util.Log;
import android.view.ViewGroup;

import com.example.bwtools.android.tools.base.dto.EventAndAd;
import com.example.bwtools.android.tools.base.mvp.MvpAdapter;


import java.util.ArrayList;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


public abstract class InfinitePagerAdapter extends FragmentStatePagerAdapter implements MvpAdapter<EventAndAd>
    {
        private final String TAG = "InfinitePagerAdapter";
        private final boolean DEBUG = true;

        public int LOOPS_COUNT = 400;

        private ArrayList<EventAndAd> EventList;

        @LayoutRes
        abstract public int setupItemLayoutID();

        @IdRes
        abstract public int setupItemImageID();

        public InfinitePagerAdapter(FragmentManager manager, ArrayList<EventAndAd> EventList)
        {
            super(manager);
            this.EventList = EventList;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public Fragment getItem(int position)
        {
            if (EventList != null && EventList.size() > 0)
            {
                debug("getItem: real position: " + position);
                position = getVirtualPosition(position); // use modulo for infinite cycling
                debug("getItem: virtual position: " + position);

                return EventAndADImageFragment.newInstance(EventList.get(position),setupItemLayoutID(),setupItemImageID());

            }
            else
            {
                //TODO Size 0일때
                return new Fragment();
            }
        }


        @Override
        public int getCount()
        {
            if (EventList != null && EventList.size() > 0)
            {
                return EventList.size()*LOOPS_COUNT; // simulate infinite by big number of products
            }
            else
            {
                return 1;
            }
        }

        public int getVirtualPosition(int position){
            return position % EventList.size();
        }

        @Override
        public ArrayList<EventAndAd> getList() {
            return EventList;
        }

        @Override
        public void setList(@NonNull ArrayList<EventAndAd> list) {
            this.EventList = list;
            notifyDataSetChanged();
        }

        @Override
        public void addList(@NonNull ArrayList<EventAndAd> list) {
            for(EventAndAd event : list)
                this.EventList.add(event);

            notifyDataSetChanged();
        }

        @Override
        public void removeList() {
            this.EventList.clear();
            notifyDataSetChanged();
        }

        public int getLoopsStartPostion(){
            return EventList.size()*(LOOPS_COUNT/2);
        }

        private void debug(String message) {
            if (DEBUG) {
                Log.e(TAG, message);
            }
        }
    }
>>>>>>> parent of cd21003... Revert "06-04"
