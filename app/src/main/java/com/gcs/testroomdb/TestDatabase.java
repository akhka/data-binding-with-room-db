package com.gcs.testroomdb;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class TestDatabase extends RoomDatabase {

    private static TestDatabase instance;
    public abstract UserDao dao();
    static final ExecutorService dbWriteExecutor = Executors.newFixedThreadPool(4);

    public static synchronized TestDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TestDatabase.class, "test_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;
    }


    // Here create initials when db first created
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBTask(instance).execute();

        }
    };

    private static class PopulateDBTask extends AsyncTask{

        private UserDao dao;

        private PopulateDBTask(TestDatabase db){
            dao = db.dao();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            dao.insertAll(new User(1, "Mohammad", "Yar"),
                    new User(2, "Mowaffaq", "Yar"),
                    new User(3, "Tareq", "Yar"),
                    new User(4, "Ehab", "Yar"));
            return null;
        }
    }

}
