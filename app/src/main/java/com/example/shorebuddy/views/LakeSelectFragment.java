package com.example.shorebuddy.views;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.shorebuddy.R;
import com.example.shorebuddy.adapters.LakeListAdapter;
import com.example.shorebuddy.data.lakes.Lake;
import com.example.shorebuddy.viewmodels.MainViewModel;

import java.util.Objects;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class LakeSelectFragment extends Fragment implements LakeListAdapter.OnLakeListener {

    private MainViewModel mainViewModel;

    private static LakeSelectFragment newInstance() {
        LakeSelectFragment fragment = new LakeSelectFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.lake_select_fragment, container, false);
        Activity activity = getActivity();
        RecyclerView lakesRecyclerView = rootView.findViewById(R.id.lakes_recycler_view);
        final LakeListAdapter lakesAdapter = new LakeListAdapter(activity, this);
        lakesRecyclerView.setAdapter(lakesAdapter);
        lakesRecyclerView.setLayoutManager(new LinearLayoutManager(activity));

        assert activity != null;
        mainViewModel.setSearchQuery("");
        mainViewModel.getFilteredLakes().observe(getViewLifecycleOwner(), lakesAdapter::setLakes);

        SearchView lakesSearchView = rootView.findViewById(R.id.lakes_search_view);
        lakesSearchView.setOnCloseListener(() -> {
            mainViewModel.setSearchQuery("");
            return false;
        });
        lakesSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mainViewModel.setCurrentSelectedLakeFromFilteredPosition(0);
                NavDirections action = LakeSelectFragmentDirections.actionLakeSelectFragmentToMainFragment();
                findNavController(LakeSelectFragment.this).navigate(action);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newQuery) {
                mainViewModel.setSearchQuery(newQuery);
                return false;
            }
        });
        return rootView;
    }

    @Override
    public void onLakeSelected(Lake lake) {
        mainViewModel.setCurrentSelectedLake(lake);
        NavDirections action = LakeSelectFragmentDirections.actionLakeSelectFragmentToMainFragment();
        findNavController(this).navigate(action);
    }
}
