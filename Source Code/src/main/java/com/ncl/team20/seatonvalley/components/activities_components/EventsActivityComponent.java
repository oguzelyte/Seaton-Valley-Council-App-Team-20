package com.ncl.team20.seatonvalley.components.activities_components;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ncl.team20.seatonvalley.adapters.PostsRecyclerViewAdapter;
import com.ncl.team20.seatonvalley.components.basic.ConnectionDetector;

/**
 * Gets the content of the EventsActivity by calling the appropriate methods. <p>
 * Last Edit: 2/03/2018 by Stelios Ioannou <p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @since 01/03/2018
 * @see com.ncl.team20.seatonvalley.activities.EventsActivity
 * @see PostsRecyclerViewAdapter
 * @see ConnectionDetector
 * @see GetPosts
 */

public class EventsActivityComponent extends GetPosts {

    /**
     * If connection is available get the number of Events posts specified in settings.
     * @param recyclerView view to add posts to
     * @param detector connection detector
     * @param context current state of application
     * @param posts number of posts to receive as specified in settings
     */
    protected void getContent(@NonNull RecyclerView recyclerView, @NonNull ConnectionDetector detector, Context context, int posts) {
        if (detector.isInternetAvailable()) {
            progressBar.setVisibility(View.VISIBLE);
            getEvents(posts);
            adapter = new PostsRecyclerViewAdapter(list, context, 3);
            recyclerView.setAdapter(adapter);
        } else {
            getEvents(posts);
            adapter = new PostsRecyclerViewAdapter(list, context, 3);
            recyclerView.setAdapter(adapter);
            this.registerReceiver(this.mConnReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    /**
     * Gets Post objects, with the category of events.
     * @param posts number of events to fetch
     */
    private void getEvents(int posts) {
        getPosts(posts, "17", this);
    }

}
