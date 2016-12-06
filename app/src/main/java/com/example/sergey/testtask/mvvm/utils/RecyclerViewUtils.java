package com.example.sergey.testtask.mvvm.utils;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * @author Sergey Rodionov
 */

public class RecyclerViewUtils {

    /**
     * Default settings of RecyclerView
     *
     * @param context      LayoutManager's context
     * @param recyclerView RecyclerView for setup
     * @param inverse
     */
    public final static void defaultSetupRecyclerView(Context context, RecyclerView recyclerView, boolean inverse) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setReverseLayout(inverse);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);
    }
}

