package com.ncl.team20.seatonvalley.data.posts;

import org.jsoup.Jsoup;

/**
 *  This class is used to beautify the title and description of the posts.
 * Removes HTML attributes, limits the description length to 100 characters
 * and the title to 45,and encodes to a readable format.<p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @since 20/02/2018
 *
 * @see org.jsoup.Jsoup
 * @see com.ncl.team20.seatonvalley.activities.EventsActivity
 * @see com.ncl.team20.seatonvalley.activities.NewsActivity
 * @see com.ncl.team20.seatonvalley.activities.MainActivity
 * @see com.ncl.team20.seatonvalley.components.activities_components.EventsActivityComponent
 * @see com.ncl.team20.seatonvalley.components.activities_components.NewsActivityComponent
 * @see com.ncl.team20.seatonvalley.components.activities_components.MainActivityComponent
 */
public class Beautifier {

    private final String title;
    private final String description;

    /**
     * Strips the HTML attributes from the text and creates shorter descriptions
     * and titles to display in the NewsActivity, MainActivity and HomeActivity
     * @param title title of website post
     * @param description description of website post
     */
    public Beautifier(String title, String description) {
        // gets the raw text from the title HTML
        title = Jsoup.parse(title).text();

        // strips the description of any markup
        description = description.replace("<p>", "");
        description = description.replace("</p>", "");
        description = description.replace("[&hellip;]", "");
        description = description.replace("&nbsp;", "");
        // gets the raw text from the page
        description = Jsoup.parse(description).text();

        // if the description string is longer than 100 characters then produce a summary
        if (description.length() > 100) {
            description = description.substring(0, 100) + "...";
        }
        // if the title string is longer than 45 characters then shorten
        if (title.length() > 45) {
            title = title.substring(0, 45) + "...";
        }
        // assigns the title and descriptionto the object fields
        this.title = title;
        this.description = description;
    }
    /**
     * @return current title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return current description
     */
    public String getDescription() {
        return description;
    }

}
