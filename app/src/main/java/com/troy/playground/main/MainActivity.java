package com.troy.playground.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.troy.playground.R;
import com.troy.playground.databinding.ActivityMainBinding;
import com.troy.playground.omlet.OmletFragment;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initUI();
    }

    void initUI(){
        showFragment(OmletFragment.newInstance());
    }

    private void showFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();

        FragmentManager manager = getSupportFragmentManager();

        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.fl_fragment_container, fragment, backStateName);
        ft.addToBackStack(backStateName);
        ft.commitAllowingStateLoss();
    }
}
