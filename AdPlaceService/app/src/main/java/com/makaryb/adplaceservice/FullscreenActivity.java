package com.makaryb.adplaceservice;

import android.annotation.SuppressLint;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import static android.view.Window.FEATURE_NO_TITLE;
import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;
import static android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;

@SuppressLint("Registered")
public abstract class FullscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(FEATURE_NO_TITLE);
        getWindow().addFlags(FLAG_FULLSCREEN);
        ErrorReporter.bindReporter(this.getApplicationContext());
    }

    @Override
    public void onResume(){
        super.onResume();
    }




    @Override
    public void finish() {
        super.finish();
    }
}
