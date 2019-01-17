package com.lwjfork.code.style;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.lwjfork.code.style.CodeInputType.NONE;
import static com.lwjfork.code.style.CodeInputType.PASSWORD;
import static com.lwjfork.code.style.CodeInputType.TEXT;

/**
 * Created by lwj on 2019/1/17.
 * lwjfork@gmail.com
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({PASSWORD, TEXT, NONE})
public @interface CodeInputType {
    int PASSWORD = 1;  // 密码样式
    int TEXT = 2;  // 明文
    int NONE = -1; // 什么都不画
}
