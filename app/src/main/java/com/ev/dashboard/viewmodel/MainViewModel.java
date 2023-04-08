package com.ev.dashboard.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ev.dashboard.model.EVData;
import com.ev.dashboard.usbmanager.SendUSBData;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel implements SendUSBData {
    private ArrayList<EVData> evDataList = new ArrayList<>();
    private MutableLiveData<List<EVData>> evLiveData = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);

    }

    public LiveData<List<EVData>> getEVLiveData() {
        return evLiveData;
    }

    @Override
    public void onReceivedUSBData(byte[] data) {
        String str = new String(data, StandardCharsets.UTF_8);
        Gson gson = new Gson();
        EVData evData = gson.fromJson(str, EVData.class);
        evDataList.add(evData);
        evLiveData.setValue(evDataList);
    }
}
