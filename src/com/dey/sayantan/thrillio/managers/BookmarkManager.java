package com.dey.sayantan.thrillio.managers;


import com.dey.sayantan.thrillio.constants.BookGenre;
import com.dey.sayantan.thrillio.constants.BookmarkType;
import com.dey.sayantan.thrillio.constants.KidFriendlyStatus;
import com.dey.sayantan.thrillio.constants.MovieGenre;
import com.dey.sayantan.thrillio.dao.BookmarkDao;
import com.dey.sayantan.thrillio.entities.*;

import java.util.Collection;
import java.util.List;

public class BookmarkManager {
    private static  BookmarkManager instance = new BookmarkManager();
    private static BookmarkDao dao = new BookmarkDao();
    private BookmarkManager(){}

    public static BookmarkManager getInstance(){
        return instance;
    }

    public Bookmark createBookmark(long id, String type){
        Bookmark bookmark = type.equalsIgnoreCase("BOOK") ? new Book() :
                type.equalsIgnoreCase("MOVIE") ? new Movie() : new WebLink();
        bookmark.setId(id);
        return bookmark;
    }
    public Movie createMovie(long id, String title, String imageUrl, String profileUrl, int releaseYear, String[] cast, String[] directors,
                             MovieGenre genre, double imdbRating){
        Movie movie = createMovie(id,title, profileUrl,releaseYear,cast,directors,genre,imdbRating);
        movie.setImageUrl(imageUrl);
        return movie;
    }
    public Movie createMovie(long id, String title, String profileUrl, int releaseYear, String[] cast, String[] directors,
                             MovieGenre genre, double imdbRating){
        Movie movie = new Movie();
        movie.setId(id);
        movie.setTitle(title);
        movie.setProfileUrl(profileUrl);
        movie.setReleaseYear(releaseYear);
        movie.setCast(cast);
        movie.setDirectors(directors);
        movie.setGenre(genre);
        movie.setImdbRating(imdbRating);

        return movie;
    }

    public Book createBook(long id, String title,String imageUrl, String profileUrl, int publicationYear, String publisher,
                           String[] authors, BookGenre genre, double amazonRating){
        Book book = createBook(id, title, profileUrl, publicationYear, publisher, authors, genre, amazonRating);
        book.setImageUrl(imageUrl);
        return book;
    }

    public Book createBook(long id, String title, String profileUrl, int publicationYear, String publisher,
                           String[] authors, BookGenre genre, double amazonRating){
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setProfileUrl(profileUrl);
        book.setPublicationYear(publicationYear);
        book.setPublisher(publisher);
        book.setAuthors(authors);
        book.setGenre(genre);
        book.setAmazonRating(amazonRating);

        return book;
    }


    public WebLink createWebLink(long id, String title,String imageUrl, String profileUrl, String url, String host){
        WebLink webLink = createWebLink(id, title,profileUrl,url, host);
        webLink.setImageUrl(imageUrl);
        return webLink;
    }
    public WebLink createWebLink(long id, String title, String profileUrl, String url, String host){
        WebLink webLink = new WebLink();
        webLink.setId(id);
        webLink.setTitle(title);
        webLink.setProfileUrl(profileUrl);
        webLink.setUrl(url);
        webLink.setHost(host);

        return webLink;
    }

    public List<List<Bookmark>> getBookmarks(){
        return dao.getBookmarks();
    }


//    public void saveUserBookmarks(List<UserBookmark> userBookmarkList) {
//        dao.saveUserBookmarkedItem(userBookmarkList);

    //    }
    public void saveUserBookmarkedItem(Bookmark bookmark, User user) {
        UserBookmark userBookmark = new UserBookmark();
        userBookmark.setUser(user);
        userBookmark.setBookmarks(bookmark);
        dao.saveUserBookmarkedItem(userBookmark);
    }
    public void deleteUserBookmarkedItem(Bookmark bookmark, User user) {
        UserBookmark userBookmark = new UserBookmark();
        userBookmark.setUser(user);
        userBookmark.setBookmarks(bookmark);
        dao.deleteUserBookmarkedItem(userBookmark);
    }

    public void setKidFriendlyStatus(KidFriendlyStatus kidFriendlyStatus, Bookmark bookmark, User user) {
        bookmark.setKidFriendlyStatus(kidFriendlyStatus);
        bookmark.setKidFriendlyMarkedBy(user);

        dao.updateKidFriendlyStatus(bookmark);
        System.out.println("Kid-friendly status : "+kidFriendlyStatus+" has been added to "+bookmark.getTitle()+
                " by "+bookmark.getKidFriendlyMarkedBy().getEmail());
    }

    public void share(User user, Bookmark bookmark) {
        bookmark.setSharedBy(user);
        System.out.println("Data to be shared: ");
        if(bookmark instanceof Book)
            System.out.println(((Book) bookmark).getItemData());
        else if(bookmark instanceof WebLink)
            System.out.println(((WebLink) bookmark).getItemData());

        dao.updateSharedByInfo(bookmark);
    }

    public void saveUserReview(User user, Bookmark bookmark, String review) {
        //postAReview calls depending on the user type
        UserReview userReview = user.postAReview(user, bookmark, review);
        dao.saveUserReview(userReview);
    }

    public List<UserReview> getPendingUserReviews() {
        return dao.getPendingUserReviews();
    }

    public <T extends Editor>void markReviewApproved(UserReview userReview, T reviewedBy) {
        UserReview approvedReview = reviewedBy.approveReview(userReview, reviewedBy);
        dao.updateUserReviews(approvedReview);
    }

    public <T extends Editor> void markReviewRejected(UserReview userReview, T reviewedBy) {
        UserReview rejectedReview = reviewedBy.rejectReview(userReview, reviewedBy);
        dao.updateUserReviews(rejectedReview);
    }

    public Collection<Bookmark> getBooks(boolean isBookmarked, long id) {
        return dao.getBooks(isBookmarked, id);
    }
    public Collection<Bookmark> getMovies(boolean isBookmarked, long id) {
        return dao.getMovies(isBookmarked, id);
    }
    public Collection<Bookmark> getWeblinks(boolean isBookmarked, long id) {
        return dao.getWeblinks(isBookmarked, id);
    }

    public Collection<UserReview> getReviewedBooks(long id) {
        return dao.getReviewedBooks(id);
    }
    public Collection<UserReview> getReviewedMovies(long id) {
        return dao.getReviewedMovies(id);
    }
    public Collection<UserReview> getReviewedWeblinks(long id) {
        return dao.getReviewedWeblinks(id);
    }

    public Bookmark getBookmark(long uid, long bid, String type) {
        return dao.getBookmark(uid,bid, type);
    }
}
