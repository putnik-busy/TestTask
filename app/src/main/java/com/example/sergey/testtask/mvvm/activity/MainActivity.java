package com.example.sergey.testtask.mvvm.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.sergey.testtask.R;
import com.example.sergey.testtask.databinding.ActivityMainBinding;
import com.example.sergey.testtask.mvvm.model.HomeScreenModel;
import com.example.sergey.testtask.mvvm.viewmodel.HomeActivityViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    private Toolbar mToolbar;
    private HomeScreenModel mModel;
    private HomeActivityViewModel mViewModel;
    private boolean mOpenSheetCurrency = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new HomeActivityViewModel(this);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mViewModel.setBinding(mBinding);
        mBinding.setViewModel(mViewModel);
        openCurrencyConverter(null, 0);
    }

    public void openCurrencyConverter(String nameCountry, int drawableRes) {
        mViewModel.openCurrencyConverter(nameCountry, drawableRes);
        mOpenSheetCurrency = false;
    }

    public void openSheetCurrency() {
        mViewModel.openSheetCurrency();
        mOpenSheetCurrency = true;
    }

    @Override
    public void onBackPressed() {
        if (mOpenSheetCurrency) {
            openCurrencyConverter(null, 0);
            mOpenSheetCurrency = false;
        } else super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                openCurrencyConverter(null, 0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
