package com.dey.sayantan.thrillio.Test;

import com.dey.sayantan.thrillio.constants.*;
import com.dey.sayantan.thrillio.entities.*;
import com.dey.sayantan.thrillio.managers.BookmarkManager;
import com.dey.sayantan.thrillio.managers.UserManager;
import com.dey.sayantan.thrillio.partner.Shareable;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ViewTest {

    @Test
    public void browse_markKidFriendly_editorialFilter_false() {
        User user = UserManager.getInstance().createUser(1000, "user0@semanticsquare.com", "test", "John", "M", Gender.MALE, UserType.USER);
        Bookmark bookmark = BookmarkManager.getInstance().createBook(4000, "Walden", "", 1854, "Wilder Publications", new String[]{"Henry David Thoreau"}, BookGenre.CHILDREN, 4.3);
        boolean flag = false;

        if (user instanceof Editor || user instanceof ChiefEditor) {
            if (bookmark.isKidFriendlyEligible() &&
                    bookmark.getKidFriendlyStatus() == KidFriendlyStatus.UNKNOWN) {
                flag = true;
            }
        }
        assertFalse("For user type User \n required: false\n found: true", flag);
    }

    @Test
    public void browse_markKidFriendly_editorialFilter_true() {
        User user = UserManager.getInstance().createUser(1000, "user0@semanticsquare.com", "test", "John", "M", Gender.MALE, UserType.EDITOR);
        Bookmark bookmark = BookmarkManager.getInstance().createBook(4000, "Walden", "", 1854, "Wilder Publications", new String[]{"Henry David Thoreau"}, BookGenre.CHILDREN, 4.3);
        boolean flag = false;

        if (user instanceof Editor || user instanceof ChiefEditor) {
            if (bookmark.isKidFriendlyEligible() &&
                    bookmark.getKidFriendlyStatus() == KidFriendlyStatus.UNKNOWN) {
                flag = true;
            }
        }
        assertTrue("For user type Editor \n required: true\n found: false", flag);
    }

    @Test
    public void browse_share_StatusNotApproved_false() {
        Bookmark bookmark = BookmarkManager.getInstance().createBook(4000, "Walden", "", 1854, "Wilder Publications", new String[]{"Henry David Thoreau"}, BookGenre.CHILDREN, 4.3);
        bookmark.setKidFriendlyStatus(KidFriendlyStatus.UNKNOWN);
        boolean flag = false;

        if (bookmark.getKidFriendlyStatus() == KidFriendlyStatus.APPROVED && bookmark instanceof Shareable) {
            flag = true;
        }
        assertFalse("For Status unknown flag \n required: false\n found: true", flag);
    }

    @Test
    public void browse_share_StatusApproved_true() {
        Bookmark bookmark = BookmarkManager.getInstance().createBook(4000, "Walden", "", 1854, "Wilder Publications", new String[]{"Henry David Thoreau"}, BookGenre.CHILDREN, 4.3);
        bookmark.setKidFriendlyStatus(KidFriendlyStatus.APPROVED);
        boolean flag = false;

        if (bookmark.getKidFriendlyStatus() == KidFriendlyStatus.APPROVED && bookmark instanceof Shareable) {
            flag = true;
        }
        assertTrue("For Status unknown flag \n required: true\n found: false", flag);
    }

    @Test
    public void browse_share_typeNonShareable_false() {
        Bookmark bookmark = BookmarkManager.getInstance().createMovie(3000,"Citizen Kane","",	1941,	new String[]{"Orson Welles","Joseph Cotten"},	new String[]{"Orson Welles"},	MovieGenre.HORROR,	8.5);
        bookmark.setKidFriendlyStatus(KidFriendlyStatus.APPROVED);
        boolean flag = false;

        if (bookmark.getKidFriendlyStatus() == KidFriendlyStatus.APPROVED && bookmark instanceof Shareable) {
            flag = true;
        }
        assertFalse("For non shareable type Movie \n required: false\n found: true", flag);
    }

    @Test
    public void browse_share_typeShareable_true() {
        Bookmark bookmark = BookmarkManager.getInstance().createBook(4000, "Walden", "", 1854, "Wilder Publications", new String[]{"Henry David Thoreau"}, BookGenre.CHILDREN, 4.3);
        bookmark.setKidFriendlyStatus(KidFriendlyStatus.APPROVED);
        boolean flag = false;

        if (bookmark.getKidFriendlyStatus() == KidFriendlyStatus.APPROVED && bookmark instanceof Shareable) {
            flag = true;
        }
        assertTrue("For shareable type Book \n required: true\n found: false", flag);
    }

    @Test
    public void browse_approveReview_notReviewed_true() {
        Bookmark bookmark = BookmarkManager.getInstance().createBook(4000, "Walden", "", 1854, "Wilder Publications", new String[]{"Henry David Thoreau"}, BookGenre.CHILDREN, 4.3);
        User user = UserManager.getInstance().createUser(1000, "user0@semanticsquare.com", "test", "John", "M", Gender.MALE, UserType.USER);
        UserReview userReview =  new UserReview(user, bookmark, "review");
        boolean flag = false;

        if(!userReview.isApproved() && userReview.getRequestReviewedBy() == null){
            //editorial stuff can review the request
           flag = true;
        }
        assertTrue("To review not checked userReviews flag \n required: true\n found: false", flag);
    }

    @Test
    public void browse_approveReview_approved_false() {
        Bookmark bookmark = BookmarkManager.getInstance().createBook(4000, "Walden", "", 1854, "Wilder Publications", new String[]{"Henry David Thoreau"}, BookGenre.CHILDREN, 4.3);
        User user = UserManager.getInstance().createUser(1000, "user0@semanticsquare.com", "test", "John", "M", Gender.MALE, UserType.USER);
        UserReview userReview =  new UserReview(user, bookmark, "review")
                .setApproved(true)
                .setRequestReviewedBy(UserManager.getInstance().createUser(4000, "user9@semanticsquare.com", "test", "editor", "M", Gender.MALE, UserType.EDITOR));
        boolean flag = false;

        if(!userReview.isApproved() && userReview.getRequestReviewedBy() == null){
            //editorial stuff can review the request
            flag = true;
        }
        assertFalse("To skip already approved userReviews flag \n required: false\n found: true", flag);
    }

    @Test
    public void browse_approveReview_rejected_false() {
        Bookmark bookmark = BookmarkManager.getInstance().createBook(4000, "Walden", "", 1854, "Wilder Publications", new String[]{"Henry David Thoreau"}, BookGenre.CHILDREN, 4.3);
        User user = UserManager.getInstance().createUser(1000, "user0@semanticsquare.com", "test", "John", "M", Gender.MALE, UserType.USER);
        UserReview userReview =  new UserReview(user, bookmark, "review")
                .setApproved(false)
                .setRequestReviewedBy(UserManager.getInstance().createUser(4000, "user9@semanticsquare.com", "test", "editor", "M", Gender.MALE, UserType.EDITOR));
        boolean flag = false;

        if(!userReview.isApproved() && userReview.getRequestReviewedBy() == null){
            //editorial stuff can review the request
            flag = true;
        }
        assertFalse("To skip already approved userReviews flag \n required: false\n found: true", flag);
    }
}