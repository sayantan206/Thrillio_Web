package com.dey.sayantan.thrillio.entities;

import com.dey.sayantan.thrillio.constants.Gender;

public class User {
    private long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Gender gender;

    /**
     * updates a user review. If user is regular user or email admin then posted reviews will reflect in view
     * only if approved by editorial stuff. Reviews posted by editor and chief editor need not to be approved
     */
    public UserReview postAReview(User user,Bookmark bookmark,String review) {
        UserReview userReview =  new UserReview(user, bookmark, review);
        return userReview;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

//    public UserType getUserType() {
//        return userType;
//    }
//
//    public void setUserType(UserType userType) {
//        this.userType = userType;
//    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
//                ", userType='" + userType + '\'' +
                '}';
    }
}
