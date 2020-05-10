package com.example.shorebuddy.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.viewpager.widget.ViewPager;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shorebuddy.R;
import com.example.shorebuddy.adapters.ImageAdapter;
import com.example.shorebuddy.data.fish.Fish;
import com.example.shorebuddy.databinding.FragmentCatchEntryBinding;
import com.example.shorebuddy.viewmodels.CatchEntryViewModel;
import com.example.shorebuddy.viewmodels.DateTimeSelectViewModel;
import com.example.shorebuddy.viewmodels.LakeSelect.LakeSelectResultViewModel;
import com.example.shorebuddy.viewmodels.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class CatchEntryFragment extends Fragment implements CatchEntryViewModel.requestPersist {
    private CatchEntryViewModel catchEntryViewModel;
    private DateTimeSelectViewModel dateTimeSelectViewModel;
    private FragmentCatchEntryBinding binding;
    private ImageView pictureTaken;
    private static final int CAMERA_REQUEST_CODE = 102;
    private String currentPhotoPath;
    private ArrayList<String> imagePaths = new ArrayList<>();

    public CatchEntryFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        catchEntryViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(CatchEntryViewModel.class);
        catchEntryViewModel.setRequestPersist(this);
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
        setupCameraBtn();
        FloatingActionButton saveBtn = binding.saveButton;
        saveBtn.setOnClickListener(v -> onSaveBtnPressed());
        catchEntryViewModel.getModeIcon().observe(getViewLifecycleOwner(), icon ->
                saveBtn.setImageDrawable(getResources().getDrawable(icon, null)));

        catchEntryViewModel.getWeight().observe(getViewLifecycleOwner(), weight -> binding.weightInput.setText(weight));
        catchEntryViewModel.getLength().observe(getViewLifecycleOwner(), length -> binding.lengthInput.setText(length));
        catchEntryViewModel.getComments().observe(getViewLifecycleOwner(), comments -> binding.commentsInput.setText(comments));

        binding.weightInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                catchEntryViewModel.setWeight(binding.weightInput.getText().toString());
            }
        });
        binding.lengthInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                catchEntryViewModel.setLength(binding.lengthInput.getText().toString());
            }
        });
        binding.commentsInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                catchEntryViewModel.setComments(binding.commentsInput.getText().toString());
            }
        });

        pictureTaken = binding.imageTaken;
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
            NavDirections action = CatchEntryFragmentDirections.actionCatchEntryFragmentToDatePickerFragment();
            findNavController(this).navigate(action);
        });
        Button timeButton = binding.timeBtn;
        catchEntryViewModel.getCatchRecordTime().observe(getViewLifecycleOwner(), timeButton::setText);
        timeButton.setOnClickListener(v -> {
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

    private void setupCameraBtn(){
        Button cameraButton = binding.cameraButton;
        cameraButton.setOnClickListener(v -> takePicture());
    }

    void takePicture(){dispatchTakePictureIntent();}

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            File f = new File(currentPhotoPath);
            Log.d("tag", "Absolute url of image is catch entry " + currentPhotoPath);
            if (currentPhotoPath != null) {
                imagePaths.add(currentPhotoPath);
                Toast.makeText(getContext(), "Picture has been added", Toast.LENGTH_LONG).show();
            }

        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(), "com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    private void onSaveBtnPressed() {
        if (binding.fishSpeciesSpinner.getSelectedItemPosition() != 0) {
            catchEntryViewModel.addPhoto(imagePaths);
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
        spinner.setAdapter(speciesAdapter);

        MediatorLiveData<Integer> spinnerMediator = new MediatorLiveData<>();
        spinnerMediator.setValue(0);
        spinnerMediator.addSource(catchEntryViewModel.getAllFish(), species -> {
            speciesAdapter.clear();
            speciesAdapter.add("Select Fish Species");
            for (Fish currentFish : species) {
                speciesAdapter.add(currentFish.species);
                speciesAdapter.notifyDataSetChanged();
            }
            int position = speciesAdapter.getPosition(catchEntryViewModel.getFish().getValue());
            spinnerMediator.setValue(position);
        });
        spinnerMediator.addSource(catchEntryViewModel.getFish(), selectedFish -> {
            if (selectedFish != null) {
                int position = speciesAdapter.getPosition(selectedFish);
                spinnerMediator.setValue(position);
            }
        });
        spinnerMediator.observe(getViewLifecycleOwner(), spinner::setSelection);

        spinner.setOnItemSelectedListener(catchEntryViewModel);
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

    @Override
    public void persist() {
        persistTextEntries();
    }
}
