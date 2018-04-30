package com.ncl.team20.seatonvalley.components.web;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 *  SeatonWebClient extends the WebViewClient.
 * <p>
 * It's used to apply custom formatting for the relevant links and manage the 
 * progress bar.
 * <p>
 * These variables: Type = 0 News/Events Articles, Type = 1 Weather Forecast, 
 * are used to determine whether or not to apply formatting and what kind of 
 * formatting to apply.
 *
 * The formatting is applied with JavaScript and some jQuery with the websites 
 * structure that was found on 26.03.2018.
 * <p>
 * (The formatting may not work if the website decide to change their structure,
 * but in such case the program won’t brake it will just not apply formatting.)
 * <p>
 * There is a handler with delay for the weather forecast styling to ensure
 * even after the page is loaded that the nested widget also renders.
 * The time for the delay is calculated with timeToLoadForecast,
 * it assumes it will take half of the time that it took the page to load ,
 * to load the nested widgets and assumes that the connection is stable.
 * There is a minimum threshold of 200ms.
 * <p>
 * Last Edit: 26/03/2018 by Stelios Ioannou<p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @since 26/03/2018
 * @see WebViewClient
 */
class SeatonWebClient extends WebViewClient {

    // ProgressBar
    private final ProgressBar progressBar;
    // Website type
    private final int type;
    // Checks if its launched
    private boolean launched = false;
    // Timer to load the website
    private long timeToLoadForecast;

    /**
     * Constructor for SeatonWebClient.
     * @param m_progressBar  progressBar to assign
     * @param m_type website type
     */
    public SeatonWebClient(ProgressBar m_progressBar, int m_type) {
        this.progressBar = m_progressBar;
        this.type = m_type;
    }

    /**
     * When the page has finished loading get the content
     * and apply some JQuery styling to the page before displaying.
     * @param view view to load the retrieved webpage in
     * @param url String representation of the URL to load.
     */
    @Override
    public void onPageFinished(@NonNull WebView view, @NonNull String url) {
        super.onPageFinished(view, url);

        // If it's for News/Events articles and it's not already launched (We used the launched to ensure
        // that we don't apply styling to redirected nested urls.
        if (type == 0 && !launched) {
            view.loadUrl("javascript:(function() { " +
                    // Gets the post content
                    "var post = document.querySelectorAll('.gdlr-item.gdlr-blog-full.gdlr-item-start-content');" +
                    // Adds an id to the post class.
                    "if (post.length) {post[0].id = 'post'};" +
                    // Hides all the content expected in the post
                    "jQuery('body > :not(#post)').hide();" +
                    // Shows the post content.
                    "jQuery('#post').appendTo('body');" +
                    // Change the max-width to render across the screen
                    "jQuery('.container').css('max-width', '2560px');" +
                    // Changes the colour to white to ensure consistency
                    "document.body.style.backgroundColor = 'white';" +
                    "})()");
            // Makes the WebView visible to show the formatted page.
            view.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            // Sets that the formatting was applied and that it was launched.
            launched = true;
            // If its for the Weather Forecast and the url matches the weather forecast link,applies formatting
        } else if (type == 1 && !launched) {
            // Calculates the time needed to load the Weather Forecast
            timeToLoadForecast = System.currentTimeMillis() - timeToLoadForecast;
            // Added a minimum 400ms delay in order to give a chance for nested receiver to load in order to ensure that it will apply the style.(It will be halved to 200ms)
            new Handler().postDelayed(() -> {
                if (timeToLoadForecast < 400) {
                    timeToLoadForecast = 400;
                }
                view.loadUrl("javascript:(function() { " +
                        // Hides everything except the widget
                        "$('body > :not(#widget)').hide();" +
                        // Adds the widget to the body
                        "$('#widget').appendTo('body');" +
                        // Adds to a var the class for the search in order to add an id to it.
                        "var search = document.querySelectorAll('.row.search-cities');" +
                        // Adds an id to the class
                        "if (search.length) {search[0].id = 'search'};" +
                        // Gets the element with the id search
                        "var searchElement = document.getElementById('search');" +
                        // Removes it from the Page
                        "searchElement.parentNode.removeChild(searchElement);" +
                        // Changes the color of the links to match the color of the app theme
                        "$('a').css({color: '#732772'});" +
                        // Changes the color of the title to match the color of the app theme.
                        "$('.widget__title').css({color: '#732772'});" +
                        // Changes the widget link color to match the color of the app theme.
                        "$('.weather-widget__link').css({color: '#732772'});" +
                        "})()");
                // Makes the View
                view.setVisibility(View.VISIBLE);
                // Removes the Visibility of the Progress Bar
                progressBar.setVisibility(View.GONE);
            }, timeToLoadForecast / 2);
            launched = true;
        } else {
            // For Redirected links just do this.
            view.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    /**
     * Hides the view until the formatting has been applied in onPageFinished
     * @param view view to load the retrieved webpage in
     * @param url String representation of the URL to load.
     * @param favicon bitmap image to display
     */
    @Override
    public void onPageStarted(@NonNull WebView view, String url, Bitmap favicon) {
        // Ensures that the view upon loading is gone,so that it will only show the formatted page.
        view.setVisibility(View.GONE);
        // Starts timer for the time to loadforecast.
        timeToLoadForecast = System.currentTimeMillis();
        super.onPageStarted(view, url, favicon);
    }


}