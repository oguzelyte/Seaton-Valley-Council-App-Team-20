package com.ncl.team20.seatonvalley.data.places;


import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * ModelPlace is used to store the place details for FindActivity.<p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @since 20/02/2018
 * @see retrofit2.Retrofit
 * @see LatLng
 * @see com.ncl.team20.seatonvalley.activities.FindActivity
 * @see com.ncl.team20.seatonvalley.adapters.LocationsRecyclerViewAdapter
 */
public class ModelPlace {

    public final String name;
    public final String vicinity; //address
    public final Double rating;
    public final Boolean openNow;
    public final Boolean openWasNull;

    /**
     * ModelPlace constructor.
     * @param mname name of the place
     * @param mvicinity address of the place
     * @param mrating place rating
     * @param mopenNow boolean indicating if place is open
     * @param mopenWasNull boolean inidicating if mopen wasn't available
     * @param mlocation latitude and longitude of place
     */
    public ModelPlace(String mname, String mvicinity, Double mrating, Boolean mopenNow, Boolean mopenWasNull, LatLng mlocation) {
        this.name = mname;
        this.vicinity = mvicinity;
        this.rating = mrating;
        this.openNow = mopenNow;
        this.openWasNull = mopenWasNull;
    }

    /**
     * Gets the Locations for EventActivity
     */
    public interface RetrofitArrayApi {
        @NonNull
        @GET
        Call<Place> getPostInfo(@Url String url);
    }

}

