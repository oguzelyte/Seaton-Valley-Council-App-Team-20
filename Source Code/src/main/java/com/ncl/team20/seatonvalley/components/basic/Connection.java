package com.ncl.team20.seatonvalley.components.basic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.ncl.team20.seatonvalley.R;

/**
 * Connection is an abstract class used as an extension to other classes when there is a change in connection.
 * Displays the relevant message in each scenario in the Broadcast Receiver.<p>
 * This class extends the Drawer abstract class.<p>
 * Last Edit: 02/03/2018 by Stelios Ioannou <p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @since 20/02/2018
 * @see Drawer
 * @see ConnectivityManager
 */
public abstract class Connection extends Drawer {


    @Nullable
    protected final BroadcastReceiver mConnReceiver = new BroadcastReceiver() {
        /**
         * Checks if the user's device is connected to WIFI/mobile data
         * whenever a new activity is opened, if it is not connected the
         * user will receive a prompt and when it is reconnected
         * it will load the page content.
         * @param context state of the application
         * @param intent object passed between activities
         */
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo currentNetworkInfo = cm != null ? cm.getActiveNetworkInfo() : null;
            if (currentNetworkInfo != null && currentNetworkInfo.isConnectedOrConnecting()) {
                recreate();
                Toast.makeText(getApplicationContext(), R.string.on_connection, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), R.string.on_no_connection,
                        Toast.LENGTH_LONG).show();
            }
        }
    };

    /**
     * Registers the receiver whenever its child activity is opened.
     */
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("");
        this.registerReceiver(mConnReceiver, intentFilter);
    }
    /**
     * Unregisters the receiver whenever its child activity is left.
     */
    @Override
    public void onPause() {
        super.onPause();
        this.unregisterReceiver(mConnReceiver);
    }

}
