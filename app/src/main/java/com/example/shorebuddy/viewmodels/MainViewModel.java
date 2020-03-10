package com.example.shorebuddy.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.shorebuddy.R;
import com.example.shorebuddy.data.lakes.Lake;
import com.example.shorebuddy.data.lakes.LakeRepository;
import com.example.shorebuddy.data.solunar.Solunar;
import com.example.shorebuddy.data.solunar.DefaultSolunarRepository;
import com.example.shorebuddy.data.weather.DefaultWeatherRepository;
import com.example.shorebuddy.data.weather.Weather;
import com.example.shorebuddy.data.weather.WeatherRepository;
import com.example.shorebuddy.utilities.Event;
import com.example.shorebuddy.utilities.SearchQuery;
import com.example.shorebuddy.utilities.UpdateManager;
import com.google.android.gms.maps.model.LatLng;

import com.example.shorebuddy.data.solunar.SolunarRepository;

import org.json.JSONException;

import java.util.List;
import java.util.Vector;

public class MainViewModel extends ViewModel implements DefaultWeatherRepository.OnAPIErrorHandler, DefaultSolunarRepository.OnSolAPIErrorHandler {
    private final LakeRepository lakeRepo = new LakeRepoStub();
    private final WeatherRepository weatherRepo = new DefaultWeatherRepository(this);
    private final SolunarRepository solunarRepo = new DefaultSolunarRepository(this);

    private final UpdateManager updateManager = new UpdateManager();

    private final MutableLiveData<Lake> currentSelectedLake = new MutableLiveData<>();
    private final MutableLiveData<String> searchStr = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> updateWeatherEvent = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> updateSolunarEvent = new MutableLiveData<>();

    private final LiveData<List<Lake>> allLakes = lakeRepo.getAllLakes();
    private final LiveData<List<Lake>> filteredLakes = Transformations.switchMap(searchStr, query -> {
        if (query.isEmpty()) {
            return allLakes;
        } else {
            return lakeRepo.getFilteredLakes(new SearchQuery(query));
        }
    });
    private final MediatorLiveData<Weather> weatherData = new MediatorLiveData<>();
    private final MediatorLiveData<Solunar> solunarData = new MediatorLiveData<>();
    private final MutableLiveData<Integer> toastData = new MutableLiveData<>();

    public MainViewModel() {
        searchStr.setValue("");
        currentSelectedLake.setValue(new Lake("Casitas"));

        LiveData<Weather> weatherInternal = Transformations.switchMap(currentSelectedLake,
                currentLake -> weatherRepo.getWeatherData(currentLake.location));
        weatherData.addSource(weatherInternal, weatherData::setValue);
        weatherData.addSource(updateWeatherEvent, updateEvent -> {
            updateManager.weatherUpdateStarted();
            weatherRepo.updateWeatherData();
        });

        LiveData<Solunar> solunarInternal = Transformations.switchMap(currentSelectedLake,
                currentLake -> solunarRepo.getSolunarData(currentLake.location));
        solunarData.addSource(solunarInternal, solunarData::setValue);
        solunarData.addSource(updateSolunarEvent, updateEvent -> {
            updateManager.solunarUpdateStarted();
            solunarRepo.updateSolunarData();
        });
    }

    public LiveData<Lake> getCurrentlySelectedLake() { return currentSelectedLake; }

    public LiveData<List<Lake>> getFilteredLakes() { return filteredLakes; }

    public LiveData<Weather> getWeatherData() { return weatherData; }

    public LiveData<Solunar> getSolunarData() { return solunarData; }

    public LiveData<Integer> getToastData() { return toastData; }

    public LiveData<Boolean> getUpdatingStatus() { return updateManager.isUpdating(); }

    public void setCurrentSelectedLake(Lake lake) { currentSelectedLake.setValue(lake); }

    public void setCurrentSelectedLakeFromFilteredPosition(int position) {
        assert filteredLakes.getValue() != null;
        currentSelectedLake.setValue(filteredLakes.getValue().get(position));
    }

    public void setSearchQuery(String query) { searchStr.setValue(query); }

    public void requestWeatherUpdate() { updateWeatherEvent.setValue(new Event<>(true)); }

    public void requestSolunarUpdate() { updateSolunarEvent.setValue(new Event<>(true)); }

    public void weatherUpdated() { updateManager.weatherUpdateFinished(); }

    public void solunarUpdated() { updateManager.solunarUpdateFinished(); }

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
            casitas.location = new LatLng(34.3924, -119.3346);
            Lake pinecrest = new Lake("Pinecrest");
            pinecrest.location = new LatLng(38.1999165, -119.9887948);
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

