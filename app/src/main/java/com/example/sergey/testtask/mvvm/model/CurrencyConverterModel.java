package com.example.sergey.testtask.mvvm.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.DrawableRes;

import com.example.sergey.testtask.BR;

/**
 * @author Sergey Rodionov
 */

public class CurrencyConverterModel extends BaseObservable {

    @DrawableRes
    private int beginDrawableRes;
    @DrawableRes
    private int endDrawableRes;
    private String beginCountry;
    private String endCountry;
    private double resultConvert;
    private String inputCount;
    private int selectedField;

    @Bindable
    public int getBeginDrawableRes() {
        return beginDrawableRes;
    }

    public void setBeginDrawableRes(int beginDrawableRes) {
        this.beginDrawableRes = beginDrawableRes;
        notifyPropertyChanged(BR.drawableRes);
    }

    @Bindable
    public int getEndDrawableRes() {
        return endDrawableRes;
    }

    public void setEndDrawableRes(int endDrawableRes) {
        this.endDrawableRes = endDrawableRes;
        notifyPropertyChanged(BR.endDrawableRes);
    }

    @Bindable
    public String getBeginCountry() {
        return beginCountry;
    }

    public void setBeginCountry(String beginCountry) {
        this.beginCountry = beginCountry;
        notifyPropertyChanged(BR.beginCountry);
    }

    @Bindable
    public String getEndCountry() {
        return endCountry;
    }

    public void setEndCountry(String endCountry) {
        this.endCountry = endCountry;
        notifyPropertyChanged(BR.endCountry);
    }

    @Bindable
    public double getResultConvert() {
        return resultConvert;
    }

    public void setResultConvert(double resultConvert) {
        this.resultConvert = resultConvert;
        notifyPropertyChanged(BR.resultConvert);
    }

    @Bindable
    public String getInputCount() {
        return inputCount;
    }

    public void setInputCount(String inputCount) {
        this.inputCount = inputCount;
        notifyPropertyChanged(BR.inputCount);
    }

    public int getSelectedField() {
        return selectedField;
    }

    public void setSelectedField(int selectedField) {
        this.selectedField = selectedField;
    }
}
