package com.dey.sayantan.thrillio.Test.entities;

import com.dey.sayantan.thrillio.constants.BookGenre;
import com.dey.sayantan.thrillio.entities.Book;
import com.dey.sayantan.thrillio.managers.BookmarkManager;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BookTest {

    @Test
    public void isKidFriendlyEligible_TypePhilosophy() {
        Book book = BookmarkManager.getInstance().createBook(4000, "Walden", "", 1854, "Wilder Publications", new String[]{"Henry David Thoreau"}, BookGenre.PHILOSOPHY, 4.3);
        assertFalse("Book type Philosophy, \niskidFriendlyEligible() expected to return : False\nFound: True", book.isKidFriendlyEligible());
    }

    @Test
    public void isKidFriendlyEligible_TypeSelfHelp() {
        Book book = BookmarkManager.getInstance().createBook(4000, "Walden", "", 1854, "Wilder Publications", new String[]{"Henry David Thoreau"}, BookGenre.SELF_HELP, 4.3);
        assertFalse("Book type Philosophy, \niskidFriendlyEligible() expected to return : False\nFound: True", book.isKidFriendlyEligible());
    }

    @Test
    public void isKidFriendlyEligible_TypeOther() {
        Book book = BookmarkManager.getInstance().createBook(4000, "Walden", "", 1854, "Wilder Publications", new String[]{"Henry David Thoreau"}, BookGenre.HISTORY, 4.3);
        assertTrue("Book type Philosophy, \niskidFriendlyEligible() expected to return : True\nFound: False", book.isKidFriendlyEligible());
    }
}