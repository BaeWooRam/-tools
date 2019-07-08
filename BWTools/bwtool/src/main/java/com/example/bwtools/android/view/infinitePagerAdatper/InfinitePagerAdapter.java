package com.example.bwtools.android.view.infinitePagerAdatper;

import android.util.Log;
import android.view.ViewGroup;

import com.example.bwtools.android.tools.base.mvp.MvpAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


public abstract class InfinitePagerAdapter<Type> extends FragmentStatePagerAdapter implements MvpAdapter<Type>
    {
        private final String TAG = "InfinitePagerAdapter";
        private final boolean DEBUG = true;
        public int LOOPS_COUNT = 400;
        protected ArrayList<Type> DataList;

        public InfinitePagerAdapter(FragmentManager manager,ArrayList<Type> DataList)
        {
            super(manager);
            this.DataList = DataList;
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
            if (DataList != null && DataList.size() > 0)
            {
                debug("getItem: real position: " + position);
                position = getVirtualPosition(position); // use modulo for infinite cycling
                debug("getItem: virtual position: " + position);

                return setItemFragment(DataList, position);

            }
            else
            {
                //TODO Size 0일때
                return new Fragment();
            }
        }

        abstract public Fragment setItemFragment(final ArrayList<Type> DataList, final int position);

        @Override
        public int getCount()
        {
            if (DataList != null && DataList.size() > 0)
            {
                return DataList.size()*LOOPS_COUNT; // simulate infinite by big number of products
            }
            else
            {
                return 1;
            }
        }

        public int getVirtualPosition(int position){
            int count = DataList.size();
            return count <= 0 ? 1:position % DataList.size();
        }

        @Override
        public ArrayList<Type> getList() {
            return DataList;
        }

        @Override
        public void setList(@NonNull ArrayList<Type> list) {
            this.DataList = list;
            notifyDataSetChanged();
        }

        @Override
        public void addList(@NonNull ArrayList<Type> list) {
            for(Type type : list)
                this.DataList.add(type);

            notifyDataSetChanged();
        }

        @Override
        public void removeList() {
            this.DataList.clear();
            notifyDataSetChanged();
        }

        public int getLoopsStartPostion(){
            return DataList.size()*(LOOPS_COUNT/2);
        }

        private void debug(String message) {
            if (DEBUG) {
                Log.e(TAG, message);
            }
        }
    }
