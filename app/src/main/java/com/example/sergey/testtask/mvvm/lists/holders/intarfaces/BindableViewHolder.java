package com.example.sergey.testtask.mvvm.lists.holders.intarfaces;

import android.databinding.ViewDataBinding;

import com.example.sergey.testtask.mvvm.lists.items.interfaces.Item;

/**
 * @author Sergey Rodionov
 */

public interface BindableViewHolder<AT extends Item> {

    <T extends ViewDataBinding> T getBinding();
    void bindTo(AT item);
}
