package com.ncl.team20.seatonvalley.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;

import com.ncl.team20.seatonvalley.R;
import com.ncl.team20.seatonvalley.components.basic.Drawer;

/**
 * InfoActivity displays the information about the villages in Seaton Valley.<p>
 * Last Edit: 02/03/2018 by Stelios Ioannou <p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Sam Clayton
 * @author Brad Read,
 * @since 20/02/2018
 * @see Drawer
 */
public class InfoActivity extends Drawer {


    /**
     * Sets up the InfoActivity layout.
     * @param savedInstanceState stores the state of the application in a Bundle object
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);

        setContentView(R.layout.activity_info);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.about);

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_info);


    }

    /**
     * Ensures that on resume, the correct element is selected.
     */
    @Override
    public void onResume() {
        super.onResume();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_info);
    }


}
