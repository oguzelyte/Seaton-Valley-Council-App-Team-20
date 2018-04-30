package com.ncl.team20.seatonvalley.activities;

import android.support.test.espresso.ViewInteraction;
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
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;


/**
 * Espresso tests for EventsActivity.
 * @author Alex Peebles
 * @since 12/03/2018
 * @see EventsActivity
 */
@SuppressWarnings({"unused", "RedundantTypeArguments"})
@RunWith(AndroidJUnit4.class)
@LargeTest
public class EventsActivityTest {

    // Opens EventActivity
    @Rule
    public ActivityTestRule<EventsActivity> mActivityTestRule = new ActivityTestRule<>(EventsActivity.class);

    @Test
    public void clickEventCard_displaysWebView() throws Exception {
        Thread.sleep(5000);
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recycler_view_events),
                        childAtPosition(
                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                1)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));


        ViewInteraction webView = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                1),
                        0),
                        isDisplayed()));
    }

    // Generated code by Espresso recorder
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

