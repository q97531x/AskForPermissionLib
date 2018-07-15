package com.example.weeboos.askforpermissionlib;

import android.app.Application;
import android.content.Context;

public class App extends Application{
    private static App app;
    public App() {
    }

    public static App getInstance() {
        if(app == null) {
            app = new App();
        }
        return app;
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }
}
