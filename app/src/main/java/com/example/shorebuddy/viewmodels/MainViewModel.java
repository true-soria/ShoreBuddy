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
import com.example.shorebuddy.data.weather.DefaultWeatherRepository;
import com.example.shorebuddy.data.weather.Weather;
import com.example.shorebuddy.data.weather.WeatherRepository;
import com.example.shorebuddy.utilities.Event;
import com.example.shorebuddy.utilities.SearchQuery;
import com.google.android.gms.maps.model.LatLng;

import com.example.shorebuddy.data.solunar.SolunarRepository;

import org.json.JSONException;

import java.util.List;
import java.util.Vector;

public class MainViewModel extends ViewModel implements DefaultWeatherRepository.OnAPIErrorHandler {
    private final LakeRepository lakeRepo = new LakeRepoStub();
    private final WeatherRepository weatherRepo = new DefaultWeatherRepository(this);
    private final SolunarRepository solunarRepo = new SolunarRepoStub();

    private final MutableLiveData<Lake> currentSelectedLake = new MutableLiveData<>();
    private final MutableLiveData<String> searchStr = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> updateWeatherDataEvent = new MutableLiveData<>();
    private final LiveData<List<Lake>> allLakes = lakeRepo.getAllLakes();
    private final LiveData<List<Lake>> filteredLakes = Transformations.switchMap(searchStr, query -> {
        if (query.isEmpty()) {
            return allLakes;
        } else {
            return lakeRepo.getFilteredLakes(new SearchQuery(query));
        }
    });
    private final LiveData<Solunar> solunarData = Transformations.switchMap(currentSelectedLake, currentLake -> solunarRepo.getSolunarData(currentLake.getLakeLatLng()));
    private final MediatorLiveData<Weather> weatherData = new MediatorLiveData<>();
    private final MutableLiveData<Integer> toastData = new MutableLiveData<>();

    public MainViewModel() {
        searchStr.setValue("");
        currentSelectedLake.setValue(new Lake("Casitas"));
        LiveData<Weather> weatherInternal = Transformations.switchMap(currentSelectedLake, currentLake -> weatherRepo.getWeatherData(currentLake.getLakeLatLng()));
        weatherData.addSource(weatherInternal, weatherData::setValue);
        weatherData.addSource(updateWeatherDataEvent, updateEvent -> weatherRepo.updateWeatherData());
    }

    public LiveData<Lake> getCurrentlySelectedLake() { return currentSelectedLake; }

    public LiveData<List<Lake>> getFilteredLakes() { return filteredLakes; }

    public LiveData<Weather> getWeatherData() { return weatherData; }

    public void requestWeatherUpdate() { updateWeatherDataEvent.setValue(new Event<>(true)); }

    public LiveData<Integer> getToastData() { return toastData; }

    public void setCurrentSelectedLake(Lake lake) { currentSelectedLake.setValue(lake); }

    public void setCurrentSelectedLakeFromFilteredPosition(int position) {
        assert filteredLakes.getValue() != null;
        currentSelectedLake.setValue(filteredLakes.getValue().get(position));
    }

    public void setSearchQuery(String query) {
        searchStr.setValue(query);
    }

    @Override
    public void onApiError(Exception e) {
        // TODO proper handling instead of instanceOf
        if (e instanceof JSONException) {
            toastData.setValue(R.string.weather_parse_error);
        } else {
            toastData.setValue(R.string.weather_fetch_error);
        }
    }

    //TODO implement solunar data
    //public LiveData<Solunar> getSolunarData() { return mSolunar; }

    private static class LakeRepoStub implements LakeRepository {
        private final Vector<Lake> lakes = new Vector<>();
        private final MutableLiveData<List<Lake>> filteredLakes = new MutableLiveData<>();
        private final MutableLiveData<List<Lake>> allLakes = new MutableLiveData<>();

        LakeRepoStub() {
            Lake casitas = new Lake("Casitas");
            casitas.setLatitude(34.3924);
            casitas.setLongitude(-119.3346);
            //casitas.setLakeCoordinates(34.3924, -119.3346);
            Lake pinecrest = new Lake("Pinecrest");
            //pinecrest.setLakeCoordinates(38.1999165, -119.9887948);
            pinecrest.setLatitude(38.1999165);
            pinecrest.setLongitude(-199.9887948);
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
            Vector<Lake> vec = (Vector<Lake>) lakes.clone();
            vec.removeIf(lake -> !lake.name.toLowerCase().contains(query.getRawString().toLowerCase()));
            filteredLakes.setValue(vec);
            return filteredLakes;
        }
    }

    private static class SolunarRepoStub implements SolunarRepository {
        @Override
        public LiveData<Solunar> getSolunarData(LatLng location) {
            MutableLiveData<Solunar> data = new MutableLiveData<>();
            data.setValue(new Solunar());
            return data;
        }
    }
}

