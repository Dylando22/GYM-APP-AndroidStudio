package com.dspencer.gogogym.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dspencer.gogogym.R;
import com.dspencer.gogogym.viewModels.WorkoutsViewModel;

public class SingleWorkoutItemFragment extends Fragment {
    public SingleWorkoutItemFragment(){super(R.layout.workout_item_fragment);}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WorkoutsViewModel viewModel = new ViewModelProvider(getActivity()).get(WorkoutsViewModel.class);

        viewModel.getCurrentWorkout().observe(getViewLifecycleOwner(), (entry) -> {
            if (entry == null) {
                getActivity().getSupportFragmentManager().popBackStack();
            } else {
                TextView WorkoutNameView = view.findViewById(R.id.WorkoutTitleView);
                TextView Reps = view.findViewById(R.id.RepsView);
                TextView Weight = view.findViewById(R.id.WeightView);
                TextView description = view.findViewById(R.id.WoroutDescriptionText);
                WorkoutNameView.setText(entry.exercise);
                Reps.setText(entry.reps);
                Weight.setText(entry.weight);
                description.setText(entry.description);
            }

        });

        view.findViewById(R.id.delete).setOnClickListener((fab) -> {
            viewModel.deleteCurrentWorkout();
        });

        view.findViewById(R.id.edit).setOnClickListener(editFab -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.HomeFragment, CreatingWorkoutTemplateFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        });

        view.findViewById(R.id.backButton).setOnClickListener(editFab -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.HomeFragment, WorkoutsFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        });


    }
}
