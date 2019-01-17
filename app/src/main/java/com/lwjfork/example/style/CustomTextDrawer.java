package com.lwjfork.example.style;

import android.graphics.Paint;
import android.graphics.Rect;

import com.lwjfork.code.text.BaseTextDrawer;

/**
 * Created by lwj on 2019/1/17.
 * lwjfork@gmail.com
 */
public class CustomTextDrawer extends BaseTextDrawer {

    private int textBaseLineY;

    public CustomTextDrawer() {
        super();
    }

    @Override
    protected void drawText(Rect rect, char c) {
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float top = fontMetrics.top;// 基线到字体上边框的距离
        float bottom = fontMetrics.bottom;// 基线到字体下边框的距离
        textBaseLineY = (int) (rect.centerY() - top / 2 - bottom / 2);
        canvas.drawText("X", rect.centerX(), textBaseLineY, textPaint);

    }
}
