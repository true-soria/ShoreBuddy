package com.example.shorebuddy.utilities;

import androidx.databinding.BaseObservable;
import androidx.databinding.Observable;
import androidx.lifecycle.MutableLiveData;

public class CustomMutableLiveData<T extends BaseObservable> extends MutableLiveData<T> {

    private Observable.OnPropertyChangedCallback callback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            setValue(getValue());
        }
    };

    public CustomMutableLiveData(T value) {
        setValue(value);
    }

    @Override
    public void setValue(T value) {
        super.setValue(value);

        value.addOnPropertyChangedCallback(callback);
    }
}
