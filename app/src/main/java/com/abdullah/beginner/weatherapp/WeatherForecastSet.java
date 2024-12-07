package com.abdullah.beginner.weatherapp;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public final class WeatherForecastSet implements Serializable
{
    public static final class WeatherForecast implements Serializable{
        private final long timestampForecast;
        private final double temp;
        private final double feelsLike;
        private final double tempMin;
        private final double tempMax;
        private final int pressure;
        private final int seaLevel;
        private final int groundLevel;
        private final int humidity;
        private final String weatherParameters;
        private final String weatherDescription;
        private final String weatherIconId;
        private final double windSpeed;
        private final int windDeg;
        private final double windGust;
        private final int visibility;
        private final int precipitationProbability;
        private final String partOfDay;
        private final int rainVolume;
        private final int cloudiness;
        private final int snowVolume;

        public WeatherForecast(long timestampForecast, double temp, double feelsLike, double tempMin, double tempMax,
                               int pressure, int seaLevel, int groundLevel, int humidity, String weatherParameters,
                               String weatherDescription, String weatherIconId, double windSpeed, int windDeg,
                               double windGust, int visibility, int precipitationProbability, String partOfDay,
                               int rainVolume, int cloudiness, int snowVolume)
        {
            this.timestampForecast = timestampForecast;
            this.temp = temp;
            this.feelsLike = feelsLike;
            this.tempMin = tempMin;
            this.tempMax = tempMax;
            this.pressure = pressure;
            this.seaLevel = seaLevel;
            this.groundLevel = groundLevel;
            this.humidity = humidity;
            this.weatherParameters = weatherParameters;
            this.weatherDescription = weatherDescription;
            this.weatherIconId = weatherIconId;
            this.windSpeed = windSpeed;
            this.windDeg = windDeg;
            this.windGust = windGust;
            this.visibility = visibility;
            this.precipitationProbability = precipitationProbability;
            this.partOfDay = partOfDay;
            this.rainVolume = rainVolume;
            this.cloudiness = cloudiness;
            this.snowVolume = snowVolume;
        }

        public long timestampForecast() {
            return timestampForecast;
        }

        public double temp() {
            return temp;
        }

        public double feelsLike() {
            return feelsLike;
        }

        public double tempMin() {
            return tempMin;
        }

        public double tempMax() {
            return tempMax;
        }

        public int pressure() {
            return pressure;
        }

        public int seaLevel() {
            return seaLevel;
        }

        public int groundLevel() {
            return groundLevel;
        }

        public int humidity() {
            return humidity;
        }

        public String weatherParameters() {
            return weatherParameters;
        }

        public String weatherDescription() {
            return weatherDescription;
        }

        public String weatherIconId() {
            return weatherIconId;
        }

        public double windSpeed() {
            return windSpeed;
        }

        public int windDeg() {
            return windDeg;
        }

        public double windGust() {
            return windGust;
        }

        public int visibility() {
            return visibility;
        }

        public int precipitationProbability() {
            return precipitationProbability;
        }

        public String partOfDay() {
            return partOfDay;
        }

        public int rainVolume() {
            return rainVolume;
        }

        public int cloudiness() {
            return cloudiness;
        }

        public int snowVolume() {
            return snowVolume;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (WeatherForecast) obj;
            return this.timestampForecast == that.timestampForecast &&
                    Double.doubleToLongBits(this.temp) == Double.doubleToLongBits(that.temp) &&
                    Double.doubleToLongBits(this.feelsLike) == Double.doubleToLongBits(that.feelsLike) &&
                    Double.doubleToLongBits(this.tempMin) == Double.doubleToLongBits(that.tempMin) &&
                    Double.doubleToLongBits(this.tempMax) == Double.doubleToLongBits(that.tempMax) &&
                    this.pressure == that.pressure &&
                    this.seaLevel == that.seaLevel &&
                    this.groundLevel == that.groundLevel &&
                    this.humidity == that.humidity &&
                    Objects.equals(this.weatherParameters, that.weatherParameters) &&
                    Objects.equals(this.weatherDescription, that.weatherDescription) &&
                    Objects.equals(this.weatherIconId, that.weatherIconId) &&
                    Double.doubleToLongBits(this.windSpeed) == Double.doubleToLongBits(that.windSpeed) &&
                    this.windDeg == that.windDeg &&
                    Double.doubleToLongBits(this.windGust) == Double.doubleToLongBits(that.windGust) &&
                    this.visibility == that.visibility &&
                    this.precipitationProbability == that.precipitationProbability &&
                    Objects.equals(this.partOfDay, that.partOfDay) &&
                    this.rainVolume == that.rainVolume &&
                    this.cloudiness == that.cloudiness &&
                    this.snowVolume == that.snowVolume;
        }

        @Override
        public int hashCode() {
            return Objects.hash(timestampForecast, temp, feelsLike, tempMin, tempMax, pressure, seaLevel, groundLevel, humidity, weatherParameters, weatherDescription, weatherIconId, windSpeed, windDeg, windGust, visibility, precipitationProbability, partOfDay, rainVolume, cloudiness, snowVolume);
        }

        @NonNull
        @Override
        public String toString() {
            return "WeatherForecast[" +
                    "timestampForecast=" + timestampForecast + ", " +
                    "temp=" + temp + ", " +
                    "feelsLike=" + feelsLike + ", " +
                    "tempMin=" + tempMin + ", " +
                    "tempMax=" + tempMax + ", " +
                    "pressure=" + pressure + ", " +
                    "seaLevel=" + seaLevel + ", " +
                    "groundLevel=" + groundLevel + ", " +
                    "humidity=" + humidity + ", " +
                    "weatherParameters=" + weatherParameters + ", " +
                    "weatherDescription=" + weatherDescription + ", " +
                    "weatherIconId=" + weatherIconId + ", " +
                    "windSpeed=" + windSpeed + ", " +
                    "windDeg=" + windDeg + ", " +
                    "windGust=" + windGust + ", " +
                    "visibility=" + visibility + ", " +
                    "precipitationProbability=" + precipitationProbability + ", " +
                    "partOfDay=" + partOfDay + ", " +
                    "rainVolume=" + rainVolume + ", " +
                    "cloudiness=" + cloudiness + ", " +
                    "snowVolume=" + snowVolume + ']';
        }
    }

    private final WeatherForecast currentWeather;
    private final WeatherForecast[] forecasts;
    private final String cityName;
    private final String countryCode;
    private final long population;
    private final long sunrise;
    private final long sunset;
    private final long timezone;

    public WeatherForecastSet(
            WeatherForecast currentWeather, WeatherForecast[] forecasts, String cityName, String countryCode,
            long population, long sunrise, long sunset, long timezone
    ) {
        this.currentWeather = currentWeather;
        this.forecasts = forecasts;
        this.cityName = cityName;
        this.countryCode = countryCode;
        this.population = population;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.timezone = timezone;
    }

    public WeatherForecast[] forecasts() {
        return forecasts;
    }

    public WeatherForecast currentWeather() {
        return currentWeather;
    }

    public String cityName() {
        return cityName;
    }

    public String countryCode() {
        return countryCode;
    }

    public long population() {
        return population;
    }

    public long sunrise() {
        return sunrise;
    }

    public long sunset() {
        return sunset;
    }

    public long timezone() {
        return timezone;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (WeatherForecastSet) obj;
        return Arrays.equals(this.forecasts, that.forecasts) &&
                Objects.equals(this.cityName, that.cityName) &&
                Objects.equals(this.countryCode, that.countryCode) &&
                Objects.equals(this.currentWeather, that.currentWeather) &&
                this.population == that.population &&
                this.sunrise == that.sunrise &&
                this.sunset == that.sunset &&
                this.timezone == that.timezone;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(forecasts), currentWeather, cityName, countryCode, population, sunrise, sunset, timezone);
    }

    @NonNull
    @Override
    public String toString() {
        return "WeatherForecastSet[" +
                "currentWeather" + currentWeather + ", " +
                "forecasts=" + forecasts + ", " +
                "cityName=" + cityName + ", " +
                "countryCode=" + countryCode + ", " +
                "population=" + population + ", " +
                "sunrise=" + sunrise + ", " +
                "sunset=" + sunset + ", " +
                "timezone=" + timezone + ']';
    }
}
