package com.abdullah.beginner.weatherapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.abdullah.beginner.weatherapp.databinding.ActivityMainBinding;

import java.util.Objects;

public class SettingsFragment extends PreferenceFragmentCompat {

    ActivityMainBinding activityMainBinding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Remove menu button from toolbar when we are in settings
        ((MainActivity) requireActivity()).hideMenuButtonFromToolbar();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }

    @Override
    public void onDestroyView() {
        // Put menu button back into toolbar
        ((MainActivity) requireActivity()).showMenuButtonFromToolbar();
        super.onDestroyView();
    }
}