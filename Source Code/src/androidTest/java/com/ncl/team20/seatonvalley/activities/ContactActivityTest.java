package com.ncl.team20.seatonvalley.activities;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ncl.team20.seatonvalley.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Espresso tests for ContactActivity.
 * @author Alex Peebles
 * @since 28/03/2018
 * @see ContactActivity
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ContactActivityTest {

    // Opens contact activity
    @Rule
    public ActivityTestRule<ContactActivity> mActivityTestRule = new ActivityTestRule<>(ContactActivity.class);

    @Test
    public void fillOutContactForm_sendEmail() throws Exception{
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.f_name_c)).check(matches(isDisplayed())).perform(typeText("Alex"),closeSoftKeyboard());
        onView(withId(R.id.l_name_c)).check(matches(isDisplayed())).perform(typeText("Peebles"),closeSoftKeyboard());
        onView(withId(R.id.subject)).check(matches(isDisplayed())).perform(typeText("Test"),closeSoftKeyboard());
        onView(withId(R.id.email_c)).check(matches(isDisplayed())).perform(typeText("alex_peebles@outlook.com"),closeSoftKeyboard());
        onView(withId(R.id.message_c)).check(matches(isDisplayed())).perform(typeText("This is a test performed by Espresso"),closeSoftKeyboard());
        onView(withId(R.id.send_message)).check(matches(isDisplayed()));
        onView(withId(R.id.send_message)).perform(click());

    }

}


