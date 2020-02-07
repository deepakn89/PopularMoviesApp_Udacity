package com.example.popularmoviesapp_latest.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.popularmoviesapp_latest.data.Movies;

@Database(entities = {Movies.class},version = 2,exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static final String TAG = "MovieDatabase";

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME="movies";
    private static MovieDatabase sInstance;

    //To avoid database crash with older version of apps
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

    public static MovieDatabase getInstance(Context context){
        if (sInstance == null) {
            synchronized (LOCK) {
                //Creating a new database instance.
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        MovieDatabase.class, MovieDatabase.DATABASE_NAME)
                        .addMigrations(MIGRATION_1_2)
                        .build();
            }
        }
        //Getting the database instance.
        return sInstance;
    }

    public abstract MovieDao movieDao();

}
