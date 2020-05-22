package com.example.shorebuddy.views;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
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
import android.widget.TextView;

import com.example.shorebuddy.adapters.LakeListAdapter;
import com.example.shorebuddy.data.lakes.Lake;
import com.example.shorebuddy.databinding.LakeSelectFragmentBinding;
import com.example.shorebuddy.viewmodels.DialogSelectResultViewModel;
import com.example.shorebuddy.viewmodels.lake_select.LakeSelectResultViewModel;
import com.example.shorebuddy.viewmodels.lake_select.LakeSelectViewModel;

import java.util.Objects;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class LakeSelectFragment extends Fragment implements LakeListAdapter.OnLakeListener, LakeFilterView.OnLakeFilterChanged {

    private LakeSelectViewModel lakeViewModel;
    private LakeSelectResultViewModel resultViewModel;
    private LakeSelectFragmentBinding binding;
    private boolean popBackStack;
    private boolean returnToEntry;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lakeViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(LakeSelectViewModel.class);
        resultViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(LakeSelectResultViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = LakeSelectFragmentBinding.inflate(inflater, container, false);

        LakeSelectFragmentArgs args = LakeSelectFragmentArgs.fromBundle(getArguments());
        popBackStack = args.getPopNavigation();
        returnToEntry = args.getRequestFromEntryFrag();

        setupLakeRecycleView();
        setupLakeSearchView();
        setupFishFilter();
        setupCountyFilter();
        setupFilterText();

        return binding.getRoot();
    }

    private void setupFishFilter() {
        CardView fishCardView = binding.speciesFilterView;
        fishCardView.setOnClickListener((v) -> {
            DialogSelectResultViewModel dialogSelectResultViewModel = new ViewModelProvider(getActivity()).get(DialogSelectResultViewModel.class);
            dialogSelectResultViewModel.setSelectedCallback((data -> lakeViewModel.setFilterSpecies(data)));

            NavDirections action = LakeSelectFragmentDirections.actionLakeSelectFragmentToSelectDialogFragment();
            findNavController(this).navigate(action);
        });

        ImageButton fishClearBtn = binding.speciesFilterClearButton;
        fishClearBtn.setOnClickListener((v) -> {
            lakeViewModel.setFilterSpecies(null);
            fishClearBtn.setVisibility(View.GONE);
        });
    }

    private void setupCountyFilter() {
        CardView fishCardView = binding.countyFilterView;
        fishCardView.setOnClickListener((v) -> {
            DialogSelectResultViewModel dialogSelectResultViewModel = new ViewModelProvider(getActivity()).get(DialogSelectResultViewModel.class);
            dialogSelectResultViewModel.setSelectedCallback((data -> lakeViewModel.setFilterCounty(data)));

            NavDirections action = LakeSelectFragmentDirections.actionLakeSelectFragmentToCountySelectDialogFragment();
            findNavController(this).navigate(action);
        });

        ImageButton countyFilterClearButton = binding.countyFilterClearButton;
        countyFilterClearButton.setOnClickListener((v) -> {
            lakeViewModel.setFilterCounty(null);
            countyFilterClearButton.setVisibility(View.GONE);
        });
    }

    private void setupFilterText() {
        ImageButton countyClearBtn = binding.countyFilterClearButton;
        ImageButton fishClearBtn = binding.speciesFilterClearButton;
        TextView countyText = binding.countyFilterText;
        TextView speciesFilterText = binding.speciesFilterText;
        lakeViewModel.getLakeFilter().observe(getViewLifecycleOwner(), filter -> {
            if (filter.county != null) {
                String s = String.join(", ", filter.county);
                countyText.setText(s);
                countyClearBtn.setVisibility(View.VISIBLE);
            } else {
                countyText.setText("All Counties");
            }

            if (filter.fish != null) {
                String s = String.join(", ", filter.fish);
                speciesFilterText.setText(s);
                fishClearBtn.setVisibility(View.VISIBLE);
            } else {
                speciesFilterText.setText("All Species");
            }
        });
    }

    private void setupLakeRecycleView() {
        Activity activity = getActivity();
        assert activity != null;

        RecyclerView lakesRecyclerView = binding.lakesRecyclerView;
        final LakeListAdapter lakesAdapter = new LakeListAdapter(activity, this);
        lakesRecyclerView.setAdapter(lakesAdapter);
        lakesRecyclerView.setLayoutManager(new LinearLayoutManager(activity));

        lakeViewModel.setSearchQuery("");
        lakeViewModel.getFilteredLakes().observe(getViewLifecycleOwner(), lakesAdapter::setLakes);
    }

    private void setupLakeSearchView() {
        SearchView lakesSearchView = binding.lakesSearchView;
        lakesSearchView.setOnCloseListener(() -> {
            lakeViewModel.setSearchQuery("");
            return false;
        });

        lakesSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String selectedLake = lakeViewModel.getLakeFromFilteredPosition(0);
                onLakeSelected(selectedLake);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newQuery) {
                lakeViewModel.setSearchQuery(newQuery);
                return false;
            }
        });
    }

    @Override
    public void onLakeSelected(String lakeName) {
        resultViewModel.setResultLake(lakeName);
        lakeViewModel.setFilterCounty(null);
        closeKeyboard();
        NavDirections action;
        if (returnToEntry) {
            action = LakeSelectFragmentDirections.actionLakeSelectFragmentToCatchEntryFragment();
            findNavController(this).navigate(action);
        } else if (popBackStack) {
            findNavController(this).popBackStack();
        } else {
            action = LakeSelectFragmentDirections.actionLakeSelectFragmentToHomepageView();
            findNavController(this).navigate(action);
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
            //lakeViewModel.setFilterCounty(county);
    }
}
