package com.example.bwtools.android;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentUtils {
    public static void NavigateToFragment(Fragment fragment, FragmentManager manager, int container_id) {
        FragmentTransaction transaction = manager.beginTransaction().replace(container_id, fragment);
        transaction.commit();
    }

    public static void StackNavigateToFragment(Fragment fragment, FragmentManager manager, int container_id, String AddToBackStack) {
        FragmentTransaction transaction = manager.beginTransaction().replace(container_id, fragment).addToBackStack(AddToBackStack);
        transaction.commit();
    }
}
