package com.test;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by m on 15.09.2017.
 */

public class TestApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("myrealm7.realm").build();
        Realm.setDefaultConfiguration(config);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
