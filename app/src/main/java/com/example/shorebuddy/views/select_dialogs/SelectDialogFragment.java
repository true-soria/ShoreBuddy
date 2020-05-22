package com.example.shorebuddy.views.select_dialogs;

import android.app.Dialog;
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

import com.example.shorebuddy.adapters.BasicFilterAdapter;
import com.example.shorebuddy.databinding.FragmentSelectDialogBinding;
import com.example.shorebuddy.utilities.BasicDetailsLookup;
import com.example.shorebuddy.utilities.BasicItemKeyProvider;
import com.example.shorebuddy.viewmodels.DialogSelectResultViewModel;
import com.example.shorebuddy.viewmodels.DialogSelectViewModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class SelectDialogFragment extends DialogFragment {
    DialogSelectResultViewModel dialogSelectResultViewModel;
    DialogSelectViewModel dialogSelectViewModel;
    FragmentSelectDialogBinding binding;
    private SelectionTracker<Long> tracker;
    protected List<String> data;
    private final Long ghostKey = -5L;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogSelectResultViewModel = new ViewModelProvider(getActivity()).get(DialogSelectResultViewModel.class);
        dialogSelectViewModel = new ViewModelProvider(getActivity()).get(DialogSelectViewModel.class);
        setCancelable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectDialogBinding.inflate(inflater, container, false);

        setupRecyclerView();
        setupSaveBtn();
        setupCloseBtn();

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        Dialog d = getDialog();
        if (d != null) {
            d.getWindow().setLayout(800, 1200);
        }
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = binding.fishRecycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        BasicFilterAdapter adapter = new BasicFilterAdapter(getActivity());
        setupDataObservation(adapter);
        recyclerView.setAdapter(adapter);
        tracker = new SelectionTracker.Builder<>("selection-1",
                recyclerView,
                new BasicItemKeyProvider(recyclerView),
                new BasicDetailsLookup(recyclerView),
                StorageStrategy.createLongStorage())
                .withSelectionPredicate(SelectionPredicates.createSelectAnything()).build();
        tracker.select(ghostKey);
        adapter.setTracker(tracker);
    }

    abstract void setupDataObservation(BasicFilterAdapter adapter);

    private void setupSaveBtn() {
        Button saveBtn = binding.saveButton;
        saveBtn.setOnClickListener((v) -> {
            ArrayList<String> fish = new ArrayList<>();
            Selection<Long> selection = tracker.getSelection();
            for (Iterator<Long> it = selection.iterator(); it.hasNext(); ) {
                Long item = it.next();
                if (!item.equals(ghostKey)) {
                    int position = item.intValue();
                    fish.add(this.data.get(position));
                }
            }
            if (fish.size() > 0) {
                setResult(fish);
            }
            dismiss();
        });
    }

    abstract void setResult(List<String> data);

    private void setupCloseBtn() {
        Button closeBtn = binding.closeBtn;
        closeBtn.setOnClickListener((v) -> dismiss());
    }
}
