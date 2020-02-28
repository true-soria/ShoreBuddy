package com.example.shorebuddy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shorebuddy.R;
import com.example.shorebuddy.data.Lake;

import java.util.List;

public class LakeListAdapter extends RecyclerView.Adapter<LakeListAdapter.LakeViewHolder> {
    public class LakeViewHolder extends RecyclerView.ViewHolder {
        private final TextView lakeItemView;

        private LakeViewHolder(View itemView) {
            super(itemView);
            lakeItemView = itemView.findViewById(R.id.lakeListTextView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Lake> mLakes;

    public LakeListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public LakeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.lake_recyclerview_item, parent, false);
        return new LakeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LakeViewHolder holder, int position) {
        if (mLakes != null) {
            Lake current_lake = mLakes.get(position);
            holder.lakeItemView.setText(current_lake.name);
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
}
