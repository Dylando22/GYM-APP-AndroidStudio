package com.dspencer.gogogym.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.dspencer.gogogym.database.AppDatabase;
import com.dspencer.gogogym.models.Workout;

import java.util.ArrayList;

public class WorkoutsViewModel extends AndroidViewModel {
    private AppDatabase database;
    private MutableLiveData<Boolean> saving = new MutableLiveData<>();
    private ObservableArrayList<Workout> entries = new ObservableArrayList<>();
    private MutableLiveData<Workout> currentWorkout = new MutableLiveData<>();
    
    public WorkoutsViewModel(@NonNull Application application) {
        super(application);
        saving.setValue(false);
        database = Room.databaseBuilder(application, AppDatabase.class, "workoutdb").build();

        new Thread(() -> {
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            ArrayList<Workout> WorkoutEntries = (ArrayList<Workout>) database.getWorkoutsRecordedDao().getAll();
            entries.addAll(WorkoutEntries);
        }).start();
    }

    public MutableLiveData<Workout> getCurrentWorkout() {
        return currentWorkout;
    }

    public void setCurrentWorkout(Workout workout) {
        this.currentWorkout.setValue(workout);
    }

    public MutableLiveData<Boolean> getSaving() {
        return saving;
    }

    public ObservableArrayList<Workout> getEntries() {
        return entries;
    }

    public void deleteCurrentWorkout() {
        new Thread(() -> {
            database.getWorkoutsRecordedDao().delete(currentWorkout.getValue());
            entries.remove(currentWorkout.getValue());
            currentWorkout.postValue(null);
        }).start();
    }

    public void saveWorkoutEntry(String exer, String weight, String reps, String descrip) {
        saving.setValue(true);
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (currentWorkout.getValue() != null) {
                Workout current = currentWorkout.getValue();
                current.exercise = exer;
                current.reps = reps;
                current.weight = weight;
                current.description = descrip;
                database.getWorkoutsRecordedDao().update(current);
                currentWorkout.postValue(current);
                int index = entries.indexOf(current);
                entries.set(index, current);
            } else {
                Workout current = new Workout();
                current.exercise = exer;
                current.reps = reps;
                current.weight = weight;
                current.description = descrip;
                current.createdAt = System.currentTimeMillis();
                current.id = database.getWorkoutsRecordedDao().insert(current);

                entries.add(current);
            }
            saving.postValue(false);
        }).start();
    }

    
}
