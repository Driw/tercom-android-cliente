package br.com.tercom.Application;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;

import br.com.tercom.Entity.LoginTercom;
import br.com.tercom.Entity.User;


public class AppTercom extends Application {
    private static AppTercom context;
    public static LoginTercom USER_STATIC;
    public static final String appVersion = "0.0.56";

    public static AppTercom getContext() {
        return context;
    }



}

