package com.example.samriddha.samweather;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.samriddha.samweather.Adaptar.ForecastAdaptar;
import com.example.samriddha.samweather.Model.WeatherForecastResult;
import com.example.samriddha.samweather.Model.WeatherResult;
import com.example.samriddha.samweather.Retrofit.IOpenWeatherMap;
import com.example.samriddha.samweather.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastFragment extends Fragment {

    static ForecastFragment instance ;
    private CompositeDisposable compositeDisposable;
    private IOpenWeatherMap mService ;
    private RecyclerView recyclerView ;
    private TextView tv_geoCoord,tv_cityName ;

    public static ForecastFragment getInstance(){

        if (instance==null)
            instance = new ForecastFragment();

        return instance ;
    }


    public ForecastFragment() {
        // Required empty public constructor
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_forecast, container, false);

        tv_cityName = (TextView)itemView.findViewById(R.id.cityname_id);
        tv_geoCoord = (TextView)itemView.findViewById(R.id.geocoordtext_id);

        recyclerView = (RecyclerView)itemView.findViewById(R.id.recyclerforecast_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        getForecastWeatherInformation();

        return itemView ;
    }

    private void getForecastWeatherInformation() {

        compositeDisposable.add(mService.getForecastWeatherByLatLon(String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude()),
                Common.APP_ID,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherForecastResult>() {
                    @Override
                    public void accept(WeatherForecastResult weatherForecastResult) throws Exception {

                        displayForecastWeather(weatherForecastResult);
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        Log.d("Error", throwable.getMessage());
                    }
                })
        );
    }
    private void displayForecastWeather(WeatherForecastResult weatherResult) {

        tv_cityName.setText(new StringBuilder(weatherResult.city.name));
        tv_geoCoord.setText(new StringBuilder(weatherResult.city.coord.toString()));

        ForecastAdaptar adaptar = new ForecastAdaptar(getContext(),weatherResult);
        recyclerView.setAdapter(adaptar);


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
