package com.example.shorebuddy.views;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.shorebuddy.adapters.ImageAdapter;
import com.example.shorebuddy.data.catches.CatchPhoto;
import com.example.shorebuddy.data.catches.CatchRecord;
import com.example.shorebuddy.data.relationships.CatchRecordWithPhotos;
import com.example.shorebuddy.viewmodels.CatchRecordDisplayViewModel;
import com.example.shorebuddy.R;

import java.util.ArrayList;
import java.util.List;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class CatchRecordDisplayFragment extends Fragment {

    private CatchRecordDisplayViewModel catchRecordDisplayViewModel;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        catchRecordDisplayViewModel = new ViewModelProvider(getActivity()).get(CatchRecordDisplayViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        int recordUid = CatchRecordDisplayFragmentArgs.fromBundle(getArguments()).getRecordUid();
        catchRecordDisplayViewModel.lookupRecord(recordUid).observe(getViewLifecycleOwner(), catchRecordDisplayViewModel::setRecord);

        View rootView = inflater.inflate(R.layout.catch_record_display_fragment, container, false);
        TextView speciesText = rootView.findViewById(R.id.fish_species);
        catchRecordDisplayViewModel.recordSpecies.observe(getViewLifecycleOwner(), speciesText::setText);
        TextView lakeText = rootView.findViewById(R.id.caught_lake);
        catchRecordDisplayViewModel.recordLake.observe(getViewLifecycleOwner(), lakeText::setText);
        TextView dateText = rootView.findViewById(R.id.date);
        catchRecordDisplayViewModel.recordDate.observe(getViewLifecycleOwner(), dateText::setText);
        TextView weightText = rootView.findViewById(R.id.weight);
        catchRecordDisplayViewModel.recordWeight.observe(getViewLifecycleOwner(), weightText::setText);
        TextView lengthText = rootView.findViewById(R.id.length);
        catchRecordDisplayViewModel.recordLength.observe(getViewLifecycleOwner(), lengthText::setText);
        TextView commentsText = rootView.findViewById(R.id.comments);
        catchRecordDisplayViewModel.recordComments.observe(getViewLifecycleOwner(), commentsText::setText);
        Button editButton = rootView.findViewById(R.id.edit_btn);
        editButton.setOnClickListener(this::onEditClick);
        viewPager = rootView.findViewById(R.id.imageSlider);
        catchRecordDisplayViewModel.photos.observe(getViewLifecycleOwner(), this::populateImagePaths);
        ImageAdapter imageAdapter = new ImageAdapter(getContext(), imagePaths);
        viewPager.setAdapter(imageAdapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void onEditClick(View v) {
        CatchRecordDisplayFragmentDirections.ActionCatchRecordDisplayFragmentToCatchEntryFragment action = CatchRecordDisplayFragmentDirections.actionCatchRecordDisplayFragmentToCatchEntryFragment();
        action.setRecordUid(catchRecordDisplayViewModel.getRecordUid());
        findNavController(this).navigate(action);
    }

    public void populateImagePaths(List<CatchPhoto> listOfPhotos){
        for(CatchPhoto catchPhoto : listOfPhotos){
            imagePaths.add(catchPhoto.path);
        }
    }

    private void onChanged(CatchRecordWithPhotos record) {
        catchRecordDisplayViewModel.setRecord(record);
    }
}
