package com.ncl.team20.seatonvalley.components.basic;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ncl.team20.seatonvalley.R;
import com.ncl.team20.seatonvalley.activities.ContactActivity;
import com.ncl.team20.seatonvalley.activities.CouncilActivity;
import com.ncl.team20.seatonvalley.activities.EventsActivity;
import com.ncl.team20.seatonvalley.activities.FindActivity;
import com.ncl.team20.seatonvalley.activities.InfoActivity;
import com.ncl.team20.seatonvalley.activities.MainActivity;
import com.ncl.team20.seatonvalley.activities.NewsActivity;
import com.ncl.team20.seatonvalley.activities.ReportActivity;
import com.ncl.team20.seatonvalley.activities.SettingsActivity;
import com.ncl.team20.seatonvalley.activities.TwitterActivity;

/**
 * This abstract class is used as an Extension to other classes to display the drawer navigation.<p>
 * Last Edit: 02/03/2018 by Stelios Ioannou <p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @since 20/02/2018
 * @see AppCompatActivity
 * @see android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
 * @see ContactActivity
 * @see CouncilActivity
 * @see EventsActivity
 * @see FindActivity
 * @see InfoActivity
 * @see com.ncl.team20.seatonvalley.activities.LoginActivity
 * @see MainActivity
 * @see NewsActivity
 * @see ReportActivity
 * @see SettingsActivity
 * @see TwitterActivity
 */
public class Drawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String temp;

    /**
     * Used to navigate to a new activity in the side bar.
     * @param item menu item of a selected activity
     * @return true when an itme is successfully selected
     */
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        //  Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Clears The Stack Returns to Main (To sustain user-friendly navigation)
            Intent i = getApplicationContext().getPackageManager()
                    .getLaunchIntentForPackage(getApplicationContext().getPackageName());
            assert i != null;
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        } else if (id == R.id.nav_news) {
            Intent intent = new Intent(this, NewsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_twitter) {
            Intent intent = new Intent(this, TwitterActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_events) {
            Intent intent = new Intent(this, EventsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_find) {
            Intent intent = new Intent(this, FindActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_contact) {
            Intent intent = new Intent(this, ContactActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_report) {
            Intent intent = new Intent(this, ReportActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_councilors) {
            Intent intent = new Intent(this, CouncilActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_info) {
            Intent intent = new Intent(this, InfoActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Seaton Valley Council App");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Download the Seaton Valley Council app to connect with your local council, click here to visit bit.ly/team-20-ncl .");
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Navigates back to MainActivity if the user presses the back button.
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            navigateUpTo(new Intent(getBaseContext(), MainActivity.class));
            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
        }
    }

    /**
     * Enables the back-button.
     */
    @Override
    public boolean onSupportNavigateUp() {
        navigateUpTo(new Intent(getBaseContext(), MainActivity.class));
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
        return true;
    }


}
