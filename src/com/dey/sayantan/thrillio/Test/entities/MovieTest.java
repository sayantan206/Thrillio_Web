package com.dey.sayantan.thrillio.Test.entities;

import com.dey.sayantan.thrillio.constants.MovieGenre;
import com.dey.sayantan.thrillio.entities.Movie;
import com.dey.sayantan.thrillio.managers.BookmarkManager;
import org.junit.Test;

import static org.junit.Assert.*;

public class MovieTest {

    @org.junit.Test
    public void isKidFriendlyEligible_TypeHorror() {
        Movie movie = BookmarkManager.getInstance().createMovie(3000,"Citizen Kane","",	1941,	new String[]{"Orson Welles","Joseph Cotten"},	new String[]{"Orson Welles"},	MovieGenre.HORROR,	8.5);
        assertFalse("Movie type Horror, \niskidFriendlyEligible() expected to return : False\nFound: True",movie.isKidFriendlyEligible());
    }

    @org.junit.Test
    public void isKidFriendlyEligible_TypeThriller() {
        Movie movie = BookmarkManager.getInstance().createMovie(3000,"Citizen Kane","",	1941,	new String[]{"Orson Welles","Joseph Cotten"},	new String[]{"Orson Welles"},	MovieGenre.THRILLERS,	8.5);
        assertFalse("Movie type Thriller, \niskidFriendlyEligible() expected to return : False\nFound: True",movie.isKidFriendlyEligible());
    }

    @org.junit.Test
    public void isKidFriendlyEligible_TypeOther() {
        Movie movie = BookmarkManager.getInstance().createMovie(3000,"Citizen Kane","",	1941,	new String[]{"Orson Welles","Joseph Cotten"},	new String[]{"Orson Welles"},	MovieGenre.CLASSICS,	8.5);
        assertTrue("Movie type Other, \niskidFriendlyEligible() expected to return : True\nFound: False",movie.isKidFriendlyEligible());
    }
}