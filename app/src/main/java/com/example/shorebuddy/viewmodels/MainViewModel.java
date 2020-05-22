package com.example.shorebuddy.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.example.shorebuddy.R;
import com.example.shorebuddy.data.fish.Fish;
import com.example.shorebuddy.data.lakes.DefaultLakeRepository;
import com.example.shorebuddy.data.lakes.Lake;
import com.example.shorebuddy.data.lakes.LakeRepository;
import com.example.shorebuddy.data.solunar.Solunar;
import com.example.shorebuddy.data.solunar.DefaultSolunarRepository;
import com.example.shorebuddy.data.weather.DefaultWeatherRepository;
import com.example.shorebuddy.data.weather.Weather;
import com.example.shorebuddy.data.weather.WeatherRepository;
import com.example.shorebuddy.utilities.UpdateManager;

import com.example.shorebuddy.data.solunar.SolunarRepository;
import com.example.shorebuddy.viewmodels.lake_select.LakeSelectResultViewModel;

import org.json.JSONException;

import java.util.List;

public class MainViewModel extends AndroidViewModel implements DefaultWeatherRepository.OnAPIErrorHandler, DefaultSolunarRepository.OnSolAPIErrorHandler, LakeSelectResultViewModel.OnLakeSelected {

    private final WeatherRepository weatherRepo = new DefaultWeatherRepository(this);

    private final SolunarRepository solunarRepo = new DefaultSolunarRepository(this);
    private LakeRepository lakeRepo;
    private final UpdateManager updateStatusManager = new UpdateManager();

    private LiveData<Lake> currentSelectedLake = new MutableLiveData<>();
    private LiveData<List<Fish>> fishInCurrentLake = Transformations.switchMap(currentSelectedLake,
            currentLake -> lakeRepo.getFishInLake(currentLake));

    private LiveData<Weather> weatherData = Transformations.switchMap(currentSelectedLake,
            currentLake -> weatherRepo.getWeatherData(currentLake.getLatLng()));
    private LiveData<Solunar> solunarData = Transformations.switchMap(currentSelectedLake,
            currentLake -> solunarRepo.getSolunarData(currentLake.getLatLng()));

    private final MutableLiveData<Integer> toastData = new MutableLiveData<>();


    public MainViewModel(@NonNull Application application) {
        super(application);
        this.lakeRepo = new DefaultLakeRepository(application);
    }

    public LiveData<Lake> getCurrentlySelectedLake() { return currentSelectedLake; }

    public LiveData<List<Fish>> getFishInCurrentLake() { return fishInCurrentLake; }

    public LiveData<Weather> getWeatherData() { return weatherData; }

    public LiveData<Solunar> getSolunarData() { return solunarData; }

    public LiveData<Integer> getToastData() { return toastData; }

    public LiveData<Boolean> getUpdatingStatus() { return updateStatusManager.isUpdating(); }

    public void setCurrentSelectedLake(String lake) {
        currentSelectedLake = lakeRepo.getLake(lake);
        fishInCurrentLake = Transformations.switchMap(currentSelectedLake,
                currentLake -> lakeRepo.getFishInLake(currentLake));
        weatherData = Transformations.switchMap(currentSelectedLake,
                currentLake -> weatherRepo.getWeatherData(currentLake.getLatLng()));
        Transformations.switchMap(currentSelectedLake,
                currentLake -> solunarRepo.getSolunarData(currentLake.getLatLng()));
    }

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

    @Override
    public void onLakeSelected(String lake) {
        setCurrentSelectedLake(lake);
    }
}

