package com.example.paindiary.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import androidx.room.TypeConverters;

import com.example.paindiary.db.typeConvert.DateConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
public class PainRecord {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="Pain_Intensity")
    @NonNull
    public int painIntensity;

    @NonNull
    @ColumnInfo(name="Pain_Location")
    public String painLocation;

    @NonNull
    @ColumnInfo(name="Mood")
    public String mood;

    @NonNull
    @ColumnInfo(name="Steps_Taken")
    public String stepsTaken;

    @NonNull
    @ColumnInfo(name="email")
    public String email;

    @NonNull
    @TypeConverters(DateConverter.class)
    @ColumnInfo(name="date")
    public Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPainIntensity() {
        return painIntensity;
    }

    public void setPainIntensity(int painIntensity) {
        this.painIntensity = painIntensity;
    }

    @NonNull
    public String getPainLocation() {
        return painLocation;
    }

    public void setPainLocation(@NonNull String painLocation) {
        this.painLocation = painLocation;
    }

    @NonNull
    public String getMood() {
        return mood;
    }

    public void setMood(@NonNull String mood) {
        this.mood = mood;
    }

    @NonNull
    public String getStepsTaken() {
        return stepsTaken;
    }

    public void setStepsTaken(@NonNull String stepsTaken) {
        this.stepsTaken = stepsTaken;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public Date getDate() {
        return date;
    }

    public void setDate(@NonNull Date date) {
        this.date = date;
    }

    public PainRecord(int painIntensity, @NonNull String painLocation, @NonNull String mood, @NonNull String stepsTaken, @NonNull String email, @NonNull Date date) {
        this.painIntensity = painIntensity;
        this.painLocation = painLocation;
        this.mood = mood;
        this.stepsTaken = stepsTaken;
        this.email = email;
        //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        //String date = formatter.format(new Date()); // Converting date to string
        this.date = date; // Converting the formatted string back to date
    }

    @Override
    public String toString() {
        return "PainRecord{" +
                "id=" + id +
                ", painIntensity=" + painIntensity +
                ", painLocation='" + painLocation + '\'' +
                ", mood='" + mood + '\'' +
                ", email=" + email +
                ", date=" + date +
                ", stepsTaken=" + stepsTaken +
                '}';
    }
}
