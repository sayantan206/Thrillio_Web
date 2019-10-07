package com.dey.sayantan.thrillio.entities;

/**
 * This class will store the data of relationship between user and bookmark
 * database will have a separate table for this entity
 */
public class UserBookmark {
    private User user;
    private Bookmark bookmarksList;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bookmark getBookmark() {
        return bookmarksList;
    }

    public void setBookmarks(Bookmark bookmarks) {
        this.bookmarksList = bookmarks;
    }
}
