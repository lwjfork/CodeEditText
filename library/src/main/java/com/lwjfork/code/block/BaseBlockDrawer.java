package com.lwjfork.code.block;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.ColorInt;

import com.lwjfork.code.base.BaseDrawer;
import com.lwjfork.code.style.BlockShape;


/**
 * Created by lwj on 2019/1/12.
 * lwjfork@gmail.com
 * 区域块绘制者
 */
public abstract class BaseBlockDrawer extends BaseDrawer {
    @ColorInt
    protected int blockNormalColor; // 正常边框、填充、下划线颜色
    @ColorInt
    protected int blockFocusColor;  // 获取焦点时边框、填充、下划线颜色
    @ColorInt
    protected int blockErrorColor; // 输入错误时边框、填充、下划线颜色
    @BlockShape
    protected int blockShape;
    protected int blockLineWidth; //  正常边框、下划线宽度
    protected boolean isErrorState;
    protected Paint blockPaint;
    protected int blockCorner; //  画边框及填充色的圆角


    public BaseBlockDrawer() {
        initPaint();
    }

    public BaseBlockDrawer(int blockNormalColor, int blockFocusColor, int blockErrorColor, @BlockShape int blockShape, int blockLineWidth, int blockCorner) {
        this.blockNormalColor = blockNormalColor;
        this.blockFocusColor = blockFocusColor;
        this.blockErrorColor = blockErrorColor;
        this.blockShape = blockShape;
        this.blockLineWidth = blockLineWidth;
        this.blockCorner = blockCorner;
        initPaint();
    }

    protected void initPaint() {
        blockPaint = new Paint();
        blockPaint.setAntiAlias(true);
        blockPaint.setColor(blockNormalColor);
    }


    @Override
    public final void drawCanvas() {
        if (canvas == null) {
            return;
        }
        clearCanvas(canvas);
        int size = blockRects.size();
        for (int i = 0; i < size; i++) {
            if (i == currentBlockIndex && isFocused()) {
                continue;
            } else {
                RectF rectF = fixPosition(i);
                if (isErrorState) {
                    drawErrorBlock(rectF);
                } else {
                    drawNormalBlock(rectF);
                }
            }
        }
        if (isFocused()) {
            drawFocusedBlock(fixPosition(currentBlockIndex));
        }
    }

    // 焦点获取绘制
    protected abstract void drawFocusedBlock(RectF rectF);

    // 常态绘制
    protected abstract void drawNormalBlock(RectF rectF);

    // 错误态绘制
    protected abstract void drawErrorBlock(RectF rectF);

    public int getBlockNormalColor() {
        return blockNormalColor;
    }

    @BlockShape
    public int getBlockShape() {
        return blockShape;
    }

    public void seBlockShape(@BlockShape int blockShape) {
        this.blockShape = blockShape;
        drawCanvas();
    }

    public int getBlockLineWidth() {
        return blockLineWidth;
    }

    public void setBlockLineWidth(int blockLineWidth) {
        this.blockLineWidth = blockLineWidth;
        if (blockShape == BlockShape.STROKE || blockShape == BlockShape.UNDERLINE) {
            blockPaint.setStrokeWidth(blockLineWidth);
            drawCanvas();
        }
    }

    public void setBlockNormalColor(int blockNormalColor) {
        this.blockNormalColor = blockNormalColor;
        drawCanvas();
    }

    public int getBlockFocusColor() {
        return blockFocusColor;
    }

    public void setBlockFocusColor(int blockFocusColor) {
        this.blockFocusColor = blockFocusColor;
        drawCanvas();
    }

    public int getBlockErrorColor() {
        return blockErrorColor;
    }

    public void setBlockErrorColor(int blockErrorColor) {
        this.blockErrorColor = blockErrorColor;
        drawCanvas();
    }

    public int getBlockCorner() {
        return blockCorner;
    }

    public void setBlockCorner(int blockCorner) {
        this.blockCorner = blockCorner;
        if (blockShape == BlockShape.STROKE || blockShape == BlockShape.SOLID) {
            drawCanvas();
        }
    }

    /**
     * fix 线的绘制宽度不一致问题
     *
     * @param i
     * @return
     */
    protected RectF fixPosition(int i) {
        if (i >= 0 && i < blockRects.size()) {
            Rect rect = blockRects.get(i);
            if (blockShape == BlockShape.UNDERLINE) {
                return new RectF(rect);
            }
            int left = rect.left;
            int top = rect.top;
            int right = rect.right;
            int bottom = rect.bottom;
            if (i == 0) { // 开头或者结尾
                left = left + blockLineWidth / 2;
            } else if (i == blockRects.size() - 1) {
                right = right - blockLineWidth / 2;
            }
            top = top + blockLineWidth / 2;
            bottom = bottom - blockLineWidth;
            return new RectF(left, top, right, bottom);
        }
        return null;
    }

    public boolean isErrorState() {
        return isErrorState;
    }

    public void setErrorState(boolean errorState) {
        isErrorState = errorState;
        drawCanvas();
    }
}
