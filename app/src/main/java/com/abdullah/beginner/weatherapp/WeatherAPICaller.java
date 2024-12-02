package com.abdullah.beginner.weatherapp;

import android.annotation.SuppressLint;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class WeatherAPICaller {
    String BASE_URL = "https://api.openweathermap.org/data/2.5/forecast";
    double latitude;
    double longitude;
    String apikey = "49d8bd8ae8b6f4d0d510afaaac7ccec3";
    public WeatherAPICaller(Double latitude, Double longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @SuppressLint("DefaultLocale")
    private String produceURLstring()
    {
        return BASE_URL
                + "?lat=" + String.format("%f", latitude)
                + "&lon=" + String.format("%f", longitude)
                + "&appid=" + apikey;
    }

    public void call() throws Exception
    {
        HttpURLConnection req = (HttpURLConnection) new URL(produceURLstring()).openConnection();
        req.setRequestMethod("GET");
        req.setRequestProperty("Content-Type", "application/json");
        req.setRequestProperty("Accept", "application/json");
        req.setConnectTimeout(10000);
        req.setReadTimeout(10000);

        try
        {
            JsonReader jr = new JsonReader(new BufferedReader(new InputStreamReader(req.getInputStream())));
            Log.d("attempt to get json data", jr.toString());
            JSONObject jo = new JSONObject();
        }
        finally {
            req.disconnect();
        }
    }
}
