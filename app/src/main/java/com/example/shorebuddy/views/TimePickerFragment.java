package com.example.shorebuddy.views;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shorebuddy.viewmodels.DateTimeSelectViewModel;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private DateTimeSelectViewModel dateTimeSelectViewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dateTimeSelectViewModel = new ViewModelProvider(getActivity()).get(DateTimeSelectViewModel.class);
        int hour = dateTimeSelectViewModel.shownHour;
        int minute = dateTimeSelectViewModel.shownMinute;

        return new TimePickerDialog(getContext(), this, hour, minute, false);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        dateTimeSelectViewModel.setHour(hourOfDay);
        dateTimeSelectViewModel.setMinute(minute);
    }
}
