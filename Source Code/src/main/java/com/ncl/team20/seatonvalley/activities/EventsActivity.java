package com.ncl.team20.seatonvalley.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ncl.team20.seatonvalley.R;
import com.ncl.team20.seatonvalley.components.activities_components.EventsActivityComponent;
import com.ncl.team20.seatonvalley.components.basic.ConnectionDetector;

/**
 * EventsActivity displays events in Seaton Valley
 * and extends EventsActivityComponent to do so.
 * <p>
 * Last Edit: 02/03/2018 by Stelios Ioannou <p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @since 20/02/2018
 * @see EventsActivityComponent
 * @see ConnectionDetector
 */
public class EventsActivity extends EventsActivityComponent {

    // Creates a detector object.
    private final ConnectionDetector detector = new ConnectionDetector(EventsActivity.this);

    /**
     * Sets up the EventsActivity layout and retrieves the number of events specified in settings.
     * @param savedInstanceState stores the state of the application in a Bundle object
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Custom Animation on Create
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);

        setContentView(R.layout.activity_events);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.events_title);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_events);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_events);
        progressBar = findViewById(R.id.progress_bar_events);
        progressBar.setVisibility(View.GONE);

        SharedPreferences settings = getSharedPreferences("settings",
                Context.MODE_PRIVATE);
        int posts =settings.getInt("events-posts", 10);


        // Gets the latest content of Events.
        getContent(recyclerView, detector, this, posts);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(EventsActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

    }

    /**
     * Ensures that on resume, the correct element is selected.
     */
    @Override
    public void onResume() {
        super.onResume();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_events);
    }


}
