package com.lwjfork.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_simple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleActivity.launch(MainActivity.this);
            }
        });
        findViewById(R.id.btn_custom_style).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomStyleActivity.launch(MainActivity.this);
            }
        });
    }
}
