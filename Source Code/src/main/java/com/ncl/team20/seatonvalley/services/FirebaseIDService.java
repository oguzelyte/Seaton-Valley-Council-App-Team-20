package com.ncl.team20.seatonvalley.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * This class is used to send the registration token to the Firebase console. <p>
 * Last Edit: 02/03/2018 by Stelios Ioannou <p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @since 20/02/2018
 * @see FirebaseInstanceIdService

 */
public class FirebaseIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseIDService";

    /**
     * Gets instance of the new token and sends it to the server
     */
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer();
    }

    /**
     * Extra functionality to be implemented if needed in the near future.
     */
    @SuppressWarnings("EmptyMethod")
    private void sendRegistrationToServer() {
    }

}
