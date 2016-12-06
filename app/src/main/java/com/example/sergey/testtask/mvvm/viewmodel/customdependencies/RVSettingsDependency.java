package com.example.sergey.testtask.mvvm.viewmodel.customdependencies;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.sergey.testtask.mvvm.utils.RecyclerViewUtils;
import com.example.sergey.testtask.mvvm.utils.SimpleHorizontalDivider;

/**
 * @author Sergey Rodionov
 */

public class RVSettingsDependency {

    @BindingAdapter("initDefaultRVSettings")
    public static void setDefaultRecyclerViewSettings(RecyclerView view, boolean apply) {
        if (apply) {
            RecyclerViewUtils.defaultSetupRecyclerView(view.getContext(), view, false);
        }
    }

    @BindingAdapter("initDefaultInverseRVSettings")
    public static void setDefaultInverseRecyclerViewSettings(RecyclerView view, boolean apply) {
        if (apply) {
            RecyclerViewUtils.defaultSetupRecyclerView(view.getContext(), view, true);
        }
    }

    @BindingAdapter("dividers")
    public static void setRecyclerViewDividers(RecyclerView view, SimpleHorizontalDivider.Builder
            dividerBuilder) {
        dividerBuilder.applyTo(view.getContext(), view);
    }

    @BindingAdapter("dividersMany")
    public static void setRecyclerViewDividersMany(RecyclerView view, SimpleHorizontalDivider.Builder[]
            dividerBuilders) {
        for (SimpleHorizontalDivider.Builder dividerBuilder : dividerBuilders) {
            dividerBuilder.applyTo(view.getContext(), view);
        }
    }

    @BindingAdapter("adapter")
    public static void setRecyclerViewAdapterSettings(RecyclerView view, RecyclerView.Adapter
            adapter) {
        view.setAdapter(adapter);
    }

    @BindingAdapter("adapter")
    public static void setRecyclerViewAdapterSettings(ListView view, ListAdapter adapter) {
        view.setAdapter(adapter);
    }

    @BindingAdapter("scrollToPosition")
    public static void setScrollToPosition(RecyclerView view, int position) {
        view.scrollToPosition(position);
    }
}
