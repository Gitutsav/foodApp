package com.utsavbucky.onebancfoodapp.utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.utsavbucky.onebancfoodapp.utils.LocaleManager;

public class OneBancFoodApp extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this);
    }
}
