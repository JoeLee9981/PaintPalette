package com.example.joe.paint;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;


public class PaintActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PalletteView palletteView = new PalletteView(this);
        palletteView.setPadding(20,20,20,20);
        for(int i = 0; i < 10; i++) {
            PaintView paintView = new PaintView(this);
            paintView.setColor(Color.DKGRAY);
            palletteView.addView(paintView);

            paintView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    PaintView view = (PaintView)v;
                    view.setColor(Color.RED);
                }
            });

            paintView.setOnSplotchTouchListener(new PaintView.OnSplotchTouchListener()
            {
                @Override
                public void onSplotchTouch(PaintView v) {
                    PaintView view = (PaintView)v;
                    view.setColor(Color.GREEN);
                }
            });
        }
        setContentView(palletteView);
    }

}

