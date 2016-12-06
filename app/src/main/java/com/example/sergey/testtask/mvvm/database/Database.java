package com.example.sergey.testtask.mvvm.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import com.example.sergey.testtask.mvvm.model.SheetCurrencyDataModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.sergey.testtask.mvvm.database.Database.Tables.INFO_CURRENCY.currencyId;

/**
 * @author Sergey Rodionov
 */

public class Database {

    public interface DatabaseHand<T> {
        void onComplete(boolean success, T result);
    }

    private abstract class DatabaseAsyncTask<T> extends AsyncTask<Void, Void, T> {
        private DatabaseHand<T> handler;
        private RuntimeException error;

        public DatabaseAsyncTask(DatabaseHand<T> handler) {
            this.handler = handler;
        }

        @Override
        protected T doInBackground(Void... params) {
            try {
                return executeMethod();
            } catch (RuntimeException error) {
                this.error = error;
                return null;
            }
        }

        protected abstract T executeMethod();

        @Override
        protected void onPostExecute(T result) {
            handler.onComplete(error == null, result);
        }
    }

    private final Context mCtx;
    private static Database instance = null;
    public static final String DB_NAME = "currency_db";
    public static final int DB_VERSION = 1;
    private DBHelper mDBHelper;
    public static SQLiteDatabase mDB;
    private static final String TEXT_TYPE_FIELD = "TEXT";
    private static final String ID_TYPE_FIELD = "INTEGER PRIMARY KEY AUTOINCREMENT";

    public Database(Context ctx) {
        mCtx = ctx;
    }

    public static Database getInstance(Context cnt) {
        if (instance == null) {
            instance = new Database(cnt);
            instance.open();
        }
        return instance;
    }

    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    public boolean isOpen() {
        return mDB.isOpen();
    }

    public void close() {
        if (mDBHelper != null) {
            mDBHelper.close();
        }
        instance = null;
    }

    public static class Tables {
        public static final String TABLE_INFO_CURRENCY = "table_info_currency";

        public interface INFO_CURRENCY {
            String ID = "_id";
            String date = "date";
            String currencyId = "currencyId";
            String numCode = "numCode";
            String charCode = "charCode";
            String nominal = "nominal";
            String name = "name";
            String value = "value";
        }
    }

    private static final String PATCH_CREATE_TABLE_INFO_CURRENCY =
            "CREATE TABLE " + Tables.TABLE_INFO_CURRENCY + "("
                    + Tables.INFO_CURRENCY.ID + " " + ID_TYPE_FIELD + ","
                    + Tables.INFO_CURRENCY.date + " " + TEXT_TYPE_FIELD + ","
                    + currencyId + " " + TEXT_TYPE_FIELD + ","
                    + Tables.INFO_CURRENCY.numCode + " " + TEXT_TYPE_FIELD + ","
                    + Tables.INFO_CURRENCY.charCode + " " + TEXT_TYPE_FIELD + ","
                    + Tables.INFO_CURRENCY.nominal + " " + TEXT_TYPE_FIELD + ","
                    + Tables.INFO_CURRENCY.name + " " + TEXT_TYPE_FIELD + ","
                    + Tables.INFO_CURRENCY.value + " " + TEXT_TYPE_FIELD + ")";

    private class DBHelper extends SQLiteOpenHelper {

        private Context mContext;

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
            this.mContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(PATCH_CREATE_TABLE_INFO_CURRENCY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("DBHelper", "Update db from " + oldVersion + " to " + newVersion);
            db.execSQL("DROP TABLE IF EXISTS " + Tables.TABLE_INFO_CURRENCY);
            onCreate(db);
        }
    }

    public void addInfoCurrency(
            SheetCurrencyDataModel dataModel,
            List<SheetCurrencyDataModel.SheetCurrencyDataModelList> countryDataModelList) {
        for (int i = 0; i < countryDataModelList.size(); i++) {
            ContentValues cv = addInfoCurrencyContentValues(dataModel, countryDataModelList.get(i));
            mDB.insert(Tables.TABLE_INFO_CURRENCY, null, cv);
        }
    }

    public void addInfoCurrencyAsync(final SheetCurrencyDataModel dataModel,
                                     final List<SheetCurrencyDataModel.SheetCurrencyDataModelList> countryDataModelList,
                                     DatabaseHand<Void> handler) {
        new DatabaseAsyncTask<Void>(handler) {
            @Override
            protected Void executeMethod() {
                addInfoCurrency(dataModel, countryDataModelList);
                return null;
            }
        }.execute();
    }

    private ContentValues addInfoCurrencyContentValues(
            SheetCurrencyDataModel dataModel,
            SheetCurrencyDataModel.SheetCurrencyDataModelList dataModelList) {
        ContentValues cv = new ContentValues();

        cv.put(Tables.INFO_CURRENCY.date, dataModel.getDate());
        cv.put(Tables.INFO_CURRENCY.currencyId, dataModelList.getId());
        cv.put(Tables.INFO_CURRENCY.numCode, dataModelList.getNumCode());
        cv.put(Tables.INFO_CURRENCY.charCode, dataModelList.getCharCode());
        cv.put(Tables.INFO_CURRENCY.nominal, dataModelList.getNominal());
        cv.put(Tables.INFO_CURRENCY.name, dataModelList.getName());
        cv.put(Tables.INFO_CURRENCY.value, dataModelList.getValue());
        return cv;
    }

    public void updateInfoCurrency(
            SheetCurrencyDataModel dataModel,
            List<SheetCurrencyDataModel.SheetCurrencyDataModelList> countryDataModelList) {
        for (int i = 0; i < countryDataModelList.size(); i++) {
            ContentValues cv = addInfoCurrencyContentValues(dataModel, countryDataModelList.get(i));
            mDB.update(Tables.TABLE_INFO_CURRENCY, cv,
                    Tables.INFO_CURRENCY.currencyId + " = '" + countryDataModelList.get(i).getId() + "'", null);
        }
    }

    public void updateInfoCurrencyAsync(
            final SheetCurrencyDataModel dataModel,
            final List<SheetCurrencyDataModel.SheetCurrencyDataModelList> countryDataModelList,
            DatabaseHand<Void> handler) {
        new DatabaseAsyncTask<Void>(handler) {
            @Override
            protected Void executeMethod() {
                updateInfoCurrency(dataModel, countryDataModelList);
                return null;
            }
        }.execute();
    }

    public List<SheetCurrencyDataModel.SheetCurrencyDataModelList> getListInfoCurrency() {
        List<SheetCurrencyDataModel.SheetCurrencyDataModelList> countryDataModelList =
                new ArrayList<>();
        Cursor c = mDB.rawQuery("SELECT * FROM " + Tables.TABLE_INFO_CURRENCY, null);
        if (c != null && c.getCount() != 0) {
            for (int i = 0; i < c.getCount(); i++) {
                c.moveToPosition(i);
                countryDataModelList.add(getInfoCountryFromCursor(c));
            }
            c.close();
        }
        return countryDataModelList;
    }

    public void getListInfoCurrencyAsync(
            DatabaseHand<List<SheetCurrencyDataModel.SheetCurrencyDataModelList>> handler) {
        new DatabaseAsyncTask<List<SheetCurrencyDataModel.SheetCurrencyDataModelList>>(handler) {
            @Override
            protected List<SheetCurrencyDataModel.SheetCurrencyDataModelList> executeMethod() {
                return getListInfoCurrency();
            }
        }.execute();
    }

    public SheetCurrencyDataModel getDateInfoCurrency(String currencyId) {
        SheetCurrencyDataModel sheetCurrencyDataModel =
                new SheetCurrencyDataModel();
        Cursor c = mDB.rawQuery("SELECT * FROM " + Tables.TABLE_INFO_CURRENCY +
                " WHERE " + currencyId + " = " + currencyId, null);
        if (c != null && c.getCount() != 0) {
            for (int i = 0; i < c.getCount(); i++) {
                c.moveToPosition(i);
                sheetCurrencyDataModel = getInfoDateCountryFromCursor(c);
            }
            c.close();
        }
        return sheetCurrencyDataModel;
    }

    private SheetCurrencyDataModel getInfoDateCountryFromCursor(Cursor c) {
        SheetCurrencyDataModel sheetCurrencyDataModel = new SheetCurrencyDataModel();
        sheetCurrencyDataModel.setDate(c.getString(c.getColumnIndex(Tables.INFO_CURRENCY.date)));
        return sheetCurrencyDataModel;
    }

    private SheetCurrencyDataModel.SheetCurrencyDataModelList getInfoCountryFromCursor(
            Cursor c) {
        SheetCurrencyDataModel.SheetCurrencyDataModelList countryDataModelList =
                new SheetCurrencyDataModel.SheetCurrencyDataModelList();

        countryDataModelList.setId(c.getString(c.getColumnIndex(currencyId)));
        countryDataModelList.setNumCode(c.getString(c.getColumnIndex(Tables.INFO_CURRENCY.numCode)));
        countryDataModelList.setCharCode(c.getString(c.getColumnIndex(Tables.INFO_CURRENCY.charCode)));
        countryDataModelList.setNominal(c.getString(c.getColumnIndex(Tables.INFO_CURRENCY.nominal)));
        countryDataModelList.setName(c.getString(c.getColumnIndex(Tables.INFO_CURRENCY.name)));
        countryDataModelList.setValue(c.getString(c.getColumnIndex(Tables.INFO_CURRENCY.value)));

        return countryDataModelList;
    }

    public boolean doesTableExist() {
        Cursor cursor = mDB.rawQuery("SELECT COUNT(*) FROM " + Tables.TABLE_INFO_CURRENCY, null);
        if (!cursor.moveToFirst()) {
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

    public void doesTableExistAsync(DatabaseHand<Boolean> handler) {
        new DatabaseAsyncTask<Boolean>(handler) {
            @Override
            protected Boolean executeMethod() {
                return doesTableExist();
            }
        }.execute();
    }
}
