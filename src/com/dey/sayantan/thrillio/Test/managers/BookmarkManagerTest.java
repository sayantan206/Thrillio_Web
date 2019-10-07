package com.dey.sayantan.thrillio.Test.managers;

import com.dey.sayantan.thrillio.constants.BookGenre;
import com.dey.sayantan.thrillio.constants.Gender;
import com.dey.sayantan.thrillio.constants.MovieGenre;
import com.dey.sayantan.thrillio.constants.UserType;
import com.dey.sayantan.thrillio.entities.Book;
import com.dey.sayantan.thrillio.entities.Bookmark;
import com.dey.sayantan.thrillio.entities.User;
import com.dey.sayantan.thrillio.entities.WebLink;
import com.dey.sayantan.thrillio.managers.BookmarkManager;
import com.dey.sayantan.thrillio.managers.UserManager;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class BookmarkManagerTest {

    @Test
    public void share_nonShareableType_false() {
        User user = UserManager.getInstance().createUser(1000, "user0@semanticsquare.com", "test", "John", "M", Gender.MALE, UserType.USER);
        Bookmark bookmark = BookmarkManager.getInstance().createMovie(3000, "Citizen Kane", "", 1941, new String[]{"Orson Welles", "Joseph Cotten"}, new String[]{"Orson Welles"}, MovieGenre.HORROR, 8.5);
        boolean flag = false;

        bookmark.setSharedBy(user);
        if (bookmark instanceof Book)
            flag = true;
        else if (bookmark instanceof WebLink)
            flag = true;

        assertFalse("For non shareable type Movie \n required: false\n found: true",flag);
    }

    @Test
    public void share_ShareableType_true() {
        User user = UserManager.getInstance().createUser(1000, "user0@semanticsquare.com", "test", "John", "M", Gender.MALE, UserType.USER);
        Bookmark bookmark = BookmarkManager.getInstance().createBook(4000, "Walden", "", 1854, "Wilder Publications", new String[]{"Henry David Thoreau"}, BookGenre.CHILDREN, 4.3);
        boolean flag = false;

        bookmark.setSharedBy(user);
        if (bookmark instanceof Book)
            flag = true;
        else if (bookmark instanceof WebLink)
            flag = true;

        assertTrue("For shareable type Book \n required: true\n found: false",flag);
    }
}