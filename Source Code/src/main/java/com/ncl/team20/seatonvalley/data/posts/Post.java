package com.ncl.team20.seatonvalley.data.posts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Simple POJO object that uses other POJO objects, in order to be saved as one simple POJO object.<p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @since 20/02/2018
 * @see com.ncl.team20.seatonvalley.activities.NewsActivity
 * @see com.ncl.team20.seatonvalley.activities.MainActivity
 * @see com.ncl.team20.seatonvalley.activities.EventsActivity
 * @see Exerpt
 * @see Title
 */
public class Post {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private Title title;

    @SerializedName("excerpt")
    @Expose
    private Exerpt excerpt;
    @SerializedName("link")
    @Expose
    private String link;

    /**
     * @return current id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return current title
     */
    public Title getTitle() {
        return title;
    }

    /**
     * @return current excerpt
     */
    public Exerpt getExcerpt() {
        return excerpt;
    }

    /**
     * @return current link
     */
    public String getLink() {
        return link;
    }

}








