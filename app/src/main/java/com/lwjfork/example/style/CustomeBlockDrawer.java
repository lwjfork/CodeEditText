package com.lwjfork.example.style;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.lwjfork.code.block.BaseBlockDrawer;

/**
 * Created by lwj on 2019/1/17.
 * lwjfork@gmail.com
 */
public class CustomeBlockDrawer extends BaseBlockDrawer {


    public CustomeBlockDrawer() {
        super();
    }

    @Override
    protected void initPaint() {
        super.initPaint();
        blockPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }


    @Override
    protected void drawFocusedBlock(RectF rectF) {
        drawRect(Color.BLUE, rectF);
    }

    @Override
    protected void drawNormalBlock(RectF rectF) {
        drawRect(Color.GREEN, rectF);
    }

    @Override
    protected void drawErrorBlock(RectF rectF) {
        drawRect(Color.RED, rectF);
    }

    private void drawRect(int color, RectF rectF) {
        if (rectF == null) {
            return;
        }
        blockPaint.setColor(color);
        canvas.drawRoundRect(rectF, blockCorner, blockCorner, blockPaint);
    }
}
