package com.example.joe.paint;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by god_laptop on 9/15/2014.
 */
public class PaletteView extends ViewGroup {

    private int[] defaultColors = { Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN, Color.BLACK,
        Color.WHITE };
    private int selectedColor;

    public PaletteView(Context context) {
        super(context);
        addDefaultPaints();
        setMinimumHeight(400);
        setMinimumWidth(400);
        setBackgroundColor(Color.DKGRAY);
    }

    /*
        Adds a PaintView for all default colors
     */
    private void addDefaultPaints() {
        for(int c : defaultColors) {
            addColor(c);
        }
        invalidate();
    }

    public void addColor(int color) {
        int index = findColor(color);
        //if color doesn't already exist add it
        if(index > -2) {
            PaintView paintView = new PaintView(this.getContext(), color);
            paintView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    PaintView view = (PaintView)v;
                    //view.setColor(Color.RED);
                    //addColor(view.getColor());
                }
            });

            paintView.setOnSplotchTouchListener(new PaintView.OnSplotchTouchListener()
            {
                @Override
                public void onSplotchTouch(PaintView v) {
                    PaintView view = (PaintView)v;
                    selectedColor = view.getColor();
                    //view.setColor(Color.GREEN);
                    deactivateAllColors();
                    view.setActive(true);
                    invalidate();
                }
            });
            addView(paintView);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.i("paletteView", "Touch Down");
        }
        else if(event.getAction() == MotionEvent.ACTION_UP) {
            Log.i("paletteView", "Touch Up");
        }


        return super.onTouchEvent(event);
    }

    public void removeColor(int color) {
        int index = findColor(color);
        //if color is found in the list remove it
        if(index != -1) {
            removeViewAt(index);
        }
    }

    private void deactivateAllColors() {
        for(int i = 0; i < getChildCount(); i++) {
            PaintView paintView = (PaintView)getChildAt(i);
            paintView.setActive(false);
        }
    }

    /*
        Searches the List of colors and returns the index
        or returns -1 if the color isn't in the List
     */
    private int findColor(int color) {
        for(int i = 0; i < getChildCount(); i++) {
            PaintView paintView = (PaintView)getChildAt(i);
            if(paintView.getColor() == color) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthSpec = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpec = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int test = getSuggestedMinimumHeight();
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

        if(width > height) {
            width = height;
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
