package com.example.shorebuddy.views;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.example.shorebuddy.R;
import com.example.shorebuddy.adapters.LakeListAdapter;
import com.example.shorebuddy.data.lakes.Lake;
import com.example.shorebuddy.viewmodels.MainViewModel;

import java.util.Objects;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class LakeSelectFragment extends Fragment implements LakeListAdapter.OnLakeListener, LakeFilterView.OnLakeFilterChanged {

    private MainViewModel mainViewModel;
    private LakeFilterView lakeFilterView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
                NavDirections action = LakeSelectFragmentDirections.actionLakeSelectFragmentToHomepageView();
                findNavController(LakeSelectFragment.this).navigate(action);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newQuery) {
                mainViewModel.setSearchQuery(newQuery);
                return false;
            }
        });
        lakeFilterView = rootView.findViewById(R.id.lakeFilterView);
        mainViewModel.getCounties().observe(getViewLifecycleOwner(), counties -> {
            lakeFilterView.setup(counties, this);
        });

        ImageButton filterButton = rootView.findViewById(R.id.filter_lakes_btn);
        filterButton.setOnClickListener(this::OnLakeFilterPressed);
        return rootView;
    }

    @Override
    public void onLakeSelected(Lake lake) {
        mainViewModel.setCurrentSelectedLake(lake);
        lakeFilterView.setVisibility(View.GONE);
        mainViewModel.setFilterCounty("");
        NavDirections action = LakeSelectFragmentDirections.actionLakeSelectFragmentToHomepageView();
        closeKeyboard();
        findNavController(this).navigate(action);
    }

    void OnLakeFilterPressed(View view) {
         if (lakeFilterView.getVisibility() == View.GONE) {
             lakeFilterView.setVisibility(View.VISIBLE);
             mainViewModel.setFilterCounty(lakeFilterView.getSelected());
         } else {
             lakeFilterView.setVisibility(View.GONE);
             mainViewModel.setFilterCounty("");
         }
    }

    private void closeKeyboard() {
        View view = Objects.requireNonNull(this.getActivity()).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onLakeFilterChanged(String county) {
        if (lakeFilterView.getVisibility() == View.VISIBLE) {
            mainViewModel.setFilterCounty(county);
        }
    }
}
