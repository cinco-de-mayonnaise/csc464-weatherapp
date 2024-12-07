package com.abdullah.beginner.weatherapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.abdullah.beginner.weatherapp.databinding.FragmentLoadingBinding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoadingFragment extends Fragment {
    private FragmentLoadingBinding binding;
    private NavController navcontroller;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // set binding
        binding = FragmentLoadingBinding.inflate(inflater, container, false);
        // set navcontroller
        navcontroller = NavHostFragment.findNavController(LoadingFragment.this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.progressBarApistatus.setIndeterminate(true);
        // should we really start the task from here? meh
        ExecutorService es_APIcaller = Executors.newSingleThreadExecutor();
        Handler APIcaller_handler = new Handler(Looper.getMainLooper());

        es_APIcaller.submit(new Runnable() {
            @Override
            public void run() {  // work here is done in another thread

                // do work here (calling the API)
//                try {
//                    Thread.sleep(7000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
                WeatherAPICaller wac = new WeatherAPICaller(23.8041, 90.4152);
                try {
                    wac.call();
                } catch (Exception e) {
                    Log.e(getClass().getName(), "Failed to get weather data!: " + e.toString());
                    Log.d(getClass().getName(), Log.getStackTraceString(e));
                }

                APIcaller_handler.post(new Runnable() {
                    @Override
                    public void run() {   // work here is done in application thread (UI)

                        // the navigation stack so we don't go back to loading again!
                        Log.d("LoadingFragment -> SwipeFragment", "Stack: " + navcontroller.getVisibleEntries().getValue().toString());
                        Log.d("LoadingFragment -> SwipeFragment", "popBack returned " + Boolean.toString(navcontroller.popBackStack(R.id.loadingFragment, true)));
                        navcontroller.navigate(R.id.SwipeFragment);
                        Log.d("LoadingFragment -> SwipeFragment", "Stack: " + navcontroller.getVisibleEntries().getValue().toString());
                    }
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        navcontroller = null;
    }

}
