package com.example.shorebuddy.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

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

public class MainViewModel extends ViewModel {
    private LakeRepository mLakeRepo;
    private WeatherRepository mWeatherRepo;
    private SolunarRepository mSolunarRepo;

    private MutableLiveData<Lake> mCurrentSelectedLake = new MutableLiveData<>();
    private MutableLiveData<String> mSearchStr = new MutableLiveData<>();
    private MutableLiveData<Event<Boolean>> mUpdateWeatherDataEvent = new MutableLiveData<>();
    private LiveData<List<Lake>> mAllLakes;
    private final LiveData<List<Lake>> mFilteredLakes = Transformations.switchMap(mSearchStr, query -> {
        if (query.isEmpty()) {
            return mAllLakes;
        } else {
            return mLakeRepo.getFilteredLakes(new SearchQuery(query));
        }
    });
    private final LiveData<Solunar> mSolunar = Transformations.switchMap(mCurrentSelectedLake, currentLake -> mSolunarRepo.getSolunarData(currentLake.getLocation()));
    private MediatorLiveData<Weather> mWeather = new MediatorLiveData<>();

    public MainViewModel() {
        //Stubs
        mLakeRepo = new LakeRepoStub();
        mWeatherRepo = new DefaultWeatherRepository();
        mSolunarRepo = new SolunarRepoStub();

        mSearchStr.setValue("");
        mCurrentSelectedLake.setValue(new Lake("Casitas"));
        mAllLakes = mLakeRepo.getAllLakes();
        LiveData<Weather> mWeatherInternal = Transformations.switchMap(mCurrentSelectedLake, currentLake -> mWeatherRepo.getWeatherData(currentLake.getLocation()));
        mWeather.addSource(mWeatherInternal, value -> mWeather.setValue(value));
        mWeather.addSource(mUpdateWeatherDataEvent, updateEvent -> mWeatherRepo.updateWeatherData());
    }

    public LiveData<Lake> getCurrentlySelectedLake() {
        return mCurrentSelectedLake;
    }

    public LiveData<List<Lake>> getAllLakes() {
        return mAllLakes;
    }

    public LiveData<List<Lake>> getFilteredLakes() {
        return mFilteredLakes;
    }

    public LiveData<Weather> getWeatherData() {
        return mWeather;
    }

    public LiveData<Solunar> getSolunarData() {
        return mSolunar;
    }

    public void setCurrentSelectedLake(Lake lake) {
        mCurrentSelectedLake.setValue(lake);
    }

    public void setCurrentSelectedLakeFromFilteredPosition(int position) {
        assert mFilteredLakes.getValue() != null;
        mCurrentSelectedLake.setValue(mFilteredLakes.getValue().get(position));
    }

    public void setSearchQuery(String query) {
        mSearchStr.setValue(query);
    }

    public void updateWeatherData() {
        mUpdateWeatherDataEvent.setValue(new Event<>(true));
    }

    private static class LakeRepoStub implements LakeRepository {
        private MutableLiveData<List<Lake>> mFilteredLakes = new MutableLiveData<>();
        private MutableLiveData<List<Lake>> mAllLakes = new MutableLiveData<>();

        LakeRepoStub() {
            Vector<Lake> vec = new Vector<>();
            vec.add(new Lake("Casitas"));
            vec.add(new Lake("Pinecrest"));
            mAllLakes.setValue(vec);
        }

        @Override
        public LiveData<List<Lake>> getAllLakes() {
            return mAllLakes;
        }

        @Override
        public LiveData<List<Lake>> getFilteredLakes(SearchQuery query) {
            Vector<Lake> vec = new Vector<>();
            vec.add(new Lake("Casitas"));
            vec.add(new Lake("Pinecrest"));
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


