package com.ncl.team20.seatonvalley.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;

import com.ncl.team20.seatonvalley.R;
import com.ncl.team20.seatonvalley.components.basic.Drawer;
import com.ncl.team20.seatonvalley.components.places.DisplayPlacesList;

/**
 * FindActivity displays the Places categories and set them their respective listeners.<p>
 * Last Edit: 02/03/2018 by Stelios Ioannou <p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @since 20/02/2018
 * @see Drawer
 * @see CardView
 */
public class FindActivity extends Drawer {

    /**
     * Sets up the FindActivity layout.
     * @param savedInstanceState stores the state of the application in a Bundle object
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Custom Animation on Create
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);

        setContentView(R.layout.activity_find);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Places");

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_find);

        setCategoriesListeners();
    }


    /**
     * Sets the categories Listeners, passes extra intent information when category clicked
     * to determine what kind of places to retrieve.
     */
    private void setCategoriesListeners() {
        final CardView cardRestaurants = findViewById(R.id.restaurants_card);
        final CardView cardCoffeeShops = findViewById(R.id.coffee_shops_card);
        final CardView cardMarkets = findViewById(R.id.markets_card);
        final CardView cardEntertainment = findViewById(R.id.entertainment_card);
        final CardView cardServices = findViewById(R.id.services_card);
        final CardView cardPetrolStations = findViewById(R.id.petrol_stations_card);


        cardRestaurants.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), DisplayPlacesList.class);
            intent.putExtra("title", "Restaurants");
            intent.putExtra("keyword", "");
            intent.putExtra("type", "restaurant");
            startActivity(intent);
        });
        cardCoffeeShops.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), DisplayPlacesList.class);
            intent.putExtra("title", "Coffee Shops");
            intent.putExtra("keyword", "");
            intent.putExtra("type", "cafe");
            startActivity(intent);

        });
        cardMarkets.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), DisplayPlacesList.class);
            intent.putExtra("title", "Shops");
            intent.putExtra("keyword", "");
            intent.putExtra("type", "store");
            startActivity(intent);
        });
        cardEntertainment.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), DisplayPlacesList.class);
            intent.putExtra("title", "Points of Interest");
            intent.putExtra("keyword", "");
            intent.putExtra("type", "point_of_interest");
            startActivity(intent);
        });
        cardServices.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), DisplayPlacesList.class);
            intent.putExtra("title", "ATM");
            intent.putExtra("keyword", "");
            intent.putExtra("type", "atm");
            startActivity(intent);
        });
        cardPetrolStations.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), DisplayPlacesList.class);
            intent.putExtra("title", "Petrol Stations");
            intent.putExtra("keyword", "");
            intent.putExtra("type", "gas_station");
            startActivity(intent);
        });
    }

    /**
     * Ensures that on resume, the correct element is selected.
     */
    @Override
    public void onResume() {
        super.onResume();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_find);
    }

}
