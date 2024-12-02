package com.abdullah.beginner.weatherapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.abdullah.beginner.weatherapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // bind activitymainlayout (we put fragments inside content_main, not activity_main)
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        // Setup App navigation (the thingy that allows us to move between different sections of the program and press (back) to go back or go home.
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
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