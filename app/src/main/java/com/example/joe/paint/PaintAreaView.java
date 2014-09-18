package com.example.joe.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Joe on 9/17/2014.
 */
public class PaintAreaView extends View {

    public PaintAreaView(Context context) {
        super(context);
        setMinimumHeight(200);
        setMinimumWidth(200);
        setBackgroundColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpec = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpec = MeasureSpec.getSize(heightMeasureSpec);

        int width = getSuggestedMinimumWidth();
        int height = getSuggestedMinimumHeight();

        if(widthMode == MeasureSpec.AT_MOST) {
            width = widthSpec;
        }
        if(heightMode == MeasureSpec.AT_MOST) {
            height = heightSpec;
        }

        if(widthMode == MeasureSpec.EXACTLY) {
            width = widthSpec;
            height = width;
        }
        if(heightMode == MeasureSpec.EXACTLY) {
            height = heightSpec;
            width = height;
        }

        if(width > height && widthMode != MeasureSpec.EXACTLY)
            width = height;
        if(height > width && heightMode != MeasureSpec.EXACTLY)
            height = width;

        setMeasuredDimension(
                resolveSizeAndState(width, widthMeasureSpec, width < getSuggestedMinimumWidth() ? MEASURED_STATE_TOO_SMALL : 0),
                resolveSizeAndState(height, heightMeasureSpec,  height < getSuggestedMinimumHeight() ? MEASURED_STATE_TOO_SMALL : 0));
    }
}
