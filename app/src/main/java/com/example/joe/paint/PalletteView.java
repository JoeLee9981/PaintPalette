package com.example.joe.paint;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by god_laptop on 9/15/2014.
 */
public class PalletteView extends ViewGroup {


    public PalletteView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthSpec = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpec = MeasureSpec.getSize(heightMeasureSpec);

        int width = Math.max(widthSpec, getSuggestedMinimumWidth());
        int height = Math.max(heightSpec, getSuggestedMinimumHeight());

        int childState = 0;
        for(int childIndex = 0; childIndex < getChildCount(); childIndex++) {
            View child = getChildAt(childIndex);
            LayoutParams childLayoutParams = child.getLayoutParams();
            child.measure(MeasureSpec.AT_MOST | 75, MeasureSpec.AT_MOST | 75);
            child.getMeasuredState();
            childState = combineMeasuredStates(childState, child.getMeasuredState());
        }

        setMeasuredDimension(
                resolveSizeAndState(width, widthMeasureSpec, childState),
                resolveSizeAndState(height, heightMeasureSpec, childState)
        );
    }

    @Override
    protected void onLayout(boolean b, int i, int i2, int i3, int i4) {

        int childWidthMax = 0;
        int childHeightMax = 0;

        for(int childIndex = 0; childIndex < getChildCount(); childIndex++) {
            View child = getChildAt(childIndex);
            childWidthMax = Math.max(childWidthMax, child.getMeasuredWidth());
            childHeightMax = Math.max(childHeightMax, child.getMeasuredHeight());
        }

        Rect layoutRect = new Rect();
        layoutRect.left = getPaddingLeft() + 25;
        layoutRect.top = getPaddingTop() + 25;
        layoutRect.right = getWidth() - getPaddingRight() - 25;
        layoutRect.bottom = getHeight() - getPaddingBottom() - 25;

        for(int childIndex = 0; childIndex < getChildCount(); childIndex++) {
            double angle = (double)childIndex / (double)getChildCount() * 2.0 * Math.PI;
            int childCenterX = (int)(layoutRect.centerX() + (double)layoutRect.width() * 0.5 * Math.cos(angle));
            int childCenterY = (int)(layoutRect.centerY() + (double)layoutRect.height() * 0.5 * Math.sin(angle));
            View child = getChildAt(childIndex);
            Rect childLayout = new Rect();
            childLayout.left = childCenterX - 25;
            childLayout.top = childCenterY - 25;
            childLayout.right = childCenterX + 25;
            childLayout.bottom = childCenterY + 25;
            child.layout(childLayout.left, childLayout.top, childLayout.right, childLayout.bottom);
        }
    }
}
