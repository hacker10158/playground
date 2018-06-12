package com.troy.playground.slidetoast;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.troy.playground.R;

public class SlideToastActivity extends AppCompatActivity implements View.OnClickListener {
    private Button triggerButton;
    private TextView targetView;
    private View slideContainer;
    private SlideToastController slideToastController;
    private int targetCounter = 0;
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
        targetView = findViewById(R.id.tv_target_count);
        slideToastController = new SlideToastController();
        slideToastController.init(slideContainer);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_trigger_slide:
                targetCounter++;
                String number = String.valueOf(targetCounter);
                targetView.setText(number);
                slideToastController.showView(number);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        slideToastController.clean();
    }
}
