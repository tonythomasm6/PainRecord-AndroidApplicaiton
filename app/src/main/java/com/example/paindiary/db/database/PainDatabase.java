package com.example.paindiary.db.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.paindiary.db.dao.PainDAO;
import com.example.paindiary.db.entity.PainRecord;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {PainRecord.class},version = 2,exportSchema = false)
public abstract class PainDatabase extends RoomDatabase {

    public abstract PainDAO painDao();
    //Static to implement singleton patter. Only one instance of db in entire application.
    private static PainDatabase INSTANCE;

    //No of threads set to4
    private static final int NUMBER_OF_THREADS=4;

    //Executor service created with threads to run db operations in the backend.
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized PainDatabase getInstance(final Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PainDatabase.class,
                    "PainDatabase").fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }
}
