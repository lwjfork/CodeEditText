package com.lwjfork.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lwjfork.code.CodeEditText;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CodeEditText et_test = (CodeEditText) findViewById(R.id.et_test);

        et_test.setOnTextChangedListener(new CodeEditText.OnTextChangedListener() {
            @Override
            public void onCodeChanged(CharSequence changeText) {
                Log.e("Text ", changeText + "");
            }

            @Override
            public void onInputCompleted(CharSequence text) {
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
            }
        });
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_test.addChar('1');
            }
        });
        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_test.delete();
            }
        });
    }
}
