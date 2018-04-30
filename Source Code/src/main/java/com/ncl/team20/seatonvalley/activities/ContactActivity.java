package com.ncl.team20.seatonvalley.activities;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ncl.team20.seatonvalley.R;
import com.ncl.team20.seatonvalley.components.basic.Connection;
import com.ncl.team20.seatonvalley.components.basic.ConnectionDetector;
import com.ncl.team20.seatonvalley.components.mail.SendMail;

/**
 * ContactActivity displays the location of the Seaton Valley Council office, its opening hours,
 * contact information and a form to get in touch with the council.
 * Extends the Connection to display the drawer and get the connection status.
 * <p>
 * Last Edit: 22/03/2018 by Stelios Ioannou<p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @author Olivija Guzelyte
 * @since 20/02/2018
 * @see Connection
 * @see MapView
 * @see GoogleMap
 */
@SuppressWarnings({"unused", "RedundantCast"})
public class ContactActivity extends Connection implements OnMapReadyCallback {

    // Connection Detector
    private final ConnectionDetector detector = new ConnectionDetector(ContactActivity.this);
    // Map View for Council Address.
    MapView map_view;
    // Layout Fields
    private EditText firstName;
    private EditText lastName;
    private EditText subject;
    private EditText email;
    private EditText msgText;
    // Variable Used For Validation
    private int errorBit = 0;

    /**
     * Starts the activity, sets up the layout (including the map) and the contact form button listener.
     * @param savedInstanceState state of the application in a Bundle object
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ensures that the keyboard is not automatically opened.
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // Custom Animation on Create
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);

        setContentView(R.layout.activity_contact);

        // Delays the map to be shown,in order to open the activity and then display the map
        // By doing so it improves the UI experience.
        new Handler().postDelayed(() -> {
            MapView map_view = findViewById(R.id.map_contact);
            map_view.onCreate(null);
            map_view.onResume();
            map_view.getMapAsync(ContactActivity.this);
        }, 100);


        // Parses Layout Fields
        firstName = (EditText) findViewById(R.id.f_name_c);
        lastName = (EditText) findViewById(R.id.l_name_c);
        subject = (EditText) findViewById(R.id.subject);
        email = (EditText) findViewById(R.id.email_c);
        msgText = (EditText) findViewById(R.id.message_c);

        // Send Button
        Button send = (Button) findViewById(R.id.send_message);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.contact_title);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_contact);


        // Sends the contact form to seatonvalleycouncil@gmail.com if there is internet connection.
        send.setOnClickListener((View v) -> {
            if (detector.isInternetAvailable()) {
                {
                    errorBit = 0;
                    validateContact(); // Validates the Contact Form
                    if (errorBit == 0) {
                        @SuppressWarnings("unchecked") AsyncTask sendMail = new SendMail(ContactActivity.this).execute(getString(R.string.seaton_valley_email),
                                getString(R.string.seaton_valley_password), email.getText(), "Subject: " + subject.getText(), "<b>Subject: </b></b>" + subject.getText() + "<br/>" + "<br/>" + "<b> First Name: </b>" + firstName.getText().toString() + "\r\n" + "<br/><b>Last Name: </b>" + lastName.getText().toString() + "<br/><br/>" + "<b>Message:</b> \n" + msgText.getText().toString() + "<br/><br/><b>Contact e-mail: </b>" + email.getText(),
                                email.getText().toString());
                    }
                }
            } else {
                validateContact();
                this.registerReceiver(this.mConnReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            }
        });
    }


    /**
     * Creates the Google Map View for the Council Address.
     * @param googleMap map to manipulate
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        // Council Address
        LatLng council_address = new LatLng(55.073095, -1.526686);

        // Creates a Camera Update
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(council_address, 17);
        // Moves to the Council Address view.
        googleMap.moveCamera(cameraUpdate);
        // Disables Gestures
        googleMap.getUiSettings().setAllGesturesEnabled(false);
        // Enables Lite Mote (For a better UI experience).
        GoogleMapOptions options = new GoogleMapOptions().liteMode(true);

        // Custom Color for the Marker
        final float marker_color = 338.0F;

        // Adds the Marker.
        googleMap.addMarker(new MarkerOptions()
                .position(council_address)
                .title(getString(R.string.map_council_address_title))
                .icon(BitmapDescriptorFactory.defaultMarker(marker_color))
        );
    }

    /**
     * Validates the input fields for the contact form.
     */
    private void validateContact() {

        // Validate first name
        if (firstName.getText().toString().length() <= 0) {
            firstName.setError(getString(R.string.first_name_error));
            errorBit = 1;
        }

        // Validate last name
        if (lastName.getText().toString().length() <= 0) {
            lastName.setError(getString(R.string.last_name_error));
            errorBit = 1;
        }

        // Validate if message is longer than 20 characters
        if (msgText.getText().toString().length() < 20) {
            msgText.setError(getString(R.string.message_body_error));
            errorBit = 1;
        }

        // Validate email using android email address matching patterns
        if (TextUtils.isEmpty(email.getText().toString()) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setError(getString(R.string.invalid_email_error));
            errorBit = 1;
        }
    }


    /**
     * Ensures that on resume,that the correct element is selected.
     */
     public void onResume() {
        super.onResume();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_contact);
    }

}
