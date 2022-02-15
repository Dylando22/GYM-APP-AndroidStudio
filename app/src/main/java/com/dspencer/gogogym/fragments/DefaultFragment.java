package com.dspencer.gogogym.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dspencer.gogogym.R;
import com.dspencer.gogogym.WorkoutsAdapter;
import com.dspencer.gogogym.viewModels.DefaultViewModel;

public class DefaultFragment extends Fragment {
    public DefaultFragment() {
        super(R.layout.default_workout_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DefaultViewModel viewModel = new ViewModelProvider(getActivity()).get(DefaultViewModel.class);
        ObservableArrayList workoutEntries = viewModel.getEntries1();
        WorkoutsAdapter adapter = new WorkoutsAdapter(
                workoutEntries,
                entry -> {
                    viewModel.setCurrentWorkout1(entry);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.HomeFragment, DefaultWorkoutItem.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                }
        );
        workoutEntries.addOnListChangedCallback(new ObservableList.OnListChangedCallback() {
            @Override
            public void onChanged(ObservableList sender) {
                getActivity().runOnUiThread(() -> {
                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
                getActivity().runOnUiThread(() -> {
                    adapter.notifyItemRangeChanged(positionStart, itemCount);
                });
            }

            @Override
            public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
                getActivity().runOnUiThread(() -> {
                    adapter.notifyItemRangeInserted(positionStart, itemCount);
                });
            }

            @Override
            public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
                getActivity().runOnUiThread(() -> {
                    adapter.notifyItemMoved(fromPosition, toPosition);
                });
            }

            @Override
            public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
                getActivity().runOnUiThread(() -> {
                    adapter.notifyItemRangeRemoved(positionStart, itemCount);
                });
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.default_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
//        comment back in below to add data to bulit in database

//        view.findViewById(R.id.default_fab).setOnClickListener(fab -> {
//            viewModel.setCurrentWorkout1(null);
//            getActivity().getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.HomeFragment, DefalutCreatingWorkout.class, null)
//                    .setReorderingAllowed(true)
//                    .addToBackStack(null)
//                    .commit();
//        });
    }
}
