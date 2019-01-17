package com.lwjfork.code.style;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.lwjfork.code.style.BlockShape.NONE;
import static com.lwjfork.code.style.BlockShape.SOLID;
import static com.lwjfork.code.style.BlockShape.STROKE;
import static com.lwjfork.code.style.BlockShape.UNDERLINE;

/**
 * Created by lwj on 2019/1/17.
 * lwjfork@gmail.com
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({STROKE, SOLID, UNDERLINE, NONE})
public @interface BlockShape {
    int STROKE = 1; // 边框
    int SOLID = 2;  // 填充
    int UNDERLINE = 3; // 下划线
    int NONE = -1;  // 什么都不画
}