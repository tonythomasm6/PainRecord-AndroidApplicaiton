package com.example.paindiary.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.example.paindiary.db.entity.PainRecord;
import com.example.paindiary.db.typeConvert.DateConverter;

import java.util.Date;
import java.util.List;

@Dao
public interface PainDAO {

    @Query("SELECT * FROM painrecord ORDER BY date desc")
    LiveData<List<PainRecord>>getAll();

    @Query("SELECT * FROM painrecord WHERE id=:painRecordId LIMIT 1")
    PainRecord findById(int painRecordId);
    @Insert
    void insert(PainRecord painRecord);

    @Delete
    void delete(PainRecord painRecord);

    @Update
    void update(PainRecord painRecord);

    @Query("DELETE FROM painrecord")
    void deleteAll();

    @TypeConverters(DateConverter.class)
    @Query("SELECT * FROM painrecord WHERE email=:email and date=:todayDate")
    List<PainRecord> findTodayRecord(String email, Date todayDate);
}
