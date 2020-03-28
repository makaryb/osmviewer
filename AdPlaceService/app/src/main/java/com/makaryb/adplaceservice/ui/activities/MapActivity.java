package com.makaryb.adplaceservice.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.annotation.Nullable;

import com.makaryb.adplaceservice.ui.map.MapController;
import com.makaryb.adplaceservice.utils.FullScreenActivity;
import com.makaryb.adplaceservice.utils.ServiceManager;

import org.osmdroid.config.Configuration;
import org.osmdroid.views.MapView;

import static com.makaryb.adplaceservice.R.layout.activity_map;
import static com.makaryb.adplaceservice.utils.ServiceManager.getMapTask;

/**
 * Более подробную информацию о работе с картой можно найти здесь:
 * https://github.com/osmdroid/osmdroid
 * https://github.com/MKergall/osmbonuspack
 */

public class MapActivity extends FullScreenActivity {
    private MapView mapView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_map);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mapView = getMapTask().initMap(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ServiceManager.setCurrentActivity(this);
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Configuration.getInstance().save(this, prefs);
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        MapController.center();
    }

    public MapView getMapView() {
        return mapView;
    }

    public void onBackClick(View view) {
        finish();
    }
}