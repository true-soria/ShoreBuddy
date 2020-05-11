package com.example.shorebuddy.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.shorebuddy.R;

import java.util.List;

/**
 * TODO: document your custom view class.
 */
public class LakeFilterView extends ConstraintLayout implements Spinner.OnItemSelectedListener {
    private Spinner spinner;
    private OnLakeFilterChanged filterChangedCallback;

    public LakeFilterView(Context context) {
        super(context);
        init(null, 0);
    }

    public LakeFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public LakeFilterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        inflate(getContext(), R.layout.lake_filter_view, this);
    }

    public void setup(List<String> counties, OnLakeFilterChanged callback) {
        filterChangedCallback = callback;
        View rootView = getRootView();
        Spinner countySpinner = rootView.findViewById(R.id.countySpinner);
        spinner = countySpinner;
        ArrayAdapter<String> countyAdapter = new ArrayAdapter<String>(getContext(), R.layout.county_spinner_dropdown_item, counties);
        countySpinner.setAdapter(countyAdapter);
        countySpinner.setOnItemSelectedListener(this);
    }

    public String getSelected() {
        return (String) spinner.getSelectedItem();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String county = (String) parent.getItemAtPosition(position);
        filterChangedCallback.onLakeFilterChanged(county);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    public interface OnLakeFilterChanged {
        void onLakeFilterChanged(String county);
    }
}
