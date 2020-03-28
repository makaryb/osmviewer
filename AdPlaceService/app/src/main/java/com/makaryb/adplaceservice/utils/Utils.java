package com.makaryb.adplaceservice.utils;

import android.app.Notification;
import android.app.NotificationManager;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.makaryb.adplaceservice.MainApplication;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static android.app.Notification.VISIBILITY_PUBLIC;
import static android.content.Context.NOTIFICATION_SERVICE;
import static java.lang.Double.isNaN;
import static java.lang.Math.min;

public class Utils {
    private static int notificationId = 0;

    private static Logger logger = Logger.getLogger(Utils.class.getName());

    private Utils() {
    }

    /**
     * Отображает уведомление в строке состояния
     *
     * @param title       заголовок уведомления
     * @param msg         сообщение для уведомления в свернутом виде
     * @param expandedMsg текст, отображаемый при разворачивании уведомления
     * @param icon        иконка для уведомления
     */
    public static void showNotification(@NotNull String title, @NotNull String msg, @Nullable String expandedMsg, @DrawableRes int icon) {
        Notification notification = createNotification(title, msg, expandedMsg, icon);
        NotificationManager mNotificationManager =
                (NotificationManager) MainApplication.getInstance().getSystemService(NOTIFICATION_SERVICE);
        if (mNotificationManager != null) {
            mNotificationManager.notify(notificationId++, notification);
        } else {
            logger.severe("Notification manager is not available. Unable to show notification.");
        }
    }

    private static Notification createNotification(@NotNull String title, @NotNull String msg, @Nullable String expandedMsg, @DrawableRes int icon) {
        Notification.Builder builder = new Notification.Builder(MainApplication.getInstance())
                .setVisibility(VISIBILITY_PUBLIC)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(msg);
        if (expandedMsg != null)
            builder.setStyle(new Notification.BigTextStyle().bigText(expandedMsg));
        return builder.build();
    }

    /**
     * Отображает уведомление в строке состояния
     *
     * @param title заголовок уведомления
     * @param msg   сообщение для уведомления в свернутом виде
     * @param icon  иконка для уведомления
     */
    public static void showNotification(@NotNull String title, @NotNull String msg, @DrawableRes int icon) {
        showNotification(title, msg, null, icon);
    }

    /**
     * Останавливает выполнения потока на заданное количество миллисекунд
     *
     * @param ms - время в миллисекундах
     */
    public static void delay(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            logger.severe(e.toString());
        }
    }

    @Contract("null -> false")
    public static boolean testIsNaN(double... var) {
        if (var == null) return false;
        boolean isNaN = false;
        for (double number : var) {
            isNaN |= isNaN(number);
        }
        return isNaN;
    }

    @NonNull
    public static String cropString(String str, int length) {
        if (str == null || length < 0) return "";//любое отрицательное число = обрезать до нуля
        return str.substring(0, min(str.length(), length));
    }
}
