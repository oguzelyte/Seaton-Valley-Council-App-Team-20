package com.ncl.team20.seatonvalley.components.activities_components;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import com.ncl.team20.seatonvalley.R;
import com.ncl.team20.seatonvalley.adapters.PostsRecyclerViewAdapter;
import com.ncl.team20.seatonvalley.components.basic.Connection;
import com.ncl.team20.seatonvalley.data.ClientRequestBuilder;
import com.ncl.team20.seatonvalley.data.posts.Beautifier;
import com.ncl.team20.seatonvalley.data.posts.ModelPost;
import com.ncl.team20.seatonvalley.data.posts.Post;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This abstract class is used as an Extension to MainActivity,NewsActivity,EventsActivity,
 * to get the relevant posts. This class is extended by the abstract class Connection.
 * <p>
 *
 * Last Edit: 02/03/2018 by Stelios Ioannou <p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @since 20/02/2018
 * @see Post
 * @see ModelPost
 * @see Connection
 * @see Retrofit
 * @see PostsRecyclerViewAdapter
 * @see ClientRequestBuilder
 *
 */
public abstract class GetPosts extends Connection {

    @Nullable
    public static List<Post> mListPost;
    final ArrayList<ModelPost> list = new ArrayList<>();
    protected ProgressBar progressBar;
    PostsRecyclerViewAdapter adapter;
    private Beautifier beautify;

    /**
     * Clears the List of ModelPosts and notifies the adapter.
     */
    public void clearList() {
        // noinspection ConstantConditions
        if (list != null) {
            list.clear();
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * Gets the user specified number of posts from the website for a category such as news/events
     * @param POSTS number of posts to receive
     * @param category the type of post to receive
     * @param context current state of application
     */
    void getPosts(int POSTS, String category, @NonNull Context context) {


        String baseURL = getString(R.string.base_url); // Standar URL that's make the request
        String request = getString(R.string.request); // Standar Request.
        request += POSTS + getString(R.string.categories);

        // Makes a Retrofit builder with the Client of ClientRequestBuilder class.
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(ClientRequestBuilder.getCacheClient(context))
                .addConverterFactory(GsonConverterFactory.create());

        // Creates the Retrofit object.
        Retrofit retrofit = builder.build();

        // Create the service.
        ModelPost.RetrofitArrayApi service = retrofit.create(ModelPost.RetrofitArrayApi.class);

        // Makes a List of Posts
        Call<List<Post>> call = service.getPostInfo(baseURL + request + category);

        // Populate each post
        call.enqueue(new Callback<List<Post>>() {
            /**
             * Gets the response and strips all the HTML markup from the Strings
             * using Beautify to display the posts
             * @param call the call made to the Retrofit API to retrieve the webpage
             * @param response the response from the API
             */
            @Override
            public void onResponse(@SuppressWarnings("NullableProblems") Call<List<Post>> call, @SuppressWarnings("NullableProblems") retrofit2.Response<List<Post>> response) {

                mListPost = response.body(); // Gets the requests response.

                progressBar.setVisibility(View.GONE); // Sets the progressBar to invisible.

                // for all posts
                // noinspection ConstantConditions
                for (int i = 0; i < response.body().size(); i++) {

                    // Gets Title
                    @SuppressWarnings("ConstantConditions") String title = response.body().get(i).getTitle().getRendered();
                    // Gets Description
                    @SuppressWarnings("ConstantConditions") String description = response.body().get(i).getExcerpt().getRendered();
                    @SuppressWarnings("ConstantConditions") int id = response.body().get(i).getId();
                    beautify = new Beautifier(title, description);
                    // Beautifies them and stores them.
                    title = beautify.getTitle();
                    description = beautify.getDescription();

                    // Saves post to object post.
                    ModelPost post = new ModelPost(title,
                            description, id);

                    // Checks if the post is not in the list,to avoid duplicates.
                    if (!list.contains(post)) {
                        list.add(post);
                    }
                }
                // Notify adapter for the change in data.
                adapter.notifyDataSetChanged();
            }

            /**
             * empty Failure method
             * @param call the call made to the Retrofit API to retrieve the webpage
             * @param t error returned from Retrofit API
             */
            @Override
            public void onFailure(@SuppressWarnings("NullableProblems") Call<List<Post>> call, @SuppressWarnings("NullableProblems") Throwable t) {

            }
        });
    }

}

