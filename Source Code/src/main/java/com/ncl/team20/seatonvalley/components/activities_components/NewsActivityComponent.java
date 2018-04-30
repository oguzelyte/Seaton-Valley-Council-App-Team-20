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
 * Gets the content of the News Activity by calling the appropriate methods.
 * Extends GetPosts. <p>
 * Last Edit: 2/03/2018 by Stelios Ioannou <p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @since 01/03/2018
 * @see GetPosts
 * @see com.ncl.team20.seatonvalley.activities.NewsActivity
 * @see PostsRecyclerViewAdapter
 * @see ConnectionDetector
 */

public class NewsActivityComponent extends GetPosts {

    /**
     * If connection is available get the number of News posts specified in settings.
     * @param recyclerView view to add posts to
     * @param detector connection detector
     * @param context current state of application
     * @param posts number of posts to receive as specified in settings
     */
    protected void getContent(@NonNull RecyclerView recyclerView, @NonNull ConnectionDetector detector, Context context, int posts) {
        if (detector.isInternetAvailable()) {
            progressBar.setVisibility(View.VISIBLE);
            getNews(posts);
            adapter = new PostsRecyclerViewAdapter(list, context, 2);
            recyclerView.setAdapter(adapter);
        } else {
            getNews(posts);
            adapter = new PostsRecyclerViewAdapter(list, context, 2);
            recyclerView.setAdapter(adapter);
            this.registerReceiver(this.mConnReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }


    /**
     * Gets Post objects with the Category of News.
     * @param posts number of News posts to fetch
     */
    private void getNews(int posts) {
        getPosts(posts, "16", this);
    }


}
