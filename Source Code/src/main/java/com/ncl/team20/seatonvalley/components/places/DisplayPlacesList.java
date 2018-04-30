package com.ncl.team20.seatonvalley.components.places;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.maps.model.LatLng;
import com.ncl.team20.seatonvalley.R;
import com.ncl.team20.seatonvalley.adapters.LocationsRecyclerViewAdapter;
import com.ncl.team20.seatonvalley.components.basic.Connection;
import com.ncl.team20.seatonvalley.components.basic.ConnectionDetector;
import com.ncl.team20.seatonvalley.data.ClientRequestBuilder;
import com.ncl.team20.seatonvalley.data.places.ModelPlace;
import com.ncl.team20.seatonvalley.data.places.Place;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * This class is used to get the relevant requested places,and makes them clickable. <p>
 * Last Edit: 02/03/2018 by Stelios Ioannou <p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @since 20/02/2018
 * @see Connection
 * @see ConnectionDetector
 * @see ClientRequestBuilder
 * @see Retrofit
 * @see LatLng
 * @see LocationsRecyclerViewAdapter
 * @see Place
 * @see ModelPlace
 */
public class DisplayPlacesList extends Connection {

    @Nullable
    public static Place mListPlace;
    private final ConnectionDetector detector = new ConnectionDetector(DisplayPlacesList.this);
    private ProgressBar progressBar;
    private ArrayList<ModelPlace> list;
    private LocationsRecyclerViewAdapter adapter;

    /**
     * Sets up the layout.
     * @param savedInstanceState stores the state of the application in a Bundle object
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        // Custom Animation on Create
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);

        setContentView(R.layout.activity_places);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle b = getIntent().getExtras();
        // @SuppressWarnings("ConstantConditions") String title = b.getString("title");
        // getSupportActionBar().setTitle(title);

        // Gets the Title of the Category.
        @SuppressWarnings("ConstantConditions") String title = b.getString("title");
        // Sets the Title.
        this.setTitle(title);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_places);
        progressBar = findViewById(R.id.progress_bar_places);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(DisplayPlacesList.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        list = new ArrayList<>();

        // Checks whether there is connection or not and then calls to get place.
        if (detector.isInternetAvailable()) {
            progressBar.setVisibility(View.VISIBLE);
            getPlaces(b);
            adapter = new LocationsRecyclerViewAdapter(list, DisplayPlacesList.this);
            recyclerView.setAdapter(adapter);
        } else {
            progressBar.setVisibility(View.GONE);
            getPlaces(b);
            adapter = new LocationsRecyclerViewAdapter(list, DisplayPlacesList.this);
            recyclerView.setAdapter(adapter);
            this.registerReceiver(this.mConnReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }


    }

    /**
     * getPlaces gets the places in Seaton Valley using Google Places API
     * and displays them in a list with all of their details.
     * @param b contains the data of the places to get by keyword and type
     */
    private void getPlaces(@NonNull Bundle b) {

        String baseURL = getString(R.string.places_base_url); // Google Places URL to make the requests.
        // Request String according to which category was selected.
        String request = getString(R.string.places_request) +
                b.getString("keyword") + getString(R.string.type) + b.getString("type") + "" +
                getString(R.string.API_KEY);


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(ClientRequestBuilder.getCacheClient(this))
                .addConverterFactory(GsonConverterFactory.create());

        // Creates the Retrofit.
        Retrofit retrofit = builder.build();

        ModelPlace.RetrofitArrayApi service = retrofit.create(ModelPlace.RetrofitArrayApi.class);
        // Makes the Request
        Call<Place> call = service.getPostInfo(baseURL + request);
        call.enqueue(new Callback<Place>() {
            /**
             * Gets the response from the places request, Adds the data received
             * to a ModelPlace object and adds it to the ModelPlace List.
             * @param call
             * @param response
             */
            @SuppressWarnings("JavaDoc")
            @Override
            public void onResponse(@SuppressWarnings("NullableProblems") Call<Place> call, @SuppressWarnings("NullableProblems") retrofit2.Response<Place> response) {

                mListPlace = response.body(); // Saves the Response
                progressBar.setVisibility(View.GONE);
                // for each place gets the relevant information and addes them to the list
                // noinspection ConstantConditions
                for (int i = 0; i < response.body().getResults().size(); i++) {

                    @SuppressWarnings("ConstantConditions") String title = response.body().getResults().get(i).getName(); // Gets Name of the Place
                    @SuppressWarnings("ConstantConditions") String vicinity = response.body().getResults().get(i).getVicinity(); // Gets Address of the Place
                    @SuppressWarnings("ConstantConditions") Double rating = response.body().getResults().get(i).getRating(); // Gets Rating of the Place
                    @SuppressWarnings("ConstantConditions") LatLng location = response.body().getResults().get(i).getGeometry().getLocation().getLatLng(); // Get the Location of the Place in LatLng type.
                    Boolean open = false;
                    Boolean openWasNull = false;
                    try {
                        // If there is an OpenNow attribute saved then
                        // noinspection ConstantConditions
                        if (response.body().getResults().get(i).getOpeningHours().getOpenNow() != null) {
                            // Save the attribute
                            // noinspection ConstantConditions
                            open = response.body().getResults().get(i).getOpeningHours().getOpenNow();
                        }
                    } catch (Exception NullPointerException) {
                        // Otherwise save that information to openWasNull.
                        openWasNull = true;
                    }
                    // Adds the new ModelPlace object to the list.
                    list.add(new ModelPlace(title, vicinity, rating, open, openWasNull, location
                    ));
                }
                // Display changes
                adapter.notifyDataSetChanged();
            }

            /**
             * Do nothing if the result fails.
             * @param call the call sent to receive place data from Google placesAPI
             * @param t exception given by API
             */
            @Override
            public void onFailure(@SuppressWarnings("NullableProblems") Call<Place> call, @SuppressWarnings("NullableProblems") Throwable t) {

            }
        });


    }


    /**
     * Overrides the onBackPressed that was defined in components,
     * because this back button is not going directly on the home screen.
     */
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }

    /**
     * Enables Back-button, implements onBackPressed() method defined in this class.
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
