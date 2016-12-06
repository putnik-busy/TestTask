package com.example.sergey.testtask;

import android.app.Application;

import com.example.sergey.testtask.mvvm.model.CurrencyConverterModel;

/**
 * @author Sergey Rodionov
 */

public class MyApplication extends Application {

    private static MyApplication sInstance;
    private CurrencyConverterModel mCurrencyConverterModel;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public synchronized static MyApplication getInstance() {
        return sInstance;
    }

    public CurrencyConverterModel getCurrencyConverterModel() {
        return mCurrencyConverterModel;
    }

    public void setCurrencyConverterModel(CurrencyConverterModel currencyConverterModel) {
        this.mCurrencyConverterModel = currencyConverterModel;
    }
}
