package com.cleanpockets.weather_report;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cleanpockets.weather_report.weather_models.ConsolidatedWeatherArray;
import com.cleanpockets.weather_report.weather_models.WeatherModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    // RecyclerView
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    // Views
    AutoCompleteTextView acLocation;
    TextView textViewTitle;
    TextView textViewLocation;
    Button btnSearch;
    ImageView imageViewLocation;

    private static final String TAG = "onFailure";
    // Declare as ArrayList because the response will send an array, not a List
    List<ConsolidatedWeatherArray> consolidatedWeatherArrayList = new ArrayList<>();

    // Model classes
    // Used to call the response body, set city name and call the array list
    WeatherModel weatherModel;
    // Used to create a reference to the interface
    MetaweatherAPI metaweatherAPI;

    private static final String WEATHER_BASE_URL = "https://www.metaweather.com/api/location/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        acLocation = view.findViewById(R.id.editTextLocation);
        textViewTitle = view.findViewById(R.id.textViewTitle);
        textViewLocation = view.findViewById(R.id.textViewCityLocation);
        btnSearch = view.findViewById(R.id.btnSearch);
        imageViewLocation = view.findViewById(R.id.imageViewLocation);
        recyclerView = view.findViewById(R.id.recyclerViewWeatherReport);

        // For AutoCompleteTextView
        // These city names are just for simplicity and test purposes
        // This can be put in a separate class
        List<String> cityNamesList = new ArrayList<>();
        Collections.addAll(cityNamesList,"Paris, France", "Cape Town, South Africa", "Lagos, Nigeria",
                "New York, USA", "London, UK", "Johannesburg, South Africa", "Nairobi, Kenya", "Helsinki, Finland", "Sydney, Australia");
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,cityNamesList);
        acLocation.setAdapter(cityAdapter);

        Map<String, Integer> cityWoeidMap = new HashMap<>();
        cityWoeidMap.put("Paris, France", 615702);
        cityWoeidMap.put("Cape Town, South Africa", 1591691);
        cityWoeidMap.put("Lagos, Nigeria", 1398823);
        cityWoeidMap.put("New York, USA", 2459115);
        cityWoeidMap.put("London, UK", 44418);
        cityWoeidMap.put("Johannesburg, South Africa", 1582504);
        cityWoeidMap.put("Nairobi, Kenya", 1528488);
        cityWoeidMap.put("Helsinki, Finland", 565346);
        cityWoeidMap.put("Sydney, Australia", 1105779);


        createRetrofit();


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Get the text inputted in the edit text
                String inputted = acLocation.getText().toString();

                int woeidValue=-1;

                for(Map.Entry<String, Integer> entry : cityWoeidMap.entrySet()) {

                    // if the input in the edit text (autocompletetextview) matches a key in the map
                    // then assign the matching value, which is the woeid in our case, to the variable woeidValue
                    if(inputted.equals(entry.getKey())) {
                        woeidValue = entry.getValue();

                    }

                }

                // We then use the value to pass to the method that will use the woeid to get the weather report
                // from the API
                getWeatherReportWoeid(woeidValue);

            }
        });

    }


    // Call retrofit components
    private void createRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WEATHER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        metaweatherAPI = retrofit.create(MetaweatherAPI.class);
    }

    // Get the weather report passed
    // We use the woeid to get the weather report of the city
    // You can pass any woeid, for simplicity we only use a certain number of cities
    private void getWeatherReportWoeid(int woeid) {

        Call<WeatherModel> call = metaweatherAPI.getWeatherReport(woeid);

        call.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {

                // If response is NOT successful
                if (!response.isSuccessful()) {
                    imageViewLocation.setImageResource(R.drawable.ic_baseline_location_off_24);
                    textViewLocation.setText("No location");
                    Toast.makeText(getActivity(), "Error occurred. Code description : " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get the response body from the API call
                weatherModel = response.body();

                textViewLocation.setText(weatherModel.getCityName());
                imageViewLocation.setImageResource(R.drawable.ic_baseline_location_on_24);

                // Get the array inside the outer object (WeatherModel)
                consolidatedWeatherArrayList = weatherModel.getConsolidatedWeatherArrayList();

                setupRecyclerView(consolidatedWeatherArrayList);


            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                imageViewLocation.setImageResource(R.drawable.ic_baseline_location_off_24);
                textViewLocation.setText("No location");
                Log.i(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }// close getWeatherReport()


    // Sets up the RecyclerView
    private void setupRecyclerView(List<ConsolidatedWeatherArray> consolidatedWeatherArray) {

        recyclerView.hasFixedSize();

        // Setup layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // Setup adapter
        adapter = new MyAdapter(consolidatedWeatherArrayList, weatherModel, getActivity());
        recyclerView.setAdapter(adapter);


    } // close setupRecyclerView()



}