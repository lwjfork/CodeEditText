package com.lwjfork.code;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.Px;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.lwjfork.code.block.BaseBlockDrawer;
import com.lwjfork.code.block.NoneBlockDrawer;
import com.lwjfork.code.block.SolidBlockDrawer;
import com.lwjfork.code.block.StrokeBlockDrawer;
import com.lwjfork.code.block.UnderlineBlockDrawer;
import com.lwjfork.code.cusor.CursorDrawer;
import com.lwjfork.code.text.BaseTextDrawer;
import com.lwjfork.code.text.NoneTextDrawer;
import com.lwjfork.code.text.PasswordTextDrawer;
import com.lwjfork.code.text.TextDrawer;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by lwj on 2019/1/11.
 * lwjfork@gmail.com
 * 验证码输入框、密码输入框
 */
@SuppressWarnings("AppcompatCustomView")
public class CodeEditText extends EditText {
    @ColorInt
    protected int blockNormalColor; // 正常边框、填充、下划线颜色
    @ColorInt
    protected int blockFocusColor;  // 获取焦点时边框、填充、下划线颜色
    @ColorInt
    protected int blockErrorColor; // 输入错误时边框、填充、下划线颜色
    protected int blockShape;
    protected int blockLineWidth; //  正常边框、下划线宽度
    protected int codeInputType; // 显示样式 支持明文和密码两种，密码时画圆点
    @ColorInt
    protected int codeTextColor;// 显示圆点、明文时的字体颜色
    protected int codeTextSize;// 明文时字体大小
    protected int dotRadius;// 密码样式时圆点的半径
    protected int blockCorner; //  画边框及填充色的圆角
    private int blockSpace; // 输入框间距
    private int maxCodeLength;// 输入码的最大长度

    private boolean showCursor;// 是否显示光标
    private int cursorDuration;// 光标闪烁间隔
    private int cursorWidth;// 光标宽度
    @ColorInt
    private int cursorColor;// 光标颜色 没有设置时默认字体颜色

    private Bitmap blockBitmap;
    private Bitmap textBitmap;
    private Bitmap cursorBitmap;
    private ArrayList<Rect> blockRects; // 每块所占区域
    private BaseBlockDrawer blockDrawer; // 方框绘制者
    private BaseTextDrawer textDrawer; // 文本绘制者
    private CursorDrawer cursorDrawer; // 光标绘制者

    protected Context mContext;
    private DisplayMetrics metrics;

    public static class BlockShape {
        public static final int STROKE = 1; // 边框
        public static final int SOLID = 2;  // 填充
        public static final int UNDERLINE = 3; // 下划线
        public static final int NONE = -1;  // 什么都不画
    }

    public static class CodeInputType {
        public static final int PASSWORD = 1;  // 密码样式
        public static final int TEXT = 2;  // 明文
        public static final int NONE = -1; // 什么都不画
    }


    public CodeEditText(Context context) {
        this(context, null);
    }

    public CodeEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CodeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public CodeEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this.mContext = context;
        metrics = mContext.getResources().getDisplayMetrics();
        parseAttrs(context, attrs, defStyleAttr, defStyleRes);
        forbidCopyAndPaste();
        initEditText();
    }


    // 解析属性
    private void parseAttrs(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CodeEditText, defStyleAttr, defStyleRes);
        maxCodeLength = typedArray.getInteger(R.styleable.CodeEditText_maxCodeLength, 6);
        codeTextColor = typedArray.getColor(R.styleable.CodeEditText_codeTextColor, Color.BLACK);
        codeTextSize = typedArray.getDimensionPixelSize(R.styleable.CodeEditText_codeTextSize, sp2px(12));
        dotRadius = typedArray.getDimensionPixelSize(R.styleable.CodeEditText_dotRadius, dp2px(5));
        codeInputType = typedArray.getInteger(R.styleable.CodeEditText_codeInputType, CodeInputType.NONE);
        textDrawer = createTextDrawer(codeInputType, codeTextColor, codeTextSize, dotRadius);

        blockNormalColor = typedArray.getColor(R.styleable.CodeEditText_blockNormalColor, codeTextColor);
        blockFocusColor = typedArray.getColor(R.styleable.CodeEditText_blockFocusColor, blockNormalColor);
        blockErrorColor = typedArray.getColor(R.styleable.CodeEditText_blockErrorColor, blockNormalColor);
        blockLineWidth = typedArray.getDimensionPixelSize(R.styleable.CodeEditText_blockLineWidth, dp2px(1));
        blockCorner = typedArray.getDimensionPixelSize(R.styleable.CodeEditText_blockCorner, 0);
        blockSpace = typedArray.getDimensionPixelSize(R.styleable.CodeEditText_blockSpace, 0);
        blockShape = typedArray.getInteger(R.styleable.CodeEditText_blockShape, BlockShape.NONE);
        blockDrawer = createBlockDrawer(blockNormalColor, blockFocusColor, blockErrorColor, blockShape, blockLineWidth, blockCorner);

        showCursor = typedArray.getBoolean(R.styleable.CodeEditText_showCursor, false);
        cursorDuration = typedArray.getInteger(R.styleable.CodeEditText_cursorDuration, 500);
        cursorWidth = typedArray.getDimensionPixelSize(R.styleable.CodeEditText_blockCorner, dp2px(1));
        cursorColor = typedArray.getColor(R.styleable.CodeEditText_cursorColor, blockNormalColor);
        cursorDrawer = createCursorDrawer(showCursor, cursorDuration, cursorWidth, cursorColor);
        typedArray.recycle();
    }

    /**
     * 光标绘制者
     *
     * @param showCursor
     * @param cursorDuration
     * @param cursorWidth
     * @param cursorColor
     * @return
     */
    private CursorDrawer createCursorDrawer(boolean showCursor, int cursorDuration, int cursorWidth, int cursorColor) {
        return new CursorDrawer(showCursor, cursorDuration, cursorWidth, cursorColor);
    }

    /**
     * 方框绘制者
     *
     * @param blockNormalColor
     * @param blockFocusColor
     * @param blockErrorColor
     * @param blockShape
     * @param blockLineWidth
     * @param blockCorner
     * @return
     */
    private BaseBlockDrawer createBlockDrawer(int blockNormalColor, int blockFocusColor, int blockErrorColor, int blockShape, int blockLineWidth, int blockCorner) {
        switch (blockShape) {
            case BlockShape.SOLID:
                return new SolidBlockDrawer(blockNormalColor, blockFocusColor, blockErrorColor, blockShape, blockLineWidth, blockCorner);
            case BlockShape.STROKE:
                return new StrokeBlockDrawer(blockNormalColor, blockFocusColor, blockErrorColor, blockShape, blockLineWidth, blockCorner);
            case BlockShape.UNDERLINE:
                return new UnderlineBlockDrawer(blockNormalColor, blockFocusColor, blockErrorColor, blockShape, blockLineWidth, blockCorner);
            default:
                return new NoneBlockDrawer(blockNormalColor, blockFocusColor, blockErrorColor, blockShape, blockLineWidth, blockCorner);
        }
    }

    /**
     * 文本绘制者
     *
     * @param codeInputType
     * @param codeTextColor
     * @param codeTextSize
     * @param dotRadius
     * @return
     */
    private BaseTextDrawer createTextDrawer(int codeInputType, int codeTextColor, int codeTextSize, int dotRadius) {
        switch (codeInputType) {
            case CodeInputType.TEXT:
                return new TextDrawer(codeInputType, codeTextColor, codeTextSize, dotRadius);
            case CodeInputType.PASSWORD:
                return new PasswordTextDrawer(codeInputType, codeTextColor, codeTextSize, dotRadius);
            default:
                return new NoneTextDrawer(codeInputType, codeTextColor, codeTextSize, dotRadius);
        }
    }

    private void initEditText() {
        setCursorVisible(false); // 不显示原来的光标
        setTextColor(Color.TRANSPARENT); // 本身的字体颜色透明
        setBackgroundDrawable(null);
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxCodeLength)}); // 设置最大长度
        setSingleLine();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = getMeasuredWidth();
        int measureHeight = getMeasuredHeight();
        initRect(measureWidth, measureHeight);
        initBitmapAndCanvas(measureWidth, measureHeight);
    }

    /**
     * 计算每个输入框的位置，并初始化绘制者
     *
     * @param measureWidth
     * @param measureHeight
     */
    private void initRect(int measureWidth, int measureHeight) {
        if (blockRects == null) {
            blockRects = new ArrayList<>();
        }
        blockRects.clear();
        int startX = 0;
        int startY = 0;
        int endX = 0;
        int endY = 0;
        int blockWidth = (measureWidth - blockSpace * (maxCodeLength - 1)) / 6;
        int blockHeight = measureHeight;
        for (int i = 0; i < maxCodeLength; i++) {
            endX = startX + blockWidth;
            endY = startY + blockHeight;
            Rect blockRect = new Rect(startX, startY, endX, endY);
            blockRects.add(blockRect);
            startX = endX + blockSpace;
            startY = 0;
        }
        blockDrawer.setBlockRects(blockRects);
        textDrawer.setBlockRects(blockRects);
        cursorDrawer.setBlockRects(blockRects);
    }

    /**
     * 创建Bitmap 和 Canvas
     *
     * @param measureWidth
     * @param measureHeight
     */
    private void initBitmapAndCanvas(int measureWidth, int measureHeight) {
        blockBitmap = blockDrawer.createBitmapAndCanvas(measureWidth, measureHeight);
        textBitmap = textDrawer.createBitmapAndCanvas(measureWidth, measureHeight);
        cursorBitmap = cursorDrawer.createBitmapAndCanvas(measureWidth, measureHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.drawBitmap(blockBitmap, 0, 0, null);
        canvas.drawBitmap(textBitmap, 0, 0, null);
        canvas.drawBitmap(cursorBitmap, 0, 0, null);
        blockDrawer.drawCanvas();
        textDrawer.drawCanvas();
        cursorDrawer.drawCanvas();
        if (isInEditMode()) {
            textDrawer.setContent("111");
        }
    }


    private void forbidCopyAndPaste() {
        setLongClickable(false);
        setTextIsSelectable(false);
        setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // setInsertionDisabled when user touches the view
            setInsertionDisabled();
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        if (id == android.R.id.paste) return false;
        return super.onTextContextMenuItem(id);
    }

    private void setInsertionDisabled() {
        try {
            Field editorField = TextView.class.getDeclaredField("mEditor");
            editorField.setAccessible(true);
            Object editorObject = editorField.get(this);

            Class editorClass = Class.forName("android.widget.Editor");
            Field mInsertionControllerEnabledField = editorClass.getDeclaredField("mInsertionControllerEnabled");
            mInsertionControllerEnabledField.setAccessible(true);
            mInsertionControllerEnabledField.set(editorObject, false);

            Field mSelectionControllerEnabledField = editorClass.getDeclaredField("mSelectionControllerEnabled");
            mSelectionControllerEnabledField.setAccessible(true);
            mSelectionControllerEnabledField.set(editorObject, false);
        } catch (Exception ignored) {
            // ignore exception here
        }
    }

    @Override
    public boolean isSuggestionsEnabled() {
        return false;
    }


    OnTextChangedListener onTextChangedListener;

    public void setOnTextChangedListener(OnTextChangedListener onTextChangedListener) {
        this.onTextChangedListener = onTextChangedListener;
    }

    /**
     * 密码监听者
     */
    public interface OnTextChangedListener {
        /**
         * 输入/删除监听
         *
         * @param changeText 输入/删除的字符
         */
        void onCodeChanged(CharSequence changeText);

        /**
         * input complete
         */
        void onInputCompleted(CharSequence text);
    }

    /**
     *  update UI
     *
     * @param text
     * @param start
     * @param lengthBefore
     * @param lengthAfter
     */
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if (isInEditMode()) {
            return;
        }
        if (lengthBefore == lengthAfter && lengthBefore == 0) {
            return;
        }
        int currentBlockIndex = text.toString().length();
        if (onTextChangedListener != null) {
            onTextChangedListener.onCodeChanged(text.toString());
            if (text.length() == maxCodeLength) {
                onTextChangedListener.onInputCompleted(text.toString());
            }
        }
        blockDrawer.setCurrentBlockIndex(currentBlockIndex);
        textDrawer.setCurrentBlockIndex(currentBlockIndex);
        cursorDrawer.setCurrentBlockIndex(currentBlockIndex);
        textDrawer.setContent(getText().toString());
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        blockDrawer.setFocused(focused);
        textDrawer.setFocused(focused);
        cursorDrawer.setFocused(focused);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        recycleBitmap(blockBitmap);
        recycleBitmap(textBitmap);
        recycleBitmap(cursorBitmap);
    }

    /**
     *  bitmap recycle
     *
     * @param bitmap
     */
    private void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    /**
     * clear Text
     */
    public void clearContent() {
        getEditableText().clear();
    }

    boolean errorState;

    public boolean isErrorState() {
        return errorState;
    }

    public void setErrorState(boolean errorState) {
        this.errorState = errorState;
        blockDrawer.setErrorState(errorState);
    }


    public int getBlockNormalColor() {
        return blockNormalColor;
    }

    public void setBlockNormalColor(@ColorInt int blockNormalColor) {
        this.blockNormalColor = blockNormalColor;
        if (blockDrawer != null) {
            blockDrawer.setBlockNormalColor(blockNormalColor);
        }
    }

    public int getBlockFocusColor() {
        return blockFocusColor;
    }

    public void setBlockFocusColor(@ColorInt int blockFocusColor) {
        this.blockFocusColor = blockFocusColor;
        if (blockDrawer != null) {
            blockDrawer.setBlockFocusColor(blockFocusColor);
        }
    }

    public int getBlockErrorColor() {
        return blockErrorColor;
    }

    public void setBlockErrorColor(@ColorInt int blockErrorColor) {
        this.blockErrorColor = blockErrorColor;
        if (blockDrawer != null) {
            blockDrawer.setBlockErrorColor(blockErrorColor);
        }
    }

    public int getBlockLineWidth() {
        return blockLineWidth;
    }

    public void setBlockLineWidth(int blockLineWidth) {
        this.blockLineWidth = blockLineWidth;
        if (blockDrawer != null) {
            blockDrawer.setBlockLineWidth(blockLineWidth);
        }
    }

    public int getCorner() {
        return blockCorner;
    }

    public void setBlockCorner(int blockCorner) {
        this.blockCorner = blockCorner;
        if (blockDrawer != null) {
            blockDrawer.setBlockCorner(blockCorner);
        }
    }

    public int getBlockSpace() {
        return blockSpace;
    }

    public void setBlockSpace(int blockSpace) {
        this.blockSpace = blockSpace;
        requestLayout();
    }

    public int getCodeTextColor() {
        return codeTextColor;
    }

    public void setCodeTextColor(@ColorInt int codeTextColor) {
        this.codeTextColor = codeTextColor;
        if (textDrawer != null) {
            textDrawer.setCodeTextColor(codeTextColor);
        }
    }

    public int getCodeTextSize() {
        return codeTextSize;
    }

    public void setCodeTextSize(@Px int codeTextSize) {
        this.codeTextSize = codeTextSize;
        if (textDrawer != null) {
            textDrawer.setCodeTextSize(codeTextSize);
        }
    }

    public int getDotRadius() {
        return dotRadius;
    }

    public void setDotRadius(@Px int dotRadius) {
        this.dotRadius = dotRadius;
        if (textDrawer != null) {
            textDrawer.setDotRadius(dotRadius);
        }
    }

    public int getMaxCodeLength() {
        return maxCodeLength;
    }

    public void setMaxCodeLength(int maxCodeLength) {
        this.maxCodeLength = maxCodeLength;
        requestLayout();
    }

    public boolean isShowCursor() {
        return cursorDrawer.isShowCursor();
    }

    public void setShowCursor(boolean showCursor) {
        cursorDrawer.setShowCursor(showCursor);
    }

    public int getCursorDuration() {
        return 1000;
    }

    public void setCursorDuration(int cursorDuration) {
        cursorDrawer.setCursorDuration(cursorDuration);
    }

    public int getCursorWidth() {
        return cursorDrawer.getCursorWidth();
    }

    public void setCursorWidth(@Px int cursorWidth) {
        cursorDrawer.setCursorWidth(cursorWidth);
    }

    public int getCursorColor() {
        return cursorDrawer.getCursorColor();
    }

    public void setCursorColor(@ColorInt int cursorColor) {
        cursorDrawer.setCursorColor(cursorColor);
    }

    /**
     * @see BlockShape
     */
    public int getBlockShape() {
        return blockDrawer.getBlockShape();
    }

    /**
     * @param blockShape
     * @see BlockShape
     */
    public void setCodeShape(int blockShape) {
        if (blockShape == blockDrawer.getBlockShape()) {
            return;
        }
        BaseBlockDrawer newBlockDrawer = createBlockDrawer(blockNormalColor, blockFocusColor, blockErrorColor, blockShape, blockLineWidth, blockCorner);
        if (blockDrawer != null) {
            newBlockDrawer.setCanvas(blockDrawer.getCanvas());
            newBlockDrawer.setFocused(blockDrawer.isFocused());
            newBlockDrawer.setBlockRects(blockDrawer.getBlockRects());
            newBlockDrawer.setErrorState(blockDrawer.isErrorState());
            newBlockDrawer.setCurrentBlockIndex(blockDrawer.getCurrentBlockIndex());
            blockDrawer = newBlockDrawer;
            invalidate();
        } else {
            blockDrawer = newBlockDrawer;
            blockDrawer.setErrorState(errorState);
            blockDrawer.setBlockRects(blockRects);
            requestLayout();
        }
    }

    /**
     * @see CodeInputType
     */
    public int getCodeInputType() {
        return textDrawer.getCodeInputType();
    }

    /**
     * @see CodeInputType
     */
    public void setCodeInputType(int codeInputType) {
        if (codeInputType == textDrawer.getCodeInputType()) {
            return;
        }

        BaseTextDrawer newTextDrawer = createTextDrawer(codeInputType, codeTextColor, codeTextSize, dotRadius);
        if (textDrawer != null) {
            newTextDrawer.setCanvas(textDrawer.getCanvas());
            newTextDrawer.setFocused(textDrawer.isFocused());
            newTextDrawer.setBlockRects(textDrawer.getBlockRects());
            newTextDrawer.setCurrentBlockIndex(textDrawer.getCurrentBlockIndex());
            newTextDrawer.setContent(textDrawer.getContent());
            textDrawer = newTextDrawer;
            invalidate();
        } else {
            textDrawer = newTextDrawer;
            textDrawer.setBlockRects(blockRects);
            requestLayout();
        }
    }

    public int dp2px(float dp) {
        float density = metrics.density;
        return (int) (dp * density + 0.5f);
    }

    public int sp2px(float sp) {
        return (int) (sp * metrics.scaledDensity + 0.5f);
    }
}
