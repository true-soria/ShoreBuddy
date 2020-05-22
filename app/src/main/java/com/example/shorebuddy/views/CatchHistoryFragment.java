package com.example.shorebuddy.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.shorebuddy.R;
import com.example.shorebuddy.adapters.CatchRecordAdapter;
import com.example.shorebuddy.data.catches.CatchRecord;
import com.example.shorebuddy.databinding.FragmentCatchHistoryBinding;
import com.example.shorebuddy.utilities.SwipeToDeleteCallback;
import com.example.shorebuddy.viewmodels.CatchHistoryViewModel;
import com.example.shorebuddy.viewmodels.DialogSelectResultViewModel;
import com.example.shorebuddy.viewmodels.lake_select.LakeSelectResultViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class CatchHistoryFragment extends Fragment implements CatchRecordAdapter.OnRecordClickedListener {

    private CatchHistoryViewModel catchHistoryViewModel;
    private CatchRecordAdapter catchRecordAdapter;
    private FragmentCatchHistoryBinding binding;

    public CatchHistoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        catchRecordAdapter = new CatchRecordAdapter(getActivity(), this);
        catchHistoryViewModel = new ViewModelProvider(getActivity()).get(CatchHistoryViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCatchHistoryBinding.inflate(inflater, container, false);

        setupLakeFilter();
        setupFishFilter();
        setupFilterText();
        setupRecyclerView();
        setupAddRecordButton();

        return binding.getRoot();
    }

    private void setupLakeFilter() {
        CardView lakeCardView = binding.lakeFilterView;
        lakeCardView.setOnClickListener((v) -> {
            LakeSelectResultViewModel lakeSelectResultViewModel = new ViewModelProvider(getActivity()).get(LakeSelectResultViewModel.class);
            lakeSelectResultViewModel.setLakeSelectedCallback(catchHistoryViewModel);

            NavDirections action = CatchHistoryFragmentDirections.actionCatchRecordsFragmentToLakeSelectFragment();
            findNavController(this).navigate(action);
        });

        ImageButton lakeClearBtn = binding.lakeFilterClearButton;
        lakeClearBtn.setOnClickListener((v) -> {
            catchHistoryViewModel.clearCatchFilterLake();
            lakeClearBtn.setVisibility(View.GONE);
        });
    }

    private void setupFishFilter() {
        CardView fishCardView = binding.speciesFilterView;
        fishCardView.setOnClickListener((v) -> {
            DialogSelectResultViewModel dialogSelectResultViewModel = new ViewModelProvider(getActivity()).get(DialogSelectResultViewModel.class);
            dialogSelectResultViewModel.setSelectedCallback(catchHistoryViewModel);

            NavDirections action = CatchHistoryFragmentDirections.actionCatchRecordsFragmentToSelectDialogFragment();
            findNavController(this).navigate(action);
        });

        ImageButton fishClearBtn = binding.speciesFilterClearButton;
        fishClearBtn.setOnClickListener((v) -> {
            catchHistoryViewModel.clearCatchFilterFish();
            fishClearBtn.setVisibility(View.GONE);
        });
    }

    private void setupFilterText() {
        ImageButton lakeClearBtn = binding.lakeFilterClearButton;
        ImageButton fishClearBtn = binding.speciesFilterClearButton;
        TextView lakeFilterText = binding.lakeFilterText;
        TextView speciesFilterText = binding.speciesFilterText;
        catchHistoryViewModel.getFilter().observe(getViewLifecycleOwner(), filter -> {
            if (filter.getLake() != null) {
                lakeFilterText.setText(filter.getLake());
                lakeClearBtn.setVisibility(View.VISIBLE);
            } else {
                lakeFilterText.setText(R.string.all_lakes);
            }

            if (filter.getFish() != null) {
                String s = String.join(", ", filter.getFish());
                speciesFilterText.setText(s);
                fishClearBtn.setVisibility(View.VISIBLE);
            } else {
                speciesFilterText.setText(R.string.all_species);
            }
        });
    }

    private void setupRecyclerView() {
        RecyclerView recordsView = binding.catchRecordRecyclerView;
        recordsView.setAdapter(catchRecordAdapter);
        recordsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SwipeToDeleteCallback swipeCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                CatchRecordAdapter adapter = (CatchRecordAdapter) recordsView.getAdapter();
                adapter.removeAt(viewHolder.getAdapterPosition());
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
        itemTouchHelper.attachToRecyclerView(recordsView);

        catchHistoryViewModel.getRecords().observe(getViewLifecycleOwner(), catchRecordAdapter::setRecords);
    }

    private void setupAddRecordButton() {
        FloatingActionButton addRecordBtn = binding.addRecordBtn;
        addRecordBtn.setOnClickListener((v) -> {
            NavDirections action = CatchHistoryFragmentDirections.actionCatchRecordsFragmentToCatchEntryFragment();
            findNavController(this).navigate(action);
        });
    }

    @Override
    public void onDeleteButtonClicked(CatchRecord record) {
        catchHistoryViewModel.deleteRecord(record);
    }

    @Override
    public void onRecordClicked(CatchRecord record) {
        NavDirections action = CatchHistoryFragmentDirections.actionCatchRecordsFragmentToCatchRecordDisplayFragment(record.uid);
        findNavController(this).navigate(action);
    }
}
