package com.example.sergey.testtask.mvvm.model;

import android.databinding.BaseObservable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * @author Sergey Rodionov
 */

@Root(name = "ValCurs", strict = false)
public class SheetCurrencyDataModel extends BaseObservable {

    @Attribute(required = false, name = "Date")
    private String mDate;

    @ElementList(required = false, name = "sheetCurrencyDataModels", inline = true, empty = true)
    private List<SheetCurrencyDataModelList> sheetCurrencyDataModels;

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public List<SheetCurrencyDataModelList> getSheetCurrencyDataModels() {
        return sheetCurrencyDataModels;
    }

    public void setSheetCurrencyDataModels(List<SheetCurrencyDataModelList> sheetCurrencyDataModels) {
        this.sheetCurrencyDataModels = sheetCurrencyDataModels;
    }

    @Root(name = "Valute")
    public static class SheetCurrencyDataModelList {

        @Attribute(required = false, name = "ID")
        private String mId;
        @Element(required = false, name = "NumCode")
        private String mNumCode;
        @Element(required = false, name = "CharCode")
        private String mCharCode;
        @Element(required = false, name = "Nominal")
        private String mNominal;
        @Element(required = false, name = "Name")
        private String mName;
        @Element(required = false, name = "Value")
        private String mValue;

        private int mSelectedFieldNumber = -1;

        public String getId() {
            return mId;
        }

        public void setId(String id) {
            this.mId = id;
        }

        public String getNumCode() {
            return mNumCode;
        }

        public void setNumCode(String numCode) {
            this.mNumCode = numCode;
        }

        public String getCharCode() {
            return mCharCode;
        }

        public void setCharCode(String charCode) {
            this.mCharCode = charCode;
        }

        public String getNominal() {
            return mNominal;
        }

        public void setNominal(String nominal) {
            this.mNominal = nominal;
        }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            this.mName = name;
        }

        public String getValue() {
            return mValue;
        }

        public void setValue(String value) {
            this.mValue = value;
        }

        public int getSelectedFieldNumber() {
            return mSelectedFieldNumber;
        }

        public void setSelectedFieldNumber(int selectedFieldNumber) {
            this.mSelectedFieldNumber = selectedFieldNumber;
        }
    }
}
