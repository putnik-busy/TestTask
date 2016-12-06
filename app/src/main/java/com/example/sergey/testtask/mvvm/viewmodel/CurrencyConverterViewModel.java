package com.example.sergey.testtask.mvvm.viewmodel;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.sergey.testtask.MyApplication;
import com.example.sergey.testtask.R;
import com.example.sergey.testtask.databinding.FragmentCurrencyConverterBinding;
import com.example.sergey.testtask.mvvm.Constants;
import com.example.sergey.testtask.mvvm.activity.MainActivity;
import com.example.sergey.testtask.mvvm.api.NetworkApi;
import com.example.sergey.testtask.mvvm.database.Database;
import com.example.sergey.testtask.mvvm.model.CurrencyConverterModel;
import com.example.sergey.testtask.mvvm.model.SheetCurrencyDataModel;
import com.example.sergey.testtask.mvvm.tasks.DownLoadTask;
import com.example.sergey.testtask.mvvm.utils.DialogUtils;
import com.example.sergey.testtask.mvvm.utils.NetworkUtils;
import com.example.sergey.testtask.mvvm.utils.ToastUtils;

import org.simpleframework.xml.core.Persister;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

/**
 * @author Sergey Rodionov
 */

public class CurrencyConverterViewModel implements LoaderManager.LoaderCallbacks<String> {

    private CurrencyConverterModel mModel;
    private AppCompatActivity mActivity;
    private Bundle mBundle;
    private static final int LOADER_RANDOM_ID = 1;
    private Loader<String> mLoader;
    private List<SheetCurrencyDataModel.SheetCurrencyDataModelList> mSheetCurrencyDataModelList;
    private ProgressDialog mProgressDialog;


    public CurrencyConverterViewModel(AppCompatActivity activity,
                                      FragmentCurrencyConverterBinding binding,
                                      String nameCountry, int drawableRes) {
        mActivity = activity;
        mModel = binding.getModel();
        if (MyApplication.getInstance().getCurrencyConverterModel() == null) {
            mProgressDialog = new ProgressDialog(mActivity);
            if (NetworkUtils.isNetworkAvailable(mActivity)) {
                showWaitDialog(true);
                initLoadingData();
            } else {
                AlertDialog alertDialog = DialogUtils.getDialog(mActivity, DialogUtils.IDD_NETWORK);
                if (alertDialog != null && alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
                alertDialog.show();
            }
        }
        initFieldDefaultValue();
        setSelectedValue(nameCountry, drawableRes);
    }

    private void initLoadingData() {
        mBundle = new Bundle();
        mBundle.putString(DownLoadTask.KEY, NetworkApi.API);
        if (mLoader != null && mLoader.isStarted()) {
            mLoader.cancelLoad();
            mLoader = null;
        }
        mLoader = mActivity.getSupportLoaderManager().initLoader(LOADER_RANDOM_ID, mBundle, this);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new DownLoadTask(mActivity, args);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        Reader reader = new StringReader(data);
        Persister serializer = new Persister();
        try {
            SheetCurrencyDataModel sheetCurrencyDataModel = serializer.read(
                    SheetCurrencyDataModel.class, reader, false);
            checkExistSaveData(sheetCurrencyDataModel, sheetCurrencyDataModel.getSheetCurrencyDataModels());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    public void convertButtonClick(View view) {
        if (mModel.getBeginCountry() != null && mModel.getEndCountry() != null &&
                mModel.getInputCount() != null) {
            mSheetCurrencyDataModelList = Database.getInstance(mActivity).getListInfoCurrency();
            mModel.setResultConvert(convertCurrency());
        } else {
            ToastUtils.show(mActivity, R.string.toast_message, Toast.LENGTH_SHORT);
        }
    }

    public void startCountryClick(View view) {
        mModel.setSelectedField(Constants.VALUE_FIRST_FIELD);
        MyApplication.getInstance().setCurrencyConverterModel(mModel);
        ((MainActivity) mActivity).openSheetCurrency();
    }

    public void endCountryClick(View view) {
        mModel.setSelectedField(Constants.VALUE_SECOND_FIELD);
        MyApplication.getInstance().setCurrencyConverterModel(mModel);
        ((MainActivity) mActivity).openSheetCurrency();
    }

    private void initFieldDefaultValue() {
        if (mModel.getBeginCountry() == null) {
            mModel.setBeginCountry("Выберите страну");
        }
        if (mModel.getEndCountry() == null) {
            mModel.setEndCountry("Выберите страну");
        }
    }

    private void setSelectedValue(String nameCountry, int drawableRes) {
        if (MyApplication.getInstance().getCurrencyConverterModel() != null) {
            if (nameCountry != null && drawableRes != 0) {
                if (MyApplication.getInstance().getCurrencyConverterModel()
                        .getSelectedField() == Constants.VALUE_FIRST_FIELD) {
                    mModel.setBeginCountry(nameCountry);
                    mModel.setBeginDrawableRes(drawableRes);
                    mModel.setEndCountry(MyApplication.getInstance().getCurrencyConverterModel().getEndCountry());
                    mModel.setEndDrawableRes(MyApplication.getInstance().getCurrencyConverterModel().getEndDrawableRes());
                    initFieldDefaultValue();
                } else if (MyApplication.getInstance().getCurrencyConverterModel()
                        .getSelectedField() == Constants.VALUE_SECOND_FIELD) {
                    mModel.setEndCountry(nameCountry);
                    mModel.setEndDrawableRes(drawableRes);
                    mModel.setBeginCountry(MyApplication.getInstance().getCurrencyConverterModel().getBeginCountry());
                    mModel.setBeginDrawableRes(MyApplication.getInstance().getCurrencyConverterModel().getBeginDrawableRes());
                    initFieldDefaultValue();
                }
            } else {
                initFieldDefaultValue();
            }
        }
    }

    private double convertCurrency() {
        double beginCurrencyValue = 0;
        double endCurrencyValue;
        double tempNominal;
        double result = 0;
        for (SheetCurrencyDataModel.SheetCurrencyDataModelList sheetCurrencyDataModelList :
                mSheetCurrencyDataModelList) {
            if (sheetCurrencyDataModelList.getName().equals(mModel.getBeginCountry())) {
                beginCurrencyValue = Double.parseDouble(sheetCurrencyDataModelList.getValue().replace(",", "."));
            }
            if (sheetCurrencyDataModelList.getName().equals(mModel.getEndCountry())) {
                endCurrencyValue = Double.parseDouble(sheetCurrencyDataModelList.getValue().replace(",", "."));
                tempNominal = Double.parseDouble(sheetCurrencyDataModelList.getNominal());
                result = (beginCurrencyValue / endCurrencyValue * tempNominal) * Double.parseDouble(mModel.getInputCount());
                break;
            }
        }
        return result;
    }

    private void checkExistSaveData(final SheetCurrencyDataModel sheetCurrencyDataModel,
                                    final List<SheetCurrencyDataModel.SheetCurrencyDataModelList> sheetCurrencyDataModels) {
        Database.getInstance(mActivity).doesTableExistAsync(new Database.DatabaseHand<Boolean>() {
            @Override
            public void onComplete(boolean success, Boolean result) {
                if (success) {
                    if (result) {
                        Database.getInstance(mActivity).updateInfoCurrencyAsync(
                                sheetCurrencyDataModel, sheetCurrencyDataModels,
                                new Database.DatabaseHand<Void>() {
                                    @Override
                                    public void onComplete(boolean success, Void result) {
                                        showWaitDialog(false);
                                    }
                                });
                    } else {
                        Database.getInstance(mActivity).addInfoCurrencyAsync(sheetCurrencyDataModel,
                                sheetCurrencyDataModels, new Database.DatabaseHand<Void>() {
                                    @Override
                                    public void onComplete(boolean success, Void result) {
                                        if (success) showWaitDialog(false);
                                    }
                                });
                    }
                }
            }
        });
    }

    private void showWaitDialog(boolean value) {
        if (value) {
            if (mProgressDialog != null) {
                mProgressDialog.setMessage("Пожалуйста, подождите...");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
            }
        } else {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
    }
}
