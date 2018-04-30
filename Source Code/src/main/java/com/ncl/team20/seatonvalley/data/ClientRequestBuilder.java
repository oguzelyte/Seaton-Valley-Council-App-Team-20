package com.ncl.team20.seatonvalley.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ncl.team20.seatonvalley.components.basic.ConnectionDetector;

import java.io.File;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * This class is used to build an OkHttpClient
 * that is used to retrieve JSON files from the web server.
 * <p>
 * Last Edit: 02/03/2018 by Stelios Ioannou <p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @since 20/02/2018
 * @see ConnectionDetector
 * @see OkHttpClient
 *
 *
 */
public class ClientRequestBuilder {

    // Gets Cache
    public static OkHttpClient getCacheClient(@NonNull final Context context) {

        // Object to check connection
        ConnectionDetector detector = new ConnectionDetector(context);
        Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = chain -> {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            if (detector.isInternetAvailable()) {

                // If there is internet connection and the cache is 5 minutes old,it doesn't make a new request
                // and returns the cached JSON,otherwise it makes a new request.
                // Implemented to balance the requests from the web server and make the app work faster.
                int maxAge = 300; // maxAge is in seconds.
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                // If there is no internet connection and the cache is 1 week old,then return the cached JSON.
                int maxStale = 60 * 60 * 24 * 7;
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        };

        File httpCacheDirectory = new File(context.getCacheDir(), "cachedir");
        int size = 5 * 1024 * 1024; // Max Cache Size - 10 MB
        Cache cache = new Cache(httpCacheDirectory, size); // Builds Cache

        // Returns the OkHttpClient Object.
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .cache(cache)
                .build();
    }

}
