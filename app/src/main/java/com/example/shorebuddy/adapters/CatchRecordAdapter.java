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

public class CatchRecordAdapter extends RecyclerView.Adapter<CatchRecordAdapter.CatchRecordHolder>
        implements OnItemClickedListener {
    private List<CatchRecord> records;
    private final LayoutInflater layoutInflater;
    private final OnRecordClickedListener onRecordClickedListener;

    public CatchRecordAdapter(Context context, OnRecordClickedListener listener) {
        layoutInflater = LayoutInflater.from(context);
        onRecordClickedListener = listener;
    }

    @NonNull
    @Override
    public CatchRecordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.catch_record_recyclerview_item, parent, false);
        return new CatchRecordHolder(itemView, this);
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
                onRecordClickedListener.onDeleteButtonClicked(current);
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

    @Override
    public void onItemClicked(int item) {
        onRecordClickedListener.onRecordClicked(records.get(item));
    }

    static class CatchRecordHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView speciesTextView;
        private final TextView lakeTextView;
        private final TextView dateTextView;
        private final ImageButton deleteButton;
        private OnItemClickedListener onItemClickedListener;

        CatchRecordHolder(@NonNull View itemView, OnItemClickedListener listener) {
            super(itemView);
            onItemClickedListener = listener;
            speciesTextView = itemView.findViewById(R.id.record_species_text);
            lakeTextView = itemView.findViewById(R.id.record_lake_text);
            dateTextView = itemView.findViewById(R.id.record_date_text);
            deleteButton = itemView.findViewById(R.id.delete_record_btn);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickedListener.onItemClicked(getAdapterPosition());
        }
    }

    public interface OnRecordClickedListener {
        void onDeleteButtonClicked(CatchRecord record);

        void onRecordClicked(CatchRecord record);
    }

}

interface OnItemClickedListener {
    void onItemClicked(int item);
}
