package com.makaryb.adplaceservice.ui.map;

import com.makaryb.adplaceservice.ui.activities.MainActivity;

import org.osmdroid.tileprovider.tilesource.XYTileSource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

//Замена AtlasFinder
public abstract class AtlasProvider {

    private static Logger logger = Logger.getLogger(AtlasProvider.class.getName());

    static XYTileSource tileSource() {
        File[] candidates = atlasCandidates();
        for (File file : candidates) {
            String provider = checkAndGetTileSourceProvider(file);
            if (provider != null) {
                logger.info("Tile source provided by " + provider + " in file " + file.toString());
                return new XYTileSource(provider, 1, 18, 256, ".png", new String[]{file.getParentFile().toString()});
            }
        }
        return null;
    }

    static File[] atlasCandidates() {
        ArrayList<File> contents = new ArrayList<>();
        String[] atlasFolderPaths = atlasFolders();
        for (String folderPath : atlasFolderPaths) {
            File folder = new File(folderPath);
            File[] folderContent = folder.listFiles();
            if (folderContent != null)
                Collections.addAll(contents, folderContent);
        }
        File[] contentsArray = new File[contents.size()];
        for (int i = 0; i < contentsArray.length; i++) {
            contentsArray[i] = contents.get(i);
        }
        return contentsArray;
    }


    private static String[] atlasFolders() {
        File[] storages = MainActivity.getInstance().getExternalFilesDirs(null); //Нужен контекст, этот подходит
        String external = storages[0].toString();
        external = external.substring(0, external.lastIndexOf('/'));
        external = external.substring(0, external.lastIndexOf('/'));
        external = external.substring(0, external.lastIndexOf('/'));
        external = external.substring(0, external.lastIndexOf('/'));
        external = external + "/osmdroid";
        //    /storage/emulated/0/osmdroid

        try {
            if(storages.length == 2) {
                String sdCard = storages[1].toString() + "/osmdroid";
                return new String[] {external, sdCard};
            }
            else {
                return new String[] {external};
            }
        } catch (Exception ignored) {
            return new String[] {external};
        }
    }


    private static String checkAndGetTileSourceProvider(File file) {
        //Если файл - zip с картой, возвращает имя провайдера, вроде "Hikebikemap.de"
        if (file == null) return null;
        try {
            if (file.isDirectory()) return null;
            ZipFile zipFile = new ZipFile(file);
            ZipEntry root = zipFile.entries().nextElement();
            return root.getName().split("/")[0];
        } catch (IOException e) {
            logger.severe(e.toString());
        }
        return null;
    }

}
