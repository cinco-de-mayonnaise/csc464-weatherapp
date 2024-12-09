package com.abdullah.beginner.weatherapp;

import static com.abdullah.beginner.weatherapp.MainActivity.CONSTANTS.BUNKEY_ARR_LATEST_WEATHERS;
import static com.abdullah.beginner.weatherapp.MainActivity.CONSTANTS.BUNKEY_WEATHER_COORDS;
import static com.abdullah.beginner.weatherapp.MainActivity.CONSTANTS.PREFERENCE_FILE_KEY;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.abdullah.beginner.weatherapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // ee koto kangaeta!
    public static class CONSTANTS
    {
        public static String BUNKEY_ARR_LATEST_WEATHERS = "latest_weather_result";
        public static String BUNKEY_FRAGMENT_WEATHER = "weather result for each fragment";
        public static String PREFERENCE_FILE_KEY = "com.abdullah.beginner.weatherapp.__preferences_file.dat";
        public static String BUNKEY_WEATHER_COORDS = "list of coordinates for which to collect weather data of";
    }

    SharedPreferences preferences;

    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get api callers out of savedInstanceState if they exist
        ArrayList<WeatherAPICaller> callers;
        if (savedInstanceState != null)
            callers = (ArrayList<WeatherAPICaller>) savedInstanceState.getSerializable(BUNKEY_ARR_LATEST_WEATHERS);
        else
            callers = new ArrayList<>();
        getIntent().putExtra(BUNKEY_ARR_LATEST_WEATHERS, callers);

        // we store application settings and cities/weatherdata here
        preferences = getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);

        // bind Activitymainlayout (we put fragments inside content_main, not activity_main)
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        // Setup App navigation (the thingy that allows us to move between different sections of the program and press (back) to go back or go home.
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
//            @Override
//            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
//                // disable up button on toolbar (because we can't fix the bug)
//                // we need to do this on every
//                Log.d("wtf bro", String.format("%s", getSupportActionBar().toString()));
//                if (getSupportActionBar() != null)
//                {
//                    getSupportActionBar().setHomeButtonEnabled(false); // disable the button
//                    getSupportActionBar().setDisplayHomeAsUpEnabled(false); // remove the left caret
//                    getSupportActionBar().setDisplayShowHomeEnabled(false); // remove the icon
//                }
//            }
//        });        // useless, had no effect

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration); // this guy automatically changes the title of the toolbar depending on the fragment you are in!

        // uhhh not sure if this is a right place to do it but /shrug
        //LocationRequest lr = new LocationManager();
    }

    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);

        // save the weather so we don't have to recall the API again and again.
        Serializable s = getIntent().getSerializableExtra(BUNKEY_ARR_LATEST_WEATHERS);
        Log.d(getClass().getName(), String.format("Saved weather states '%s' to activity bundle. ", s));
        outState.putSerializable(BUNKEY_ARR_LATEST_WEATHERS, s);
    }

    public void hideMenuButtonFromToolbar()
    {
        binding.toolbar.getMenu().clear();
    }

    public void showMenuButtonFromToolbar()
    {
        //getMenuInflater().inflate(R.menu.menu_main, binding.toolbar.getMenu());
        onCreateOptionsMenu(binding.toolbar.getMenu());

        // not sure if there was a simpler way of just hiding and showing the menu button than to do something aggressive like inflate/clear
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            navController.navigate(R.id.action_SwipeFragment_to_settingsFragment);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {

        // this is a hack: i couldnt remove the Up(back button on toolbar) button from the toolbar
        // and I couldnt manipulate the stack to remove the last element.
        // so I'm just making it equivalent
        // to the back button of the phone.

        //return NavigationUI.navigateUp(navController, appBarConfiguration)
        getOnBackPressedDispatcher().onBackPressed();
        //return navController.popBackStack(); // make up button equivalent to back button
        return false;
    }
}