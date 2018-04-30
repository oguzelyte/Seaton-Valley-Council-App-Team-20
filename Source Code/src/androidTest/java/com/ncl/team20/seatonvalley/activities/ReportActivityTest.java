package com.ncl.team20.seatonvalley.activities;


import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.ncl.team20.seatonvalley.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
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
 * Espresso tests for ReportActivity.
 * @author Alex Peebles
 * @since 12/03/2018
 * @see ReportActivity
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ReportActivityTest {

    // Opens ReportActivity
    @Rule
    public ActivityTestRule<ReportActivity> mActivityTestRule = new ActivityTestRule<>(ReportActivity.class);

    @Test
    public void fillOutReportForm_sendEmail() throws Exception{
        onView(withId(R.id.report_layout)).check(matches(isDisplayed()));
        onView(withId(R.id.report_first_name)).check(matches(isDisplayed())).perform(typeText("Alex"),closeSoftKeyboard());
        onView(withId(R.id.report_last_name)).check(matches(isDisplayed())).perform(typeText("Peebles"),closeSoftKeyboard());
        onView(withId(R.id.report_litter_btn)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.report_email)).check(matches(isDisplayed())).perform(typeText("alex_peebles@outlook.com"),closeSoftKeyboard());
        onView(withId(R.id.report_message)).check(matches(isDisplayed())).perform(typeText("This is a test performed by Espresso"),closeSoftKeyboard());
        //onView(withId(R.id.report_btn)).check(matches().isDisplayed());
        onView(withId(R.id.report_btn)).check(matches(isDisplayed())).perform(click());


    }

    // Espresso generated method
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
