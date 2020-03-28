package com.makaryb.adplaceservice.utils;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static android.view.Window.FEATURE_NO_TITLE;
import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;
import static android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;

/**
 * В рамках данного приложения желательно наследовть активити от данного класса.
 * Все активити, наследующиеся от данного будут полноэкранными (без статус-бара).
 * Данные класс реализует необходимую логику работы с ServiceManager при переходе между активити.
 */

@SuppressLint("Registered")
public abstract class FullScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(FLAG_KEEP_SCREEN_ON);

        requestWindowFeature(FEATURE_NO_TITLE);

        getWindow().addFlags(FLAG_FULLSCREEN);
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
