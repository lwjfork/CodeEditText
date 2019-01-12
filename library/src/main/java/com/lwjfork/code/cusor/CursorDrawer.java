package com.lwjfork.code.cusor;

import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;

import com.lwjfork.code.base.BaseDrawer;


/**
 * Created by lwj on 2019/1/12.
 * lwjfork@gmail.com
 * 光标绘制者
 */
public class CursorDrawer extends BaseDrawer {
    private boolean showCursor;// 是否显示光标
    private int cursorDuration;// 光标闪烁间隔
    private int cursorWidth;// 光标宽度
    @ColorInt
    private int cursorColor;// 光标颜色 没有设置时默认黑色
    private Paint cursorPaint;

    public CursorDrawer(boolean showCursor, int cursorDuration, int cursorWidth, int cursorColor) {
        this.showCursor = showCursor;
        this.cursorDuration = cursorDuration;
        this.cursorWidth = cursorWidth;
        this.cursorColor = cursorColor;
        iniPaint();
    }

    private void iniPaint() {
        cursorPaint = new Paint();
        cursorPaint.setAntiAlias(true);
        cursorPaint.setColor(cursorColor);
        cursorPaint.setStyle(Paint.Style.FILL);
        cursorPaint.setStrokeWidth(cursorWidth);
    }

    @Override
    public void setFocused(boolean isFocused) {
        super.setFocused(isFocused);
    }

    @Override
    public void drawCanvas() {

    }


    public void drawCursor() {
        if (!showCursor) { // 不显示光标
            clearCanvas(canvas);
            return;
        }
        if (!isFocused()) { // 失去焦点
            clearCanvas(canvas);
            return;
        }
        if (currentBlockIndex >= blockRects.size()) { // 已经输入完成
            clearCanvas(canvas);
            return;
        }
        clearCanvas(canvas);
        Rect rect = blockRects.get(currentBlockIndex);
        int startX = rect.centerX() - cursorWidth / 2;
        int endX = startX;
        int startY = rect.centerY() - (rect.bottom - rect.top) / 6;
        int endY = rect.centerY() + (rect.bottom - rect.top) / 6;
        canvas.drawLine(startX, startY, endX, endY, cursorPaint);
    }

    public void cancelCursor() {
        clearCanvas(canvas);
    }


    public boolean isShowCursor() {
        return showCursor && isFocused() && currentBlockIndex < blockRects.size();
    }

    public void setShowCursor(boolean showCursor) {
        this.showCursor = showCursor;
    }

    public int getCursorDuration() {
        return cursorDuration;
    }

    public void setCursorDuration(int cursorDuration) {
        this.cursorDuration = cursorDuration;
    }

    public int getCursorWidth() {
        return cursorWidth;
    }

    public void setCursorWidth(int cursorWidth) {
        this.cursorWidth = cursorWidth;
    }

    public int getCursorColor() {
        return cursorColor;
    }

    public void setCursorColor(int cursorColor) {
        this.cursorColor = cursorColor;
    }

    @Override
    public void setCurrentBlockIndex(int currentBlockIndex) {
        super.setCurrentBlockIndex(currentBlockIndex);
    }


}
