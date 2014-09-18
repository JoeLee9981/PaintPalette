package com.example.joe.paint;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;


public class PaintActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setBackgroundColor(Color.RED);

        PaintAreaView paintAreaView = new PaintAreaView(this);
        mainLayout.addView(paintAreaView);

        LinearLayout paletteLayout = new LinearLayout(this);
        PaletteView paletteView = new PaletteView(this);
        paletteView.setPadding(20, 20, 20, 20);

        paletteLayout.addView(paletteView);
        mainLayout.addView(paletteLayout);

        setContentView(mainLayout);

    }

}

