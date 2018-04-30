package com.ncl.team20.seatonvalley.components.web;

import android.annotation.SuppressLint;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.ncl.team20.seatonvalley.R;
import com.ncl.team20.seatonvalley.components.basic.Connection;
import com.ncl.team20.seatonvalley.components.basic.ConnectionDetector;

/**
 * DisplayWeatherForecast extends the Connection abstract class.
 * It's used to open the weather forecast for Seaton Valley.<p>
 * Last Edit: 26/03/2018 by Stelios Ioannou <p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @since 26/03/2018
 * @see WebView
 * @see Connection
 * @see ConnectionDetector
 */
@SuppressWarnings("RedundantCast")
public class DisplayWeatherForecast extends Connection {

    //Creates a ConnectionDetector to determine connectivity.
    private final ConnectionDetector detector = new ConnectionDetector(DisplayWeatherForecast.this);


    /**
     * Sets up the layout and loads the weather data in a webview
     * @param savedInstanceState stores the state of the application in a Bundle object
     */
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WebView webView = findViewById(R.id.weatherwebview);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ProgressBar progressBar = findViewById(R.id.progress_bar_weather);

        //Checks Connection.
        if (detector.isInternetAvailable()) {
            //Enables JavaScript
            webView.getSettings().setJavaScriptEnabled(true);
            //Opens the relevant web link.
            webView.loadUrl(getString(R.string.seaton_open_weather_link));
            //Custom WebView of SeatonWebClient
            webView.setWebViewClient(new SeatonWebClient(progressBar, 1));
        } else {
            webView.setNetworkAvailable(false);
            this.registerReceiver(this.mConnReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }
}
