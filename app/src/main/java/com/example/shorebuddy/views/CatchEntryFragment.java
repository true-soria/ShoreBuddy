package com.example.shorebuddy.views;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class CatchEntryFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private CatchEntryViewModel catchEntryViewModel;
    private DateTimeSelectViewModel dateTimeSelectViewModel;

    public CatchEntryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        catchEntryViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(CatchEntryViewModel.class);
        dateTimeSelectViewModel = new ViewModelProvider(getActivity()).get(DateTimeSelectViewModel.class);
        setCurrentlySelectedLake();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_catch_entry, container, false);
        setupLakeBtn(rootView);
        setupDateTimeBtn(rootView);
        FloatingActionButton saveBtn = rootView.findViewById(R.id.save_button);
        saveBtn.setOnClickListener(v -> onSaveBtnPressed(rootView));
        catchEntryViewModel.getModeIcon().observe(getViewLifecycleOwner(), icon -> {
            saveBtn.setImageDrawable(getResources().getDrawable(icon, null));
        });

        setupCurrentRecord();
        setupFishSpinner(rootView);

        EditText weightTextBox = rootView.findViewById(R.id.weight_input);
        EditText lengthTextBox = rootView.findViewById(R.id.length_input);
        EditText commentsTextBox = rootView.findViewById(R.id.comments_input);
        catchEntryViewModel.getWeight().observe(getViewLifecycleOwner(), weight -> weightTextBox.setText(weight.toString()));
        catchEntryViewModel.getLength().observe(getViewLifecycleOwner(), length -> lengthTextBox.setText(length.toString()));
        catchEntryViewModel.getComments().observe(getViewLifecycleOwner(), commentsTextBox::setText);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupCurrentRecord();
    }

    private void setupCurrentRecord() {
        catchEntryViewModel.reset();
        int currentRecord = CatchEntryFragmentArgs.fromBundle(getArguments()).getRecordUid();
        if (currentRecord != -1) {
            catchEntryViewModel.findCatchRecord(currentRecord).observe(getViewLifecycleOwner(), record -> {
                catchEntryViewModel.setLake(record.record.lake);
                catchEntryViewModel.editRecord(record);
            });
        } else {
            setCurrentlySelectedLake();
        }
    }

    private void setCurrentlySelectedLake() {
        MainViewModel mainViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        String lake;
        // TODO: REMOVE HIGH PRIORITY, enforce selected lake choice.
        if (mainViewModel.getCurrentlySelectedLake().getValue() == null) {
            lake = "Pinecrest Lake";
        } else {
            lake = mainViewModel.getCurrentlySelectedLake().getValue().lakeName;
        }
        catchEntryViewModel.setLake(lake);
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
        catchEntryViewModel.getLake().observe(getViewLifecycleOwner(), button::setText);
    }

    private void onSaveBtnPressed(View v) {
        EditText weight_input = v.findViewById(R.id.weight_input);
        EditText length_input = v.findViewById(R.id.length_input);
        EditText comments_input = v.findViewById(R.id.comments_input);
        String weight = weight_input.getText().toString();
        String len = length_input.getText().toString();
        String comments = comments_input.getText().toString();
        catchEntryViewModel.setWeight(weight);
        catchEntryViewModel.setLength(len);
        catchEntryViewModel.setComments(comments);
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
        String fish = (String) parent.getItemAtPosition(position);
        catchEntryViewModel.setFish(fish);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        catchEntryViewModel.setFish(null);
    }

    private void setupFishSpinner(View rootView) {
        Spinner fishSpinner = rootView.findViewById(R.id.fish_species_spinner);
        fishSpinner.setOnItemSelectedListener(this);
        List<String> fish = new ArrayList<>();
        ArrayAdapter<String> speciesAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_item, fish);
        catchEntryViewModel.getAllFish().observe(getViewLifecycleOwner(), species -> {
                    speciesAdapter.clear();
                    for (Fish currentFish : species) {
                        speciesAdapter.add(currentFish.species);
                        speciesAdapter.notifyDataSetChanged();
                    }
        });
        fishSpinner.setAdapter(speciesAdapter);
        LiveData<String> currentlySelectedFish = catchEntryViewModel.getFish();
        currentlySelectedFish.observe(getViewLifecycleOwner(), selectedFish -> {
            if (selectedFish != null) {
                int position = speciesAdapter.getPosition(selectedFish);
                fishSpinner.setSelection(position);
            }
        });

    }

    private void setupDateTimeBtnObservations() {
        dateTimeSelectViewModel.getYear().observe(getViewLifecycleOwner(), value -> catchEntryViewModel.updateCalendar(CatchEntryViewModel.CalendarField.YEAR, value));
        dateTimeSelectViewModel.getMonth().observe(getViewLifecycleOwner(), value -> catchEntryViewModel.updateCalendar(CatchEntryViewModel.CalendarField.MONTH, value));
        dateTimeSelectViewModel.getDay().observe(getViewLifecycleOwner(), value -> catchEntryViewModel.updateCalendar(CatchEntryViewModel.CalendarField.DAY, value));
        dateTimeSelectViewModel.getHour().observe(getViewLifecycleOwner(), value -> catchEntryViewModel.updateCalendar(CatchEntryViewModel.CalendarField.HOUR, value));
        dateTimeSelectViewModel.getMinute().observe(getViewLifecycleOwner(), value -> catchEntryViewModel.updateCalendar(CatchEntryViewModel.CalendarField.MINUTE, value));
        catchEntryViewModel.getYear().observe(getViewLifecycleOwner(), year -> dateTimeSelectViewModel.shownYear = year);
        catchEntryViewModel.getMonth().observe(getViewLifecycleOwner(), month -> dateTimeSelectViewModel.shownMonth = month);
        catchEntryViewModel.getDay().observe(getViewLifecycleOwner(), day -> dateTimeSelectViewModel.shownDay = day);
        catchEntryViewModel.getHour().observe(getViewLifecycleOwner(), hour -> dateTimeSelectViewModel.shownHour = hour);
        catchEntryViewModel.getMinute().observe(getViewLifecycleOwner(), minute -> dateTimeSelectViewModel.shownMinute = minute);
    }
}
