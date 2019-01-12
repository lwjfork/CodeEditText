package com.lwjfork.code.text;

import android.graphics.Rect;

/**
 * Created by lwj on 2019/1/12.
 * lwjfork@gmail.com
 * 明文绘制
 */
public class NoneTextDrawer extends BaseTextDrawer {


    public NoneTextDrawer(int codeInputType, int codeTextColor, int codeTextSize, int dotRadius) {
        super(codeInputType, codeTextColor, codeTextSize, dotRadius);
    }

    @Override
    protected void drawText(Rect rect, char c) {

    }
}
