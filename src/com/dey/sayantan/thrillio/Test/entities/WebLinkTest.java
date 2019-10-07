package com.dey.sayantan.thrillio.Test.entities;

import com.dey.sayantan.thrillio.entities.WebLink;
import com.dey.sayantan.thrillio.managers.BookmarkManager;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WebLinkTest {

    @org.junit.Test
    public void isKidFriendlyEligible_pornInUrl() {
        //Test 1 : porn in url -- false
        WebLink webLink = BookmarkManager.getInstance().createWebLink(2000, "Taming Tiger, Part 2", "",
                "http://www.porn.com/article/2072759/core-java/taming-tiger--part-2.html",
                "http://www.javaworld.com");

        assertFalse("For keyword porn in url \n Expected : False\n Found : True", webLink.isKidFriendlyEligible());
    }

    @org.junit.Test
    public void isKidFriendlyEligible_pornInTitle() {
        //Test 2 : porn in title -- false
        WebLink webLink = BookmarkManager.getInstance().createWebLink(2000, "Taming Porn, Part 2", "",
                "http://www.xyz.com/article/2072759/core-java/taming-tiger--part-2.html",
                "http://www.javaworld.com");

        assertFalse("For keyword porn in title \n Expected : False\n Found : True", webLink.isKidFriendlyEligible());
    }

    @org.junit.Test
    public void isKidFriendlyEligible_adultInHost() {
        //Test 3 : adult in host -- false
        WebLink webLink = BookmarkManager.getInstance().createWebLink(2000, "Taming Tiger, Part 2", "",
                "http://www.xyz.com/article/2072759/core-java/taming-tiger--part-2.html",
                "http://www.adult.com");

        assertFalse("For keyword adult in host \n Expected : False\n Found : True", webLink.isKidFriendlyEligible());
    }

    @org.junit.Test
    public void isKidFriendlyEligible_adultInUrl_NotInHost() {
        //Test 4 : adult in url, but but not in host part -- true
        WebLink webLink = BookmarkManager.getInstance().createWebLink(2000, "Taming Tiger, Part 2", "",
                "http://www.adult.com/article/2072759/core-java/taming-tiger--part-2.html",
                "http://www.javaworld.com");

        assertTrue("For keyword adult in url, not in host \n Expected : True\n Found : False", webLink.isKidFriendlyEligible());
    }

    @org.junit.Test
    public void isKidFriendlyEligible_adultInTitle() {
        //Test 5 : adult in title only -- true
        WebLink webLink = BookmarkManager.getInstance().createWebLink(2000,"Taming adult, Part 2","",
                "http://www.xyz.com/article/2072759/core-java/taming-tiger--part-2.html",
                "http://www.javaworld.com");

        assertTrue("For keyword adult in Title \n Expected : True\n Found : False",webLink.isKidFriendlyEligible());

    }
}