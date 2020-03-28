package com.makaryb.adplaceservice.ui.map;

import android.app.Activity;
import android.widget.LinearLayout;

import com.makaryb.adplaceservice.R;
import com.makaryb.adplaceservice.utils.Utils;

import org.jetbrains.annotations.NotNull;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.IRegisterReceiver;
import org.osmdroid.tileprovider.MapTileProviderArray;
import org.osmdroid.tileprovider.modules.MapTileFileArchiveProvider;
import org.osmdroid.tileprovider.modules.MapTileFilesystemProvider;
import org.osmdroid.tileprovider.modules.MapTileModuleProviderBase;
import org.osmdroid.tileprovider.modules.ZipFileArchive;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.tileprovider.util.SimpleRegisterReceiver;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.makaryb.adplaceservice.R.string.atlas_not_available;
import static com.makaryb.adplaceservice.ui.map.MapController.centerMapOnRC;

/**
 * Данный класс инициализирует карту и перерисовывает на неё маркеры и область видимости.
 */

public final class MapUpdateTask implements Runnable {

    private static final int AMOUNT_OF_OVERLAYS = 5;

    private static Logger logger = Logger.getLogger(MapUpdateTask.class.getName());

    private MapView mapView;

    public MapUpdateTask() {
        logger.info("MapTask has been created");
    }

    public void run() {
        if (mapView == null) return;
        mapView.postInvalidate();
    }

    public MapView initMap(Activity activity) {
        Configuration.getInstance().setDebugMapView(true);
        Configuration.getInstance().setDebugMode(true);

        final IRegisterReceiver registerReceiver = new SimpleRegisterReceiver(activity.getApplicationContext());
        ITileSource tileSource = AtlasProvider.tileSource();
        if(tileSource == null) tileSource = TileSourceFactory.HIKEBIKEMAP;

        File[] atlases = AtlasProvider.atlasCandidates();
        List<ZipFileArchive> archives = new ArrayList<>();
        for(File file: atlases){
            try {
                ZipFileArchive archive = new ZipFileArchive();
                archive.init(file);
                archives.add(archive);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        ZipFileArchive[] archiveArray = new ZipFileArchive[archives.size()];
        for (int i = 0; i < archiveArray.length; i++) {
            archiveArray[i] = archives.get(i);
        }
        final MapTileFilesystemProvider filesystemProvider = new MapTileFilesystemProvider(registerReceiver, tileSource);
        MapTileFileArchiveProvider archiveProvider = new MapTileFileArchiveProvider(
                registerReceiver, tileSource, archiveArray);
        final MapTileProviderArray tileProviderArray = new MapTileProviderArray(
                tileSource, registerReceiver, new MapTileModuleProviderBase[]{
            filesystemProvider, archiveProvider });

        if(archiveArray.length == 0) {
            logger.info(String.valueOf(atlas_not_available));
        }
        mapView = new MapView(activity, tileProviderArray);
        ((LinearLayout)activity.findViewById(R.id.map_layout)).addView(mapView);
        addMarkerOverlays();
        initMapView(mapView);
        return mapView;
    }

    private void addMarkerOverlays() {
        for (int i = 0; i < AMOUNT_OF_OVERLAYS; i++) {
            mapView.getOverlays().add(new Marker(mapView));
        }
    }

    private void setInitialCenter() {
            centerMapOnRC();
    }

    private void initMapView(@NotNull MapView mMapView) {
        mMapView.setMultiTouchControls(true);
        mMapView.setHasTransientState(true);
        mMapView.setUseDataConnection(false);
        mMapView.setBuiltInZoomControls(true);
        mMapView.setTilesScaledToDpi(true);
        mMapView.setMinZoomLevel(3.);
        mMapView.getController().setZoom(12.);
    }
}
