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

import java.util.List;
import java.util.Vector;

public class MainViewModel extends ViewModel implements DefaultWeatherRepository.OnAPIErrorHandler {
    private final LakeRepository mLakeRepo = new LakeRepoStub();
    private final WeatherRepository mWeatherRepo = new DefaultWeatherRepository(this);
    private final SolunarRepository mSolunarRepo = new SolunarRepoStub();

    private final MutableLiveData<Lake> mCurrentSelectedLake = new MutableLiveData<>();
    private final MutableLiveData<String> mSearchStr = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> mUpdateWeatherDataEvent = new MutableLiveData<>();
    private final LiveData<List<Lake>> mAllLakes = mLakeRepo.getAllLakes();
    private final LiveData<List<Lake>> mFilteredLakes = Transformations.switchMap(mSearchStr, query -> {
        if (query.isEmpty()) {
            return mAllLakes;
        } else {
            return mLakeRepo.getFilteredLakes(new SearchQuery(query));
        }
    });
    private final LiveData<Solunar> mSolunar = Transformations.switchMap(mCurrentSelectedLake, currentLake -> mSolunarRepo.getSolunarData(currentLake.location));
    private final MediatorLiveData<Weather> mWeather = new MediatorLiveData<>();
    private final MutableLiveData<Integer> mToast = new MutableLiveData<>();

    public MainViewModel() {
        mSearchStr.setValue("");
        mCurrentSelectedLake.setValue(new Lake("Casitas"));
        LiveData<Weather> mWeatherInternal = Transformations.switchMap(mCurrentSelectedLake, currentLake -> mWeatherRepo.getWeatherData(currentLake.location));
        mWeather.addSource(mWeatherInternal, mWeather::setValue);
        mWeather.addSource(mUpdateWeatherDataEvent, updateEvent -> mWeatherRepo.updateWeatherData());
    }

    public LiveData<Lake> getCurrentlySelectedLake() { return mCurrentSelectedLake; }

    public LiveData<List<Lake>> getFilteredLakes() { return mFilteredLakes; }

    public LiveData<Weather> getWeatherData() { return mWeather; }

    public void requestWeatherUpdate() { mUpdateWeatherDataEvent.setValue(new Event<>(true)); }

    public LiveData<Integer> getToastData() { return mToast; }

    public void setCurrentSelectedLake(Lake lake) { mCurrentSelectedLake.setValue(lake); }

    public void setCurrentSelectedLakeFromFilteredPosition(int position) {
        assert mFilteredLakes.getValue() != null;
        mCurrentSelectedLake.setValue(mFilteredLakes.getValue().get(position));
    }

    public void setSearchQuery(String query) {
        mSearchStr.setValue(query);
    }

    @Override
    public void onApiError(Exception e) {
        mToast.setValue(R.string.weather_error);
    }

    //TODO implement solunar data
    //public LiveData<Solunar> getSolunarData() { return mSolunar; }

    private static class LakeRepoStub implements LakeRepository {
        private final Vector<Lake> mLakes = new Vector<>();
        private final MutableLiveData<List<Lake>> mFilteredLakes = new MutableLiveData<>();
        private final MutableLiveData<List<Lake>> mAllLakes = new MutableLiveData<>();

        LakeRepoStub() {
            Lake casitas = new Lake("Casitas");
            casitas.location = new LatLng(34.3924, 119.3346);
            Lake pinecrest = new Lake("Pinecrest");
            pinecrest.location = new LatLng(38.1999165, 119.9887948);
            mLakes.add(casitas);
            mLakes.add(pinecrest);
            mAllLakes.setValue(mLakes);
        }

        @Override
        public LiveData<List<Lake>> getAllLakes() {
            return mAllLakes;
        }

        @Override
        public LiveData<List<Lake>> getFilteredLakes(SearchQuery query) {
            Vector<Lake> vec = mLakes;
            vec.removeIf(lake -> !lake.name.toLowerCase().contains(query.getRawString().toLowerCase()));
            mFilteredLakes.setValue(vec);
            return mFilteredLakes;
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

