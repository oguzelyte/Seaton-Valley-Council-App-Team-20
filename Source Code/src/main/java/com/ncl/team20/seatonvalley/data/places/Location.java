package com.ncl.team20.seatonvalley.data.places;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Simple POJO object to  be used to store the Location
 * attributes of a place for FindActivity.<p>
 * Documentation Edit: 22/04/2018 by Alex Peebles <p>
 * @author Stelios Ioannou
 * @since 20/02/2018
 * @see LatLng
 * @see com.ncl.team20.seatonvalley.activities.FindActivity
 * @see com.ncl.team20.seatonvalley.adapters.LocationsRecyclerViewAdapter
 */
public class Location {

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
     * @param lat lat to set
     */
    public void setLat(Double lat) {
        this.lat = lat;
    }

    /**
     * @return current lng
     */
    public Double getLng() {
        return lng;
    }

    /**
     * @param lng lng to set
     */
    public void setLng(Double lng) {
        this.lng = lng;
    }

    /**
     * Returns a LatLng of the location (Google Maps DataType: LatLng)
     */
    @NonNull
    public LatLng getLatLng() {
        return new LatLng(lat, lng);
    }
}