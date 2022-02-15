package com.dspencer.gogogym.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dspencer.gogogym.models.Workout;

import java.util.List;

@Dao
public interface WorkoutsRecordedDao {
    
    @Insert
    public long insert(Workout entry);

    @Query("SELECT * FROM Workout")
    public List<Workout> getAll();

    @Query("SELECT * FROM Workout WHERE id = :id LIMIT 1")
    public Workout findById(long id);

    @Update
    public void update(Workout entry);

    @Delete
    public void delete(Workout entry);
}
