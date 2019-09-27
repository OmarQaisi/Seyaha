package com.example.seyaha;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import javax.annotation.Nullable;


public class CustomTextView extends androidx.appcompat.widget.AppCompatTextView {
    public CustomTextView(Context context) {
        super(context);
        init_text(context, null);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init_text(context, attrs);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init_text(context, attrs);
    }

    public void init_text(Context context, AttributeSet attributeSet) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setTypeface(getResources().getFont(R.font.montserrat_medium));
        }

    }


}
