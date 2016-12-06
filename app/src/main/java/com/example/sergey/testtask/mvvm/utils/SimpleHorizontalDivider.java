package com.example.sergey.testtask.mvvm.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Sergey Rodionov
 */

public class SimpleHorizontalDivider extends RecyclerView.ItemDecoration {

    public enum DividerType {
        Top,
        Bottom,
        Both
    }

    protected enum DividerDrawType {
        Drawable, Paint, Color
    }

    public static class Builder {
        private int[] mViewTypes;
        private DividerType mDividerType;
        private Drawable mDividerDrawable;
        private boolean mCanHaveFirstDivider;
        private boolean mCanHaveLastDivider;
        private Paint mPaint;
        private float mStrokeWidth = 1;
        private int mColor;
        private Rect mMargin = new Rect();
        private DividerDrawType mDividerDrawingType;


        public Builder setViewTypes(int[] viewTypes) {
            mViewTypes = viewTypes;
            return this;
        }

        public Builder setDividerType(DividerType dividerType) {
            mDividerType = dividerType;
            return this;
        }

        public Builder setDividerDrawable(Drawable mDividerDrawable) {
            this.mDividerDrawable = mDividerDrawable;
            mDividerDrawingType = DividerDrawType.Drawable;
            return this;
        }

        public Drawable getDividerDrawable() {
            return mDividerDrawable;
        }

        public int[] getViewTypes() {
            return mViewTypes;
        }

        public DividerType getDividerType() {
            return mDividerType;
        }

        public void applyTo(Context context, RecyclerView recyclerView) {
            SimpleHorizontalDivider divider = new SimpleHorizontalDivider(context);
            divider.setDividerType(this.getDividerType());
            divider.setViewTypes(this.getViewTypes());
            divider.setStrokeWidth(this.getStrokeWidth());
            divider.setCanHaveFirstDivider(this.isCanHaveFirstDivider());
            divider.setCanHaveLastDivider(this.isCanHaveLastDivider());
            divider.setMargin(this.getMargin());

            switch (mDividerDrawingType) {
                case Drawable:
                    divider.setDivider(this.getDividerDrawable());
                case Paint:
                    divider.setPaint(this.getPaint());
                    break;
                case Color:
                    divider.setColor(this.getColor());
                    break;
            }

            recyclerView.addItemDecoration(divider);
        }

        public boolean isCanHaveFirstDivider() {
            return mCanHaveFirstDivider;
        }

        public Builder setCanHaveFirstDivider(boolean canHaveFirstDivider) {
            this.mCanHaveFirstDivider = canHaveFirstDivider;
            return this;
        }

        public boolean isCanHaveLastDivider() {
            return mCanHaveLastDivider;
        }

        public Builder setCanHaveLastDivider(boolean canHaveLastDivider) {
            this.mCanHaveLastDivider = canHaveLastDivider;
            return this;
        }

        public Paint getPaint() {
            return mPaint;
        }

        public Builder setPaint(Paint paint) {
            this.mPaint = paint;
            mDividerDrawingType = DividerDrawType.Paint;
            return this;
        }

        public float getStrokeWidth() {
            return mStrokeWidth;
        }

        public Builder setStrokeWidth(float strokeWidth) {
            this.mStrokeWidth = strokeWidth;
            return this;
        }

        public int getColor() {
            return mColor;
        }

        public Builder setColor(int color) {
            this.mColor = color;
            mDividerDrawingType = DividerDrawType.Color;
            return this;
        }

        public Rect getMargin() {
            return mMargin;
        }

        public Builder setMargin(Rect margin) {
            this.mMargin = margin;
            return this;
        }
    }

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private Drawable mDivider;
    private int[] mViewTypes;
    private DividerType mDividerType;
    private boolean mCanHaveFirstDivider;
    private boolean mCanHaveLastDivider;
    private DividerDrawType mDividerDrawingType;
    private Paint mPaint;
    private float mStrokeWidth = 1;
    private int mColor;
    private Rect mMargin = new Rect();


    /**
     * Default divider will be used
     */
    public SimpleHorizontalDivider(Context context) {
        final TypedArray styledAttributes = context.obtainStyledAttributes(ATTRS);
        mDivider = styledAttributes.getDrawable(0);
        mDividerDrawingType = DividerDrawType.Drawable;
        styledAttributes.recycle();
    }

    /**
     * Custom divider will be used
     */
    public SimpleHorizontalDivider(Context context, int resId) {
        mDivider = ContextCompat.getDrawable(context, resId);
    }

    private boolean isAllowedType(int itemPosition, RecyclerView parent) {
        try {
            if (mViewTypes == null) return true;
            int nextViewType = parent.getAdapter().getItemViewType(itemPosition);
            for (int viewType :
                    mViewTypes) {
                if (nextViewType == viewType) {
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private boolean bHaveDivider(int position, int viewPosition, RecyclerView parent, boolean isViewTransparent, boolean bIsFirst) {
        boolean isItemHaveAllowedType = isAllowedType(position, parent);
        if (!isViewTransparent && isItemHaveAllowedType) {
            if (mDividerType == DividerType.Both) return true;
            if (bIsFirst && mDividerType == DividerType.Top)
                return true;
        }
        if (!bIsFirst) {
            if ((mDividerType == DividerType.Bottom || mDividerType == DividerType.Both) && !isViewTransparent && isItemHaveAllowedType)
                return true;
            else if (mDividerType == DividerType.Top || mDividerType == DividerType.Both) {
                int nextItemPosition = position + 1;
                if (nextItemPosition < parent.getAdapter().getItemCount()) {
                    if (viewPosition + 1 < parent.getChildCount()) {
                        View nextChild = parent.getChildAt(viewPosition + 1);
                        if (ViewCompat.getAlpha(nextChild) < 1) return false;
                    }
                    return isAllowedType(nextItemPosition, parent);
                }
            }
        }
        return false;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = mCanHaveLastDivider ? parent.getChildCount() : parent.getChildCount() - 1;
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            boolean isViewTransparent = ViewCompat.getAlpha(child) < 1;
            if (i == 0 && mCanHaveFirstDivider && bHaveDivider(position, i, parent, isViewTransparent, true)) {
                Rect firstBounds = getFirstDividerBound(position, parent, child);
                drawDivider(firstBounds, position, parent, c);
            }
            if (bHaveDivider(position, i, parent, isViewTransparent, false)) {
                Rect bounds = getDividerBound(position, parent, child);
                drawDivider(bounds, position, parent, c);
            }
        }
    }

    protected Rect getDividerBound(int position, RecyclerView parent, View child) {
        Rect bounds = new Rect(0, 0, 0, 0);
        int transitionX = (int) ViewCompat.getTranslationX(child);
        int transitionY = (int) ViewCompat.getTranslationY(child);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
        bounds.left = parent.getPaddingLeft() +
                mMargin.left + transitionX;
        bounds.right = parent.getWidth() - parent.getPaddingRight() -
                mMargin.right + transitionX;

        int dividerSize = (int) getDividerSize(position, parent);
        if (mDividerDrawingType == DividerDrawType.Drawable) {
            bounds.top = child.getBottom() + params.topMargin + transitionY;
            bounds.bottom = bounds.top + dividerSize;
        } else {
            bounds.top = child.getBottom() + params.topMargin + dividerSize / 2 + transitionY;
            bounds.bottom = bounds.top;
        }

        return bounds;
    }

    protected Rect getFirstDividerBound(int position, RecyclerView parent, View child) {
        Rect bounds = new Rect(0, 0, 0, 0);
        int transitionX = (int) ViewCompat.getTranslationX(child);
        int transitionY = (int) ViewCompat.getTranslationY(child);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
        bounds.left = parent.getPaddingLeft() + mMargin.left + transitionX;
        bounds.right = parent.getWidth() - parent.getPaddingRight() -
                mMargin.right + transitionX;

        int dividerSize = (int) getDividerSize(position, parent);
        if (mDividerDrawingType == DividerDrawType.Drawable) {
            bounds.top = params.topMargin + transitionY;
            bounds.bottom = child.getTop() + bounds.top + dividerSize;
        } else {
            bounds.top = child.getTop() + params.topMargin + dividerSize / 2 + transitionY;
            bounds.bottom = bounds.top;
        }

        return bounds;
    }

    private float getDividerSize(int position, RecyclerView parent) {
        switch (mDividerDrawingType) {
            case Drawable:
                return mDivider.getIntrinsicHeight();
            case Paint:
            case Color:
                return mStrokeWidth;
            default:
                return 0;
        }
    }

    private void drawDivider(Rect bounds, int childPosition, RecyclerView parent, Canvas c) {
        switch (mDividerDrawingType) {
            case Drawable:
                mDivider.setBounds(bounds.left, bounds.top, bounds.right, bounds.bottom);
                mDivider.draw(c);
                break;
            case Paint:
                c.drawLine(bounds.left, bounds.top, bounds.right, bounds.bottom, mPaint);
                break;
            case Color:
                mPaint.setColor(mColor);
                mPaint.setStrokeWidth(mStrokeWidth);
                c.drawLine(bounds.left, bounds.top, bounds.right, bounds.bottom, mPaint);
                break;
        }
    }

    public int[] getViewTypes() {
        return mViewTypes;
    }

    public void setViewTypes(int[] mViewTypes) {
        this.mViewTypes = mViewTypes;
    }

    public Drawable getDivider() {
        return mDivider;
    }

    public void setDivider(Drawable mDivider) {
        this.mDivider = mDivider;
    }

    public DividerType getDividerType() {
        return mDividerType;
    }

    // TODO: need use it
    public void setDividerType(DividerType mDividerType) {
        this.mDividerType = mDividerType;
    }

    public boolean isCanHaveFirstDivider() {
        return mCanHaveFirstDivider;
    }

    public void setCanHaveFirstDivider(boolean canHaveFirstDivider) {
        this.mCanHaveFirstDivider = canHaveFirstDivider;
    }

    public boolean isCanHaveLastDivider() {
        return mCanHaveLastDivider;
    }

    public void setCanHaveLastDivider(boolean canHaveLastDivider) {
        this.mCanHaveLastDivider = canHaveLastDivider;
    }

    public Paint getPaint() {
        return mPaint;
    }

    public void setPaint(Paint paint) {
        this.mPaint = paint;
        mDividerDrawingType = DividerDrawType.Paint;
    }

    public float getStrokeWidth() {
        return mStrokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.mStrokeWidth = strokeWidth;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        this.mColor = color;
        mPaint = new Paint();
        mDividerDrawingType = DividerDrawType.Color;
    }

    public Rect getMargin() {
        return mMargin;
    }

    public void setMargin(Rect margin) {
        this.mMargin = margin;
    }
}
