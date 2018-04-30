package com.ncl.team20.seatonvalley;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ncl.team20.seatonvalley.activities.EventsActivity;
import com.ncl.team20.seatonvalley.activities.MainActivity;
import com.ncl.team20.seatonvalley.activities.NewsActivity;
import com.ncl.team20.seatonvalley.components.basic.Connection;
import com.ncl.team20.seatonvalley.components.basic.ConnectionDetector;
import com.ncl.team20.seatonvalley.data.posts.Beautifier;

import org.jsoup.Jsoup;

/**
 * This class extends the Connection abstract class.
 * It's used to open the links of the posts  news, latest news and events.<p>
 * Last Edit: 02/03/2018 by Stelios <p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @since 20/02/2018
 * @see Connection
 * @see ConnectionDetector
 * @see Jsoup
 * @see EventsActivity
 * @see MainActivity
 * @see NewsActivity
 *
 */
@SuppressWarnings("ConstantConditions")
public class DisplayPostDetails extends Connection {

    // Creates a ConnectionDetector to determine connectivity.
    private final ConnectionDetector detector = new ConnectionDetector(DisplayPostDetails.this);
    private Button addEvent;

    /**
     * Sets up the layout for the post and opens the selected post
     * @param savedInstanceState
     */
    @SuppressWarnings("JavaDoc")
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Sets UI elements
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // Opens inside an internal Webview.
        viewPost();


    }

    /**
     * Retrieves the webpage and then extracts the
     * important data from it and displays it in the activity
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void viewPost() {

        ImageButton share = findViewById(R.id.share_post_button);
        TextView titleText = findViewById(R.id.textTitle);
        share.setColorFilter(Color.WHITE);

        WebView webView = findViewById(R.id.post_webview);
        Intent i = getIntent();

        // Gets the position of the post.
        @SuppressWarnings("ConstantConditions") int position = i.getExtras().getInt("itemPosition");
        // Gets what type of post it is.
        int type = i.getExtras().getInt("type");

        // For Each if statement below it checks if there is internet connection and if so it opens the link,otherwise
        // it won't open and display an error message and register a Receiver.
        // The jsoup statement converts the title to a proper encoding and then the title is set as a title for the activity

        // For the latest news post in the MainActivity section.
        if (type == 1) {
            String title = Jsoup.parse(MainActivity.mListPost.get(position).getTitle().getRendered()).text();
            String description = MainActivity.mListPost.get(position).getExcerpt().getRendered();
            Beautifier beautify = new Beautifier(title, description);
            setTitle(beautify.getTitle()); // Sets Action Bar
            titleText.setText(title);

            // Share Code for Latest News
            if (detector.isInternetAvailable()) {
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(MainActivity.mListPost.get(position).getLink());
                //  Open WebView Inside the App.
                webView.setWebViewClient(new WebViewClient());
            } else {
                this.registerReceiver(this.mConnReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            }

            share.setOnClickListener(v -> {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, beautify.getTitle());
                sharingIntent.putExtra(Intent.EXTRA_TEXT, beautify.getDescription() + "For more information visit: " + MainActivity.mListPost.get(position).getLink());
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            });

            // For the NewsActivity section.
        } else if (type == 2) {
            String title = Jsoup.parse(NewsActivity.mListPost.get(position).getTitle().getRendered()).text();
            String description = NewsActivity.mListPost.get(position).getExcerpt().getRendered();
            Beautifier beautify = new Beautifier(title, description);
            setTitle(beautify.getTitle());
            titleText.setText(title);

            if (detector.isInternetAvailable()) {
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(NewsActivity.mListPost.get(position).getLink());
                //  Open WebView Inside the App.
                webView.setWebViewClient(new WebViewClient());
            } else {
                this.registerReceiver(this.mConnReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            }

            // Share Code for News
            share.setOnClickListener(v -> {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, beautify.getTitle());
                sharingIntent.putExtra(Intent.EXTRA_TEXT, beautify.getDescription() + "For more information visit: " + NewsActivity.mListPost.get(position).getLink());
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            });
            // For the Event section.
        } else if (type == 3) {
            String title = Jsoup.parse(EventsActivity.mListPost.get(position).getTitle().getRendered()).text();
            String description = EventsActivity.mListPost.get(position).getExcerpt().getRendered();
            Beautifier beautify = new Beautifier(title, description);
            setTitle(beautify.getTitle());
            titleText.setText(title);
            Button addEvent = findViewById(R.id.add_event);
            // Makes Visible the Add Event Button
            addEvent.setVisibility(View.VISIBLE);


            // Share Code for Events
            share.setOnClickListener(v -> {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, beautify.getTitle());
                sharingIntent.putExtra(Intent.EXTRA_TEXT, beautify.getDescription() + "For more information visit: " + EventsActivity.mListPost.get(position).getLink());
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            });

            // Add Event Code
            addEvent.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra(CalendarContract.Events.TITLE, beautify.getTitle());
                intent.putExtra(CalendarContract.Events.DESCRIPTION, beautify.getDescription());
                startActivity(intent);
            });

            if (detector.isInternetAvailable()) {
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(EventsActivity.mListPost.get(position).getLink());
                //  Open WebView Inside the App.
                webView.setWebViewClient(new WebViewClient());
            } else {
                this.registerReceiver(this.mConnReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            }


        }
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);


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
     * Enables Back-button, implements the correct implementation that is needed for this method.
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
