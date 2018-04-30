package com.ncl.team20.seatonvalley.data.places;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Simple POJO object to  be used to store the Geometry
 * attributes of a place for FindActivity.<p>
 * Documentation Edit: 22/04/2018 by Alex Peebles <p>
 * @author Stelios Ioannou
 * @since 20/02/2018
 * @see Location
 * @see Viewport
 * @see com.ncl.team20.seatonvalley.activities.FindActivity
 * @see com.ncl.team20.seatonvalley.adapters.LocationsRecyclerViewAdapter
 */
public class Geometry {

    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("viewport")
    @Expose
    private Viewport viewport;

    /**
     * @return the current location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return current viewport
     */
    public Viewport getViewport() {
        return viewport;
    }

    /**
     *
     * @param viewport viewport to set
     */
    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

}