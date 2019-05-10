package com.troy.playground.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.troy.playground.R;
import com.troy.playground.databinding.ActivityMainBinding;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initUI();
    }

    void initUI(){

    }

}
