package com.dspencer.gogogym.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dspencer.gogogym.R;

public class HomeFragment extends Fragment {
    public HomeFragment() {
        super(R.layout.home_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button def = view.findViewById(R.id.Default);
        Button Starter = view.findViewById(R.id.Starter);
        def.setOnClickListener(go -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.HomeFragment, DefaultFragment.class,null)
                    .addToBackStack(null)
                    .commit();
        });
        Starter.setOnClickListener(go -> {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.HomeFragment, WorkoutsFragment.class,null)
                    .addToBackStack(null)
                    .commit();
        });

        }
}
