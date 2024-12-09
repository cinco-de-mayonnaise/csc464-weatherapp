package com.abdullah.beginner.weatherapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class SwipeCollectionAdapter extends FragmentStateAdapter {

    MainActivity activity;
    private ArrayList<WeatherAPICaller> getAPICallers()
    {
        return (ArrayList<WeatherAPICaller>) activity.getIntent().getSerializableExtra(MainActivity.CONSTANTS.BUNKEY_ARR_LATEST_WEATHERS);
    }

    public SwipeCollectionAdapter(@NonNull Fragment fragment) {
        super(fragment);
        this.activity = (MainActivity) fragment.getActivity();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment f = new SummaryFragment__swipechild();
        f.setArguments(new Bundle());
        ArrayList<WeatherAPICaller> callers = getAPICallers();
        f.getArguments().putSerializable(MainActivity.CONSTANTS.BUNKEY_FRAGMENT_WEATHER, callers.get(position));
        return f;
    }

    @Override
    public int getItemCount() {
        return getAPICallers().size(); // only one thing for now
    }
}
