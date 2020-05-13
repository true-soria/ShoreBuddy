package com.example.shorebuddy.viewmodels.lake_select;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.shorebuddy.data.lakes.DefaultLakeRepository;
import com.example.shorebuddy.data.lakes.Lake;
import com.example.shorebuddy.data.lakes.LakeFilter;
import com.example.shorebuddy.data.lakes.LakeRepository;
import com.example.shorebuddy.utilities.SearchQuery;

import java.util.List;

public class LakeSelectViewModel extends AndroidViewModel {
    private LakeRepository lakeRepo;

    private final LiveData<List<Lake>> filteredLakes;
    private final MutableLiveData<String> searchStr = new MutableLiveData<>();
    private final MutableLiveData<String> filterCounty = new MutableLiveData<>();
    private final MediatorLiveData<LakeFilter> lakeFilter = new MediatorLiveData<>();

    private final LiveData<List<String>> counties;


    public LakeSelectViewModel(@NonNull Application application) {
        super(application);

        lakeRepo = new DefaultLakeRepository(application);
        counties = lakeRepo.getAllCounties();
        lakeFilter.setValue(new LakeFilter());
        lakeFilter.addSource(searchStr, query -> {
            LakeFilter currentFilter = lakeFilter.getValue();
            currentFilter.name = new SearchQuery(query);
            lakeFilter.setValue(currentFilter);
        });
        lakeFilter.addSource(filterCounty, county -> {
            LakeFilter currentFilter = lakeFilter.getValue();
            currentFilter.county = county;
            lakeFilter.setValue(currentFilter);
        });
        searchStr.setValue("");
        filterCounty.setValue("");

        filteredLakes = Transformations.switchMap(lakeFilter, filter -> lakeRepo.getFilteredLakes(filter));
    }

    public LiveData<List<Lake>> getFilteredLakes() { return filteredLakes; }

    public LiveData<List<String>> getCounties() { return counties; }

    public void setSearchQuery(String query) { searchStr.setValue(query); }

    public void setFilterCounty(String county) { filterCounty.setValue(county); }

    public Lake getLakeFromFilteredPosition(int position) {
        assert filteredLakes.getValue() != null;
        return filteredLakes.getValue().get(position);
    }
}
