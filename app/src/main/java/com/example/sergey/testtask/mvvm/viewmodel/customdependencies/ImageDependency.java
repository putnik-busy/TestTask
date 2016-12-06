package com.example.sergey.testtask.mvvm.viewmodel.customdependencies;

import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.example.sergey.testtask.mvvm.tasks.ImageLoaderTask;

/**
 * @author Sergey Rodionov
 */

public class ImageDependency {

    @BindingAdapter("imageResources")
    public static void setImageResources(final ImageView imageView, @DrawableRes int resId) {
        ImageLoaderTask imageLoaderTask = new ImageLoaderTask(imageView.getContext(), imageView);
        imageLoaderTask.loadBitmap(resId, imageView);
    }
}
