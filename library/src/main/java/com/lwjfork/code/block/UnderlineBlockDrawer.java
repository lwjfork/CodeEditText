package com.lwjfork.code.block;

import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by lwj on 2019/1/12.
 * lwjfork@gmail.com
 * 下划线绘制者
 */
public class UnderlineBlockDrawer extends BaseBlockDrawer {

    public UnderlineBlockDrawer() {
        super();
    }

    public UnderlineBlockDrawer(int blockNormalColor, int blockFocusedColor, int blockErrorColor, int blockShape, int blockLineWidth, int blockCorner) {
        super(blockNormalColor, blockFocusedColor, blockErrorColor, blockShape, blockLineWidth, blockCorner);
    }

    protected void initPaint() {
        super.initPaint();
        blockPaint.setStyle(Paint.Style.FILL);
        blockPaint.setStrokeWidth(blockLineWidth);
    }


    @Override
    protected void drawFocusedBlock(RectF rectF) {
        drawLine(blockFocusColor, rectF);
    }

    @Override
    protected void drawNormalBlock(RectF rectF) {
        drawLine(blockNormalColor, rectF);
    }

    @Override
    protected void drawErrorBlock(RectF rectF) {
        drawLine(blockErrorColor, rectF);
    }

    private void drawLine(int color, RectF rectF) {
        if (rectF == null) {
            return;
        }
        blockPaint.setColor(color);
        canvas.drawLine(rectF.left, rectF.bottom - blockLineWidth, rectF.right, rectF.bottom - blockLineWidth, blockPaint);
    }
}
