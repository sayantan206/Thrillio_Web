package com.dey.sayantan.thrillio.entities;

public class UserReview {
    private User user;
    private Bookmark bookmark;
    private String review;
    private boolean isApproved;
    private User requestReviewedBy;

    public User getUser() {
        return user;
    }

    public Bookmark getBookmark() {
        return bookmark;
    }

    public String getReview() {
        return review;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public User getRequestReviewedBy() {
        return requestReviewedBy;
    }

    public UserReview setApproved(boolean approved) {
        isApproved = approved;
        return this;
    }

    public UserReview setRequestReviewedBy(User requestReviewedBy) {
        this.requestReviewedBy = requestReviewedBy;
        return this;
    }

    public UserReview(User user, Bookmark bookmark, String review) {
        this.user = user;
        this.bookmark = bookmark;
        this.review = review;
    }
}
