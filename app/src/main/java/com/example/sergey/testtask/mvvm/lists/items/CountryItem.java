package com.example.sergey.testtask.mvvm.lists.items;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.DrawableRes;

import com.example.sergey.testtask.R;
import com.example.sergey.testtask.mvvm.lists.items.interfaces.ItemWithLayout;

import com.example.sergey.testtask.BR;

/**
 * @author Sergey Rodionov
 */

public class CountryItem extends BaseObservable implements ItemWithLayout {

    @DrawableRes
    private int drawableRes;
    private String countryName;

    @Override
    public int getLayoutId() {
        return R.layout.item_list;
    }

    @Bindable
    public int getDrawableRes() {
        return drawableRes;
    }

    public void setDrawableRes(int drawableRes) {
        this.drawableRes = drawableRes;
        notifyPropertyChanged(BR.drawableRes);
    }

    @Bindable
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
        notifyPropertyChanged(BR.countryName);
    }
}
