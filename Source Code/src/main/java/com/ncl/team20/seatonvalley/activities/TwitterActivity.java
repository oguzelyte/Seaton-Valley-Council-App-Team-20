package com.ncl.team20.seatonvalley.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.ncl.team20.seatonvalley.R;
import com.ncl.team20.seatonvalley.components.basic.Connection;
import com.ncl.team20.seatonvalley.components.basic.ConnectionDetector;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthException;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

/**
 * TwitterActivity is used to display the tweets of
 * Seaton valley and it extends the Connection component. <p>
 * Last Edit: 02/03/2018 by Stelios Ioannou <p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author David Mcgeough
 * @author Stelios Ioannou
 * @since 20/02/2018
 * @see  Connection
 * @see TweetTimelineListAdapter
 * @see SwipeRefreshLayout
 * @see ConnectionDetector
 * @see Twitter
 * @see TwitterConfig
 *
 */
public class TwitterActivity extends Connection {

    private final ConnectionDetector detector = new ConnectionDetector(TwitterActivity.this);
    private SwipeRefreshLayout swipeRefreshLayout;
    private TweetTimelineListAdapter adapter;

    /**
     * Launches the LoginActivity when a guest user tries to favorite a Tweet.
     *
     */
    private final Callback<Tweet> actionCallback = new Callback<Tweet>() {
        @Override
        public void success(Result<Tweet> result) {
            // Intentionally blank
        }

        @Override
        public void failure(TwitterException exception) {
            if (exception instanceof TwitterAuthException) {
                // launch custom login flow
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(login);
            }
        }
    };

    /**
     * Sets up the TwitterActivity layout and retrieves
     * the @SeatonValleyCC tweets using the Twitter API.
     *
     * @param savedInstanceState
     */
    @SuppressWarnings("JavaDoc")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);

        setContentView(R.layout.activity_twitter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.twitter_title);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_twitter);
        ProgressBar progressBar = findViewById(R.id.progress_bar_twitter);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        // Checks if there is internet connections
        if (detector.isInternetAvailable()) {

            progressBar.setVisibility(View.VISIBLE);

            // Creates a twitter config
            TwitterConfig config = new TwitterConfig.Builder(this)
                    .twitterAuthConfig(new TwitterAuthConfig(getString(R.string.consumer_key), getString(R.string.consumer_secret)))
                    .debug(true)
                    .build();

            // Initializes it
            Twitter.initialize(config);


            // Creates the UserTimeLine
            final UserTimeline userTimeline = new UserTimeline.Builder()
                    .screenName(getString(R.string.council_twitter_name))
                    .build();

            // Overrides the userTimeLine next to fix the bug that resides in the Twitter sdk.
            userTimeline.next(null, new Callback<TimelineResult<Tweet>>() {
                        @Override
                        public void success(Result<TimelineResult<Tweet>> result) {
                            ListView listView = findViewById(R.id.twitter_list);
                            TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(TwitterActivity.this)
                                    .setTimeline(userTimeline)
                                    .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
                                    .setOnActionCallback(actionCallback)
                                    .build();
                            listView.setAdapter(adapter);
                            // Pull to Refresh Action to Update Twitter.
                            swipeRefreshLayout.setOnRefreshListener(() -> {
                                swipeRefreshLayout.setRefreshing(true);
                                adapter.refresh(new Callback<TimelineResult<Tweet>>() {
                                    @Override
                                    public void success(Result<TimelineResult<Tweet>> result1) {
                                        swipeRefreshLayout.setRefreshing(false);
                                    }

                                    @Override
                                    public void failure(TwitterException exception) {
                                        //  Toast or some other action
                                    }
                                });
                            });
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void failure(@NonNull TwitterException exception) {
                            exception.printStackTrace();
                        }


                    }
            );


        } else {
            progressBar.setVisibility(View.GONE);
            this.registerReceiver(this.mConnReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }


    /**
     * Ensures that on resume,that the correct element is selected.
     * @see NavigationView
     */
    @Override
    public void onResume() {
        super.onResume();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_twitter);
    }


}
