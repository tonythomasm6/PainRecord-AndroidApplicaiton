package com.example.paindiary.db.viewModel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.paindiary.db.entity.PainRecord;
import com.example.paindiary.db.repository.PainRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PainViewModel extends AndroidViewModel {

    private PainRepository pRepository;
    private LiveData<List<PainRecord>> allPainRecords;

    public PainViewModel(@NonNull Application application) {
        super(application);
        pRepository = new PainRepository(application);
        allPainRecords = pRepository.getAllPainRecords();
    }

    @RequiresApi(api= Build.VERSION_CODES.N)
    public CompletableFuture<PainRecord> findByIdFuture(final int painRecordId){
        return pRepository.findByIdFuture(painRecordId);
    }

    @RequiresApi(api=Build.VERSION_CODES.N)
    public CompletableFuture<List<PainRecord>> findTodayRecord(final String email, final Date date){
        return pRepository.findTodayRecord(email,date);
    }

    public LiveData<List<PainRecord>> getAllPainRecords(){return allPainRecords;}

    public void insert(PainRecord painRecord){ pRepository.insert(painRecord);}

    public void deleteALl(){pRepository.deleteAll();}

    public void update(PainRecord painRecord){pRepository.updatePain(painRecord);}

}
