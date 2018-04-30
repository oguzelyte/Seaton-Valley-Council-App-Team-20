package com.ncl.team20.seatonvalley.data.places;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Simple POJO object to be used to the Result attributes of a place in FindActivity.<p>
 * Documentation Edit: 22/04/2018 by Alex Peebles <p>
 * @author Stelios Ioannou
 * @since 20/02/2018
 * @see com.ncl.team20.seatonvalley.activities.FindActivity
 * @see com.ncl.team20.seatonvalley.adapters.LocationsRecyclerViewAdapter

 */
@SuppressWarnings("NullableProblems")
public class Result {

    @SerializedName("geometry")
    @Expose
    private Geometry geometry;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("opening_hours")
    @Expose
    private OpeningHours openingHours;
    @SerializedName("place_id")
    @Expose
    private String placeId;
    @SerializedName("rating")
    @Expose
    private Double rating;
    @SerializedName("reference")
    @Expose
    private String reference;
    @SerializedName("scope")
    @Expose
    private String scope;
    @Nullable
    @SerializedName("types")
    @Expose
    private List<String> types = null;
    @SerializedName("vicinity")
    @Expose
    private String vicinity;

    /**
     * @return current geometry
     */
    public Geometry getGeometry() {
        return geometry;
    }

    /**
     * @param geometry  geometry toset
     */
    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    /**
     * @return current icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return current id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return current name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return current opening hours
     */
    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    /**
     * @param openingHours openingHours to set
     */
    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }

    /**
     * @return current placeId
     */
    public String getPlaceId() {
        return placeId;
    }

    /**
     * @param placeId placeId to set
     */
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    /**
     * @return current rating
     */
    public Double getRating() {
        return rating;
    }

    /**
     * @param rating rating to set
     */
    public void setRating(Double rating) {
        this.rating = rating;
    }

    /**
     * @return currentReference
     */
    public String getReference() {
        return reference;
    }

    /**
     * @param reference referemce tp set
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * @return current scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * @param scope scope to set
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * @return current types
     */
    @Nullable
    public List<String> getTypes() {
        return types;
    }

    /**
     * @param types types to set
     */
    public void setTypes(List<String> types) {
        this.types = types;
    }

    /**
     * @return current vicinity
     */
    public String getVicinity() {
        return vicinity;
    }

    /**
     * @param vicinity vicinity to set
     */
    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

}