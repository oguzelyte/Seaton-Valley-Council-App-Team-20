package com.ncl.team20.seatonvalley.components.activities_components;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ncl.team20.seatonvalley.R;
import com.ncl.team20.seatonvalley.adapters.PostsRecyclerViewAdapter;
import com.ncl.team20.seatonvalley.components.basic.ConnectionDetector;
import com.ncl.team20.seatonvalley.components.web.DisplayWeatherForecast;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Gets the content of the MainActivity.
 * Extends the GetPosts class.
 * <p>
 * Last Edit: 2/03/2018 by Stelios Ioannou <p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @author Alex Peebles
 * @since 20/03/2018
 * @see ConnectionDetector
 * @see PostsRecyclerViewAdapter
 * @see DisplayWeatherForecast
 * @see com.ncl.team20.seatonvalley.activities.MainActivity
 * @see GetPosts
 * @see Picasso
 * @see JsonObjectRequest
 * @see Volley
 * @see JSONArray
 * @see JSONObject
 *
 */
public class MainActivityComponent extends GetPosts {

    /**
     * If connection is available get the 3 latest news posts for the carousel.
     * Else display an error message.
     * @param recyclerView view to add posts to
     * @param detector connection detector
     * @param context current state of application
     */
    protected void getContent(@NonNull RecyclerView recyclerView, @NonNull ConnectionDetector detector, Context context) {

        TextView errorMessage = findViewById(R.id.net_down);

        if (detector.isInternetAvailable()) {
            errorMessage.setVisibility(View.GONE);
            getWeather();
            progressBar.setVisibility(View.VISIBLE);
            getLatestNews();
            adapter = new PostsRecyclerViewAdapter(list, context, 1);
            recyclerView.setAdapter(adapter);
        } else {
            getLatestNews();
            adapter = new PostsRecyclerViewAdapter(list, context, 1);
            // Display the text if the 
            if (list.size() == 0) {
                errorMessage.setVisibility(View.VISIBLE);
            } else {
                errorMessage.setVisibility(View.GONE);
            }
            recyclerView.setAdapter(adapter);
            this.registerReceiver(this.mConnReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        adapter.notifyDataSetChanged();

    }

    /**
     * Gets the 3 latest news posts using get posts.
     *
     *
     */
    private void getLatestNews() {
        getPosts(3, "16", this);
    }

    /**
     * Gets the weather of Seaton Valley and updates the UI contents.<p>
     * Last Edit: 2/03/2018 by Stelios Ioannou
     *
     * @author Alex Peebles
     * @since 23/02/2018
     *
     */
    private void getWeather() {

        LinearLayout carousel = findViewById(R.id.carousel);
        LinearLayout weatherLayout = findViewById(R.id.weather);
        TextView temperatureView = weatherLayout.findViewById(R.id.weather_temperature);
        ImageView weatherIcon = carousel.findViewById(R.id.weather_icon);

        weatherLayout.setClickable(true);

        // On weather click it open the relevant weather forecast in app.
        weatherLayout.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), DisplayWeatherForecast.class);
            startActivity(intent);
        })
        ;

        // Key to access OpenWeatherMap API.
        String API_KEY = getString(R.string.open_weather_api_key);

        // Seaton Delaval used for the Weather Info. 
        final String query = "id=2638273" + "&appid=" + API_KEY + "&units=metric";
        // creates a request for JSON data at Seaton Delaval since it's the center of Seaton.
        final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?";
        final String[] weatherIconCode = new String[1];
        final String IMAGES_URL = "http://openweathermap.org/img/w/";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, BASE_URL + query, null, new Response.Listener<JSONObject>() {
            /**
             * Runs if the JSON data is successfully retrieved
             * @param response data received from request.
             *
             */
            @Override
            public void onResponse(@NonNull JSONObject response) {
                try {
                    JSONObject mainObject = response.getJSONObject("main");
                    JSONArray weatherArray = response.getJSONArray("weather");
                    JSONObject weatherObject = (JSONObject) weatherArray.get(0);
                    // sets the temp textView text to the current celsius temperature
                    double temp = Math.round(mainObject.getDouble("temp"));
                    temperatureView.setText(String.format("%s\u2103", (int) temp));
                    // retrieves a weather icon code that corresponds to an image
                    weatherIconCode[0] = weatherObject.getString("icon");
                    // initialise Piccasso API
                    Picasso picasso = Picasso.with(getApplicationContext());
                    // retrieves image from the url using the weather icon code and places it in an ImageView
                    picasso.load(String.format("%s%s.png", IMAGES_URL, weatherIconCode[0])).resize(100, 100).into(weatherIcon);
                } catch (JSONException ignored) {
                }
            }
        }, new Response.ErrorListener() {
            /**
             * Displays a toast if the JSON request encountered an error.
             * @param error error information.
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), R.string.weather_error, Toast.LENGTH_LONG).show();
            }

        });
        Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);
    }

}