package com.ncl.team20.seatonvalley.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;

import com.google.firebase.messaging.FirebaseMessaging;
import com.ncl.team20.seatonvalley.R;
import com.ncl.team20.seatonvalley.components.activities_components.MainActivityComponent;
import com.ncl.team20.seatonvalley.components.basic.ConnectionDetector;
import com.ncl.team20.seatonvalley.components.basic.SimpleSnapHelper;

import cz.intik.overflowindicator.OverflowPagerIndicator;

import static android.view.View.GONE;

/**
 * MainActivity loads when the application is launching, it extends the abstract class Connection.<p>
 * Last Edit: 23/03/2018 by Stelios Ioannou <p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @author Sam Clayton
 * @since 20/02/2018
 * @see MainActivityComponent
 * @see ConnectionDetector
 * @see FirebaseMessaging
 * @see OverflowPagerIndicator
 */

@SuppressWarnings("RedundantCast")
public class MainActivity extends MainActivityComponent {

    // Creates a ConnectionDetector to determine connectivity.
    private final ConnectionDetector detector = new ConnectionDetector(MainActivity.this);

    // Creates UI elements.
    private int position;
    private LinearLayout btnNews;
    private LinearLayout btnTwitter;
    private LinearLayout btnEvents;
    private LinearLayout btnFind;
    private LinearLayout btnContact;
    private LinearLayout btnReport;
    private LinearLayout btnCouncil;
    private LinearLayout btnInfo;
    private LinearLayout btnSettings;
    private NavigationView navigationView;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean launched = false;

    /**
     * Sets up the MainActivity layout and attaches listener to the activity buttons.
     * @param savedInstanceState stores the state of the application in a Bundle object
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Gets the Shared Preferences
        SharedPreferences settings = getSharedPreferences("settings",
                Context.MODE_PRIVATE);
        // Gets the value of the preference for NotificatiQons
        boolean isEnabled = settings.getBoolean("key", true);
        // Subscribe if Enabled
        if (isEnabled) {
            FirebaseMessaging.getInstance().subscribeToTopic("notifications");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Checks if a previous activity was already launched,to avoid re-opening.
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.home_screen_title));

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Gets UI elements
        btnNews = (LinearLayout) findViewById(R.id.btnNews);
        btnTwitter = findViewById(R.id.btnTwitter);
        btnEvents = findViewById(R.id.btnEvents);
        btnFind = findViewById(R.id.btnFind);
        btnContact = findViewById(R.id.btnContact);
        btnReport = findViewById(R.id.btnReport);
        btnCouncil = findViewById(R.id.btnCouncil);
        btnInfo = findViewById(R.id.btnInfo);
        btnSettings = findViewById(R.id.btnSettings);
        recyclerView = findViewById(R.id.recycler_view_latest);
        recyclerView.setMinimumHeight(180);
        progressBar = findViewById(R.id.progress_bar_latest);
        progressBar.setVisibility(GONE);
        mLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setClickable(false);


        // Gets the Main Activity Content
        getContent(recyclerView, detector, this);
        getIndicator();


        btnNews.setOnClickListener(v -> {
            btnNews.setEnabled(false);
            Intent intent = new Intent(getApplicationContext(), NewsActivity.class);
            startActivity(intent);
        });

        btnTwitter.setOnClickListener(v -> {
            btnNews.setEnabled(false);
            Intent intent = new Intent(getApplicationContext(), TwitterActivity.class);
            startActivity(intent);
        });

        btnEvents.setOnClickListener(v -> {
            btnNews.setEnabled(false);
            Intent intent = new Intent(getApplicationContext(), EventsActivity.class);
            startActivity(intent);
        });

        btnFind.setOnClickListener(v -> {
            btnNews.setEnabled(false);
            Intent intent = new Intent(getApplicationContext(), FindActivity.class);
            startActivity(intent);
        });

        btnContact.setOnClickListener(v -> {
            btnNews.setEnabled(false);
            Intent intent = new Intent(getApplicationContext(), ContactActivity.class);
            startActivity(intent);
        });

        btnReport.setOnClickListener(v -> {
            btnNews.setEnabled(false);
            Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
            startActivity(intent);
        });

        btnCouncil.setOnClickListener(v -> {
            btnNews.setEnabled(false);
            Intent intent = new Intent(getApplicationContext(), CouncilActivity.class);
            startActivity(intent);
        });

        btnInfo.setOnClickListener(v -> {
            btnNews.setEnabled(false);
            Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
            startActivity(intent);
        });

        btnSettings.setOnClickListener(v -> {
            btnNews.setEnabled(false);
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        });

        // Custom Transition
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);

    }


    /**
     * Restores the MainActivity to its usual state and registers a connection receiver.
     * @since 20/02/2018
     */
    @Override
    protected void onResume() {
        super.onResume();
        // Re-enables the UI buttons when the MainActivity is resumed.
        btnNews.setEnabled(true);
        btnTwitter.setEnabled(true);
        btnEvents.setEnabled(true);
        btnFind.setEnabled(true);
        btnContact.setEnabled(true);
        btnReport.setEnabled(true);
        btnCouncil.setEnabled(true);
        btnInfo.setEnabled(true);
        btnSettings.setEnabled(true);
        // Sets the Correct Button in the Drawer
        navigationView.setCheckedItem(R.id.nav_home);

        // Refreshes the content upon resume if the activity was already launched.
        if (launched) {
            this.clearList();
            getContent(recyclerView, detector, this);
            getIndicator();
        }
        // After the first-launch it's always true.
        if (!launched) {
            launched = true;
        }
    }

    /**
     * Disable Back-Functionality for this activity.
     * @return false
     */
    @Override
    public boolean onSupportNavigateUp() {
        return false;
    }

    /**
     * Gets the selected post indicator.
     */
    private void getIndicator() {
        try {
            // Parses the Indicator from the Layout
            final OverflowPagerIndicator overflowPagerIndicator = findViewById(R.id.view_pager_indicator);
            // Attaches ihe Indicator to the Recycle View
            overflowPagerIndicator.attachToRecyclerView(recyclerView);
            // Creates a SimpleSnapHelper object that Snapos the Items (Like Pages)
            SimpleSnapHelper snapHelper = new SimpleSnapHelper(overflowPagerIndicator) {
            };
            // Attaches the recycle viewer to the Snap Helper.
            snapHelper.attachToRecyclerView(recyclerView);

            // Custom method by Stelios Ioannou to ensure that the indicator selected the correct item.
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                // Custom Implementation of SimpleSnapHelper from Petr Introvic
                // Default Implementation didn't return the proper snapping position to the indicator.
                // Implemented by Stelios Ioannou on 23/03/2018
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    // Ensures that the selected indicator is correct,by getting the position
                    // After Snapping
                    // There
                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                        View centerView = snapHelper.findSnapView(mLayoutManager);
                        // In-case the recycle viewer is empty,catches the excpection in order to not crash.
                        try {
                            assert centerView != null;
                            position = mLayoutManager.getPosition(centerView);
                        } catch (Exception ignored) {
                        }
                        // Selects Position.
                        overflowPagerIndicator.onPageSelected(position);
                    }
                }
            });
        } catch (Exception ignored) {

        }
    }


}