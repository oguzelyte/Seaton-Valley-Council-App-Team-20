package com.ncl.team20.seatonvalley.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ncl.team20.seatonvalley.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;


/**
 * LoginActivity loads when the Twitter requests a login,
 * in order to favourite tweets to your twitter account. <p>
 * Last Edit: 17/04/2018 by Stelios Ioannou <p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @author Sam Clayton
 * @since 20/02/2018
 * @see AppCompatActivity
 * @see TwitterLoginButton
 *
 */

@SuppressWarnings("RedundantCast")
public class LoginActivity extends AppCompatActivity {


    private final Activity activity = this;
    private TwitterLoginButton loginButton;
    private boolean shouldExecuteOnResume;

    /**
     * Sets up the LoginActivity layout. Displays a prompt to the user to login to their
     * Twitter account, sets up the button and proceeds for the user.
     * @param savedInstanceState stores the state of the application in a Bundle object
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Notifies the User
        Toast.makeText(getApplicationContext(), "Please Login with your Twitter account",
                Toast.LENGTH_LONG).show();

        // Assigns the login button to twitter
        loginButton = (TwitterLoginButton) findViewById(R.id.login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            // On Login
            public void success(Result<TwitterSession> result) {
                activity.finish();
            }

            // On Failure
            @Override
            public void failure(TwitterException exception) {
            }
        });
        // Automatically clicks the login button for a smoother UI experience.
        loginButton.performClick();
        // Implemented so when the back button is pressed to go directly to the Twitter Activity.
        shouldExecuteOnResume = false;
    }

    /**
     * Finishes the activity when onResume is called.
     */
    public void onResume() {
        super.onResume();
        // Finish the Activity if it should execute on resume
        if (shouldExecuteOnResume) {
            activity.finish();
        } else {
            shouldExecuteOnResume = true;
        }
    }

    /**
     * Processes the result of the Twitter user login.
     * @param requestCode the request code for Twitter login
     * @param resultCode the result of the Twitter login
     * @param data user data requested
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }


}
