package com.lwjfork.code.block;

import android.graphics.Paint;

/**
 * Created by lwj on 2019/1/12.
 * lwjfork@gmail.com
 * 边框绘制者
 */
public class StrokeBlockDrawer extends SolidBlockDrawer {


    public StrokeBlockDrawer() {
        super();
    }


    public StrokeBlockDrawer(int blockNormalColor, int blockFocusedColor, int blockErrorColor, int blockShape, int blockLineWidth, int blockCorner) {
        super(blockNormalColor, blockFocusedColor, blockErrorColor, blockShape, blockLineWidth, blockCorner);
    }

    @Override
    protected void initPaint() {
        super.initPaint();
        blockPaint.setStyle(Paint.Style.STROKE);
        blockPaint.setStrokeWidth(blockLineWidth);
    }

}
