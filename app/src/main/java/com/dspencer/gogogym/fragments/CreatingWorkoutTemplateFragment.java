package com.dspencer.gogogym.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dspencer.gogogym.R;
import com.dspencer.gogogym.viewModels.WorkoutsViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class CreatingWorkoutTemplateFragment extends Fragment {
    private boolean previouslySaving = false;
    public CreatingWorkoutTemplateFragment(){super(R.layout.workout_template_fragment);}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WorkoutsViewModel viewModel = new ViewModelProvider(getActivity()).get(WorkoutsViewModel.class);
        viewModel.getCurrentWorkout().observe(getViewLifecycleOwner(), (entry) -> {
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

        viewModel.getSaving().observe(getViewLifecycleOwner(), (saving) -> {
            if (saving && !previouslySaving) {
                MaterialButton button = view.findViewById(R.id.saveButton);
                button.setEnabled(false);
                button.setText("Saving...");
                previouslySaving = saving;
            } else if (previouslySaving && !saving) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });



        view.findViewById(R.id.saveButton).setOnClickListener(saveButton -> {
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
