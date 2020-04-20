package com.example.shorebuddy.views;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.shorebuddy.R;
import com.example.shorebuddy.data.fish.Fish;
import com.example.shorebuddy.viewmodels.CatchEntryViewModel;
import com.example.shorebuddy.viewmodels.DateTimeSelectViewModel;
import com.example.shorebuddy.viewmodels.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class CatchEntryFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private CatchEntryViewModel catchEntryViewModel;
    private DateTimeSelectViewModel dateTimeSelectViewModel;

    public CatchEntryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainViewModel mainViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        catchEntryViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(CatchEntryViewModel.class);
        String lake;
        // TODO: REMOVE HIGH PRIORITY, enforce selected lake choice.
        if (mainViewModel.getCurrentlySelectedLake().getValue() == null) {
            lake = "Pinecrest Lake";
        } else {
            lake = mainViewModel.getCurrentlySelectedLake().getValue().lakeName;
        }
        catchEntryViewModel.setLake(lake);
        dateTimeSelectViewModel = new ViewModelProvider(getActivity()).get(DateTimeSelectViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_catch_entry, container, false);
        setupFishSpinner(rootView);
        setupLakeBtn(rootView);
        setupDateTimeBtn(rootView);
        FloatingActionButton saveBtn = rootView.findViewById(R.id.save_button);
        saveBtn.setOnClickListener(v -> onSaveBtnPressed(rootView));

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        catchEntryViewModel.resetCalendar();
    }

    private void setupDateTimeBtn(View rootView) {
        Button dateButton = rootView.findViewById(R.id.date_btn);
        catchEntryViewModel.getCatchRecordDate().observe(getViewLifecycleOwner(), dateButton::setText);
        dateButton.setOnClickListener(v -> {
            NavDirections action = CatchEntryFragmentDirections.actionCatchEntryFragmentToDatePickerFragment();
            findNavController(this).navigate(action);
        });
        Button timeButton = rootView.findViewById(R.id.time_btn);
        catchEntryViewModel.getCatchRecordTime().observe(getViewLifecycleOwner(), timeButton::setText);
        timeButton.setOnClickListener(v -> {
            NavDirections action = CatchEntryFragmentDirections.actionCatchEntryFragmentToTimePickerFragment();
            findNavController(this).navigate(action);
        });
        setupDateTimeBtnObservations();
    }

    private void setupLakeBtn(View rootView) {
        Button button = rootView.findViewById(R.id.caught_lake_btn);
        button.setText(catchEntryViewModel.getLake());
    }

    private void onSaveBtnPressed(View v) {
        EditText weight = v.findViewById(R.id.weight_input);
        EditText length = v.findViewById(R.id.length_input);
        EditText comments = v.findViewById(R.id.comments_input);
        catchEntryViewModel.setWeight(weight.getText().toString());
        catchEntryViewModel.setLength(length.getText().toString());
        catchEntryViewModel.setComments(comments.getText().toString());
        saveCatchRecord();
    }

    private void saveCatchRecord() {
        closeKeyboard();
        catchEntryViewModel.recordCatch();
        NavDirections action = CatchEntryFragmentDirections.actionCatchEntryFragmentToCatchRecordsFragment();
        findNavController(this).navigate(action);

    }

    private void closeKeyboard() {
        View view = Objects.requireNonNull(this.getActivity()).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Fish fish = (Fish) parent.getItemAtPosition(position);
        catchEntryViewModel.setFish(fish.species);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        catchEntryViewModel.setFish(null);
    }

    private void setupFishSpinner(View rootView) {
        Spinner fishSpinner = rootView.findViewById(R.id.fish_species_spinner);
        fishSpinner.setOnItemSelectedListener(this);
        catchEntryViewModel.getAllFish().observe(getViewLifecycleOwner(), (species) -> {
            ArrayAdapter<Fish> speciesAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_item, species);
            fishSpinner.setAdapter(speciesAdapter);
        });
    }

    private void setupDateTimeBtnObservations() {
        dateTimeSelectViewModel.getYear().observe(getViewLifecycleOwner(), catchEntryViewModel::setYear);
        dateTimeSelectViewModel.getMonth().observe(getViewLifecycleOwner(), catchEntryViewModel::setMonth);
        dateTimeSelectViewModel.getDay().observe(getViewLifecycleOwner(), catchEntryViewModel::setDay);
        dateTimeSelectViewModel.getHour().observe(getViewLifecycleOwner(), catchEntryViewModel::setHour);
        dateTimeSelectViewModel.getMinute().observe(getViewLifecycleOwner(), catchEntryViewModel::setMinute);
        catchEntryViewModel.getYear().observe(getViewLifecycleOwner(), year -> dateTimeSelectViewModel.shownYear = year);
        catchEntryViewModel.getMonth().observe(getViewLifecycleOwner(), month -> dateTimeSelectViewModel.shownMonth = month);
        catchEntryViewModel.getDay().observe(getViewLifecycleOwner(), day -> dateTimeSelectViewModel.shownDay = day);
        catchEntryViewModel.getHour().observe(getViewLifecycleOwner(), hour -> dateTimeSelectViewModel.shownHour = hour);
        catchEntryViewModel.getMinute().observe(getViewLifecycleOwner(), minute -> dateTimeSelectViewModel.shownMinute = minute);
    }
}
