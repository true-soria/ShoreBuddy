package com.example.shorebuddy;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.shorebuddy.viewmodels.MainViewModel;

import java.util.Objects;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);

        TextView currentLakeTextView = rootView.findViewById(R.id.current_lake_text);
        mViewModel.getCurrentlySelectedLake().observe(getViewLifecycleOwner(), lake -> {
            currentLakeTextView.setText(String.format("%s%s", getString(R.string.current_lake_text), lake.name));
        });
        Button selectLakeBtn = rootView.findViewById(R.id.select_lake_btn);
        selectLakeBtn.setOnClickListener(this::onClickSelectLakeBtn);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void onClickSelectLakeBtn(View v) {
        NavDirections action = MainFragmentDirections.actionMainFragmentToLakeSelectFragment();
        findNavController(this).navigate(action);
    }

}
