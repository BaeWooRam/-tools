package com.example.bwtools.android.util;


import android.util.Log;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentUtils {
    private final String TAG = "FragmentUtils";
    public static void ContainerReplaceFragment(Fragment replaceFragment, FragmentManager fragmentManager, @IdRes int containerID) {
        try {
            FragmentTransaction transaction = fragmentManager.beginTransaction().replace(containerID, replaceFragment);
            transaction.commit();
        }catch (Exception e){
            Log.e("TAG",e.toString());
        }

    }

    public static void StackContainerReplaceFragment(Fragment replaceFragment, FragmentManager fragmentManager, @IdRes int containerID) {
        try {
            Log.e("TAG","count = "+fragmentManager.getBackStackEntryCount());
            FragmentTransaction transaction = fragmentManager.beginTransaction().replace(containerID, replaceFragment).addToBackStack(null);
            transaction.commit();
        }catch (Exception e){
            Log.e("TAG",e.toString());
        }
    }
}
