package com.example.shorebuddy.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shorebuddy.R;
import com.example.shorebuddy.adapters.CatchRecordAdapter;
import com.example.shorebuddy.data.catches.CatchRecord;
import com.example.shorebuddy.viewmodels.CatchHistoryViewModel;
import com.example.shorebuddy.viewmodels.CatchRecordDisplayViewModel;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class CatchHistoryFragment extends Fragment implements CatchRecordAdapter.OnRecordClickedListener {

    private CatchHistoryViewModel catchHistoryViewModel;
    private CatchRecordAdapter catchRecordAdapter;

    public CatchHistoryFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        catchRecordAdapter = new CatchRecordAdapter(getActivity(), this);
        catchHistoryViewModel = new ViewModelProvider(getActivity()).get(CatchHistoryViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_catch_history, container, false);
        RecyclerView recordsView = rootView.findViewById(R.id.catch_record_recycler_view);
        recordsView.setAdapter(catchRecordAdapter);
        recordsView.setLayoutManager(new LinearLayoutManager(getActivity()));

        catchHistoryViewModel.getRecords().observe(getViewLifecycleOwner(), catchRecordAdapter::setRecords);
        return rootView;
    }

    @Override
    public void onDeleteButtonClicked(CatchRecord record) {
        catchHistoryViewModel.deleteRecord(record);
    }

    @Override
    public void onRecordClicked(CatchRecord record) {
        CatchRecordDisplayViewModel catchRecordDisplayViewModel = new ViewModelProvider(getActivity()).get(CatchRecordDisplayViewModel.class);
        catchRecordDisplayViewModel.setRecord(record);
        NavDirections action = CatchHistoryFragmentDirections.actionCatchRecordsFragmentToCatchRecordDisplayFragment();
        findNavController(this).navigate(action);
    }
}
