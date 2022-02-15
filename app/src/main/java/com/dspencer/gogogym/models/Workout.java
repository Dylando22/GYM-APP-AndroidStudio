package com.dspencer.gogogym.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Workout {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo
    public String exercise;

    @ColumnInfo
    public String reps;

    @ColumnInfo
    public String weight;

    @ColumnInfo
    public String description;

    @ColumnInfo(name = "created_at")
    public long createdAt;
}
