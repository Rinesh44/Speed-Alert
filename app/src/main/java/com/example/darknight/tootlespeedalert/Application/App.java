package com.example.darknight.tootlespeedalert.Application;



import android.app.Application;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by darknight on 4/24/18.
 */

public class App extends Application {

    @Override
    public void onCreate() {

        super.onCreate();

        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder().build();

        Realm.setDefaultConfiguration(config);

    }


}
