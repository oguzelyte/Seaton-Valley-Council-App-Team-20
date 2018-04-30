package com.ncl.team20.seatonvalley.components.web;

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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ncl.team20.seatonvalley.R;
import com.ncl.team20.seatonvalley.activities.EventsActivity;
import com.ncl.team20.seatonvalley.activities.MainActivity;
import com.ncl.team20.seatonvalley.activities.NewsActivity;
import com.ncl.team20.seatonvalley.components.basic.Connection;
import com.ncl.team20.seatonvalley.components.basic.ConnectionDetector;
import com.ncl.team20.seatonvalley.data.posts.Beautifier;

import org.jsoup.Jsoup;

/**
 * This class extends the Connection abstract class.
 * It's used to open the links of the posts news, latest news and events.
 * Type=0 is used for DisplayPostDetails in the SeatonWebClient
 * <p>
 * Last Edit: 27/03/2018 by Stelios Ioannou <p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @since 20/02/2018
 * @see Connection
 * @see ConnectionDetector
 * @see com.ncl.team20.seatonvalley.components.activities_components.EventsActivityComponent
 * @see com.ncl.team20.seatonvalley.components.activities_components.MainActivityComponent
 * @see com.ncl.team20.seatonvalley.components.activities_components.NewsActivityComponent
 * @see com.ncl.team20.seatonvalley.components.activities_components.GetPosts
 * @see NewsActivity
 * @see EventsActivity
 * @see NewsActivity
 * @see Beautifier
 * @see WebView
 * @see Jsoup
 */
@SuppressWarnings("ConstantConditions")
public class DisplayPostDetails extends Connection {

    // Creates a ConnectionDetector to determine connectivity.
    private final ConnectionDetector detector = new ConnectionDetector(DisplayPostDetails.this);
    // Button to add the event
    private Button addEvent;
    // ProgressBar to indicate the loading of the post.
    private ProgressBar progressBar;
    // WebView to display the Post
    private WebView webView;
    // osition of the Post
    private int position;
    // Beautifer to beautify post content.
    private Beautifier beautify;
    // Button to share the article.
    private ImageButton share;

    /**
     * Sets up the layout.
     * @param savedInstanceState stores the state of the application in a Bundle object
     */
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
        // Opens inside an internal custom WebView depending from what type of Activity it is.
        viewPost();
    }

    /**
     * Retrieves the post from the Seaton Valley website, converts its encoding using Jsoup and
     * calls Beautify which strips away the HTML markup to get the title and page content.
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void viewPost() {
        share = findViewById(R.id.share_post_button);
        TextView titleText = findViewById(R.id.textTitle);
        // Makes the Share Button White.
        share.setColorFilter(Color.WHITE);
        webView = findViewById(R.id.post_webview);
        // Enables JavaScript
        webView.getSettings().setJavaScriptEnabled(true);
        progressBar = findViewById(R.id.progress_bar_post);
        // Create a custom WebClient from SeatonWebClient.java ((passes the progress bar,and the type)
        webView.setWebViewClient(new SeatonWebClient(progressBar, 0));
        Intent i = getIntent();
        // Gets the position of the post.
        // noinspection ConstantConditions
        position = i.getExtras().getInt("itemPosition");
        // Gets what type of post it is.
        int type = i.getExtras().getInt("type");


        // For Each if statement below it checks if there is internet connection and opens the link, otherwise
        // it won't open and displays an error message and register a Receiver.
        // The jsoup statement converts the title to a compatible encoding and then the title is set as a title for the activity
        // Type=0 is for Main Activity,Type=1 News Activity,Type 2=Events Activity

        // For the latest news post in the MainActivity section.
        if (type == 1) {
            String title = Jsoup.parse(MainActivity.mListPost.get(position).getTitle().getRendered()).text();
            String description = MainActivity.mListPost.get(position).getExcerpt().getRendered();
            beautify = new Beautifier(title, description);
            setTitle(beautify.getTitle()); // Sets Action Bar
            titleText.setText(title);
            // Method call for the Latest News
            latestNews();
            // For the NewsActivity section.
        } else if (type == 2) {
            String title = Jsoup.parse(NewsActivity.mListPost.get(position).getTitle().getRendered()).text();
            String description = NewsActivity.mListPost.get(position).getExcerpt().getRendered();
            beautify = new Beautifier(title, description);
            setTitle(beautify.getTitle());
            this.setTitle(title);
            // Sets title for the activity
            titleText.setText(title);
            // Method call for the News Activity
            news();
            // For the Event section.
        } else if (type == 3) {
            String title = Jsoup.parse(EventsActivity.mListPost.get(position).getTitle().getRendered()).text();
            String description = EventsActivity.mListPost.get(position).getExcerpt().getRendered();
            beautify = new Beautifier(title, description);
            setTitle(beautify.getTitle());
            // Sets title for the activity
            titleText.setText(title);
            // Method call for the Events Activity
            events();
        }
        // Custom Animation
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
    }

    /**
     * Actions to Perform for the ‘Latest News' feature in the MainActivity.
     */
    private void latestNews() {
        if (detector.isInternetAvailable()) {
            // Gets the Url and Loads into the Webview.
            webView.loadUrl(MainActivity.mListPost.get(position).getLink());
            //  Open WebView Inside the App with the SeatonWebClient.
            webView.setWebViewClient(new SeatonWebClient(progressBar, 0));
        } else {
            this.registerReceiver(this.mConnReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        // Share Code for Latest News
        share.setOnClickListener(v -> {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, beautify.getTitle());
            sharingIntent.putExtra(Intent.EXTRA_TEXT, beautify.getDescription() + "For more information visit: " + MainActivity.mListPost.get(position).getLink());
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        });
    }

    /**
     * Actions to Perform for the NewsActivity
     */
    private void news() {
        if (detector.isInternetAvailable()) {
            // Gets the Url and Loads into the Webview.
            webView.loadUrl(NewsActivity.mListPost.get(position).getLink());
            //  Open WebView Inside the App with the SeatonWebClient.
            webView.setWebViewClient(new SeatonWebClient(progressBar, 0));
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
    }

    /**
     * Actions to perform for the EventsActivity
     * */
    private void events() {
        Button addEvent = findViewById(R.id.add_event);
        // Makes Visible the Add Event Button
        addEvent.setVisibility(View.VISIBLE);
        // Share Button Code for Events
        share.setOnClickListener(v -> {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, beautify.getTitle());
            sharingIntent.putExtra(Intent.EXTRA_TEXT, beautify.getDescription() + "For more information visit: " + EventsActivity.mListPost.get(position).getLink());
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        });
        // Add Event Button Code
        addEvent.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_INSERT);
            intent.setType("vnd.android.cursor.item/event");
            intent.putExtra(CalendarContract.Events.TITLE, beautify.getTitle());
            intent.putExtra(CalendarContract.Events.DESCRIPTION, beautify.getDescription());
            startActivity(intent);
        });
        if (detector.isInternetAvailable()) {
            webView.loadUrl(EventsActivity.mListPost.get(position).getLink());
            webView.setWebViewClient(new SeatonWebClient(progressBar, 0));
        } else {
            this.registerReceiver(this.mConnReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    /**
     * Overrides the onBackPressed that was defined in components,
     * because this back button goes to FindActivity.
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