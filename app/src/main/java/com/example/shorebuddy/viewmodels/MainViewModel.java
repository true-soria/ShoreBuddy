package com.example.shorebuddy.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.example.shorebuddy.R;
import com.example.shorebuddy.data.catches.CatchRecord;
import com.example.shorebuddy.data.catches.CatchRepository;
import com.example.shorebuddy.data.catches.DefaultCatchRepository;
import com.example.shorebuddy.data.fish.DefaultFishRepository;
import com.example.shorebuddy.data.fish.Fish;
import com.example.shorebuddy.data.fish.FishRepository;
import com.example.shorebuddy.data.lakes.DefaultLakeRepository;
import com.example.shorebuddy.data.lakes.Lake;
import com.example.shorebuddy.data.lakes.LakeRepository;
import com.example.shorebuddy.data.solunar.Solunar;
import com.example.shorebuddy.data.solunar.DefaultSolunarRepository;
import com.example.shorebuddy.data.weather.DefaultWeatherRepository;
import com.example.shorebuddy.data.weather.Weather;
import com.example.shorebuddy.data.weather.WeatherRepository;
import com.example.shorebuddy.utilities.SearchQuery;
import com.example.shorebuddy.utilities.UpdateManager;

import com.example.shorebuddy.data.solunar.SolunarRepository;

import org.json.JSONException;

import java.util.List;

public class MainViewModel extends AndroidViewModel implements DefaultWeatherRepository.OnAPIErrorHandler, DefaultSolunarRepository.OnSolAPIErrorHandler {

    private final WeatherRepository weatherRepo = new DefaultWeatherRepository(this);

    private final SolunarRepository solunarRepo = new DefaultSolunarRepository(this);
    private LakeRepository lakeRepo;
    private FishRepository fishRepository;
    private CatchRepository catchRepository;
    private final UpdateManager updateStatusManager = new UpdateManager();

    private final MutableLiveData<Lake> currentSelectedLake = new MutableLiveData<>();
    private final LiveData<List<Fish>> fishInCurrentLake = Transformations.switchMap(currentSelectedLake,
            currentLake -> lakeRepo.getFishInLake(currentLake));
    private final MutableLiveData<String> searchStr = new MutableLiveData<>();

    private  LiveData<List<Lake>> allLakes;
    private final LiveData<List<Lake>> filteredLakes;
    private final LiveData<Weather> weatherData = Transformations.switchMap(currentSelectedLake,
            currentLake -> weatherRepo.getWeatherData(currentLake.getLatLng()));
    private final LiveData<Solunar> solunarData = Transformations.switchMap(currentSelectedLake,
            currentLake -> solunarRepo.getSolunarData(currentLake.getLatLng()));

    private final MutableLiveData<Integer> toastData = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.lakeRepo = new DefaultLakeRepository(application);
        this.catchRepository = new DefaultCatchRepository(application);
        this.fishRepository = new DefaultFishRepository(application);
        this.allLakes = lakeRepo.getAllLakes();
        searchStr.setValue("");
        filteredLakes = Transformations.switchMap(searchStr, query -> {
            if (query.isEmpty()) {
                return allLakes;
            } else {
                return lakeRepo.getFilteredLakes(new SearchQuery(query));
            }
        });
    }

    public LiveData<List<Lake>> getAllLakes() {return this.allLakes;}

    public LiveData<Lake> getCurrentlySelectedLake() { return currentSelectedLake; }

    public LiveData<List<Fish>> getFishInCurrentLake() { return fishInCurrentLake; }

    public LiveData<List<Lake>> getFilteredLakes() { return filteredLakes; }

    public LiveData<Weather> getWeatherData() { return weatherData; }

    public LiveData<Solunar> getSolunarData() { return solunarData; }

    public LiveData<Integer> getToastData() { return toastData; }

    public LiveData<Boolean> getUpdatingStatus() { return updateStatusManager.isUpdating(); }

    public void recordCatch(CatchRecord record) { catchRepository.recordCatch(record); }

    public void deleteCatch(CatchRecord record) { catchRepository.deleteCatch(record); }

    public void updateCatch(CatchRecord record) { catchRepository.updateCatch(record); }

    public LiveData<List<CatchRecord>> getAllCatchRecordsDecending() { return catchRepository.getCatchRecordsDescending(); }

    public LiveData<List<Fish>> getAllFish() { return fishRepository.getAllFish(); }

    public void setCurrentSelectedLake(Lake lake) { currentSelectedLake.setValue(lake); }

    public void setCurrentSelectedLakeFromFilteredPosition(int position) {
        assert filteredLakes.getValue() != null;
        currentSelectedLake.setValue(filteredLakes.getValue().get(position));
    }

    public void setSearchQuery(String query) { searchStr.setValue(query); }

    public void requestWeatherUpdate() {
        updateStatusManager.weatherUpdateStarted();
        weatherRepo.updateWeatherData();
    }

    public void requestSolunarUpdate() {
        updateStatusManager.solunarUpdateStarted();
        solunarRepo.updateSolunarData();
    }

    public void weatherUpdated() { updateStatusManager.weatherUpdateFinished(); }

    public void solunarUpdated() { updateStatusManager.solunarUpdateFinished(); }

    @Override
    public void onApiError(Exception e) {
        // TODO proper handling instead of instanceOf
        if (e instanceof JSONException) {
            toastData.setValue(R.string.weather_parse_error);
        } else {
            toastData.setValue(R.string.weather_fetch_error);
        }
    }

    @Override
    public void onSolAPIError(Exception e) {
        if (e instanceof JSONException)
        {
            toastData.setValue(R.string.solunar_parse_error);
        }
        else {
            toastData.setValue(R.string.solunar_fetch_error);
        }
    }
}

