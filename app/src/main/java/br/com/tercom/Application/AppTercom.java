package br.com.tercom.Application;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;

import br.com.tercom.DataBase.AppDataBase;
import br.com.tercom.Entity.User;


public class AppTercom extends Application {
    private static AppTercom context;
    private static AppDataBase dataBase;
    public static User USER_STATIC;


    public static AppDataBase getDataBase()
    {
        return dataBase;
    }

    public AppTercom()
    {
        context = this;
        new StaticAsyncDB(this).execute();
    }

    public static AppTercom getContext() {
        return context;
    }


    private static class StaticAsyncDB extends AsyncTask<Void, Void, Boolean>
    {
        private WeakReference<AppTercom> wref;

        public StaticAsyncDB(AppTercom ctx)
        {
            wref = new WeakReference<>(ctx);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            dataBase = Room.databaseBuilder(context, AppDataBase.class, "DataBaseName")
                    .allowMainThreadQueries()
                    .build();
            return true;
        }
    }
}

