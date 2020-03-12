package com.example.shorebuddy.viewmodels;

import android.app.Application;
import android.content.pm.LauncherApps;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.shorebuddy.R;
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
import java.util.Vector;

public class MainViewModel extends AndroidViewModel implements DefaultWeatherRepository.OnAPIErrorHandler, DefaultSolunarRepository.OnSolAPIErrorHandler {

    //private LakeRepository lakeRepo = new LakeRepoStub();
    private final WeatherRepository weatherRepo = new DefaultWeatherRepository(this);

    private final SolunarRepository solunarRepo = new DefaultSolunarRepository(this);
    private LakeRepository lakeRepo;
    private final UpdateManager updateStatusManager = new UpdateManager();

    private final MutableLiveData<Lake> currentSelectedLake = new MutableLiveData<>();
    private final MutableLiveData<String> searchStr = new MutableLiveData<>();

   private  LiveData<List<Lake>> allLakes;
   private final LiveData<List<Lake>> filteredLakes;

//    //TODO rewrite
//    private LiveData<List<Lake>> filteredLakes = Transformations.switchMap(searchStr, query -> {
//        if (query.isEmpty()) {
//            return allLakes;
//        } else {
//            return lakeRepo.getFilteredLakes(new SearchQuery(query));
//        }
//    });

    private final LiveData<Weather> weatherData = Transformations.switchMap(currentSelectedLake,
            currentLake -> weatherRepo.getWeatherData(currentLake.getLakeLatLng()));
    private final LiveData<Solunar> solunarData = Transformations.switchMap(currentSelectedLake,
            currentLake -> solunarRepo.getSolunarData(currentLake.getLakeLatLng()));

    private final MutableLiveData<Integer> toastData = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.lakeRepo = new DefaultLakeRepository(application);
        this.allLakes = lakeRepo.getAllLakes();

        //TODO rewrite
        filteredLakes = Transformations.switchMap(searchStr, query -> {
            if (query.isEmpty()) {
                return allLakes;
            } else {
                return lakeRepo.getFilteredLakes(new SearchQuery(query));
            }
        });
        searchStr.setValue("");
        currentSelectedLake.setValue(new Lake("Casitas"));
    }

//    public MainViewModel() {
//        searchStr.setValue("");
//        currentSelectedLake.setValue(new Lake("Casitas"));
//    }



    public LiveData<Lake> getCurrentlySelectedLake() { return currentSelectedLake; }

    //TODO rewrite
    public LiveData<List<Lake>> getFilteredLakes() { return filteredLakes; }

    public LiveData<Weather> getWeatherData() { return weatherData; }

    public LiveData<Solunar> getSolunarData() { return solunarData; }

    public LiveData<Integer> getToastData() { return toastData; }

    public LiveData<Boolean> getUpdatingStatus() { return updateStatusManager.isUpdating(); }

    public void setCurrentSelectedLake(Lake lake) { currentSelectedLake.setValue(lake); }


    //TODO REWRITE
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

    private static class LakeRepoStub implements LakeRepository {
        private final Vector<Lake> lakes = new Vector<>();
        private final MutableLiveData<List<Lake>> filteredLakes = new MutableLiveData<>();
        private final MutableLiveData<List<Lake>> allLakes = new MutableLiveData<>();

        LakeRepoStub() {
            Lake casitas = new Lake("Casitas");
            casitas.setLatitude(34.3924);
            casitas.setLongitude(-119.3346);
            Lake pinecrest = new Lake("Pinecrest");
            pinecrest.setLatitude(38.19606115930);
            pinecrest.setLongitude(-119.98234788600);
            lakes.add(casitas);
            lakes.add(pinecrest);
            allLakes.setValue(lakes);
        }

        @Override
        public LiveData<List<Lake>> getAllLakes() {
            return allLakes;
        }

        @Override
        public LiveData<List<Lake>> getFilteredLakes(SearchQuery query) {
            Vector<Lake> vec = new Vector<>();
            for (Lake lake: lakes) {
                if (lake.name.toLowerCase().contains(query.getRawString().toLowerCase())) {
                    vec.add(lake);
                }
            }
            filteredLakes.setValue(vec);
            return filteredLakes;
        }
    }
}

