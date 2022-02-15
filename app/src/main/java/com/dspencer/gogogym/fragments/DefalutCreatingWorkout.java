//Comment in to add info to built in database


package com.dspencer.gogogym.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dspencer.gogogym.R;
import com.dspencer.gogogym.viewModels.DefaultViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class DefalutCreatingWorkout extends Fragment {
    private boolean previouslySaving = false;

    public DefalutCreatingWorkout() {
        super(R.layout.workout_template_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DefaultViewModel viewModel = new ViewModelProvider(getActivity()).get(DefaultViewModel.class);
        viewModel.getCurrentWorkout1().observe(getViewLifecycleOwner(), (entry) -> {
            if (entry != null) {
                TextInputEditText exercise = view.findViewById(R.id.exercise_input);
                TextInputEditText weight = view.findViewById(R.id.workout_input);
                TextInputEditText reps = view.findViewById(R.id.reps_input);
                TextInputEditText description = view.findViewById(R.id.workout_description);
                exercise.setText(entry.exercise);
                weight.setText(entry.weight);
                reps.setText(entry.reps);
                description.setText((entry.description));
            }
        });

        viewModel.getSaving1().observe(getViewLifecycleOwner(), (saving) -> {
            if (saving && !previouslySaving) {
                MaterialButton button = view.findViewById(R.id.SaveDefault);
                button.setEnabled(false);
                button.setText("Saving...");
                previouslySaving = saving;
            } else if (previouslySaving && !saving) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        view.findViewById(R.id.SaveDefault).setOnClickListener(SaveDefault -> {
            TextInputEditText exercise = view.findViewById(R.id.exercise_input);
            TextInputEditText weight = view.findViewById(R.id.workout_input);
            TextInputEditText reps = view.findViewById(R.id.reps_input);
            TextInputEditText description = view.findViewById(R.id.workout_description);
            viewModel.saveWorkoutEntry(exercise.getText().toString(),
                    weight.getText().toString(),
                    reps.getText().toString(),
                    description.getText().toString());
        });

    }
}
