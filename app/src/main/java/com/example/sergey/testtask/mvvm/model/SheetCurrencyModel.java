package com.example.sergey.testtask.mvvm.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.sergey.testtask.BR;
import com.example.sergey.testtask.mvvm.lists.adapters.UniversalRecyclerViewAdapter;
import com.example.sergey.testtask.mvvm.utils.SimpleHorizontalDivider;

import java.util.List;

/**
 * @author Sergey Rodionov
 */

public class SheetCurrencyModel extends BaseObservable {

    private UniversalRecyclerViewAdapter mCountryAdapter;
    private SimpleHorizontalDivider.Builder mDividers;
    private List<SheetCurrencyDataModel.SheetCurrencyDataModelList> mCountryDataModelList;
    private SheetCurrencyDataModel mDataModel;

    @Bindable
    public UniversalRecyclerViewAdapter getCountryAdapter() {
        return mCountryAdapter;
    }

    public void setCountryAdapter(UniversalRecyclerViewAdapter countryAdapter) {
        this.mCountryAdapter = countryAdapter;
        notifyPropertyChanged(BR.countryAdapter);
    }

    public SimpleHorizontalDivider.Builder getDividers() {
        return mDividers;
    }

    public void setDividers(SimpleHorizontalDivider.Builder dividers) {
        this.mDividers = dividers;
    }

    public List<SheetCurrencyDataModel.SheetCurrencyDataModelList> getCountryDataModelList() {
        return mCountryDataModelList;
    }

    public void setCountryDataModelList(List<SheetCurrencyDataModel.SheetCurrencyDataModelList> countryDataModelList) {
        this.mCountryDataModelList = countryDataModelList;
    }

    public SheetCurrencyDataModel getDataModel() {
        return mDataModel;
    }

    public void setDataModel(SheetCurrencyDataModel dataModel) {
        this.mDataModel = dataModel;
    }
}
