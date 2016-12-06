package com.example.sergey.testtask.mvvm.viewmodel.customdependencies;

import android.content.Context;
import android.content.ContextWrapper;
import android.databinding.BindingAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * @author Sergey Rodionov
 */

public class ToolbarDependency {
    @BindingAdapter(value = {"actionbar", "homeButton"}, requireAll = false)
    public static void setActionbar(Toolbar view, boolean isAсtionbar, boolean hasHomeButton) {
        Context context = view.getContext();
        if (context != null && context instanceof ContextWrapper)
            context = ((ContextWrapper) view.getContext()).getBaseContext();
        if (context != null && context instanceof AppCompatActivity) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) context;
            if (isAсtionbar) {
                appCompatActivity.setSupportActionBar(view);
                if (appCompatActivity.getSupportActionBar() != null) {
                    appCompatActivity.getSupportActionBar().setTitle("");
                    final ActionBar actionBar = appCompatActivity.getSupportActionBar();
                    if (actionBar != null) {
                        actionBar.setDisplayHomeAsUpEnabled(hasHomeButton);
                        actionBar.setHomeButtonEnabled(hasHomeButton);
                    }
                }
            } else {
                appCompatActivity.setSupportActionBar(null);
            }
        }
    }
}