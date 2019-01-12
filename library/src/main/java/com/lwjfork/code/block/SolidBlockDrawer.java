package com.lwjfork.code.block;

import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by lwj on 2019/1/12.
 * lwjfork@gmail.com
 * 填充色绘制者
 */
public class SolidBlockDrawer extends BaseBlockDrawer {


    public SolidBlockDrawer(int blockNormalColor, int blockFocusColor, int blockErrorColor, int blockShape, int blockLineWidth, int blockCorner) {
        super(blockNormalColor, blockFocusColor, blockErrorColor, blockShape, blockLineWidth, blockCorner);
        initPaint();
    }

    protected void initPaint() {
        blockPaint = new Paint();
        blockPaint.setStyle(Paint.Style.FILL);
        blockPaint.setAntiAlias(true);
        blockPaint.setColor(blockNormalColor);
    }



    @Override
    protected void drawFocusedBlock(RectF rectF) {
        drawRect(blockFocusColor, rectF);
    }

    @Override
    protected void drawNormalBlock(RectF rectF) {
        drawRect(blockNormalColor, rectF);
    }

    @Override
    protected void drawErrorBlock(RectF rectF) {
        drawRect(blockErrorColor, rectF);
    }

    private void drawRect(int color, RectF rectF) {
        if (rectF == null) {
            return;
        }
        blockPaint.setColor(color);
        canvas.drawRoundRect(rectF, blockCorner, blockCorner, blockPaint);
    }
}
