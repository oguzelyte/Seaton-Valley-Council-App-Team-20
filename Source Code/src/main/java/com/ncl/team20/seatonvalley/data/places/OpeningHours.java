package com.ncl.team20.seatonvalley.data.places;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
// TODO
/**
 * Simple POJO object to  be used to store the OpeningHours attributes of a place in FindActivity.<p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @since 20/02/2018
 * @see Location
 * @see com.ncl.team20.seatonvalley.activities.FindActivity
 * @see com.ncl.team20.seatonvalley.adapters.LocationsRecyclerViewAdapter
 */
@SuppressWarnings("NullableProblems")
public class OpeningHours {

    @SerializedName("open_now")
    @Expose
    private Boolean openNow;
    @Nullable
    @SerializedName("weekday_text")
    @Expose
    private List<Object> weekdayText = null;

    /**
     * @return current openNow value
     */
    public Boolean getOpenNow() {
        return openNow;
    }

    /**
     * @param openNow sets the object field to true/false
     */
    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }

    /**
     * @return current opening times
     */
    @Nullable
    public List<Object> getWeekdayText() {
        return weekdayText;
    }

    /**
     *
     * @param weekdayText opening times to set
     */
    public void setWeekdayText(List<Object> weekdayText) {
        this.weekdayText = weekdayText;
    }

}