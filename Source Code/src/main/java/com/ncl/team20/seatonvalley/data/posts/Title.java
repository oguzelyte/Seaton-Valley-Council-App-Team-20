package com.ncl.team20.seatonvalley.data.posts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Simple POJO object to be used to save the post title for
 * EventsActivity, NewsActivity and MainActivity. <p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @since 20/02/2018
 * @see com.ncl.team20.seatonvalley.activities.NewsActivity
 * @see com.ncl.team20.seatonvalley.activities.MainActivity
 * @see com.ncl.team20.seatonvalley.activities.EventsActivity
 */
public class Title {

    @SerializedName("rendered")
    @Expose
    private String rendered;

    /**
     *
     * @return current title
     */
    public String getRendered() {
        return rendered;
    }

}