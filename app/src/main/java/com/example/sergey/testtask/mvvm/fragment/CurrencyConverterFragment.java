package com.example.sergey.testtask.mvvm.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sergey.testtask.R;
import com.example.sergey.testtask.databinding.FragmentCurrencyConverterBinding;
import com.example.sergey.testtask.mvvm.model.CurrencyConverterModel;
import com.example.sergey.testtask.mvvm.viewmodel.CurrencyConverterViewModel;

/**
 * @author Sergey Rodionov
 */

public class CurrencyConverterFragment extends Fragment implements ContainerFragment {

    private CurrencyConverterViewModel mViewModel;
    private CurrencyConverterModel mModel;
    private FragmentCurrencyConverterBinding mBinding;
    private String mNameCountry;
    private int mDrawableRes;

    public static CurrencyConverterFragment newInstance(String nameCountry, int drawableRes) {
        CurrencyConverterFragment currencyConverterFragment = new CurrencyConverterFragment();
        currencyConverterFragment.setNameCountry(nameCountry);
        currencyConverterFragment.setDrawableRes(drawableRes);
        return currencyConverterFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentCurrencyConverterBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();
        mModel = new CurrencyConverterModel();
        mBinding.setModel(mModel);
        mViewModel = new CurrencyConverterViewModel((AppCompatActivity) getActivity(), mBinding,
                getNameCountry(), getDrawableRes());
        mBinding.setViewModel(mViewModel);
        return view;
    }

    @Override
    public int getTitle() {
        return R.string.main_title;
    }

    @Override
    public String getTitleText() {
        return null;
    }

    public String getNameCountry() {
        return mNameCountry;
    }

    public void setNameCountry(String nameCountry) {
        this.mNameCountry = nameCountry;
    }

    public int getDrawableRes() {
        return mDrawableRes;
    }

    public void setDrawableRes(int drawableRes) {
        this.mDrawableRes = drawableRes;
    }
}
