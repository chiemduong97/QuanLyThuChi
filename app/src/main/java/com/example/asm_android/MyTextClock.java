package com.example.asm_android;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextClock;

import androidx.annotation.RequiresApi;

public class MyTextClock extends TextClock {
    public MyTextClock(Context context) {
        super(context);
        //
        this.setDesigningText();
    }

    public MyTextClock(Context context, AttributeSet attrs) {
        super(context, attrs);
        //
        this.setDesigningText();
    }

    public MyTextClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //
        this.setDesigningText();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyTextClock(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        //
        this.setDesigningText();
    }

    private void setDesigningText() {
        // The default text is displayed when designing the interface.
        this.setText("11:30:00");
    }
    @Override
    protected void onAttachedToWindow() {
        try {
            super.onAttachedToWindow();
        } catch(Exception e)  {
        }
    }
}
