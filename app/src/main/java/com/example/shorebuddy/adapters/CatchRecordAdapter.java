package com.example.shorebuddy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shorebuddy.R;
import com.example.shorebuddy.data.catches.CatchRecord;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CatchRecordAdapter extends RecyclerView.Adapter<CatchRecordAdapter.CatchRecordHolder> {
    private List<CatchRecord> records;
    private final LayoutInflater layoutInflater;
    private final OnDeleteButtonClickedListener deleteCallback;

    public CatchRecordAdapter(Context context, OnDeleteButtonClickedListener listener) {
        layoutInflater = LayoutInflater.from(context);
        deleteCallback = listener;
    }

    @NonNull
    @Override
    public CatchRecordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.catch_record_recyclerview_item, parent, false);
        return new CatchRecordHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CatchRecordHolder holder, int position) {
        if (records != null) {
            CatchRecord current = records.get(position);
            holder.speciesTextView.setText(current.fish);
            holder.lakeTextView.setText(current.lake);

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.US);
            holder.dateTextView.setText(dateFormat.format(current.timeCaught.getTime()));

            holder.deleteButton.setOnClickListener((v -> {
                deleteCallback.onDeleteButtonClicked(current);
            }));
        } else {
            holder.speciesTextView.setText(R.string.error_str);
        }
    }

    @Override
    public int getItemCount() {
        if (records != null) {
            return records.size();
        } else {
            return 0;
        }
    }

    public void setRecords(List<CatchRecord> records) {
        this.records = records;
        notifyDataSetChanged();
    }

    class CatchRecordHolder extends RecyclerView.ViewHolder {
        private final TextView speciesTextView;
        private final TextView lakeTextView;
        private final TextView dateTextView;
        private final ImageButton deleteButton;

        CatchRecordHolder(@NonNull View itemView) {
            super(itemView);
            speciesTextView = itemView.findViewById(R.id.record_species_text);
            lakeTextView = itemView.findViewById(R.id.record_lake_text);
            dateTextView = itemView.findViewById(R.id.record_date_text);
            deleteButton = itemView.findViewById(R.id.delete_record_btn);
        }
    }

    public interface OnDeleteButtonClickedListener {
        void onDeleteButtonClicked(CatchRecord record);
    }
}
