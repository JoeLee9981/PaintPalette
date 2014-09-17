package com.example.joe.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by god_laptop on 9/3/2014.
 */
public class PaintView extends View {

    public interface OnSplotchTouchListener {
        public void onSplotchTouch(PaintView v);
    }

    OnSplotchTouchListener onSplotchTouchListener = null;

    int color = Color.CYAN;
    RectF contentRect;
    float radius;

    public PaintView(Context context) {
        super(context);
        setMinimumHeight(75);
        setMinimumWidth(75);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        invalidate();
    }

    public void setOnSplotchTouchListener(OnSplotchTouchListener listener) {
        this.onSplotchTouchListener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        float circleCenterX = contentRect.centerX();
        float circleCenterY = contentRect.centerY();

        float distance = (float)Math.sqrt((circleCenterX - x) * (circleCenterX - x)
                + (circleCenterY - y) * (circleCenterY - y));

        if(distance < radius) {
            Log.i("paintView", "Toch in circle");
            if(onSplotchTouchListener != null)
                onSplotchTouchListener.onSplotchTouch(this);
        }
        else {
            Log.i("paintView", "Touch not in cirlce");
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        Path path = new Path();

        contentRect = new RectF();
        contentRect.left = getPaddingLeft();
        contentRect.top = getPaddingTop();
        contentRect.right = getWidth() - getPaddingRight();
        contentRect.bottom = getHeight() - getPaddingBottom();

        PointF center = new PointF(contentRect.centerX(), contentRect.centerY());
        float maxRadius = Math.min(contentRect.width() * 0.5f, contentRect.height() * 0.5f);
        float minRadius = .025f * maxRadius;
        radius = minRadius + (maxRadius - minRadius) * 0.5f;
        center.x = getWidth() * 0.5f;
        center.y = getHeight() * 0.5f;

        int pointCount = 50;
        for(int pointIndex = 0; pointIndex < pointCount; pointIndex++) {
            PointF point = new PointF();

            radius += (Math.random() - 0.5) * 2.0f * (maxRadius - radius);

            point.x = center.x + radius * (float)Math.cos((double)pointIndex / (double)pointCount * 2.0 * Math.PI);
            point.y = center.y + radius * (float)Math.sin((double)pointIndex / (double)pointCount * 2.0 * Math.PI);

            if(pointIndex == 0)
                path.moveTo(point.x, point.y);
            else
                path.lineTo(point.x, point.y);
        }

        canvas.drawPath(path, paint);
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

        if(width > height && heightMode != MeasureSpec.EXACTLY)
            width = height;
        if(height > width && heightMode != MeasureSpec.EXACTLY)
            height = width;

        setMeasuredDimension(
                resolveSizeAndState(width, widthMeasureSpec, width < getSuggestedMinimumWidth() ? MEASURED_STATE_TOO_SMALL : 0),
                resolveSizeAndState(height, heightMeasureSpec,  height < getSuggestedMinimumHeight() ? MEASURED_STATE_TOO_SMALL : 0));
    }
}
