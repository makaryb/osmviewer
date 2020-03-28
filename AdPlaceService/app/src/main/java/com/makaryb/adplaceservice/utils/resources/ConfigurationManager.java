package com.makaryb.adplaceservice.utils.resources;

import androidx.annotation.NonNull;

import com.makaryb.adplaceservice.ui.activities.MainActivity;

import org.jetbrains.annotations.Contract;

import java.io.File;

import static com.makaryb.adplaceservice.BuildConfig.GitHash;
import static com.makaryb.adplaceservice.BuildConfig.GitVersion;
import static com.makaryb.adplaceservice.R.bool.show_hash;
import static com.makaryb.adplaceservice.R.integer.service_manager_update_time;
import static com.makaryb.adplaceservice.utils.resources.ResourceManager.getBoolean;
import static com.makaryb.adplaceservice.utils.resources.ResourceManager.getInteger;

/**
 * Класс для получения данных конфигурации.
 * Часть данных берется из фалйа configuraton.xml, часть из BuildConfig и часть создается по определенным правилам.
 */

public class ConfigurationManager {
    /**
     * @return время в миллисекундах задержки между выполнениями задач в ServiceManager
     * @see com.makaryb.adplaceservice.utils.ServiceManager
     */
    public static int getServiceManagerUpdateTime() {
        return getInteger(service_manager_update_time);
    }

    /**
     * @return версию приложения из тегов гита и хэш коммита head
     */
    @Contract(pure = true)
    @NonNull
    public static String getFullVersion() {
        return getVersion() + " hash: " + getHash();
    }

    /**
     * @return текущую версию приложения из тегов гита
     */
    @Contract(pure = true)
    @NonNull
    public static String getVersion() {
        return GitVersion;
    }

    /**
     * @return хэш коммита head
     */
    @Contract(pure = true)
    @NonNull
    public static String getHash() {
        return GitHash;
    }

    /**
     * @return нужно ли отобразить хэм коммита head
     */
    public static boolean getShowHash() {
        return getBoolean(show_hash);
    }

    /**
     * @return путь к папке с атласами
     */
    @NonNull
    public static String getAtlasFolder() {
        File[] storages =  MainActivity.getInstance().getExternalFilesDirs(null);
        String[] storageDirs = new String[storages.length];
        for (int i = 0; i < storages.length; i++) {
            String s = storages[i].toString();
            s = s.substring(0, s.lastIndexOf('/'));
            s = s.substring(0, s.lastIndexOf('/'));
            s = s.substring(0, s.lastIndexOf('/'));
            s = s.substring(0, s.lastIndexOf('/'));
            s = s + "/osmdroid";
            storageDirs[i] = s;
        }
        if(storageDirs.length == 2)
            return storageDirs[1];
        else
            return storageDirs[2];
    }
}
