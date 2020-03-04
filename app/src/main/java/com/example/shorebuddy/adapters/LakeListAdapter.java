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
        private Lake mLake;
        private final TextView lakeItemView;
        private OnLakeListener onLakeListener;

        private LakeViewHolder(View itemView, OnLakeListener onLakeListener) {
            super(itemView);
            lakeItemView = itemView.findViewById(R.id.lakeListTextView);
            this.onLakeListener = onLakeListener;
            lakeItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.onLakeListener.onLakeSelected(mLake);
        }
    }

    private final LayoutInflater mInflater;
    private OnLakeListener mOnLakeListener;
    private List<Lake> mLakes;

    public LakeListAdapter(Context context, OnLakeListener onLakeListener) {
        mInflater = LayoutInflater.from(context);
        this.mOnLakeListener = onLakeListener;
    }

    @NotNull
    @Override
    public LakeViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.lake_recyclerview_item, parent, false);
        return new LakeViewHolder(itemView, mOnLakeListener);
    }

    @Override
    public void onBindViewHolder(@NotNull LakeViewHolder holder, int position) {
        if (mLakes != null) {
            Lake current_lake = mLakes.get(position);
            holder.lakeItemView.setText(current_lake.name);
            holder.mLake = current_lake;
        }
    }

    public void setLakes(List<Lake> lakes) {
        mLakes = lakes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mLakes != null) {
            return mLakes.size();
        }
        else return 0;
    }

    public interface OnLakeListener {
        void onLakeSelected(Lake lake);
    }
}
