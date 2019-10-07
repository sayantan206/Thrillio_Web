package com.dey.sayantan.thrillio.entities;

import com.dey.sayantan.thrillio.constants.KidFriendlyStatus;

public abstract class Bookmark {
    private long id;
    private String title;
    private String profileUrl;
    private KidFriendlyStatus kidFriendlyStatus = KidFriendlyStatus.UNKNOWN;
    private User kidFriendlyMarkedBy;
    private User sharedBy;

    public User getKidFriendlyMarkedBy() {
        return kidFriendlyMarkedBy;
    }

    public void setKidFriendlyMarkedBy(User kidFriendlyMarkedBy) {
        this.kidFriendlyMarkedBy = kidFriendlyMarkedBy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    /**
     * This method determines if a bookmark is kid friendly eligible or not
     * if the bookmark is kid friendly eligible then only an editor or chief editor
     * can mark it as kidFriendly
     */
    public abstract boolean isKidFriendlyEligible();

    public KidFriendlyStatus getKidFriendlyStatus() {
        return kidFriendlyStatus;
    }

    public void setKidFriendlyStatus(KidFriendlyStatus kidFriendlyStatus) {
        this.kidFriendlyStatus = kidFriendlyStatus;
    }

    @Override
    public String toString() {
        return "id: "+id+", title:"+title+", profileUrl: "+profileUrl;
    }

    public User getSharedBy() {
        return sharedBy;
    }

    public void setSharedBy(User sharedBy) {
        this.sharedBy = sharedBy;
    }
}
