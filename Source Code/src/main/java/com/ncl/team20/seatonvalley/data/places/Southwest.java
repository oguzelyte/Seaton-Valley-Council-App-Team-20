package com.ncl.team20.seatonvalley.data.places;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Simple POJO object to be used to store Southwest attributes of a place in FindActivity.<p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @since 20/02/2018
 * @see com.ncl.team20.seatonvalley.activities.FindActivity
 * @see com.ncl.team20.seatonvalley.adapters.LocationsRecyclerViewAdapter
 *
 */
public class Southwest {

    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    private Double lng;

    /**
     * @return current latitude
     */
    public Double getLat() {
        return lat;
    }

    /**
     * @param lat latitude to set
     */
    public void setLat(Double lat) {
        this.lat = lat;
    }

    /**
     * @return current longitude
     */
    public Double getLng() {
        return lng;
    }
    /**
     * @param lng longitude to set
     */
    public void setLng(Double lng) {
        this.lng = lng;
    }

}