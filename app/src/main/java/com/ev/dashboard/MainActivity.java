package com.ev.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.ev.dashboard.model.EVData;
import com.ev.dashboard.viewmodel.MainViewModel;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getEVLiveData().observe(this, new Observer<List<EVData>>() {
            @Override
            public void onChanged(List<EVData> evDataList) {
                Log.i("EV Data from USB", String.valueOf(evDataList));
            }
        });
    }
}