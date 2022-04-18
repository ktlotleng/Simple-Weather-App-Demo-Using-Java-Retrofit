package com.cleanpockets.weather_report;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cleanpockets.weather_report.weather_models.ConsolidatedWeatherArray;
import com.cleanpockets.weather_report.weather_models.WeatherModel;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    List<ConsolidatedWeatherArray> consolidatedWeatherArrayList;

    WeatherModel weatherModel;

    Context context;


    public MyAdapter(List<ConsolidatedWeatherArray> consolidatedWeatherArrayList, WeatherModel weatherModel, Context context) {
        this.consolidatedWeatherArrayList = consolidatedWeatherArrayList;
        this.weatherModel = weatherModel;
        this.context = context;
    }

    //Cache views
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewDate;
        TextView textViewTemperature;
        TextView textViewCondition;
        ImageView imageViewCondition;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTemperature = itemView.findViewById(R.id.textViewTemperature);
            textViewCondition = itemView.findViewById(R.id.textViewCondition);
            imageViewCondition = itemView.findViewById(R.id.imageViewCondition);

        }
    }

    // Inflate layout (layout being used inside the RecyclerView)
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_layout,parent,false);
        return new MyViewHolder(view);
    }


    // Populate views
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.textViewDate.setText(consolidatedWeatherArrayList.get(position).getDate());
        holder.textViewCondition.setText(consolidatedWeatherArrayList.get(position).getWeatherCondition());

        // Get the weather and round it off to avoid trailing numbers
        double decimalTemperature = consolidatedWeatherArrayList.get(position).getTemperature();
        int roundedTemperature = (int) Math.round(decimalTemperature);
        // u2103 is the Celsius degree symbol
        holder.textViewTemperature.setText(roundedTemperature + "\u2103");

        // Get the weather condition abbreviation from the endpoint
        String conditionAbbr = consolidatedWeatherArrayList.get(position).getWeatherConditionAbbr();

        String conditionUrl = getCondition(conditionAbbr);

        Glide.with(context)
                .load(conditionUrl)
                .placeholder(R.drawable.ic_baseline_broken_image_24)
                .error(R.drawable.ic_baseline_error_24)
                .into(holder.imageViewCondition);



    }

    // Get the weather condition image/icon
    private String getCondition(String conditionAbbr) {
        String conditionUrl;

        switch (conditionAbbr) {
            case "sn":
                conditionUrl = "https://www.metaweather.com/static/img/weather/ico/sn.ico";
                return conditionUrl;
            case "sl":
                conditionUrl = "https://www.metaweather.com/static/img/weather/ico/sl.ico";
                return conditionUrl;
            case "h":
                conditionUrl = "https://www.metaweather.com/static/img/weather/ico/h.ico";
                return conditionUrl;
            case "t":
                conditionUrl = "https://www.metaweather.com/static/img/weather/ico/t.ico";
                return conditionUrl;
            case "hr":
                conditionUrl = "https://www.metaweather.com/static/img/weather/ico/hr.ico";
                return conditionUrl;
            case "lr":
                conditionUrl = "https://www.metaweather.com/static/img/weather/ico/lr.ico";
                return conditionUrl;
            case "s":
                conditionUrl = "https://www.metaweather.com/static/img/weather/ico/s.ico";
                return conditionUrl;
            case "hc":
                conditionUrl = "https://www.metaweather.com/static/img/weather/ico/hc.ico";
                return conditionUrl;
            case "lc":
                conditionUrl = "https://www.metaweather.com/static/img/weather/ico/lc.ico";
                return conditionUrl;
            case "c":
                //conditionUrl = "https://www.metaweather.com/static/img/weather/ico/c.ico";
                conditionUrl = "https://www.metaweather.com/static/img/weather/png/64/c.png";
                return conditionUrl;


            default:
                return "https://thumbs.dreamstime.com/z/cloud-warning-icon-element-weather-illustration-signs-symbols-can-be-used-web-logo-mobile-app-ui-ux-white-background-141803223.jpg";
                //throw new IllegalStateException("Unexpected value: " + conditionAbbr);
        }
    }

    @Override
    public int getItemCount() {
        return consolidatedWeatherArrayList.size();
    }

}
