package com.ncl.team20.seatonvalley.data.places;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Simple POJO object to be used to store Viewport attributes of a place in FindActivity.<p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @since 20/02/2018
 * @see com.ncl.team20.seatonvalley.activities.FindActivity
 * @see com.ncl.team20.seatonvalley.adapters.LocationsRecyclerViewAdapter
 * @see Northeast
 * @see Southwest
 * @see com.ncl.team20.seatonvalley.activities.EventsActivity
 */
public class Viewport {

    @SerializedName("northeast")
    @Expose
    private Northeast northeast;
    @SerializedName("southwest")
    @Expose
    private Southwest southwest;

    /**
     * @return current northeast object
     */
    public Northeast getNortheast() {
        return northeast;
    }

    /**
     * @param northeast northwest to set
     */
    public void setNortheast(Northeast northeast) {
        this.northeast = northeast;
    }

    /**
     * @return current southwest object
     */
    public Southwest getSouthwest() {
        return southwest;
    }
    /**
     * @param southwest southwest to set
     */
    public void setSouthwest(Southwest southwest) {
        this.southwest = southwest;
    }

}