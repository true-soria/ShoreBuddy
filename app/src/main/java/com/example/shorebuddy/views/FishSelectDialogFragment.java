package com.example.shorebuddy.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.selection.Selection;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shorebuddy.adapters.SpeciesFilterAdapter;
import com.example.shorebuddy.data.fish.Fish;
import com.example.shorebuddy.databinding.FragmentFishSelectDialogBinding;
import com.example.shorebuddy.utilities.SpeciesDetailsLookup;
import com.example.shorebuddy.utilities.SpeciesItemKeyProvider;
import com.example.shorebuddy.viewmodels.fish_select.FishSelectResultViewModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FishSelectDialogFragment extends DialogFragment {

    private FragmentFishSelectDialogBinding binding;

    private FishSelectResultViewModel fishSelectResultViewModel;

    private SelectionTracker<Long> tracker;

    private List<Fish> fish;

    private final Long ghostKey = -5L;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fishSelectResultViewModel = new ViewModelProvider(getActivity()).get(FishSelectResultViewModel.class);
        setCancelable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFishSelectDialogBinding.inflate(inflater, container, false);

        setupRecyclerView();
        setupSaveBtn();
        setupCloseBtn();

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(800, 1200);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = binding.fishRecycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SpeciesFilterAdapter adapter = new SpeciesFilterAdapter(getActivity());
        fishSelectResultViewModel.getFish().observe(getViewLifecycleOwner(), fish -> {
            adapter.setFish(fish);
            this.fish = fish;
        });
        recyclerView.setAdapter(adapter);
        tracker = new SelectionTracker.Builder<>("selection-1",
                recyclerView,
                new SpeciesItemKeyProvider(recyclerView),
                new SpeciesDetailsLookup(recyclerView),
                StorageStrategy.createLongStorage())
                .withSelectionPredicate(SelectionPredicates.createSelectAnything()).build();
        tracker.select(ghostKey);
        adapter.setTracker(tracker);

    }

    private void setupSaveBtn() {
        Button saveBtn = binding.saveButton;
        saveBtn.setOnClickListener((v) -> {
            ArrayList<Fish> fish = new ArrayList<>();
            Selection<Long> selection = tracker.getSelection();
            for (Iterator<Long> it = selection.iterator(); it.hasNext(); ) {
                Long item = it.next();
                if (!item.equals(ghostKey)) {
                    int position = item.intValue();
                    fish.add(this.fish.get(position));
                }
            }
            fishSelectResultViewModel.setResultFish(fish);
            dismiss();
        });
    }

    private void setupCloseBtn() {
        Button closeBtn = binding.closeBtn;
        closeBtn.setOnClickListener((v) -> dismiss());
    }
}
