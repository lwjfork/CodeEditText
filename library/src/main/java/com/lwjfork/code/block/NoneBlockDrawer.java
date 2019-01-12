package com.lwjfork.code.block;

import android.graphics.RectF;

/**
 * Created by lwj on 2019/1/12.
 * lwjfork@gmail.com
 * 任性，就是什么都不画
 */
public class NoneBlockDrawer extends BaseBlockDrawer {

    public NoneBlockDrawer(int blockNormalColor, int blockFocusedColor, int blockErrorColor, int blockShape, int blockLineWidth, int blockCorner) {
        super(blockNormalColor, blockFocusedColor, blockErrorColor, blockShape, blockLineWidth, blockCorner);
    }

    @Override
    protected void drawFocusedBlock(RectF rectF) {

    }

    @Override
    protected void drawNormalBlock(RectF rectF) {

    }

    @Override
    protected void drawErrorBlock(RectF rectF) {

    }
}
