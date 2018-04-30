package com.ncl.team20.seatonvalley.components.basic;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * ConnectionDetector is used as an object in other classes,to determine whether there is
 * internet connection or not.<p>
 * Last Edit: 02/03/2018 by Stelios Ioannou <p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @since 20/02/2018
 * @see ConnectivityManager
 *
 */
public class ConnectionDetector {

    private final Context _context;

    /**
     * @param context is the context of the class that we want to check if there is internet connection
     *                or not
     */
    public ConnectionDetector(Context context) {
        this._context = context;
    }

    /**
     * Method that checks connectivity using ConnectivityManager
     * @return returns the status of the connection
     */
    public boolean isInternetAvailable() {
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (NetworkInfo anInfo : info)
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }
}