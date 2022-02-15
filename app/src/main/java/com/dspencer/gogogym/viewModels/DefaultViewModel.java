package com.dspencer.gogogym.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.dspencer.gogogym.database.DefaultDatabase;
import com.dspencer.gogogym.models.Workout;

import java.util.ArrayList;

public class DefaultViewModel extends AndroidViewModel {
    private DefaultDatabase dfdatabase;
    private MutableLiveData<Boolean> saving1 = new MutableLiveData<>();
    private ObservableArrayList<Workout> entries1 = new ObservableArrayList<>();
    private MutableLiveData<Workout> currentWorkout1 = new MutableLiveData<>();

    public DefaultViewModel(@NonNull Application application) {
        super(application);
        saving1.setValue(false);
        dfdatabase = Room.databaseBuilder(application, DefaultDatabase.class, "defaultdb").build();

        new Thread(() -> {
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            ArrayList<Workout> WorkoutEntries = (ArrayList<Workout>) dfdatabase.getDefaultDao().getAll();
            entries1.addAll(WorkoutEntries);
        }).start();
    }


    public MutableLiveData<Workout> getCurrentWorkout1() {
        return currentWorkout1;
    }

    public void setCurrentWorkout1(Workout workout) {
        this.currentWorkout1.setValue(workout);
    }

    public MutableLiveData<Boolean> getSaving1() {
        return saving1;
    }

    public ObservableArrayList<Workout> getEntries1() {
        return entries1;
    }

    public void deleteCurrentWorkout() {
        new Thread(() -> {
            dfdatabase.getDefaultDao().delete(currentWorkout1.getValue());
            entries1.remove(currentWorkout1.getValue());
            currentWorkout1.postValue(null);
        }).start();
    }

    public void saveWorkoutEntry(String exer, String weight, String reps, String descrip) {
        saving1.setValue(true);
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (currentWorkout1.getValue() != null) {
                Workout current = currentWorkout1.getValue();
                current.exercise = exer;
                current.reps = reps;
                current.weight = weight;
                current.description = descrip;
                dfdatabase.getDefaultDao().update(current);
                currentWorkout1.postValue(current);
                int index = entries1.indexOf(current);
                entries1.set(index, current);
            } else {
                Workout current = new Workout();
                current.exercise = exer;
                current.reps = reps;
                current.weight = weight;
                current.description = descrip;
                current.createdAt = System.currentTimeMillis();
                current.id = dfdatabase.getDefaultDao().insert(current);

                entries1.add(current);
            }
            saving1.postValue(false);
        }).start();
    }
}
