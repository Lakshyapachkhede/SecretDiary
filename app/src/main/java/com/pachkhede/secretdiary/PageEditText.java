package com.pachkhede.secretdiary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

public class PageEditText extends AppCompatEditText {
    private Paint paint;

    public PageEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public PageEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int height = getHeight();
        int lineHeight = getLineHeight();
        int totalLines = height / lineHeight;


        int baseLine = getLineBounds(0, null);

        for (int i = 0; i < totalLines; i++) {
            canvas.drawLine(30, baseLine + i * lineHeight, getWidth() - 30, baseLine + i * lineHeight, paint);
        }

        super.onDraw(canvas);

    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xFFE0E0E0);
        paint.setStrokeWidth(2f);
    }




}
