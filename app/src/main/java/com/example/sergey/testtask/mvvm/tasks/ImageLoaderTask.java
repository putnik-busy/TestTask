package com.example.sergey.testtask.mvvm.tasks;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * @author Sergey Rodionov
 */

public class ImageLoaderTask extends AsyncTask<Integer, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    private int data = 0;
    private Context mContext;

    public ImageLoaderTask(Context context, ImageView imageView) {
        mContext = context;
        imageViewReference = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected Bitmap doInBackground(Integer... params) {
        data = params[0];
        return decodeBitmapFromResource(mContext.getResources(), data, 50, 40);
    }

    public void loadBitmap(int resId, ImageView imageView) {
        if (cancelPotentialWork(resId, imageView)) {
            final ImageLoaderTask task = new ImageLoaderTask(mContext, imageView);
            final AsyncDrawable asyncDrawable =
                    new AsyncDrawable(mContext.getResources(), null, task);
            imageView.setImageDrawable(asyncDrawable);
            task.execute(resId);
        }
    }

    static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<ImageLoaderTask> imageLoaderTaskWeakReference;

        public AsyncDrawable(Resources res, Bitmap bitmap,
                             ImageLoaderTask imageLoaderTask) {
            super(res, bitmap);
            imageLoaderTaskWeakReference =
                    new WeakReference<ImageLoaderTask>(imageLoaderTask);
        }

        public ImageLoaderTask getImageLoaderTask() {
            return imageLoaderTaskWeakReference.get();
        }
    }

    private static ImageLoaderTask getImageLoaderTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getImageLoaderTask();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            final ImageLoaderTask imageLoaderTask =
                    getImageLoaderTask(imageView);
            if (this == imageLoaderTask && imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    public static Bitmap decodeBitmapFromResource(Resources res, int resId,
                                                  int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static boolean cancelPotentialWork(int data, ImageView imageView) {
        final ImageLoaderTask imageLoaderTask = getImageLoaderTask(imageView);

        if (imageLoaderTask != null) {
            final int bitmapData = imageLoaderTask.data;
            if (bitmapData == 0 || bitmapData != data) {
                imageLoaderTask.cancel(true);
            } else {
                return false;
            }
        }
        return true;
    }
}
