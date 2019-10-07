package com.dey.sayantan.thrillio.entities;

public class EmailAdmin extends User {
    @Override
    public UserReview postAReview(User user,Bookmark bookmark,String review) {
        UserReview userReview =  new UserReview(user, bookmark, review);
        return userReview;
    }

    @Override
    public String toString() {
        return "EmailAdmin{ " + super.toString()+"}";
    }
}
