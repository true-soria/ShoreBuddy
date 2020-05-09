package com.example.shorebuddy.views;

import androidx.lifecycle.ViewModelProvider;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.shorebuddy.adapters.ImageAdapter;
import com.example.shorebuddy.viewmodels.CatchRecordDisplayViewModel;
import com.example.shorebuddy.R;

import java.io.File;
import java.util.ArrayList;

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

        ImageAdapter imageAdapter = new ImageAdapter(getContext(), imagePaths);
        viewPager.setAdapter(imageAdapter);
        // File f = new File(catchRecordDisplayViewModel.photos.getValue().get(0).path);
        //Log.d("tag", "Absolute url of image is from record " + Uri.fromFile(f));
        //recordPhoto.setImageURI(Uri.fromFile(f));
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

}
