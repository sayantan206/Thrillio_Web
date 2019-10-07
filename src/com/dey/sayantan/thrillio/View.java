package com.dey.sayantan.thrillio;

import com.dey.sayantan.thrillio.constants.KidFriendlyStatus;
import com.dey.sayantan.thrillio.entities.*;
import com.dey.sayantan.thrillio.partner.Shareable;

import java.util.List;

public class View {
    //    private static List<Bookmark> savedBookmarksList;
    private Launch launch;
    private List<UserReview> userReviews;

    public View() {
        launch = new Launch();
    }

    /*public static void bookmark(User user, Bookmark[][] bookmarks){
        System.out.println("\n"+user.getEmail()+" is browsing bookmarks");
        savedBookmarksList = new ArrayList<>();

        for (int i=0; i<DataStore.USER_BOOKMARK_LIMIT; i++) {
            int typeOffset = (int)(Math.random() * DataStore.BOOKMARK_TYPE_COUNT);
            int bookmarkOffset = (int)(Math.random() * DataStore.BOOKMARK_COUNT_PER_TYPE);

            savedBookmarksList.add(bookmarks[typeOffset][bookmarkOffset]);
        }
    }*/

    public void browse(User user, List<List<Bookmark>> bookmarks) {
        System.out.println("\n------------------------------------>" + user.getEmail() + " is browsing bookmarks <------------------------------------");
//        savedBookmarksList = new ArrayList<>();
        int bookmarkCount = 0;
        int reviewCount = 0;
        userReviews = launch.getPendingUserReviews();

        for (List<Bookmark> bookmarkList : bookmarks) {
            for (Bookmark bookmark : bookmarkList) {

                //TODO: Instead of selecting bookmarking randomly, let user choose the bookmark
                //start bookmarking
                if (bookmarkCount < DataStore.USER_BOOKMARK_LIMIT) {
                    if (getRandomDecision()) {
                        //TODO: update bookmark count in database
                        launch.saveUserBookmarkedItem(bookmark, user);
                        bookmarkCount++;
                    }
                }

                //post a review
                if (reviewCount < DataStore.REVIEW_LIMIT_PER_USER) {
                    if (getRandomDecision()) {
                        //TODO: take review from user
                        final String review = "GOOD";
                        launch.saveUserReview(user, bookmark, review);
                        reviewCount++;
                    }
                }

                if (user instanceof Editor || user instanceof ChiefEditor) {
                    //Mark as kid-friendly
                    if (bookmark.isKidFriendlyEligible() &&
                            bookmark.getKidFriendlyStatus() == KidFriendlyStatus.UNKNOWN) {
                        KidFriendlyStatus status = getRandomisedKidFriendlyDecision();
                        if (status != KidFriendlyStatus.UNKNOWN) {
                            //TODO: remove 1st param after database implementation as it is only for printing data
                            launch.setKidFriendlyStatus(status, bookmark, user);
                        }
                    }

                    //Share a bookmark
                    if (bookmark.getKidFriendlyStatus() == KidFriendlyStatus.APPROVED && bookmark instanceof Shareable
                            && bookmark.getSharedBy() == null) {
                        if (getRandomDecision()) {
                            launch.share(user, bookmark);
                        }
                    }

                    //approve or reject a review
                    for (UserReview userReview : userReviews) {
                        //if review not given then userReview is eligible for review by editorial staff
                        if (!userReview.isApproved() && userReview.getRequestReviewedBy() == null) {
                            if (getRandomDecision()) {
                                //approve review
                                if (user instanceof ChiefEditor) {
                                    launch.markReviewApproved(userReview, (ChiefEditor) user);
                                } else {
                                    launch.markReviewApproved(userReview, (Editor) user);
                                }
                            } else {
                                //reject review
                                if (user instanceof ChiefEditor) {
                                    launch.markReviewRejected(userReview, (ChiefEditor) user);
                                } else {
                                    launch.markReviewRejected(userReview, (Editor) user);
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    private KidFriendlyStatus getRandomisedKidFriendlyDecision() {
        final double decision = Math.random();
        return decision < 0.4 ? KidFriendlyStatus.APPROVED :
                (decision >= 0.4 && decision < 0.8) ? KidFriendlyStatus.REJECTED : KidFriendlyStatus.UNKNOWN;
    }

    private boolean getRandomDecision() {
        return Math.random() < 0.5 ? true : false;
    }

   /* public static List<Bookmark> getUserBookmarks(){
        return savedBookmarksList;
    }*/
}
