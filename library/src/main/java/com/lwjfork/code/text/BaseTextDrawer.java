package com.lwjfork.code.text;

import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;

import com.lwjfork.code.CodeEditText;
import com.lwjfork.code.base.BaseDrawer;


/**
 * Created by lwj on 2019/1/12.
 * lwjfork@gmail.com
 */
public abstract class BaseTextDrawer extends BaseDrawer {

    protected int codeInputType; // 显示样式 支持明文和密码两种，密码时画圆点
    @ColorInt
    protected int codeTextColor;// 显示圆点、明文时的字体颜色
    protected int codeTextSize;// 明文时字体大小
    protected int dotRadius;// 密码样式时圆点的半径

    protected Paint textPaint;   // 文字

    protected String content = new String();

    public BaseTextDrawer() {
    }

    public BaseTextDrawer(int codeInputType, int codeTextColor, int codeTextSize, int dotRadius) {
        this.codeInputType = codeInputType;
        this.codeTextColor = codeTextColor;
        this.codeTextSize = codeTextSize;
        this.dotRadius = dotRadius;
        initPaint();
    }


    public void initPaint() {
        textPaint = new Paint();
        textPaint.setColor(codeTextColor);
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(codeTextSize);
    }

    @Override
    public void drawCanvas() {
        if (canvas == null) {
            return;
        }
        if (textPaint == null) {
            initPaint();
        }
        clearCanvas(canvas);
        int length = content.length();
        for (int i = 0; i < length; i++) {
            drawText(blockRects.get(i), content.charAt(i));
        }
    }


    protected abstract void drawText(Rect rect, char c);

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        drawCanvas();
    }


    public int getCodeInputType() {
        return codeInputType;
    }

    public void setCodeInputType(int codeInputType) {
        this.codeInputType = codeInputType;
    }

    public int getCodeTextColor() {
        return codeTextColor;
    }

    public void setCodeTextColor(int codeTextColor) {
        this.codeTextColor = codeTextColor;
        textPaint.setColor(codeTextColor);
        drawCanvas();
    }

    public int getCodeTextSize() {
        return codeTextSize;
    }

    public void setCodeTextSize(int codeTextSize) {
        this.codeTextSize = codeTextSize;
        textPaint.setTextSize(codeTextSize);
        if (codeInputType == CodeEditText.CodeInputType.TEXT) {
            drawCanvas();
        }
    }

    public int getDotRadius() {
        return dotRadius;
    }

    public void setDotRadius(int dotRadius) {
        this.dotRadius = dotRadius;
        if (codeInputType == CodeEditText.CodeInputType.PASSWORD) {
            drawCanvas();
        }
    }


}
