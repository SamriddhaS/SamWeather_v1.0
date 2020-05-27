package com.example.samriddha.samweather.Retrofit;

import com.example.samriddha.samweather.Model.WeatherForecastResult;
import com.example.samriddha.samweather.Model.WeatherResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeatherMap {

    @GET("weather")
    Observable<WeatherResult> getWeatherByLatLon(@Query("lat") String lat,
                                                 @Query("lon") String lon,
                                                 @Query("appid") String appid,
                                                 @Query("units") String unit);


    @GET("forecast")
    Observable<WeatherForecastResult> getForecastWeatherByLatLon(@Query("lat") String lat,
                                                                 @Query("lon") String lon,
                                                                 @Query("appid") String appid,
                                                                 @Query("units") String unit);


    @GET("weather")
    Observable<WeatherResult> getWeatherByCityName(@Query("q") String cityname,
                                                 @Query("appid") String appid,
                                                 @Query("units") String unit);

}
