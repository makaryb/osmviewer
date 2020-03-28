package com.makaryb.adplaceservice.utils.resources;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import androidx.annotation.BoolRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.annotation.RawRes;
import androidx.annotation.StringRes;

import org.jetbrains.annotations.Contract;

import java.io.InputStream;

/**
 * Класс позволяет удобно получить данные из ресурсов приложения в любой точке программы
 */

public final class ResourceManager {
    private static Resources resources;

    private ResourceManager() {
    }

    public static void attachResources(Resources resources) {
        ResourceManager.resources = resources;
    }

    public static boolean getBoolean(@BoolRes int id) {
        return resources.getBoolean(id);
    }

    public static int getInteger(@IntegerRes int id) {
        return resources.getInteger(id);
    }

    @NonNull
    public static String getString(@StringRes int id) {
        return resources.getString(id);
    }

    public static InputStream getRaw(@RawRes int id) {
        return resources.openRawResource(id);
    }

    public static Drawable getDrawable(@DrawableRes int id) {
        return resources.getDrawable(id);
    }

    public static int getColor(@ColorRes int id) {
        return resources.getColor(id);
    }

    @Contract(pure = true)
    public static Resources getResources() {
        return resources;
    }
}
