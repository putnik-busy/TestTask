package com.example.sergey.testtask.mvvm.utils;

import android.support.v4.util.LruCache;

import com.example.sergey.testtask.mvvm.model.SheetCurrencyDataModel;

/**
 * @author Sergey Rodionov
 */

public class DiskLruRequestCache extends LruCache<String,SheetCurrencyDataModel> {
    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public DiskLruRequestCache(int maxSize) {
        super(maxSize);
    }
    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */

}
