package com.example.sergey.testtask.mvvm.viewmodel;

import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;

import com.example.sergey.testtask.BR;
import com.example.sergey.testtask.databinding.FragmentSheetCurrencyBinding;
import com.example.sergey.testtask.mvvm.activity.MainActivity;
import com.example.sergey.testtask.mvvm.database.Database;
import com.example.sergey.testtask.mvvm.lists.adapters.UniversalRecyclerViewAdapter;
import com.example.sergey.testtask.mvvm.lists.items.CountryItem;
import com.example.sergey.testtask.mvvm.lists.items.interfaces.ItemWithLayout;
import com.example.sergey.testtask.mvvm.model.SheetCurrencyDataModel;
import com.example.sergey.testtask.mvvm.model.SheetCurrencyModel;
import com.example.sergey.testtask.mvvm.utils.DimensionUtils;
import com.example.sergey.testtask.mvvm.utils.SimpleHorizontalDivider;
import com.example.sergey.testtask.mvvm.viewmodel.customdependencies.ItemClickDependency;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergey Rodionov
 */

public class SheetCurrencyViewModel implements ItemClickDependency.onItemClickListener {

    private SheetCurrencyModel mModel;
    private AppCompatActivity mActivity;
    private List<ItemWithLayout> mItems;
    private List<SheetCurrencyDataModel.SheetCurrencyDataModelList> mSheetCurrencyDataModelList;

    public SheetCurrencyViewModel(AppCompatActivity activity, FragmentSheetCurrencyBinding binding) {
        mActivity = activity;
        mModel = binding.getModel();
        mSheetCurrencyDataModelList = new ArrayList<>();
        initDivider();
        Database.getInstance(mActivity).getListInfoCurrencyAsync(
                new Database.DatabaseHand<List<SheetCurrencyDataModel.SheetCurrencyDataModelList>>() {
                    @Override
                    public void onComplete(boolean success, List<SheetCurrencyDataModel.SheetCurrencyDataModelList> result) {
                        if (success) {
                            mSheetCurrencyDataModelList.addAll(result);
                            mModel.setCountryDataModelList(mSheetCurrencyDataModelList);
                            initAdapter();
                        }
                    }
                });
    }

    private void initAdapter() {
        mItems = new ArrayList<>();
        for (int i = 0; i < mModel.getCountryDataModelList().size(); i++) {
            CountryItem countryItem = new CountryItem();
            countryItem.setCountryName(mModel.getCountryDataModelList().get(i).getName());
            countryItem.setDrawableRes(getIdentifierImage(mModel.getCountryDataModelList()
                    .get(i).getCharCode().toLowerCase()));
            mItems.add(countryItem);
        }
        mModel.setCountryAdapter(new UniversalRecyclerViewAdapter(mItems, this, BR.viewModel));
    }

    private void initDivider() {
        SimpleHorizontalDivider.Builder divider = new SimpleHorizontalDivider.Builder()
                .setColor(Color.GRAY)
                .setMargin(new Rect(28, 0, 0, 0))
                .setDividerType(SimpleHorizontalDivider.DividerType.Bottom)
                .setStrokeWidth(DimensionUtils.dpToPx(mActivity, 1));
        mModel.setDividers(divider);
    }

    private int getIdentifierImage(String fileName) {
        if (fileName.equals("try")) {
            return mActivity.getResources().getIdentifier("tyrkey", "drawable", mActivity.getPackageName());
        }
        return mActivity.getResources().getIdentifier(fileName, "drawable", mActivity.getPackageName());
    }

    @Override
    public void onItemClick(Object param) {
        if (param != null && param instanceof CountryItem) {

            final String nameCountry;
            final int drawableRes;

            nameCountry = ((CountryItem) param).getCountryName();
            drawableRes = ((CountryItem) param).getDrawableRes();
            ((MainActivity) mActivity).openCurrencyConverter(nameCountry, drawableRes);
        }
    }
}
