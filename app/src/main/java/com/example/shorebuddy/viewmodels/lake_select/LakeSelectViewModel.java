package com.example.shorebuddy.viewmodels.lake_select;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.shorebuddy.data.fish.Fish;
import com.example.shorebuddy.data.lakes.DefaultLakeRepository;
import com.example.shorebuddy.data.lakes.Lake;
import com.example.shorebuddy.data.lakes.LakeFilter;
import com.example.shorebuddy.data.lakes.LakeRepository;
import com.example.shorebuddy.utilities.SearchQuery;

import java.util.List;

public class LakeSelectViewModel extends AndroidViewModel {
    private LakeRepository lakeRepo;

    private final LiveData<List<String>> filteredLakes;
    private final MutableLiveData<String> searchStr = new MutableLiveData<>();
    private final MutableLiveData<List<String>> filterCounties = new MutableLiveData<>();
    private final MutableLiveData<List<String>> filterSpecies = new MutableLiveData<>();
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
        lakeFilter.addSource(filterCounties, county -> {
            LakeFilter currentFilter = lakeFilter.getValue();
            currentFilter.county = county;
            lakeFilter.setValue(currentFilter);
        });
        lakeFilter.addSource(filterSpecies, species -> {
            LakeFilter currentFilter = lakeFilter.getValue();
            currentFilter.fish = species;
            lakeFilter.setValue(currentFilter);
        });
        searchStr.setValue("");
        filterCounties.setValue(null);
        filterSpecies.setValue(null);

        filteredLakes = Transformations.switchMap(lakeFilter, filter -> lakeRepo.getFilteredLakes(filter));
    }

    public LiveData<List<String>> getFilteredLakes() { return filteredLakes; }

    public LiveData<LakeFilter> getLakeFilter() {
        return lakeFilter;
    }

    public void setSearchQuery(String query) { searchStr.setValue(query); }

    public void setFilterCounty(List<String> county) { filterCounties.setValue(county); }

    public void setFilterSpecies(List<String> fish) { filterSpecies.setValue(fish); }

    public String getLakeFromFilteredPosition(int position) {
        assert filteredLakes.getValue() != null;
        return filteredLakes.getValue().get(position);
    }
}
