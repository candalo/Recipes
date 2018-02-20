package br.com.candalo.recipes;


import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

public class RecipesApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }
}
