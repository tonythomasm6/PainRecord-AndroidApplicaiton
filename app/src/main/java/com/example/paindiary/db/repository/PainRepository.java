package com.example.paindiary.db.repository;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.paindiary.db.dao.PainDAO;
import com.example.paindiary.db.database.PainDatabase;
import com.example.paindiary.db.entity.PainRecord;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class PainRepository {

    private PainDAO painDao;
    private LiveData<List<PainRecord>> allPainRecords;

    public PainRepository(Application application){
        PainDatabase db =PainDatabase.getInstance(application);
        painDao = db.painDao();
        allPainRecords =painDao.getAll();
    }

    //Executed on different thread
    public LiveData<List<PainRecord>> getAllPainRecords(){
        return allPainRecords;
    }

    public void insert(final PainRecord painRecord){
        PainDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {   painDao.insert(painRecord);   }
        });
    }

    public void deleteAll(){
        PainDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {    painDao.deleteAll();   }
        });
    }

    public void delete(final PainRecord painRecord){
        PainDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {  painDao.delete(painRecord);   }
        });
    }

    public void updatePain(final PainRecord painRecord){
        PainDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() { painDao.update(painRecord);  }
        });
    }

    @RequiresApi(api= Build.VERSION_CODES.N)
    public CompletableFuture<PainRecord> findByIdFuture(final int painRecordId){
        return CompletableFuture.supplyAsync(new Supplier<PainRecord>() {
            @Override
            public PainRecord get() {
                return painDao.findById(painRecordId);
            }
        }, PainDatabase.databaseWriteExecutor);
    }

    @RequiresApi(api=Build.VERSION_CODES.N)
    public CompletableFuture<List<PainRecord>>  findTodayRecord(final String email, final Date date){
        return CompletableFuture.supplyAsync(new Supplier<List<PainRecord>>() {
            @Override
            public List<PainRecord> get() {
                return painDao.findTodayRecord(email,date);
            }
        }, PainDatabase.databaseWriteExecutor);
    }
}
