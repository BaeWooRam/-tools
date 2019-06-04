package com.example.bwtools.android.view.infinitePagerAdatper;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.bwtools.android.tools.base.dto.EventAndAd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EventAndADImageFragment extends Fragment {
    private String targetURI;
    private int targetLayoutID;
    private int targetImageViewID;


    public static EventAndADImageFragment newInstance(EventAndAd targetEvent, int targetLayoutID, int targetImageViewID) {
        EventAndADImageFragment fragment = new EventAndADImageFragment();

        fragment.targetURI = targetEvent.getImageThumbnail();
        fragment.targetLayoutID = targetLayoutID;
        fragment.targetImageViewID = targetImageViewID;
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = View.inflate(getContext(), targetLayoutID, null);
        ImageView mainImageView = v.findViewById(targetImageViewID);

        Glide.with(getContext())
                .load(targetURI)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mainImageView);

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... Void) {
                Glide.get(getContext()).clearDiskCache();
                return null;
            }
        }.execute();

        try {
            finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
