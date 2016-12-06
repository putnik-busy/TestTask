package com.example.sergey.testtask.mvvm.viewmodel;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.sergey.testtask.R;
import com.example.sergey.testtask.databinding.ActivityMainBinding;
import com.example.sergey.testtask.mvvm.fragment.ContainerFragment;
import com.example.sergey.testtask.mvvm.fragment.CurrencyConverterFragment;
import com.example.sergey.testtask.mvvm.fragment.SheetCurrencyFragment;
import com.example.sergey.testtask.mvvm.model.HomeScreenModel;

/**
 * @author Sergey Rodionov
 */

public class HomeActivityViewModel {

    private AppCompatActivity mActivity;
    private HomeScreenModel mModel;
    private ActivityMainBinding mBinding;

    public HomeActivityViewModel(AppCompatActivity activity) {
        mActivity = activity;
        mModel = new HomeScreenModel();
        initToolBar();
    }

    private void initToolBar() {
        if (mActivity.getSupportActionBar() != null) {
            mActivity.getSupportActionBar().setTitle("");
            mActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public void openCurrencyConverter(String nameCountry, int drawableRes) {
        CurrencyConverterFragment currencyConverterFragment = CurrencyConverterFragment
                .newInstance(nameCountry, drawableRes);
        mModel.setVisibleHomeButton(false);
        replaceFragmentContentAndConfig(R.id.containerView, currencyConverterFragment,
                "currencyConverterFragment");
    }

    public void openSheetCurrency() {
        SheetCurrencyFragment sheetCurrencyFragment = SheetCurrencyFragment.newInstance();
        mModel.setVisibleHomeButton(true);
        replaceFragmentContentAndConfig(R.id.containerView, sheetCurrencyFragment, "sheetCurrencyFragment");
    }

    public void replaceFragmentContentAndConfig(int resource, Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = mActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(resource, fragment, tag);
        fragmentTransaction.commit();
        if (fragment instanceof ContainerFragment) {
            ContainerFragment containerFragment = (ContainerFragment) fragment;
            mModel.setTitleToolbar(containerFragment.getTitle());
        }
    }

    public void setBinding(ActivityMainBinding binding) {
        mBinding = binding;
        mBinding.setModel(mModel);
    }
}
