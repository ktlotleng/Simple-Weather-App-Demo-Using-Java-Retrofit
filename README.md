# Simple-Weather-App-Demo-Using-Java-Retrofit

A simple Java application that pulls data from an API using Retrofit.

This application only demonstrates how to work with an online API using Retrofit. It acts as a demo application to a full weather app, this is not a full app, which is evident by it's simplicity. You can use it as a skeleton to make your own full weather application.

The application pulls data from https://www.metaweather.com/api/ using Retrofit. The API endpoint is in a JSON format. The app also uses Jetpack components.

# How the app backend works
1. The app uses a RecyclerView to display the weather for 5 days.
2. Retrofit reads data from https://www.metaweather.com/api/, the data is in a form of a JSON.
3. The data is displayed onto the RecyclerView.
4. The app only fetches the following data; location (city name),woeid, date, the temperate, weather condition and the icon of the weather condition.
5. AutoCompleteTextView is used to suggest city names (only limited in this app - 9 cities) and matches the city with its woeid.

You can search woeids on https://www.woeids.com/
