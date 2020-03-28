package com.makaryb.adplaceservice.ui.map;

import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.views.MapView;

import static com.makaryb.adplaceservice.utils.Utils.testIsNaN;
import static com.makaryb.adplaceservice.utils.resources.ResourceManager.getDrawable;
import static java.lang.Math.PI;
import static java.lang.Math.cos;

/**
 * Класс содержит в себе вспомогтельные методы для работы карты.
 */

public class MapUtils {
    public static final double earthRadiusInMeters = 6376000f;

    private static final int NUMBER_OF_SECTORS = 36;// иконка имеет 36 различных дискретных углов поворота
    private static final int DEGREES_PER_SECTOR = 360 / NUMBER_OF_SECTORS;

    private final static Drawable[] aircraftIcons = new Drawable[NUMBER_OF_SECTORS];

    @Contract("null -> false")
    public static boolean checkLocation(Location location) {
        return location != null &&
                checkLocation(location.getLatitude(), location.getLongitude(), location.getAltitude());
    }

    public static boolean checkLocation(double lat, double lon) {
        return !testIsNaN(lat, lon);
    }

    public static boolean checkLocation(double lat, double lon, double alt) {
        return !testIsNaN(lat, lon, alt);
    }

    static double distance(double lat1, double lon1, double lat2, double lon2){
        double metersy = metersPerLatitudeDegree() * (lat2 - lat1);
        double metersx = metersPerLongitudeDegree(lat1) * (lon2 - lon1);
        return Math.sqrt(metersx* metersx + metersy * metersy);
    }


    static double metersPerLatitudeDegree() {
        return earthRadiusInMeters * PI / 180;
    }

    static double metersPerLongitudeDegree(double latitude) {
        return earthRadiusInMeters * PI / 180 * cos(latitude / 180 * PI);
    }
    static Point coordsToPixels(@NotNull MapView osmMapView, double latitude, double longitude) {
        return osmMapView.getProjection().toPixels(new IGeoPoint() {
            @Override
            public int getLatitudeE6() {
                return 0;
            }

            @Override
            public int getLongitudeE6() {
                return 0;
            }

            @Override
            public double getLatitude() {
                return latitude;
            }

            @Override
            public double getLongitude() {
                return longitude;
            }
        }, null);
    }
}
