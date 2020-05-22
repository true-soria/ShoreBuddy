package com.example.shorebuddy.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.shorebuddy.data.catches.CatchRecord;
import com.example.shorebuddy.data.catches.CatchRecordFilter;
import com.example.shorebuddy.data.catches.CatchRepository;
import com.example.shorebuddy.data.catches.DefaultCatchRepository;
import com.example.shorebuddy.utilities.CustomMutableLiveData;
import com.example.shorebuddy.viewmodels.lake_select.LakeSelectResultViewModel;

import java.util.List;

public class CatchHistoryViewModel
        extends AndroidViewModel
        implements LakeSelectResultViewModel.OnLakeSelected,
        DialogSelectResultViewModel.OnDialogDataSaved
{
    private CatchRepository catchRepository;

    private CustomMutableLiveData<CatchRecordFilter> catchFilter = new CustomMutableLiveData<>(new CatchRecordFilter());
    private LiveData<List<CatchRecord>> filteredRecords =
            Transformations.switchMap(catchFilter, filter -> catchRepository.getCatchRecordsFiltered(filter));

    public CatchHistoryViewModel(@NonNull Application application) {
        super(application);
        catchRepository = new DefaultCatchRepository(application);
    }

    public void resetFilter() {
        clearCatchFilterLake();
        clearCatchFilterFish();
    }

    public LiveData<List<CatchRecord>> getRecords() {
        return filteredRecords;
    }

    public LiveData<CatchRecordFilter> getFilter() {
        return catchFilter;
    }

    public void setCatchFilterLake(String lake) {
        catchFilter.getValue().setLake(lake);
    }

    public void clearCatchFilterLake() {
        catchFilter.getValue().clearLake();
    }

    public void setCatchFilterFish(List<String> fish) {
        catchFilter.getValue().setFish(fish);
    }

    public void clearCatchFilterFish() {
        catchFilter.getValue().clearFish();
    }

    public void deleteRecord(CatchRecord record) {
        catchRepository.deleteCatch(record);
    }

    @Override
    public void onLakeSelected(String lake) {
        setCatchFilterLake(lake);
    }

    @Override
    public void onSelected(List<String> fish) {
        setCatchFilterFish(fish);
    }
}
