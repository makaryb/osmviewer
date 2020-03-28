package com.makaryb.adplaceservice;

import androidx.multidex.MultiDexApplication;

import org.jetbrains.annotations.Contract;

import static com.makaryb.adplaceservice.utils.ServiceManager.initServiceManager;
import static com.makaryb.adplaceservice.utils.resources.ConfigurationManager.getServiceManagerUpdateTime;
import static com.makaryb.adplaceservice.utils.resources.ResourceManager.attachResources;

public class MainApplication extends MultiDexApplication {
    private static MainApplication instance;

    @Contract(pure = true)
    public static MainApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        attachResources(getResources());
        initServiceManager(getServiceManagerUpdateTime());
    }
}
