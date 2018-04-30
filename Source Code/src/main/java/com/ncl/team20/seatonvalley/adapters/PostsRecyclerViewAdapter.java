package com.ncl.team20.seatonvalley.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ncl.team20.seatonvalley.R;
import com.ncl.team20.seatonvalley.components.web.DisplayPostDetails;
import com.ncl.team20.seatonvalley.data.posts.ModelPost;

import java.util.ArrayList;

/**
 * This class is used to display the posts in EventsActivity, NewsActivity and MainActivity,
 * as well as making them clickable by using the DisplayPostDetails.class.
 * <p>
 * Last Edit: 02/03/2018 by Stelios Ioannou <p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @since 20/02/2018
 * @see DisplayPostDetails
 * @see RecyclerView
 * @see com.ncl.team20.seatonvalley.components.activities_components.EventsActivityComponent
 * @see com.ncl.team20.seatonvalley.components.activities_components.NewsActivityComponent
 * @see com.ncl.team20.seatonvalley.components.activities_components.GetPosts
 * @see com.ncl.team20.seatonvalley.activities.EventsActivity
 * @see com.ncl.team20.seatonvalley.activities.NewsActivity
 * @see com.ncl.team20.seatonvalley.activities.MainActivity
 *
 */

public class PostsRecyclerViewAdapter extends RecyclerView.Adapter {

    private final ArrayList<ModelPost> dataset;
    private final Context context;
    private final int type;

    /**
     * Constructor for Adapter, assigns the object fields.
     * @param mlist list of posts to display
     * @param mContext state of application
     * @param mType type of posts to display
     */
    public PostsRecyclerViewAdapter(ArrayList<ModelPost> mlist, Context mContext, int mType) {
        this.dataset = mlist;
        this.context = mContext;
        this.type = mType;
    }

    /**
     * Create the ImageTypeViewHolder for each place.
     * @param parent contains the views in the layout
     * @param viewType defines the type of view
     * @return ImageTypeViewHolder populated with post details
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        // Checks if the request comes from the MainActivity, if so create the appropriate ImageTypeViewHolder.
        if (type == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.latest_post_details, parent, false);
            return new ImageTypeViewHolder(view);
        } else {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_details, parent, false);
        }
        return new ImageTypeViewHolder(view);
    }

    /**
     * Display the posts details and makes the display posts clickable, and on click opens the relevant link.
     * Also type specifies which class makes the request.
     *
     * @param holder   the holder of the ImageTypeView
     * @param position the position in the list
     */
    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final ModelPost object = dataset.get(position);

        ((ImageTypeViewHolder) holder).title.setText(object.title);
        ((ImageTypeViewHolder) holder).description.setText(object.description);


        ((ImageTypeViewHolder) holder).card.setOnClickListener(v -> {
            Intent intent = new Intent(context, DisplayPostDetails.class);
            intent.putExtra("itemPosition", position);
            intent.putExtra("type", type);
            context.startActivity(intent);
        });

        ((ImageTypeViewHolder) holder).title.setOnClickListener(v -> {
            Intent intent = new Intent(context, DisplayPostDetails.class);
            intent.putExtra("itemPosition", position);
            intent.putExtra("type", type);
            context.startActivity(intent);
        });

        ((ImageTypeViewHolder) holder).description.setOnClickListener(v -> {
            Intent intent = new Intent(context, DisplayPostDetails.class);
            intent.putExtra("itemPosition", position);
            intent.putExtra("type", type);
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
     * Object used to store the layout fields of the post view.
     * Last Edit: 23/03/2018 by Stelios Ioannou
     * Documentation Edit: 22/04/2018 by Alex Peebles
     * @author Stelios Ioannou
     * @since 20/02/2018
     * @see RecyclerView
     *
     */
    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {
        final TextView title;
        final TextView description;
        final CardView card;

        /**
         * Constructor used to populate object fields.
         * @param itemView
         */
        @SuppressWarnings("JavaDoc")
        public ImageTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title);
            this.description = itemView.findViewById(R.id.description);
            this.card = itemView.findViewById(R.id.card);
        }
    }
}
