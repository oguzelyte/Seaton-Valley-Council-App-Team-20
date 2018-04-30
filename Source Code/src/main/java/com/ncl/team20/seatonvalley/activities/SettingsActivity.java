package com.ncl.team20.seatonvalley.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.ncl.team20.seatonvalley.R;
import com.ncl.team20.seatonvalley.components.basic.Drawer;
import com.twitter.sdk.android.core.TwitterCore;

import org.apache.commons.io.FileUtils;

/**
 * SettingsActivity is used to control the applications settings such as:
 * number of  news and event posts to display, toggle notifications and clearing cache.<p>
 * Last Edit: 02/03/2018 by Stelios Ioannou
 *  * Documentation Edit: 22/04/2018 by Alex Peebles <p>
 * @author Stelios Ioannou
 * @since 20/02/2018
 * @see Drawer
 * @see FirebaseMessaging
 *
 */
public class SettingsActivity extends Drawer {

    /**
     * Sets up the SettingsActivity layout and settings
     * based either default or user defined preferences
     * @param savedInstanceState state of the application in a Bundle object
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Custom Animation on Create
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.settings_title);

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_settings);

        final Button btnClearCache = findViewById(R.id.clear_cache_btn);

        btnClearCache.setOnClickListener(v ->

        {
            btnClearCache.setEnabled(false);
            clearCache();
        });

        // Creates a SharedPreferences object
        SharedPreferences settings = getSharedPreferences("settings",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        // Create a Switch object for the relevant Switch in content settings.
        final Switch switchNotifications = findViewById(R.id.toggle_notifications);


        // Sets the Switch to the relevant state that is saved in SharedPreferences.
        switchNotifications.setChecked(settings.getBoolean("key", true));

        // On Switch
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Saves the relevant info to the SharedPreferences
            if (isChecked) {
                FirebaseMessaging.getInstance().subscribeToTopic("notifications");
                editor.putBoolean("key", true);
            } else {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("notifications");
                editor.putBoolean("key", false);
            }
            editor.apply();
        });

        // Spinner to select number of posts to display.
        final Spinner newsToDisplay = findViewById(R.id.news_to_display);

        // Def Value 0,0 is the position of the element in the list,since list[0] is 10 , the default value is 10
        newsToDisplay.setSelection(settings.getInt("position-news", 0));

        // Updates News to Display on Selected Items
        newsToDisplay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(@NonNull AdapterView<?> adapterView, View view, int i, long l) {
                int posts = Integer.parseInt(String.valueOf(adapterView.getItemAtPosition(i)));
                editor.putInt("news-posts", posts).apply();
                editor.putInt("position-news", i).apply();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        final Spinner eventsToDisplay = findViewById(R.id.events_to_display);

        // Def Value 0,0 is the position of the element in the list,since list[0] is 10 , the default value is 10
        eventsToDisplay.setSelection(settings.getInt("position-events", 0));

        // Updates Events to Display on Selected Items
        eventsToDisplay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(@NonNull AdapterView<?> adapterView, View view, int i, long l) {
                int posts = Integer.parseInt(String.valueOf(adapterView.getItemAtPosition(i)));
                editor.putInt("events-posts", posts).apply();
                editor.putInt("position-events", i).apply();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    /**
     * Clears App Cache.
     */
    private void clearCache() {
        FileUtils.deleteQuietly(this.getCacheDir());

        Context context = getApplicationContext();
        CharSequence text = "Your cache has been cleared.";
        int duration = Toast.LENGTH_LONG;
        // Logs out of Twitter if there is an Active Session.
        try {
            if (TwitterCore.getInstance().getSessionManager() != null) {
                TwitterCore.getInstance().getSessionManager().clearActiveSession();
            }
        } catch (Exception e) {
            Log.d("e", "Twitter was not initialised, can’t log out.");
        }
        // Display the Message.
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    /**
     * Ensures that on resume, the correct element is selected.
     */
    @Override
    public void onResume() {
        super.onResume();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_settings);
    }


}
