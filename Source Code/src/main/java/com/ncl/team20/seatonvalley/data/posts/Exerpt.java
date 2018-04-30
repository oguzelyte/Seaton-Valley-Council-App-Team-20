package com.ncl.team20.seatonvalley.data.posts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Simple POJO object to be used to save the post description. <p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @since 20/02/2018

 */
public class Exerpt {

    @SerializedName("rendered")
    @Expose
    private String rendered;

    /**
     * @return current description
     */
    public String getRendered() {
        return rendered;
    }

}