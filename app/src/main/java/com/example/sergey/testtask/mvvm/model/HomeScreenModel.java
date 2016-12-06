package com.example.sergey.testtask.mvvm.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.sergey.testtask.BR;

/**
 * @author Sergey Rodionov
 */

public class HomeScreenModel extends BaseObservable {

    private int mTitleToolbar;
    private String mTitleToolbarString;
    private boolean mVisibleHomeButton;

    @Bindable
    public int getTitleToolbar() {
        return mTitleToolbar;
    }

    public void setTitleToolbar(int titleToolbar) {
        this.mTitleToolbar = titleToolbar;
        notifyPropertyChanged(BR.titleToolbar);
    }

    @Bindable
    public String getTitleToolbarString() {
        return mTitleToolbarString;
    }

    public void setTitleToolbarString(String titleToolbarString) {
        this.mTitleToolbarString = titleToolbarString;
        notifyPropertyChanged(BR.titleToolbarString);
    }

    @Bindable
    public boolean isVisibleHomeButton() {
        return mVisibleHomeButton;
    }

    public void setVisibleHomeButton(boolean visibleHomeButton) {
        this.mVisibleHomeButton = visibleHomeButton;
        notifyPropertyChanged(BR.visibleHomeButton);
    }
}
