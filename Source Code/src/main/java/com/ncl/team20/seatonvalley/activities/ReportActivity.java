package com.ncl.team20.seatonvalley.activities;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ncl.team20.seatonvalley.R;
import com.ncl.team20.seatonvalley.components.basic.Connection;
import com.ncl.team20.seatonvalley.components.basic.ConnectionDetector;
import com.ncl.team20.seatonvalley.components.mail.SendMail;

/**
 * ReportActivity allows a user to submit a form via email
 * Extends the Connection to display the drawer and get Connection status.<p>
 * Last edited : 22/03/2018 by Stelios Ioannou <p>
 * @author Stelios Ioannou,Olivija Guzelyte
 * @since 20/02/2018
 * @see Connection
 * @see ConnectionDetector
 *
 */
@SuppressWarnings("RedundantCast")
public class ReportActivity extends Connection {

    // Connection Detector
    private final ConnectionDetector detector = new ConnectionDetector(ReportActivity.this);
    // Layout Fields
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private RadioButton dogFouling;
    private RadioButton litter;
    private RadioButton other;
    private EditText otherText;
    private EditText msgText;
    private String reportIssue;
    // Variable Used For Validation
    private int errorBit = 0;

    /**
     * Sets up the ReportActivity layout (including the map) and the report form button listener
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
        setContentView(R.layout.activity_report);

        // Take IDs of the xml file's objects
        firstName = (EditText) findViewById(R.id.report_first_name);
        lastName = (EditText) findViewById(R.id.report_last_name);
        email = (EditText) findViewById(R.id.report_email);
        litter = (RadioButton) findViewById(R.id.report_litter_btn);
        RadioGroup rGroup = (RadioGroup) findViewById(R.id.report_btn_group);
        dogFouling = (RadioButton) findViewById(R.id.report_dog_fouling_btn);
        other = (RadioButton) findViewById(R.id.report_other_btn);
        otherText = (EditText) findViewById(R.id.report_other);
        msgText = (EditText) findViewById(R.id.report_message);
        Button send = (Button) findViewById(R.id.report_btn);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.report_title);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_report);

        rGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (other.isChecked()) {
                findViewById(R.id.report_other).setVisibility(View.VISIBLE);
            } else {
                findViewById(R.id.report_other).setVisibility(View.GONE);
            }
        });

        // Sends the report form to seatonvalleycouncil@gmail.com if there is internet connection.
        send.setOnClickListener((View v) -> {
            if (detector.isInternetAvailable()) {
                errorBit = 0;
                if (dogFouling.isChecked())
                    reportIssue = "Dog Fouling";
                else if (litter.isChecked())
                    reportIssue = "Litter";
                else
                    reportIssue = otherText.getText().toString();
                validateReport(); // Validates the Report Form
                if (errorBit == 0) {
                    // noinspection unchecked
                    @SuppressWarnings("unchecked") AsyncTask sendMail = new SendMail(ReportActivity.this).execute(getString(R.string.seaton_valley_email),
                            getString(R.string.seaton_valley_password), email.getText(), "Reported Issue: " + reportIssue, "<b>Reported Issue: </b></b>" + reportIssue + "<br/>" + "<br/>" + "<b> First Name: </b>" + firstName.getText().toString() + "\r\n" + "<br/><b>Last Name: </b>" + lastName.getText().toString() + "<br/><br/>" + "<b>Message:</b> \n" + msgText.getText().toString() + "<br/><br/><b>Contact e-mail: </b>" + email.getText(),
                            email.getText().toString());
                }
            } else {
                validateReport();
                this.registerReceiver(this.mConnReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            }

        });
    }
    /**
     * Validates the input fields for the report form
     *
     */
    private void validateReport() {

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

        // Validate if issue checked
        if (!litter.isChecked() && !dogFouling.isChecked() && !other.isChecked()) {
            other.setError(getString(R.string.subject_specify));
            errorBit = 1;
        } else {
            other.setError(null);
        }


        // Validate if message longer than 20 characters.
        if (msgText.getText().toString().length() < 20) {
            msgText.setError(getString(R.string.message_body_error));
            errorBit = 1;
        }

        // Validate email using android email address matching patterns
        if (TextUtils.isEmpty(email.getText().toString()) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setError(getString(R.string.invalid_email_error));
            errorBit = 1;
        }

        if (other.isChecked()) {
            if (TextUtils.isEmpty(otherText.getText().toString())) {
                otherText.setError(getString(R.string.other_text));
                errorBit = 1;
            }
        }

    }

}
