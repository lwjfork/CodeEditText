package com.lwjfork.example;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.lwjfork.code.CodeEditText;

/**
 * Created by lwj on 2019/1/17.
 * lwjfork@gmail.com
 */
public class SimpleActivity extends Activity {


    public static void launch(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SimpleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        CodeEditText et_test = findViewById(R.id.et_test);
        et_test.setOnTextChangedListener(new CodeEditText.OnTextChangedListener() {
            @Override
            public void onCodeChanged(CharSequence changeText) {
                Log.e("SimpleActivity", String.format("onCodeChanged -- %s", changeText + ""));
            }

            @Override
            public void onInputCompleted(CharSequence text) {
                Log.e("SimpleActivity", String.format("onInputCompleted -- %s", text + ""));
            }
        });
    }
}
