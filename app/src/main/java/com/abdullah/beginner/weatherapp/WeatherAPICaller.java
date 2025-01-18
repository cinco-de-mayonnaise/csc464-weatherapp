package com.abdullah.beginner.weatherapp;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class WeatherAPICaller implements Serializable {
    final String BASE_URL_FORECAST = "https://api.openweathermap.org/data/2.5/forecast";
    final String BASE_URL_CURRENT = "https://api.openweathermap.org/data/2.5/weather";
    double latitude;
    double longitude;
    WeatherForecastSet result;
    String apikey = "49d8bd8ae8b6f4d0d510afaaac7ccec3";


    public WeatherAPICaller(Double latitude, Double longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public WeatherAPICaller(double latitude, double longitude, String apikey) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.apikey = apikey;
    }

    @SuppressLint("DefaultLocale")
    private String produceURLstring(String BASE_URL)
    {
        return BASE_URL
                + "?appid=" + apikey
                + "&lat=" + String.format("%f", latitude)
                + "&lon=" + String.format("%f", longitude)
                ;
    }

    private String getStringFromResponse(HttpURLConnection req) throws Exception {
        try
        {
            StringBuilder s = new StringBuilder();
            BufferedReader bf = new BufferedReader(new InputStreamReader(req.getInputStream()));
            //Log.d(getClass().getName(), "Attempting to get json data");

            while (true)
            {
                String line = bf.readLine();
                if (line == null)
                    break;
                s.append(line);
            }

            return s.toString();
        }
        finally {
            req.disconnect();
        }
    }

    @NonNull
    public WeatherForecastSet call() throws Exception
    {
        return this.call(false);
    }

    @NonNull
    public WeatherForecastSet call(boolean forceupdate) throws Exception {

        if (!forceupdate)
        {
            // save on API calls by reusing previously obtained result
            if (this.result != null)
            {
                long unixTimeDifference = (System.currentTimeMillis() / 1000L) - this.result.currentWeather().timestampForecast();

                if (unixTimeDifference < 5*60)  // reuse old result if its been less than 5 minutes!
                {
                    Log.i(getClass().getName(), "Previously obtained weather result was found and used! Age: " + unixTimeDifference + " seconds");
                    return this.result;
                }
            }
        }
        Log.i(getClass().getName(), "No previous results found, connecting to servers...");

        HttpURLConnection req_forecast = (HttpURLConnection) new URL(produceURLstring(BASE_URL_FORECAST)).openConnection();
        req_forecast.setRequestMethod("GET");
        req_forecast.setRequestProperty("Content-Type", "application/json");
        req_forecast.setRequestProperty("Accept", "application/json");
        req_forecast.setConnectTimeout(10000);
        req_forecast.setReadTimeout(10000);

        HttpURLConnection req_current= (HttpURLConnection) new URL(produceURLstring(BASE_URL_CURRENT)).openConnection();
        req_forecast.setRequestMethod("GET");
        req_forecast.setRequestProperty("Content-Type", "application/json");
        req_forecast.setRequestProperty("Accept", "application/json");
        req_forecast.setConnectTimeout(10000);
        req_forecast.setReadTimeout(10000);


        WeatherForecastSet result = unpack(getStringFromResponse(req_forecast), getStringFromResponse(req_current));

        // WORKS!
        req_forecast.disconnect();
        req_current.disconnect();

        this.result = result;

        return result;
    }


    public static void printWeatherForecastSet(WeatherForecastSet weatherSet) {
        System.out.println("====== Weather Forecast Details ======");
        System.out.println("City: " + weatherSet.cityName() + ", Country: " + weatherSet.countryCode());
        System.out.println("Population: " + weatherSet.population());
        System.out.println("Sunrise: " + weatherSet.sunrise());
        System.out.println("Sunset: " + weatherSet.sunset());
        System.out.println("Timezone: " + weatherSet.timezone());
        System.out.println("\nForecast Entries: " + weatherSet.forecasts().length);

        System.out.println("\n=== Current Weather ===");
        System.out.println("Timestamp: " + weatherSet.currentWeather().timestampForecast());
        System.out.println("Temperature: " + weatherSet.currentWeather().temp() + "K");
        System.out.println("Feels Like: " + weatherSet.currentWeather().feelsLike() + "K");
        System.out.println("Min Temp: " + weatherSet.currentWeather().tempMin() + "K");
        System.out.println("Max Temp: " + weatherSet.currentWeather().tempMax() + "K");
        System.out.println("Pressure: " + weatherSet.currentWeather().pressure() + " hPa");
        System.out.println("Sea Level: " + weatherSet.currentWeather().seaLevel() + " hPa");
        System.out.println("Ground Level: " + weatherSet.currentWeather().groundLevel() + " hPa");
        System.out.println("Humidity: " + weatherSet.currentWeather().humidity() + "%");
        System.out.println("Weather: " + weatherSet.currentWeather().weatherParameters());
        System.out.println("Description: " + weatherSet.currentWeather().weatherDescription());
        System.out.println("Icon ID: " + weatherSet.currentWeather().weatherIconId());
        System.out.println("Wind Speed: " + weatherSet.currentWeather().windSpeed() + " m/s");
        System.out.println("Wind Direction: " + weatherSet.currentWeather().windDeg() + "°");
        System.out.println("Wind Gust: " + weatherSet.currentWeather().windGust() + " m/s");
        System.out.println("Visibility: " + weatherSet.currentWeather().visibility() + " m");
        System.out.println("Precipitation Probability: " + weatherSet.currentWeather().precipitationProbability() + "%");
        System.out.println("Part of Day: " + weatherSet.currentWeather().partOfDay());
        System.out.println("Rain Volume: " + weatherSet.currentWeather().rainVolume() + " mm");
        System.out.println("Cloudiness: " + weatherSet.currentWeather().cloudiness() + "%");
        System.out.println("Snow Volume: " + weatherSet.currentWeather().snowVolume() + " mm");

        for (int i = 0; i < weatherSet.forecasts().length; i++) {
            WeatherForecastSet.WeatherForecast forecast = weatherSet.forecasts()[i];

            System.out.println("\n--- Forecast Entry " + (i + 1) + " ---");
            System.out.println("Timestamp: " + forecast.timestampForecast());
            System.out.println("Temperature: " + forecast.temp() + "K");
            System.out.println("Feels Like: " + forecast.feelsLike() + "K");
            System.out.println("Min Temp: " + forecast.tempMin() + "K");
            System.out.println("Max Temp: " + forecast.tempMax() + "K");
            System.out.println("Pressure: " + forecast.pressure() + " hPa");
            System.out.println("Sea Level: " + forecast.seaLevel() + " hPa");
            System.out.println("Ground Level: " + forecast.groundLevel() + " hPa");
            System.out.println("Humidity: " + forecast.humidity() + "%");
            System.out.println("Weather: " + forecast.weatherParameters());
            System.out.println("Description: " + forecast.weatherDescription());
            System.out.println("Icon ID: " + forecast.weatherIconId());
            System.out.println("Wind Speed: " + forecast.windSpeed() + " m/s");
            System.out.println("Wind Direction: " + forecast.windDeg() + "°");
            System.out.println("Wind Gust: " + forecast.windGust() + " m/s");
            System.out.println("Visibility: " + forecast.visibility() + " m");
            System.out.println("Precipitation Probability: " + forecast.precipitationProbability() + "%");
            System.out.println("Part of Day: " + forecast.partOfDay());
            System.out.println("Rain Volume: " + forecast.rainVolume() + " mm");
            System.out.println("Cloudiness: " + forecast.cloudiness() + "%");
            System.out.println("Snow Volume: " + forecast.snowVolume() + " mm");
        }
    }

    private static WeatherForecastSet.WeatherForecast __unpack_weatherforecast(JSONObject weatherEntry) throws JSONException
    {
        long timestamp_forecast = weatherEntry.getLong("dt");

        // Extract main weather data
        JSONObject mainData = weatherEntry.getJSONObject("main");
        double temp = mainData.getDouble("temp");
        double feelsLike = mainData.getDouble("feels_like");
        double tempMin = mainData.getDouble("temp_min");
        double tempMax = mainData.getDouble("temp_max");
        int pressure = mainData.getInt("pressure");
        int seaLevel = mainData.getInt("sea_level");
        int groundLevel = mainData.getInt("grnd_level");
        int humidity = mainData.getInt("humidity");

        // Extract weather description
        JSONArray weatherArray = weatherEntry.getJSONArray("weather");
        String weatherParameters = weatherArray.getJSONObject(0).getString("main");
        String weatherDescription = weatherArray.getJSONObject(0).getString("description");
        String weatherIconId = weatherArray.getJSONObject(0).getString("icon");

        // Extract wind data
        JSONObject windData = weatherEntry.getJSONObject("wind");
        double windSpeed = windData.getDouble("speed");
        int windDeg = windData.getInt("deg");

        // Visibility
        int visibility = weatherEntry.getInt("visibility");

        // Extract time of day data
        JSONObject sysEntry = weatherEntry.getJSONObject("sys");


        /// get optional data
        // probability of precipitation: does not exist in current weather, only forecasts
        int pop;
        try {
            pop = weatherEntry.getInt("pop");
        } catch (JSONException j) {
            pop = -1;
        }

        // partOfDay: does not exist in current weather, only forecasts
        String partOfDay;
        try {
            partOfDay = sysEntry.getString("pod");
        } catch (JSONException j)
        {
            // pass
            partOfDay = null;
        }

        // gust: does not exist in some current weather, only forecasts
        double gust;
        try {
            gust = windData.getDouble("gust");
        } catch (JSONException j)
        {
            gust = -1;
        }


        // rain volume: only exists if it rains (duh)
        int rainVolume;
        try {
            JSONObject rainEntry = weatherEntry.getJSONObject("rain");
            rainVolume = rainEntry.getInt("3h");
        } catch (JSONException j) {
            rainVolume = -1;
        }

        // cloudiness percentage: only exists if its cloudy
        int cloudiness;
        try {
            JSONObject cloudEntry = weatherEntry.getJSONObject("clouds");
            cloudiness = cloudEntry.getInt("all");
        } catch (JSONException j) {
            cloudiness = -1;
        }

        // snow volume: only exists if it snows (duh)
        int snowVolume;
        try {
            JSONObject snowEntry = weatherEntry.getJSONObject("snow");
            snowVolume = snowEntry.getInt("3h");
        } catch (JSONException j) {
            snowVolume = -1;
        }

        return new WeatherForecastSet.WeatherForecast(
                timestamp_forecast,
                temp,
                feelsLike,
                tempMin,
                tempMax,
                pressure,
                seaLevel,
                groundLevel,
                humidity,
                weatherParameters,
                weatherDescription,
                weatherIconId,
                windSpeed,
                windDeg,
                gust,
                visibility,
                pop,
                partOfDay,
                rainVolume,
                cloudiness,
                snowVolume
        );

    }

    private static WeatherForecastSet unpack(String forecast_json, String current_json) throws JSONException, RuntimeException
    {
        JSONObject root = new JSONObject(forecast_json);

        int cod = root.getInt("cod");
        //String message = root.getString("message");

        switch (cod)
        {
            case 401:
                throw new RuntimeException("Invalid API key");
            case 404:
                throw new RuntimeException("Not found.");
        }

        JSONArray list_weathers = root.getJSONArray("list");
        ArrayList<WeatherForecastSet.WeatherForecast> forecasts = new ArrayList<>();

        for (int i = 0; i < list_weathers.length(); i++)
        {
            // System.out.println("Element " + i);
            JSONObject wf = list_weathers.getJSONObject(i);

            forecasts.add(__unpack_weatherforecast(wf));
        }

        JSONObject city_info = root.getJSONObject("city");
        String cityName = city_info.getString("name");
        String countryCode = city_info.getString("country");
        long population = city_info.getLong("population");
        long sunrise = city_info.getLong("sunrise");
        long sunset = city_info.getLong("sunset");
        long timezone = city_info.getLong("timezone");

        /// read current json from here
        root = new JSONObject(current_json);

        cod = root.getInt("cod");
        //String message = root.getString("message");

        switch (cod)
        {
            case 401:
                throw new RuntimeException("Invalid API key");
            case 404:
                throw new RuntimeException("Not found.");
        }

        WeatherForecastSet.WeatherForecast current_weather = __unpack_weatherforecast(root);
        // hack cause i need to get this shitty project done by today

        // predict min and max temperature of a day by going through 8 entries of predicted weather and grabbing the min/max
        double tempMin = 99999;
        double tempMax = -99999;

        for (int i = 0; i < 8; i++)
        {
            WeatherForecastSet.WeatherForecast f = forecasts.get(i);
            if (f.temp() > tempMax)
                tempMax = f.temp();
            else if (f.temp() < tempMin)
                tempMin = f.temp();
        }

        WeatherForecastSet.WeatherForecast current_weather_min_max_modified = new WeatherForecastSet.WeatherForecast(
                current_weather.timestampForecast(),
                current_weather.temp(),
                current_weather.feelsLike(),
                tempMin,
                tempMax,
                current_weather.pressure(),
                current_weather.seaLevel(),
                current_weather.groundLevel(),
                current_weather.humidity(),
                current_weather.weatherParameters(),
                current_weather.weatherDescription(),
                current_weather.weatherIconId(),
                current_weather.windSpeed(),
                current_weather.windDeg(),
                current_weather.windGust(),
                current_weather.visibility(),
                current_weather.precipitationProbability(),
                current_weather.partOfDay(),
                current_weather.rainVolume(),
                current_weather.cloudiness(),
                current_weather.snowVolume()
        );

        return new WeatherForecastSet(
                current_weather_min_max_modified,
                forecasts.toArray(new WeatherForecastSet.WeatherForecast[0]),
                cityName,
                countryCode,
                population,
                sunrise,
                sunset,
                timezone
        );
    }


}
