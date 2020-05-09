package com.example.shorebuddy.views;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shorebuddy.R;
import com.example.shorebuddy.data.fish.Fish;
import com.example.shorebuddy.databinding.FragmentCatchEntryBinding;
import com.example.shorebuddy.viewmodels.CatchEntryViewModel;
import com.example.shorebuddy.viewmodels.DateTimeSelectViewModel;
import com.example.shorebuddy.viewmodels.LakeSelect.LakeSelectResultViewModel;
import com.example.shorebuddy.viewmodels.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class CatchEntryFragment extends Fragment {
    private CatchEntryViewModel catchEntryViewModel;
    private DateTimeSelectViewModel dateTimeSelectViewModel;
    private FragmentCatchEntryBinding binding;

    public CatchEntryFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        catchEntryViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(CatchEntryViewModel.class);
        dateTimeSelectViewModel = new ViewModelProvider(getActivity()).get(DateTimeSelectViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setupCurrentRecord();
        binding = FragmentCatchEntryBinding.inflate(inflater, container, false);
        setupLakeBtn();
        setupDateTimeBtn();
        setupFishSpinner();

        FloatingActionButton saveBtn = binding.saveButton;
        saveBtn.setOnClickListener(v -> onSaveBtnPressed());
        catchEntryViewModel.getModeIcon().observe(getViewLifecycleOwner(), icon ->
                saveBtn.setImageDrawable(getResources().getDrawable(icon, null)));

        catchEntryViewModel.getWeight().observe(getViewLifecycleOwner(), weight -> binding.weightInput.setText(weight));
        catchEntryViewModel.getLength().observe(getViewLifecycleOwner(), length -> binding.lengthInput.setText(length.toString()));
        catchEntryViewModel.getComments().observe(getViewLifecycleOwner(), comments -> binding.commentsInput.setText(comments));

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.fishSpeciesSpinner.setOnItemSelectedListener(null);
        binding = null;
    }

    private void setupCurrentRecord() {
        int currentRecord = CatchEntryFragmentArgs.fromBundle(getArguments()).getRecordUid();
        if (currentRecord > -1) {
            catchEntryViewModel.findCatchRecord(currentRecord).observe(getViewLifecycleOwner(), record -> {
                catchEntryViewModel.setLake(record.record.lake);
                catchEntryViewModel.editRecord(record);
            });
        } else if (currentRecord != -2) {
                catchEntryViewModel.reset();
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

    private void setupDateTimeBtn() {
        Button dateButton = binding.dateBtn;
        catchEntryViewModel.getCatchRecordDate().observe(getViewLifecycleOwner(), dateButton::setText);
        dateButton.setOnClickListener(v -> {
            persistTextEntries();
            NavDirections action = CatchEntryFragmentDirections.actionCatchEntryFragmentToDatePickerFragment();
            findNavController(this).navigate(action);
        });
        Button timeButton = binding.timeBtn;
        catchEntryViewModel.getCatchRecordTime().observe(getViewLifecycleOwner(), timeButton::setText);
        timeButton.setOnClickListener(v -> {
            persistTextEntries();
            NavDirections action = CatchEntryFragmentDirections.actionCatchEntryFragmentToTimePickerFragment();
            findNavController(this).navigate(action);
        });
        setupDateTimeBtnObservations();
    }

    private void setupLakeBtn() {
        Button button = binding.caughtLakeBtn;
        catchEntryViewModel.getLake().observe(getViewLifecycleOwner(), button::setText);
        button.setOnClickListener(v -> onLakeSelectBtnPressed());

    }

    private void onSaveBtnPressed() {
        if (binding.fishSpeciesSpinner.getSelectedItemPosition() != 0) {
            persistTextEntries();
            saveCatchRecord();
            setupCurrentRecord();
        } else {
            Toast.makeText(getContext(), "Please select a fish.", Toast.LENGTH_LONG).show();
        }
    }

    private void persistTextEntries() {
        catchEntryViewModel.setWeight(binding.weightInput.getText().toString());
        catchEntryViewModel.setLength(binding.lengthInput.getText().toString());
        catchEntryViewModel.setComments(binding.commentsInput.getText().toString());
    }

    private void onLakeSelectBtnPressed() {
        persistTextEntries();
        LakeSelectResultViewModel resultViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(LakeSelectResultViewModel.class);
        resultViewModel.setLakeSelectedCallback(catchEntryViewModel);
        NavDirections action = CatchEntryFragmentDirections.actionCatchEntryFragmentToLakeSelectFragment();
        findNavController(this).navigate(action);
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

    private void setupFishSpinner() {
        Spinner spinner = binding.fishSpeciesSpinner;

        List<String> fish = new ArrayList<>();
        ArrayAdapter<String> speciesAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_item, fish);

        MediatorLiveData<Integer> spinnerMediator = new MediatorLiveData<>();
        spinnerMediator.setValue(0);
        spinnerMediator.addSource(catchEntryViewModel.getAllFish(), species -> {
            speciesAdapter.clear();
            speciesAdapter.add("Select Fish Species");
            for (Fish currentFish : species) {
                speciesAdapter.add(currentFish.species);
                speciesAdapter.notifyDataSetChanged();
            }
            spinner.setSelection(spinnerMediator.getValue());
        });
        spinnerMediator.addSource(catchEntryViewModel.getFish(), selectedFish -> {
            if (selectedFish != null) {
                int position = speciesAdapter.getPosition(selectedFish);
                spinnerMediator.setValue(position);
                spinner.setSelection(position);
            }
        });

        spinner.setAdapter(speciesAdapter);

        spinner.setOnItemSelectedListener(catchEntryViewModel);


        //catchEntryViewModel.getAllFish().observe(getViewLifecycleOwner(), species -> {
            //speciesAdapter.clear();
            //speciesAdapter.add("Select Fish Species");
            //for (Fish currentFish : species) {
                //speciesAdapter.add(currentFish.species);
                //speciesAdapter.notifyDataSetChanged();
            //}
        //});
        //LiveData<String> currentlySelectedFish = catchEntryViewModel.getFish();
        //currentlySelectedFish.observe(getViewLifecycleOwner(), selectedFish -> {
            //int position;
            //if (selectedFish != null && spinner.getAdapter().getCount() > 1) {
                //position = speciesAdapter.getPosition(selectedFish);
            //} else {
                //position = 0;
            //}
            //spinner.setSelection(position);
        //});
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
