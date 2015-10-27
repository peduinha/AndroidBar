package com.example.alexandre.bar;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Alexandre on 12/10/2015.
 */
public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "u8KZKbYfH953sIyuqmK9LDUYak4ZJWeMX3oSWng4", "zZEtxsXV39eIsuY0QGbEUHCLaLIw8IDbrA8Ux6YD");

    }
}


