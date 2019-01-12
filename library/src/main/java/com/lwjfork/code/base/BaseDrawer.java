package com.lwjfork.code.base;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by lwj on 2019/1/12.
 * lwjfork@gmail.com
 */
public abstract class BaseDrawer {

    protected Canvas canvas;
    protected ArrayList<Rect> blockRects;
    protected int currentBlockIndex;
    private boolean isFocused = false;


    public Bitmap createBitmapAndCanvas(int measureWidth, int measureHeight) {
        Bitmap blockBitmap = Bitmap.createBitmap(measureWidth, measureHeight, Bitmap.Config.ARGB_8888);
        canvas = new Canvas();
        canvas.setBitmap(blockBitmap);
        return blockBitmap;
    }

    public void setBlockRects(ArrayList<Rect> blockRects) {
        this.blockRects = blockRects;
    }

    public void setCurrentBlockIndex(int currentBlockIndex) {
        this.currentBlockIndex = currentBlockIndex;
    }

    public void setFocused(boolean isFocused) {
        this.isFocused = isFocused;
    }


    public boolean isFocused() {
        return isFocused;
    }

    protected void clearCanvas(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR); // 清空
    }


    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public ArrayList<Rect> getBlockRects() {
        return blockRects;
    }

    public int getCurrentBlockIndex() {
        return currentBlockIndex;
    }

    public abstract void drawCanvas();
}
