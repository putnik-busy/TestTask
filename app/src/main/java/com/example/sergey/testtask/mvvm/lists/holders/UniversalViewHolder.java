package com.example.sergey.testtask.mvvm.lists.holders;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.example.sergey.testtask.BR;
import com.example.sergey.testtask.mvvm.lists.holders.intarfaces.BindableViewHolder;
import com.example.sergey.testtask.mvvm.lists.items.interfaces.ItemWithLayout;

/**
 * @author Sergey Rodionov
 */

public class UniversalViewHolder extends RecyclerView.ViewHolder implements
        BindableViewHolder<ItemWithLayout> {

    private ViewDataBinding mBinding;

    public UniversalViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public ViewDataBinding getBinding() {
        return mBinding;
    }

    @Override
    public void bindTo(ItemWithLayout item) {
        mBinding.setVariable(BR.model, item);
        mBinding.executePendingBindings();
    }
}
