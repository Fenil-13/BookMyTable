package com.digitalgenius.bookmytable.utils.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class MyTextView_Poppins_SemiBold extends TextView {

    public MyTextView_Poppins_SemiBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyTextView_Poppins_SemiBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextView_Poppins_SemiBold(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-SemiBold.ttf");
            setTypeface(tf);
        }
    }

}