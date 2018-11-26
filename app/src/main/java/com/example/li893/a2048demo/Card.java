package com.example.li893.a2048demo;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout {
    private TextView textview;
    private int num = 0;

    public Card(Context context) {
        super(context);
        textview = new TextView(getContext());
        textview.setTextSize(32);
        textview.setBackgroundColor(Color.parseColor("#FAEBD7"));
        textview.setTextColor(Color.GRAY);
        textview.setGravity(Gravity.CENTER);
        LayoutParams lp = new LayoutParams(230,230);
        lp.setMargins(25, 25, 10, 10);
        addView(textview, lp);

    }


    public void setNum(int num) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.num = num;
                textview.setText(num + "");
            }
        }
    }






    }
