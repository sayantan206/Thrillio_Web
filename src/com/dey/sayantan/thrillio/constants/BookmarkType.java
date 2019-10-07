package com.dey.sayantan.thrillio.constants;

public enum BookmarkType {
    WEB_LINK(0),
    MOVIE(1),
    BOOK(2);

    private final int index;
    private BookmarkType(int index){this.index = index;}
}
