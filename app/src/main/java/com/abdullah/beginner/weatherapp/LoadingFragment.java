package com.abdullah.beginner.weatherapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.abdullah.beginner.weatherapp.databinding.FragmentLoadingBinding;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoadingFragment extends Fragment {
    private FragmentLoadingBinding binding;
    private NavController navcontroller;
    Handler handler;
    ExecutorService es_APIcaller;
    ArrayList<WeatherAPICaller> arr_callers;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // set Handler
        handler = new Handler(Looper.getMainLooper());
        // set binding
        binding = FragmentLoadingBinding.inflate(inflater, container, false);
        // set navcontroller
        navcontroller = NavHostFragment.findNavController(LoadingFragment.this);
        // set the executor service to call apis
        es_APIcaller = Executors.newFixedThreadPool(4); // should not have too many concurrent connections.

        return binding.getRoot();
    }

    private void showLoadingFailedAlert(String cause)
    {
        Runnable r = new Runnable() {
            @Override
            public void run() {   // work here is done in application thread (UI)
                String c = cause;
                if (c == null)
                    c = "";

                AlertDialog a = new AlertDialog.Builder(requireContext()).create();
                a.setCanceledOnTouchOutside(false);
                a.setCancelable(false);
                a.setMessage("Unable to retrieve data from OpenWeatherAPI servers. Are you connected to the Internet?\n\n" + c);
                a.setTitle("Loading failed!");
                a.setButton(DialogInterface.BUTTON_POSITIVE, "Reload", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(getClass().getName(), "Trying reload again...");
                        handler.post(new Runnable() {   // posting instead of directly calling so we dont have a infinite recursion in the call stack (even tho it requires user interaction)
                            @Override
                            public void run() {
                                callTheApi(0);
                            }
                        });
                    }
                });
                a.show();
            }
        };

        if (Looper.myLooper() == Looper.getMainLooper())  // we are on main thread
            r.run();
        else // not on main thread
            handler.post(r);
    }

    private void callTheApi(int i)
    {
        es_APIcaller.submit(new Runnable() {
            @Override
            public void run()
            {  // work here is done in another thread
                WeatherAPICaller wac = arr_callers.get(i);
                Exception exception_cause = null;
                WeatherForecastSet weather_result = null;

                try {
                    weather_result = wac.call();
                } catch (Exception e) {
                    Log.e(getClass().getName(), "Failed to get weather data!: " + e.toString());
                    Log.d(getClass().getName(), Log.getStackTraceString(e));
                    // save this exception for info
                    exception_cause = e;
                }

                if (weather_result != null)
                {
                    final WeatherForecastSet finalWeather_result = weather_result;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {   // work here is done in application thread (UI)
                            // store results in activity
                            Log.i(getClass().getName(), String.format("Put recently collected weather information of %d at in Activity Bundle", finalWeather_result.currentWeather().timestampForecast()));
                            //requireActivity().getIntent().putExtra(MainActivity.BUNKEY_LATEST_WEATHER, finalWeather_result);

                            // clear the navigation stack so we don't go back to loading again!
                            Log.d("LoadingFragment -> SwipeFragment", "Stack was: " + navcontroller.getVisibleEntries().getValue().toString());
                            Log.d("LoadingFragment -> SwipeFragment", "popBack returned " + Boolean.toString(navcontroller.popBackStack(R.id.loadingFragment, true)));
                            navcontroller.navigate(R.id.SwipeFragment);
                            Log.d("LoadingFragment -> SwipeFragment", "Stack is now: " + navcontroller.getVisibleEntries().getValue().toString());
                            // after this we will end up leaving loadingFragment permanently!
                            // is cleanup required here? idk xd
                        }
                    });
                }
                else
                {
                    showLoadingFailedAlert(exception_cause.getMessage());
                    // on the contrary, showLoadingFailedAlert() should not block because we dont wanna hang up any other
                    // internal functions that will be called after onViewCreated() is called.
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.progressBarApistatus.setIndeterminate(true);

        // should we really start the task from here? meh

        // read in arraylist from mainactivity
        arr_callers = (ArrayList<WeatherAPICaller>) requireActivity().getIntent().getSerializableExtra(MainActivity.CONSTANTS.BUNKEY_ARR_LATEST_WEATHERS);
        if (arr_callers == null)
        {
            throw new RuntimeException("This list shouldn't have been empty, was set by MainActivity");
        }

        // if arr_callers is empty, get new weather location
        if (arr_callers.isEmpty())
        {
            arr_callers.add(new WeatherAPICaller(23.8041, 90.4152));
        }

        callTheApi(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        navcontroller = null;
    }

}
