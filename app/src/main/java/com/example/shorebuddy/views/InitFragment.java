package com.example.shorebuddy.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shorebuddy.R;
import com.example.shorebuddy.data.lakes.Lake;
import com.example.shorebuddy.viewmodels.lake_select.LakeSelectResultViewModel;
import com.example.shorebuddy.viewmodels.MainViewModel;

import java.util.Objects;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class InitFragment extends Fragment implements LakeSelectResultViewModel.OnLakeSelected {
    public InitFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LakeSelectResultViewModel resultViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(LakeSelectResultViewModel.class);
        resultViewModel.setLakeSelectedCallback(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_init, container, false);
        NavDirections action = InitFragmentDirections.actionInitFragmentToLakeSelectFragment();
        findNavController(this).navigate(action);
        return rootView;
    }

    @Override
    public void onLakeSelected(String lake) {
        MainViewModel mainViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        mainViewModel.setCurrentSelectedLake(lake);
    }
}
