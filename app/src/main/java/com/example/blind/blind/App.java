package com.example.blind.blind;

import android.app.Application;

/**
 * Created by drcov on 04.06.2018.
 */

public class App extends Application {
    public static App INSTANCE;
    private static DbHelper dbHelper;


    public static DbHelper getDbHelper() {
        if (dbHelper == null)
            dbHelper = new DbHelper(INSTANCE);
        return dbHelper;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }
}
