package com.lwjfork.code.block;

import android.graphics.Paint;
import android.graphics.RectF;

import com.lwjfork.code.style.BlockShape;

/**
 * Created by lwj on 2019/1/12.
 * lwjfork@gmail.com
 * 填充色绘制者
 */
public class SolidBlockDrawer extends BaseBlockDrawer {

    public SolidBlockDrawer() {
        super();
    }

    public SolidBlockDrawer(int blockNormalColor, int blockFocusColor, int blockErrorColor, @BlockShape int blockShape, int blockLineWidth, int blockCorner) {
        super(blockNormalColor, blockFocusColor, blockErrorColor, blockShape, blockLineWidth, blockCorner);
    }

    @Override
    protected void initPaint() {
        super.initPaint();
        blockPaint.setStyle(Paint.Style.FILL);
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
