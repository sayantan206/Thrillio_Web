package com.dey.sayantan.thrillio.entities;

public class ChiefEditor extends Editor {
    public void updateHomepage(){}

    @Override
    public <T extends Editor> UserReview approveReview(UserReview userReview, T approvedBy) {
        return userReview.setApproved(true).setRequestReviewedBy(approvedBy);
    }

    @Override
    public String toString() {
        return "ChiefEditor{ " + super.toString()+"}";
    }
}
