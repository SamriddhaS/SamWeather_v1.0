package com.example.samriddha.samweather.Adaptar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.samriddha.samweather.Common;
import com.example.samriddha.samweather.Model.WeatherForecastResult;
import com.example.samriddha.samweather.R;
import com.squareup.picasso.Picasso;

public class ForecastAdaptar extends RecyclerView.Adapter<ForecastViewHolder> {

    Context context ;
    WeatherForecastResult weatherForecastResult;

    public ForecastAdaptar(Context context, WeatherForecastResult weatherForecastResult) {
        this.context = context;
        this.weatherForecastResult = weatherForecastResult;
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.item_weatherforecast,parent,false);

        return new ForecastViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {

        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/w/").append(weatherForecastResult.list.get(position)
                .weather.get(0).getIcon()).append(".png").toString()).into(holder.weatherImage);

        holder.tv_date.setText(new StringBuilder(Common.ConvertUnixToDate(weatherForecastResult.list.get(position).dt)).toString());
        holder.tv_description.setText(new StringBuilder(weatherForecastResult.list.get(position).weather.get(0).getDescription()).toString());
        holder.tv_temperature.setText(new StringBuilder(String.valueOf(weatherForecastResult.list.get(position).main.getTemp())).append("°C"));

    }

    @Override
    public int getItemCount() {
        return weatherForecastResult.list.size();
    }
}
