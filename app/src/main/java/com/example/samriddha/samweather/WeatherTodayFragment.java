package com.example.samriddha.samweather;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samriddha.samweather.Model.WeatherResult;
import com.example.samriddha.samweather.Retrofit.IOpenWeatherMap;
import com.example.samriddha.samweather.Retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherTodayFragment extends Fragment {

    private ImageView weatherImage ;
    private TextView tv_cityname, tv_humidity,tv_sunrise,tv_sunset,tv_wind,tv_pressure,tv_temperature,tv_description,tv_datetime
                ,tv_geocoord ;
    private LinearLayout weatherPanel;
    private ProgressBar progressBar ;

    private CompositeDisposable compositeDisposable;
    private IOpenWeatherMap mService;

    static WeatherTodayFragment instance ;
    public static WeatherTodayFragment getInstance(){

        if (instance==null)
            instance = new WeatherTodayFragment();

        return instance ;
    }

    public WeatherTodayFragment() {

        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather_today, container, false);

        weatherImage = (ImageView)view.findViewById(R.id.weatherimage_id);
        tv_cityname = (TextView)view.findViewById(R.id.cityname_id);
        tv_humidity = (TextView)view.findViewById(R.id.humiditytext_id);
        tv_sunrise = (TextView)view.findViewById(R.id.sunrisetext_id);
        tv_sunset = (TextView)view.findViewById(R.id.sunsettext_id);
        tv_wind = (TextView)view.findViewById(R.id.windtext_id);
        tv_pressure = (TextView)view.findViewById(R.id.pressuretext_id);
        tv_temperature = (TextView)view.findViewById(R.id.temperature_id);
        tv_description = (TextView)view.findViewById(R.id.description_id);
        tv_datetime = (TextView)view.findViewById(R.id.datetime_id);
        tv_geocoord = (TextView)view.findViewById(R.id.geocoordtext_id);

        weatherPanel = (LinearLayout)view.findViewById(R.id.weatherpanel_id);
        progressBar = (ProgressBar)view.findViewById(R.id.progressbar_id);

        getWeatherInformation();


        return view ;

    }

    private void getWeatherInformation() {

        compositeDisposable.add(mService.getWeatherByLatLon(String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude()),
                Common.APP_ID,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResult>() {
                    @Override
                    public void accept(WeatherResult weatherResult) throws Exception {

                        //Load Information
                        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/").append(weatherResult.getWeather()
                        .get(0).getIcon()).append(".png").toString()).into(weatherImage);

                        tv_cityname.setText(weatherResult.getName());
                        tv_description.setText(new StringBuilder("Weather In ").append(weatherResult.getName()).toString());
                        tv_temperature.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp())).append("°C"));
                        tv_datetime.setText(Common.ConvertUnixToDate(weatherResult.getDt()));
                        tv_pressure.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getPressure())).append("hpa").toString());
                        tv_humidity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getHumidity())).append("%").toString());
                        tv_sunrise.setText(Common.ConvertUnixToHour(weatherResult.getSys().getSunrise()));
                        tv_sunset.setText(Common.ConvertUnixToHour(weatherResult.getSys().getSunset()));
                        tv_geocoord.setText(new StringBuilder("{").append(weatherResult.getCoord().toString()).append("}").toString());

                        weatherPanel.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        Toast.makeText(getActivity(), ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                })

        );
    }


    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
