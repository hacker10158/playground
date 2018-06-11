package com.troy.playground;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.troy.playground.slidetoast.SlideToastActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button mSlideButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    void initUI(){
        mSlideButton = findViewById(R.id.btn_slide);
        mSlideButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_slide:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SlideToastActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
