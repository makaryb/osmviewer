package com.makaryb.adplaceservice.utils;

import android.app.Activity;
import android.content.Intent;

import com.makaryb.adplaceservice.ui.activities.MapActivity;
import com.makaryb.adplaceservice.ui.map.MapUpdateTask;
import com.makaryb.adplaceservice.utils.gps.GpsProvider;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.logging.Logger;

import static com.makaryb.adplaceservice.utils.Utils.delay;

public final class ServiceManager {
    private static Logger logger;

    private static int UPDATE_TIME;
    private static volatile Activity currentActivity;

    private static MapUpdateTask mapTask;

    public static volatile boolean isRunning;

    private ServiceManager() {
    }

    @Contract(pure = true)
    public static synchronized
    Activity getCurrentActivity() {
        return currentActivity;
    }

    /**
     * Запуск ServiceManager
     */
    public static void start() {
        isRunning = true;
        runServiceThread();
        logger.info("ServiceManager has been launched");
    }

    private static void runServiceThread() {
        Thread thread = new Thread(() -> {
            while (isRunning) {
                if (currentActivity != null) {
                    executeTasks();
                }
                delay(UPDATE_TIME);
            }
        }, "Services Thread");
        thread.start();
    }

    /**
     * Любые периодические действия, такие как обновление элементов UI, получение и отправка данных
     * могут быть помещены в тело данного метода. Данный метод вызывается с периодичностью из configuration.xml
     *
     * @see #UPDATE_TIME
     */
    private static void executeTasks() {
        if (currentActivity instanceof MapActivity) {
            mapTask.run();
        }
    }

    /**
     * Инициализация всех необходимых для работы класса переменных
     */
    public static void initServiceManager(int updateTime) {
        UPDATE_TIME = updateTime;
        GpsProvider.getInstance();
    }

    /**
     * Инициализвция тасков
     */
    public static void initTasks() {
        mapTask = new MapUpdateTask();
    }

    /**
     * При переходе на новое активити необходимо вызывать данный метод. Если активити наследуется от
     * FullScreenActivity, то работа с данны методом осуществляется автоматически
     *
     * @param activity открываемое активити
     */
    public static synchronized void setCurrentActivity(Activity activity) {
        currentActivity = activity;
    }

    /**
     * Выполняет заданный таск
     *
     * @param task задача для выполнения
     */
    public static void executeTask(Runnable task) {
        if (task == null) return;
        new Thread(task, "additional ui task" + new Random().nextInt())
                .run();
    }

    /**
     * Остановка выполнения ServiceManager
     */
    public static void stop() {
        isRunning = false;
        logger.info("All services has been stopped");
    }

    /**
     * Запускает новое активити, которое будет являться дочерним для текущего активити.
     * @param activityClass класс, представляющий новое активити
     */
    public static void startActivity(Class activityClass) {
        if (currentActivity != null)
            currentActivity.startActivity(new Intent(currentActivity, activityClass));
    }

    @Contract(pure = true)
    @NotNull
    public static MapUpdateTask getMapTask() {
        return mapTask;
    }
}
