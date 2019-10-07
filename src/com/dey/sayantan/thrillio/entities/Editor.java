package com.dey.sayantan.thrillio.entities;


public class Editor extends User {
    @Override
    public UserReview postAReview(User user,Bookmark bookmark,String review) {
        UserReview userReview =  new UserReview(user, bookmark, review)
                                                .setApproved(true)
                                                .setRequestReviewedBy(this);
        return userReview;
    }

    public <T extends Editor>UserReview approveReview(UserReview userReview,T approvedBy){
        return userReview.setApproved(true).setRequestReviewedBy(approvedBy);
    }
    public <T extends Editor>UserReview rejectReview(UserReview userReview,T rejectedBy){
        return userReview.setApproved(false).setRequestReviewedBy(rejectedBy);
    }

    @Override
    public String toString() {
        return "Editor{ " + super.toString()+"}";
    }
}
