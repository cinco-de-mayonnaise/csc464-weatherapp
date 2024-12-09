package com.abdullah.beginner.weatherapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.abdullah.beginner.weatherapp.databinding.FragmentSwipeBinding;
import com.abdullah.beginner.weatherapp.databinding.FragmentSwipechildSummaryBinding;

import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class SummaryFragment__swipechild extends Fragment {

    WeatherForecastSet weather_data; // all the data shown in this fragment comes from this class instance.
    FragmentSwipechildSummaryBinding binding;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSwipechildSummaryBinding.inflate(inflater, container, false);
        // set navcontroller
        //navcontroller = NavHostFragment.findNavController(SummaryFragment__swipechild.this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState == null)
            savedInstanceState = getArguments();

        // get Weather result out of passed bundle
        assert savedInstanceState != null;
        WeatherAPICaller caller = (WeatherAPICaller) savedInstanceState.getSerializable(MainActivity.CONSTANTS.BUNKEY_FRAGMENT_WEATHER);
        assert caller != null;
        weather_data = caller.result;

        if (weather_data != null)
            Log.d(getClass().getName(), "Obtained weather result successfully!");
        else
        {
            // failed to obtain weather data, how are you even here?
            // go to loading fragment as fallback.
            AlertDialog a = new AlertDialog.Builder(requireContext()).create();
            a.setMessage("Unable to load this fragment. This is likely a bug. Try restarting the application!");
            a.setTitle("Loading failed!");
            a.show();

            Log.d(getClass().getName(), "oh nooo faa kee! Bundle get/put methods are not reliable!");
        }

        // load all data into all controls of the fragment
        loadData();

        // programmatically enable marquee if cityname text is too long
        if (isTooLongTextForTextView(binding.textViewCityName, binding.textViewCityName.getText().toString()))
            binding.textViewCityName.setSelected(true);
        else
            binding.textViewCityName.setSelected(false);

        // obtain weather data set by loading fragment
        //weather_data = (WeatherForecastSet) requireActivity().getIntent().getSerializableExtra(MainActivity.BUNKEY_LATEST_WEATHER);
    }

    private double KelvinToCelsius(double Kelvin)
    {
        return Kelvin - 273.15;
    }

    private void loadData()
    {
        if (weather_data == null)
            throw new RuntimeException("Weather data does not exist!");

        long currentTempNow = Math.round(KelvinToCelsius(weather_data.currentWeather().temp()));
        double currentTempNow_double = KelvinToCelsius(weather_data.currentWeather().temp());
        long currentTempNowMin = Math.round(KelvinToCelsius(weather_data.currentWeather().tempMin()));
        long currentTempNowMax = Math.round(KelvinToCelsius(weather_data.currentWeather().tempMax()));
        String timeAccordingToWeather = new Date(weather_data.currentWeather().timestampForecast() * 1000).toString();


        binding.textViewCityName.setText(weather_data.cityName());
        binding.textViewCityTempNow.setText(String.format(Locale.getDefault(), "%d°C", currentTempNow));
        binding.textViewCityTempMinToday.setText(String.format(Locale.getDefault(), "%d°C", currentTempNowMin));
        binding.textViewCityTempMaxToday.setText(String.format(Locale.getDefault(), "%d°C", currentTempNowMax));
        binding.textViewWeatherTime.setText(timeAccordingToWeather);
    }

    private boolean isTooLongTextForTextView(TextView t, String text) {
        float widthOfTheText = t.getPaint().measureText(text);
        return widthOfTheText > t.getWidth();
    }
}
