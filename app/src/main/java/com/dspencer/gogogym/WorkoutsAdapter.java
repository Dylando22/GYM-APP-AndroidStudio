package com.dspencer.gogogym;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import com.dspencer.gogogym.models.Workout;

public class WorkoutsAdapter extends RecyclerView.Adapter<WorkoutsAdapter.ViewHolder>{
    ObservableArrayList<Workout> entries;

    public interface OnWorkoutEntryClicked {
        public void onClick(Workout entry);
    }

    OnWorkoutEntryClicked listener;

    public WorkoutsAdapter(ObservableArrayList<Workout> entries, OnWorkoutEntryClicked listener) {
        this.entries = entries;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WorkoutsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_list_item_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutsAdapter.ViewHolder holder, int position) {
        TextView textView = holder.itemView.findViewById(R.id.workoutListItem);
        textView.setText(entries.get(position).exercise);
        holder.itemView.setOnClickListener(view -> {
            listener.onClick(entries.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
