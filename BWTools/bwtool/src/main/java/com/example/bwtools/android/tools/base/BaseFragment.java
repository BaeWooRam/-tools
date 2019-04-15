package com.example.bwtools.android.tools.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    private View thisLayout;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisLayout = inflater.inflate(getLayoutId(), container, false);
        SetUp();
        return thisLayout;
    }

    @LayoutRes
    public abstract int getLayoutId();

    public View getFragmentLayout(){
        return thisLayout;
    }

    public abstract void SetUp();

}
