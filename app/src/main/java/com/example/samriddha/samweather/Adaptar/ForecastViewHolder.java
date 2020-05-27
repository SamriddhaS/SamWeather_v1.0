package com.example.samriddha.samweather.Adaptar;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samriddha.samweather.ForecastFragment;
import com.example.samriddha.samweather.R;

class ForecastViewHolder extends RecyclerView.ViewHolder{

    TextView tv_date , tv_description , tv_temperature ;
    ImageView weatherImage ;

    public ForecastViewHolder(View itemView) {
        super(itemView);

        tv_date = (TextView)itemView.findViewById(R.id.forecastdate_id);
        tv_description = (TextView)itemView.findViewById(R.id.forecastdescription_id);
        tv_temperature = (TextView)itemView.findViewById(R.id.temperature_id);
        weatherImage = (ImageView) itemView.findViewById(R.id.weatherimage_id);

    }
}
