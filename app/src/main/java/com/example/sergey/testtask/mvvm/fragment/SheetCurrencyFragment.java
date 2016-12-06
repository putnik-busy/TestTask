package com.example.sergey.testtask.mvvm.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sergey.testtask.R;
import com.example.sergey.testtask.databinding.FragmentSheetCurrencyBinding;
import com.example.sergey.testtask.mvvm.model.SheetCurrencyModel;
import com.example.sergey.testtask.mvvm.viewmodel.SheetCurrencyViewModel;

/**
 * @author Sergey Rodionov
 */

public class SheetCurrencyFragment extends Fragment implements ContainerFragment {

    private SheetCurrencyViewModel mViewModel;
    private SheetCurrencyModel mModel;
    private FragmentSheetCurrencyBinding mBinding;

    public static SheetCurrencyFragment newInstance() {
        SheetCurrencyFragment sheetCurrencyFragment = new SheetCurrencyFragment();
        return sheetCurrencyFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentSheetCurrencyBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();
        mModel = new SheetCurrencyModel();
        mBinding.setModel(mModel);
        mViewModel = new SheetCurrencyViewModel((AppCompatActivity) getActivity(), mBinding);
        mBinding.setViewModel(mViewModel);
        return view;
    }

    @Override
    public int getTitle() {
        return R.string.second_title;
    }

    @Override
    public String getTitleText() {
        return null;
    }
}
