package com.example.bwtools.android.tools.base;

import com.example.bwtools.android.tools.base.mvp.MvpPresenter;

public class BasePresenter<mvpView> implements MvpPresenter<mvpView> {
    protected mvpView mvpView;

    protected BasePresenter(mvpView mvpView) {
        setupMvpView(mvpView);
    }

    @Override
    public void setupMvpView(mvpView mvpView) {
        this.mvpView = mvpView;
    }
}
