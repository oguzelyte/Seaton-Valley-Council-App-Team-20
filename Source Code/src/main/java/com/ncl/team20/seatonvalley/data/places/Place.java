package com.ncl.team20.seatonvalley.data.places;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Simple POJO object that uses other places POJO objects,
 * in order to be saved as one simple POJO object. <p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @since 20/02/2018
 * @see com.ncl.team20.seatonvalley.activities.FindActivity
 * @see com.ncl.team20.seatonvalley.adapters.LocationsRecyclerViewAdapter
 */

@SuppressWarnings("NullableProblems")
public class Place {

    @Nullable
    @SerializedName("html_attributions")
    @Expose
    private List<Object> htmlAttributions = null;
    @Nullable
    @SerializedName("results")
    @Expose
    private List<Result> results = null;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * @return current list of html attributions
     */
    @Nullable
    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    /**
     * @param htmlAttributions list of htmlAttributions to set
     */
    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    /**
     * @return current list of results
     */
    @Nullable
    public List<Result> getResults() {
        return results;
    }

    /**
     * @param results list of Result objects to set
     */
    public void setResults(List<Result> results) {
        this.results = results;
    }

    /**
     * @return current status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

}