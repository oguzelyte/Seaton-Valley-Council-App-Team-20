package com.ncl.team20.seatonvalley.data.posts;


import android.support.annotation.NonNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * This is class is used to store the post details for EventsActivity, NewsActivity and MainActivity. <p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @since 20/02/2018
 * @see com.ncl.team20.seatonvalley.activities.EventsActivity
 * @see com.ncl.team20.seatonvalley.activities.NewsActivity
 * @see com.ncl.team20.seatonvalley.activities.MainActivity
 * @see retrofit2.Retrofit
 */

public class ModelPost {

    public final String title;
    public final String description;
    private final int id;

    /**
     * Constructor for ModelPost.
     * @param mtitle title of the post to display
     * @param mdescription description of the post to display
     * @param mid id of the post to display
     */
    public ModelPost(String mtitle, String mdescription, int mid) {

        this.title = mtitle;
        this.description = mdescription;
        this.id = mid;
    }

    /**
     * @return current id
     */
    public int getId() {
        return id;
    }

    /**
     *  Overrides the equals method in order to compare posts.
     *  Post id is unique. Used by list.contains to avoid duplicates
     *  @return  true if the posts have the same ids.
     */
    @Override
    public boolean equals(@NonNull Object obj) {
        //They are equal if they have the same post id.
        if (this.getClass() == obj.getClass()) {
            ModelPost other = (ModelPost) obj;
            if (this.id == other.id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the Post using Retrofit
     */
    public interface RetrofitArrayApi {
        @NonNull
        @GET
        Call<List<Post>> getPostInfo(@Url String url);
    }
}

