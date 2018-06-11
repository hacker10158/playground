package com.troy.playground.slidetoast;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.troy.playground.R;

public class SlideToastActivity extends AppCompatActivity implements View.OnClickListener {
    private Button triggerButton;
    private SlideContainer slideContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_toast);
        initUI();
    }

    void initUI(){
        slideContainer = findViewById(R.id.fl_container);
        triggerButton = findViewById(R.id.btn_trigger_slide);
        triggerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_trigger_slide:
                slideContainer.showView();
                break;
            default:
                break;
        }
    }
}
