package com.makaryb.adplaceservice.utils.gps;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

import com.makaryb.adplaceservice.MainApplication;
import com.makaryb.adplaceservice.utils.interfaces.Changeable;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.Context.LOCATION_SERVICE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.location.LocationManager.GPS_PROVIDER;
import static com.makaryb.adplaceservice.ui.map.MapUtils.checkLocation;

/**
 * Предоставляет информацию о GPS-координатах Android-устройтсва
 */
public class GpsProvider implements Changeable {
    private static GpsProvider instance;
    private static boolean isChanged = true;

    private GpsProvider() {
        LocationManager locationManager = (LocationManager) MainApplication.getInstance().getSystemService(LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener();
        if (ActivityCompat.checkSelfPermission(MainApplication.getInstance(), ACCESS_FINE_LOCATION) != PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainApplication.getInstance(), ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
            return;
        }
        if (locationManager != null) {
            /*
             minTime=5000 - minimum time interval between location updates, in milliseconds
             minDistance=10 - minimum distance between location updates, in meters
            */
            locationManager.requestLocationUpdates(GPS_PROVIDER, 5000, 10, locationListener);
        }
    }

    /**
     * Get instance of gpsProvider.
     *
     * @return the gps provider
     */
    @NotNull
    public static GpsProvider getInstance() {
        if (instance == null) instance = new GpsProvider();
        return instance;
    }

    public static boolean isLocationAvailable() {
        return getInstance().getLocation() != null && checkLocation(getInstance().getLocation());
    }

    /**
     * Get current location.
     *
     * @return the location
     */
    public Location getLocation() {
        return MyLocationListener.getLocation();
    }

    @Override
    public boolean isChanged() {
        return isChanged;
    }

    @Override
    public void markChanged() {
        isChanged = true;
    }

    @Override
    public void markChangesHandled() {
        isChanged = false;
    }

    private static class MyLocationListener implements LocationListener {
        /**
         * Get current location.
         *
         * @return the location
         */
        @Contract(pure = true)
        static Location getLocation() {
            return location;
        }

        /**
         * The Location.
         */
        static Location location;

        @Override
        public void onLocationChanged(Location loc) {
            location = loc;
            isChanged = true;//todo: oh, static gosh...
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    }
}
