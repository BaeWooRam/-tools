package com.example.bwtools.android.view.infinitePagerAdatper;

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
