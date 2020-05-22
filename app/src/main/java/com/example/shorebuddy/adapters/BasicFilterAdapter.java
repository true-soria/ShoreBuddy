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
import com.example.shorebuddy.utilities.BasicDetailsLookup;

import java.util.ArrayList;
import java.util.List;

public class BasicFilterAdapter extends RecyclerView.Adapter<BasicFilterAdapter.SpeciesViewHolder> {
    private List<String> data = new ArrayList<>();
    private Context context;
    private SelectionTracker<Long> tracker;

    public BasicFilterAdapter(Context context) {
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
        holder.textView.setText(data.get(position));

        if (tracker.isSelected((long) position)) {
            holder.checkmark.setVisibility(View.VISIBLE);
        } else {
            holder.checkmark.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setTracker(SelectionTracker<Long> tracker) {
        this.tracker = tracker;
    }

    public static class SpeciesViewHolder extends RecyclerView.ViewHolder implements BasicDetailsLookup.ItemLookupViewHolder<Long> {
        TextView textView;
        ImageView checkmark;

        public SpeciesViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.fish_species_text);
            checkmark = itemView.findViewById(R.id.checkmark);
        }

        @Override
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
