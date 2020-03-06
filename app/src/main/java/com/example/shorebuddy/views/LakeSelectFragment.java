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

import com.example.shorebuddy.views.LakeSelectFragmentDirections;
import com.example.shorebuddy.R;
import com.example.shorebuddy.adapters.LakeListAdapter;
import com.example.shorebuddy.data.lakes.Lake;
import com.example.shorebuddy.viewmodels.MainViewModel;

import java.util.Objects;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LakeSelectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LakeSelectFragment extends Fragment implements LakeListAdapter.OnLakeListener {

    private MainViewModel mViewModel;

    public LakeSelectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LakeSelectFragment.
     */
    // TODO: Rename and change types and number of parameters
    private static LakeSelectFragment newInstance() {
        LakeSelectFragment fragment = new LakeSelectFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_lake_select, container, false);
        Activity activity = getActivity();
        RecyclerView lakesRecyclerView = rootView.findViewById(R.id.lakes_recycler_view);
        final LakeListAdapter lakesAdapter = new LakeListAdapter(activity, this);
        lakesRecyclerView.setAdapter(lakesAdapter);
        lakesRecyclerView.setLayoutManager(new LinearLayoutManager(activity));

        assert activity != null;
        mViewModel.setSearchQuery("");
        mViewModel.getFilteredLakes().observe(getViewLifecycleOwner(), lakesAdapter::setLakes);

        SearchView lakesSearchView = rootView.findViewById(R.id.lakes_search_view);
        lakesSearchView.setOnCloseListener(() -> {
            mViewModel.setSearchQuery("");
            return false;
        });
        lakesSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mViewModel.setCurrentSelectedLakeFromFilteredPosition(0);
                NavDirections action = LakeSelectFragmentDirections.actionLakeSelectFragmentToMainFragment();
                findNavController(LakeSelectFragment.this).navigate(action);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newQuery) {
                mViewModel.setSearchQuery(newQuery);
                return false;
            }
        });
        return rootView;
    }

    @Override
    public void onLakeSelected(Lake lake) {
        mViewModel.setCurrentSelectedLake(lake);
        NavDirections action = LakeSelectFragmentDirections.actionLakeSelectFragmentToMainFragment();
        findNavController(this).navigate(action);
    }
}
