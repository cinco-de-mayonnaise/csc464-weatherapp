package com.abdullah.beginner.weatherapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.abdullah.beginner.weatherapp.databinding.FragmentSwipeBinding;

public class SwipeFragment extends Fragment {

    private FragmentSwipeBinding binding;
    private NavController navcontroller;

    private ViewPager2 viewPager;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // set binding
        binding = FragmentSwipeBinding.inflate(inflater, container, false);
        // set navcontroller
        navcontroller = NavHostFragment.findNavController(SwipeFragment.this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.swiper.setAdapter(new SwipeCollectionAdapter(this));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        navcontroller = null;
    }

}