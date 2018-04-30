package com.ncl.team20.seatonvalley.components.basic;

import android.support.annotation.NonNull;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;

import cz.intik.overflowindicator.OverflowPagerIndicator;

/**
 * SnapHelper which allows snapping of pages, customized with notifying of {@link OverflowPagerIndicator}
 * when position is snapped
 * <p>
 * Taken from
 * https://github.com/intik/overflow-pager-indicator/blob/master/library/src/main/java/cz/intik/overflowindicator/SimpleSnapHelper.java
 *
 * @author Petr Introvic <introvic.petr@gmail.com>
 *         created 07.10.2017.
 * @see OverflowPagerIndicator
 */

// Custom Implementation of SimpleSnapHelper from Petr Introvic
// Default Implementation caused a bug to the last indicator
// Implemented by Stelios Ioannou on 23/03/2018

public class SimpleSnapHelper extends PagerSnapHelper {

    @NonNull
    private final OverflowPagerIndicator mOverflowPagerIndicator;

    public SimpleSnapHelper(@NonNull OverflowPagerIndicator overflowPagerIndicator) {
        mOverflowPagerIndicator = overflowPagerIndicator;
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        int position = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);

        // Notify OverflowPagerIndicator about changed page
        // Custom Modification by Stelios Ioannou to not overlap the last indicator in the MainActivity.
        // Since items are always less then than 3 in latest news change only if position is less than 3.
        if (position < 3) {
            mOverflowPagerIndicator.onPageSelected(position);
        }

        return position;
    }

}
