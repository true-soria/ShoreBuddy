package com.example.shorebuddy.utilities;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

public class BasicDetailsLookup extends ItemDetailsLookup<Long> {

    private RecyclerView recyclerView;

    public BasicDetailsLookup(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Nullable
    @Override
    public ItemDetails<Long> getItemDetails(@NonNull MotionEvent e) {
        View touchedView =recyclerView.findChildViewUnder(e.getX(), e.getY());
        if (touchedView != null) {
            ItemLookupViewHolder<Long> viewHolder = (ItemLookupViewHolder<Long>) recyclerView.getChildViewHolder(touchedView);
            return viewHolder.getItemDetails();
        }
        return null;
    }

    public interface ItemLookupViewHolder<T> {
        ItemDetailsLookup.ItemDetails<T> getItemDetails();
    }
}
