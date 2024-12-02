package com.abdullah.beginner.weatherapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SwipeCollectionAdapter extends FragmentStateAdapter {
    public SwipeCollectionAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment f = new SummaryFragment__swipechild();

        return f;
    }

    @Override
    public int getItemCount() {
        return 1; // only one thing for now
    }
}
