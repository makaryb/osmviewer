package com.makaryb.adplaceservice.ui.map;

import android.location.Location;

import com.makaryb.adplaceservice.ui.activities.MapActivity;
import com.makaryb.adplaceservice.utils.gps.GpsProvider;

import org.osmdroid.util.GeoPoint;

import static com.makaryb.adplaceservice.ui.map.MapUtils.checkLocation;
import static com.makaryb.adplaceservice.utils.ServiceManager.getCurrentActivity;

/**
 * Данный класс предоставляет возможность отдавать команды карте на центрирование на определенных объектах.
 */

public class MapController {
    public static void center(){
        if(centerMapOnRC()) {
            return;
        }
        centerMapDefault();
    }

    public static boolean centerMapOnRC() {
        Location location = GpsProvider.getInstance().getLocation();
        if (checkLocation(location)) {
            centerMap(new GeoPoint(
                    location.getLatitude(),
                    location.getLongitude()));
            return true;
        } else {
            return false;
        }
    }

    private static void centerMapDefault() {
        double SPBSTU_LATITUDE   = 60.006225;
        double SPBSTU_LONGTITUDE = 30.379211;
        try {
            centerMap(new GeoPoint(SPBSTU_LATITUDE, SPBSTU_LONGTITUDE));
        }catch (RuntimeException e){
            e.printStackTrace();
        }
    }


    private static void centerMap(GeoPoint point) {
        if (getCurrentActivity() == null) {
            return;
        }
        if (getCurrentActivity() instanceof MapActivity) {
            ((MapActivity) getCurrentActivity()).getMapView().getController().setCenter(point);
        }
    }
}
