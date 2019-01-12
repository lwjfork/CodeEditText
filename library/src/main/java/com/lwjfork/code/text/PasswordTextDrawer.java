package com.lwjfork.code.text;

import android.graphics.Rect;

/**
 * Created by lwj on 2019/1/12.
 * lwjfork@gmail.com
 * 密文绘制
 */
public class PasswordTextDrawer extends BaseTextDrawer {


    public PasswordTextDrawer(int codeInputType, int codeTextColor, int codeTextSize, int dotRadius) {
        super(codeInputType, codeTextColor, codeTextSize, dotRadius);
    }

    @Override
    protected void drawText(Rect rect, char c) {
        canvas.drawCircle(rect.centerX(), rect.centerY(), dotRadius, textPaint);
    }
}
