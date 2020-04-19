package com.example.shorebuddy.views;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shorebuddy.viewmodels.DateTimeSelectViewModel;

import org.jetbrains.annotations.NotNull;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private DateTimeSelectViewModel dateTimeSelectViewModel;

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dateTimeSelectViewModel = new ViewModelProvider(getActivity()).get(DateTimeSelectViewModel.class);
        int year = dateTimeSelectViewModel.shownYear;
        int month = dateTimeSelectViewModel.shownMonth;
        int day = dateTimeSelectViewModel.shownDay;

        return new DatePickerDialog(getContext(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dateTimeSelectViewModel.setYear(year);
        dateTimeSelectViewModel.setMonth(month);
        dateTimeSelectViewModel.setDay(dayOfMonth);
    }
}

