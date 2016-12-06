package com.example.sergey.testtask.mvvm.lists.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.sergey.testtask.mvvm.lists.holders.UniversalViewHolder;
import com.example.sergey.testtask.mvvm.lists.holders.intarfaces.BindableViewHolder;
import com.example.sergey.testtask.mvvm.lists.items.interfaces.ItemWithLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergey Rodionov
 */

public class UniversalRecyclerViewAdapter extends RecyclerView.Adapter {

    private List<ItemWithLayout> mItems;
    private List<Integer> mTypes = new ArrayList<>();
    private Object mViewModel;
    private Integer mViewModelId;

    public UniversalRecyclerViewAdapter() {
        this(new ArrayList<ItemWithLayout>(), null, null);
    }

    public UniversalRecyclerViewAdapter(List<ItemWithLayout> itemList) {
        this(itemList, null, null);
    }

    public UniversalRecyclerViewAdapter(Object viewModel, Integer viewModelId) {
        this(new ArrayList<ItemWithLayout>(), viewModel, viewModelId);
    }

    public UniversalRecyclerViewAdapter(List<ItemWithLayout> itemList, Object viewModel, Integer viewModelId) {
        mViewModel = viewModel;
        mViewModelId = viewModelId;
        mItems = itemList;
        for (ItemWithLayout item :
                mItems) {
            addTypeIfNotExists(item);
        }
    }

    @Override
    public int getItemViewType(int position) {
        ItemWithLayout currentItem = mItems.get(position);
        return mTypes.indexOf(currentItem.getLayoutId());
    }

    private void addTypeIfNotExists(ItemWithLayout item) {
        if (!mTypes.contains(item.getLayoutId())) {
            mTypes.add(item.getLayoutId());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, mTypes.get(viewType), parent, false);
        if (mViewModel != null && mViewModelId != null)
            binding.setVariable(mViewModelId, mViewModel);
        return new UniversalViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemWithLayout currentItem = mItems.get(position);
        if (holder instanceof UniversalViewHolder) {
            BindableViewHolder<ItemWithLayout> bindableViewHolder = (UniversalViewHolder) holder;
            bindableViewHolder.bindTo(currentItem);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addItem(ItemWithLayout item) {
        mItems.add(item);
        addTypeIfNotExists(item);
        notifyItemInserted(mItems.size() - 1);
    }

    public void replaceItem(int position, ItemWithLayout item) {
        mItems.set(position, item);
        addTypeIfNotExists(item);
        notifyItemChanged(position);
    }

    public void clear() {
        int itemsCount = mItems.size();
        mItems.clear();
        mTypes.clear();
        notifyItemRangeRemoved(0, itemsCount);
    }

    public void addItemsRange(List<ItemWithLayout> drawerItem) {
        mItems.addAll(drawerItem);
        for (ItemWithLayout item : mItems) {
            addTypeIfNotExists(item);
        }
        notifyItemRangeInserted(mItems.size() - drawerItem.size(), drawerItem.size());
    }
}
