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
import com.ncl.team20.seatonvalley.components.activities_components.NewsActivityComponent;
import com.ncl.team20.seatonvalley.components.basic.ConnectionDetector;

/**
 * NewsActivity displays news and extends NewsActivityComponent to do so. <p>
 * Last Edit: 02/03/2018 by Stelios Ioannou <p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @since 20/02/2018
 * @see NewsActivityComponent
 * @see ConnectionDetector
 */
public class NewsActivity extends NewsActivityComponent {

    private final ConnectionDetector detector = new ConnectionDetector(NewsActivity.this);

    /**
     * Sets up the NewsActivity layout  and retrieves the specified number of news items from settings.
     * @param savedInstanceState stores the state of the application in a Bundle object
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        // Custom Animation on Create
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("News");

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_news);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar_news);
        progressBar.setVisibility(View.GONE);

        SharedPreferences settings = getSharedPreferences("settings",
                Context.MODE_PRIVATE);
        int posts =settings.getInt("news-posts", 10);


        // Gets the Latest News Content With the Specified Number of Posts
        getContent(recyclerView, detector, this, posts);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(NewsActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

    }

    /** 
     * Ensures that on resume, the correct element is selected.
     */
    public void onResume() {
        super.onResume();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_news);
    }


}
