package com.example.shorebuddy.utilities;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shorebuddy.adapters.SpeciesFilterAdapter;

public class SpeciesDetailsLookup extends ItemDetailsLookup<Long> {

    private RecyclerView recyclerView;

    public SpeciesDetailsLookup(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Nullable
    @Override
    public ItemDetails<Long> getItemDetails(@NonNull MotionEvent e) {
        View touchedView =recyclerView.findChildViewUnder(e.getX(), e.getY());
        if (touchedView != null) {
            SpeciesFilterAdapter.SpeciesViewHolder viewHolder = (SpeciesFilterAdapter.SpeciesViewHolder) recyclerView.getChildViewHolder(touchedView);
            return viewHolder.getItemDetails();
        }
        return null;
    }
}
