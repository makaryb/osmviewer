package com.makaryb.adplaceservice.ui.activities;

import android.os.Bundle;

import androidx.core.app.ActivityCompat;

import com.makaryb.adplaceservice.utils.FullScreenActivity;
import com.makaryb.adplaceservice.utils.ServiceManager;

import java.util.logging.Logger;

import org.jetbrains.annotations.Contract;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.CHANGE_WIFI_STATE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.SYSTEM_ALERT_WINDOW;
import static android.Manifest.permission.VIBRATE;
import static android.Manifest.permission.WAKE_LOCK;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.M;

import static com.makaryb.adplaceservice.R.layout.activity_main;

public class MainActivity extends FullScreenActivity {

    private static Logger logger = Logger.getLogger(MainActivity.class.getName());

    private static MainActivity instance;

    @Contract(pure = true)
    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        if (SDK_INT >= M) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            WRITE_EXTERNAL_STORAGE,
                            VIBRATE,
                            INTERNET,
                            ACCESS_WIFI_STATE,
                            WAKE_LOCK,
                            ACCESS_COARSE_LOCATION,
                            ACCESS_NETWORK_STATE,
                            ACCESS_FINE_LOCATION,
                            CHANGE_WIFI_STATE,
                            MOUNT_UNMOUNT_FILESYSTEMS,
                            READ_EXTERNAL_STORAGE,
                            SYSTEM_ALERT_WINDOW,
                            READ_PHONE_STATE
                    }
                    , 1);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ServiceManager.initTasks();
        setContentView(activity_main);
    }

    @Override
    public void onResume() {
        super.onResume();
        ServiceManager.setCurrentActivity(this);

    }
}

