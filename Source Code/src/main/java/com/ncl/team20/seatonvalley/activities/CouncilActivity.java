package com.ncl.team20.seatonvalley.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;

import com.ncl.team20.seatonvalley.R;
import com.ncl.team20.seatonvalley.components.basic.Drawer;

/**
 * CouncilActivity displays information about the council,
 * the council members and their contact information.
 * Extends the Drawer to display the drawer.
 * <p>
 * Last Edit: 02/03/2018 by Stelios Ioannou
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Sam Clayton
 * @since 20/02/2018
 * @see Drawer
 */
public class CouncilActivity extends Drawer {

    /**
     * Sets up the CouncilActivity layout.
     * @param savedInstanceState stores the state of the application in a Bundle object
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Custom Animation on Create
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);

        setContentView(R.layout.activity_council);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.council);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_councilors);

    }


    /**Ensures that on resume, the correct element is selected.
     * @see NavigationView
     */
    @Override
    public void onResume() {
        super.onResume();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_councilors);
    }

}
