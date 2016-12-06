package com.example.sergey.testtask;

import com.example.sergey.testtask.mvvm.model.SheetCurrencyDataModel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * @author Sergey Rodionov
 */

public class SheetCurrencyDataModelTest {

    public SheetCurrencyDataModelTest() {
    }

    @Test
    public void bestDataModel() throws Exception {
        assertEquals("R01010", initData().getId());
        assertEquals("1", initData().getNominal());
        assertEquals("47,4904", initData().getValue());
        assertEquals(3, initData().getCharCode().length());
    }

    @Test
    public void badDataModel() throws Exception {
        assertEquals("-1", initData().getNominal());
        assertEquals("-47,4904", initData().getValue());
        assertEquals(4, initData().getCharCode().length());
    }

    private SheetCurrencyDataModel.SheetCurrencyDataModelList initData() {
        SheetCurrencyDataModel.SheetCurrencyDataModelList sheetCurrencyDataModelList =
                new SheetCurrencyDataModel.SheetCurrencyDataModelList();
        sheetCurrencyDataModelList.setId("R01010");
        sheetCurrencyDataModelList.setNumCode("036");
        sheetCurrencyDataModelList.setCharCode("AUD");
        sheetCurrencyDataModelList.setNominal("1");
        sheetCurrencyDataModelList.setName("Австралийский доллар");
        sheetCurrencyDataModelList.setValue("47,4904");
        return sheetCurrencyDataModelList;
    }
}
