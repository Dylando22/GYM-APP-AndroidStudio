package com.dspencer.gogogym.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.dspencer.gogogym.models.Workout;

@Database(entities = {Workout.class}, version = 1)
public abstract class DefaultDatabase extends RoomDatabase {
    public abstract WorkoutsRecordedDao getDefaultDao();
}