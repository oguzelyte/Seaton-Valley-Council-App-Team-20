package com.ncl.team20.seatonvalley.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ncl.team20.seatonvalley.R;
import com.ncl.team20.seatonvalley.components.places.DisplayPlaceDetails;
import com.ncl.team20.seatonvalley.data.places.ModelPlace;

import java.util.ArrayList;


/**
 * This class is used to display the place, and make them clickable to open to their respective Google Map.<p>
 * Last Edit: 23/03/2018 by Stelios Ioannou <p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @since 20/02/2018
 * @see RecyclerView
 * @see ModelPlace
 * @see com.ncl.team20.seatonvalley.activities.FindActivity
 * .
 */
public class LocationsRecyclerViewAdapter extends RecyclerView.Adapter {

    private final ArrayList<ModelPlace> dataset;

    private final Context context;

    /**
     * Constructor for Adapter, assigns the object fields.
     * @param mlist list of ModePlaces to add to the dataset
     * @param context application state
     */
    public LocationsRecyclerViewAdapter(ArrayList<ModelPlace> mlist, Context context) {
        this.dataset = mlist;
        this.context = context;
    }

    /**
     * Create the ImageTypeViewHolder for each place.
     * @param parent contains the views in layout
     * @param viewType defines the type of view
     * @return ImageTypeViewHolder populated with place details
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewOne = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_details, parent, false);
        return new ImageTypeViewHolder(viewOne);
    }

    /**
     *  Displays the place details in the ImageTypeView holder
     *  and makes it clickable to open to it's relevant link.
     *  @param holder the holder of the ImageTypeView
     */
    @SuppressLint({"RecyclerView", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        final ModelPlace object = dataset.get(position);

        ((ImageTypeViewHolder) holder).name.setText(object.name);
        ((ImageTypeViewHolder) holder).address.setText(object.vicinity);
        // checks if rating is set and if not write unavailable
        if (object.rating == null) {
            ((ImageTypeViewHolder) holder).rating.setText(R.string.rating_not_available);
        } else {
            ((ImageTypeViewHolder) holder).rating.setText(context.getString(R.string.rating) + String.valueOf(object.rating) + context.getString(R.string.max_rating));
        }

        // Checks if it has a value
        if (!object.openWasNull) {
            // If Open show Open,else show Closed.
            if (object.openNow) {
                ((ImageTypeViewHolder) holder).open.setText(R.string.open);
                ((ImageTypeViewHolder) holder).open.setTextColor(Color.parseColor(context.getString(R.string.open_color)));
            } else {
                ((ImageTypeViewHolder) holder).open.setText(R.string.closed);
                ((ImageTypeViewHolder) holder).open.setTextColor(Color.parseColor(context.getString(R.string.closed_color)));
            }
        } else {
            // Else removes the option if it's open or closed.
            ((ImageTypeViewHolder) holder).open.setVisibility(View.GONE);
        }

        // On Card Click - Opens Google Map Link.
        ((ImageTypeViewHolder) holder).card.setOnClickListener(v -> {
            Intent intent = new Intent(context, DisplayPlaceDetails.class);
            intent.putExtra("itemPosition", position);
            context.startActivity(intent);
        });

        // On Name Click - Open Google Map Link.
        ((ImageTypeViewHolder) holder).name.setOnClickListener(v -> {
            Intent intent = new Intent(context, DisplayPlaceDetails.class);
            intent.putExtra("itemPosition", position);
            context.startActivity(intent);
        });

    }

    /**
     *
     * @return size of dataset
     */
    @Override
    public int getItemCount() {
        return dataset.size();
    }

    /**
     * Object used to store the layout fields of the place.
     * Last Edit: 23/03/2018 by Stelios Ioannou
     * Documentation Edit: 22/04/2018 by Alex Peebles
     * @author Stelios Ioannou
     * @since 20/02/2018
     * @see RecyclerView
     *
     */
    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {

        final TextView name;
        final TextView address;
        final TextView rating;
        final TextView open;
        final CardView card;


        // Current Position of the Card.
        int currentPosition;

        /**
         * Constructor used to populate object fields
         * @param itemView view to take the layouts from
         */
        public ImageTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            // Gets the Layout Fields from the itemView
            this.name = itemView.findViewById(R.id.place_name);
            this.address = itemView.findViewById(R.id.place_address);
            this.rating = itemView.findViewById(R.id.place_rating);
            this.card = itemView.findViewById(R.id.card);
            this.open = itemView.findViewById(R.id.place_open_now);
        }

    }


}
