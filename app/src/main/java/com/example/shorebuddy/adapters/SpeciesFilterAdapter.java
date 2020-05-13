package com.example.shorebuddy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shorebuddy.R;
import com.example.shorebuddy.data.fish.Fish;

import java.util.ArrayList;
import java.util.List;

public class SpeciesFilterAdapter extends RecyclerView.Adapter<SpeciesFilterAdapter.SpeciesViewHolder> {
    private List<Fish> fish = new ArrayList<>();
    private Context context;
    private SelectionTracker<Long> tracker;

    public SpeciesFilterAdapter(Context context) {
        setHasStableIds(true);
        this.context = context;
    }

    @NonNull
    @Override
    public SpeciesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SpeciesViewHolder(LayoutInflater.from(context).inflate(R.layout.fish_recyclerview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SpeciesViewHolder holder, int position) {
        holder.speciesText.setText(fish.get(position).species);

        if (tracker.isSelected((long) position)) {
            holder.checkmark.setVisibility(View.VISIBLE);
        } else {
            holder.checkmark.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (fish != null) {
            return fish.size();
        } else {
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setFish(List<Fish> fish) {
        this.fish = fish;
        notifyDataSetChanged();
    }

    public void setTracker(SelectionTracker<Long> tracker) {
        this.tracker = tracker;
    }

    public static class SpeciesViewHolder extends RecyclerView.ViewHolder {
        TextView speciesText;
        ImageView checkmark;

        public SpeciesViewHolder(@NonNull View itemView) {
            super(itemView);
            speciesText = itemView.findViewById(R.id.fish_species_text);
            checkmark = itemView.findViewById(R.id.checkmark);
        }

        public ItemDetailsLookup.ItemDetails<Long> getItemDetails() {
            return new ItemDetailsLookup.ItemDetails<Long>() {
                @Override
                public int getPosition() {
                    return getAdapterPosition();
                }

                @Nullable
                @Override
                public Long getSelectionKey() {
                    return getItemId();
                }
            };
        }
    }
}
