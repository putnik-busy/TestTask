package com.example.sergey.testtask.mvvm.viewmodel.customdependencies;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * @author Sergey Rodionov
 */

public class ItemClickDependency {

    public interface onItemClickListener {
        void onItemClick(Object param);
    }

    @BindingAdapter({"onClick", "param"})
    public static void bindOnClick(View view, final onItemClickListener listener, final Object param) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.onItemClick(param);
            }
        });
    }
}
