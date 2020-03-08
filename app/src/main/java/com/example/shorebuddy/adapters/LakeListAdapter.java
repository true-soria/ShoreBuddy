package com.example.shorebuddy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shorebuddy.R;
import com.example.shorebuddy.data.lakes.Lake;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LakeListAdapter extends RecyclerView.Adapter<LakeListAdapter.LakeViewHolder> {
    public static class LakeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Lake lake;
        private final TextView lakeItemView;
        private final OnLakeListener onLakeListener;

        private LakeViewHolder(View itemView, OnLakeListener onLakeListener) {
            super(itemView);
            lakeItemView = itemView.findViewById(R.id.lakeListTextView);
            this.onLakeListener = onLakeListener;
            lakeItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) { onLakeListener.onLakeSelected(lake); }
    }

    private final LayoutInflater inflater;
    private final OnLakeListener onLakeListener;
    private List<Lake> lakes;

    public LakeListAdapter(Context context, OnLakeListener onLakeListener) {
        inflater = LayoutInflater.from(context);
        this.onLakeListener = onLakeListener;
    }

    @NotNull
    @Override
    public LakeViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.lake_recyclerview_item, parent, false);
        return new LakeViewHolder(itemView, onLakeListener);
    }

    @Override
    public void onBindViewHolder(@NotNull LakeViewHolder holder, int position) {
        if (lakes != null) {
            Lake current_lake = lakes.get(position);
            holder.lakeItemView.setText(current_lake.name);
            holder.lake = current_lake;
        }
    }

    public void setLakes(List<Lake> lakes) {
        this.lakes = lakes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (lakes != null) {
            return lakes.size();
        }
        else return 0;
    }

    public interface OnLakeListener {
        void onLakeSelected(Lake lake);
    }
}
