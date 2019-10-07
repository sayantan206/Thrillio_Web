package com.dey.sayantan.thrillio;

import com.dey.sayantan.thrillio.constants.KidFriendlyStatus;
import com.dey.sayantan.thrillio.entities.*;
import com.dey.sayantan.thrillio.managers.BookmarkManager;
import com.dey.sayantan.thrillio.managers.UserManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

//this class acts as a central controller and it takes data from models and showing it to view(cli)
@WebServlet(urlPatterns = {"/browse", "/browse/save", "/browse/mybooks", "/browse/mybooks/delete", "/browse/mybooks/review", "/browse/mybooks/review/submitReview","/browse/mybooks/review/myReviews"})
public class Launch extends HttpServlet {
    public static final int GUEST_ID = 0;
    private static List<User> users;
    private static List<List<Bookmark>> bookmarks;
    private static View view = new View();

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = null;
        System.out.println("Request path: " + request.getServletPath());

        //check session validity
        if (request.getSession().getAttribute("userId") != null) {
            long userId = (long) request.getSession().getAttribute("userId");

            if (userId != GUEST_ID) {
                //features available only to registered users
                if (request.getServletPath().contains("/browse")) {
                    if (request.getServletPath().contains("save")) {
                        //save the current bookmark and reload page
                        dispatcher = request.getRequestDispatcher("/browse.jsp");
                        Long bookmarkId = Long.valueOf(request.getParameter("bid"));
                        String type = request.getParameter("type");

                        User user = UserManager.getInstance().createUser(userId, "", "", "", "", null, null);
                        Bookmark bookmark = type.equalsIgnoreCase("Book") ? BookmarkManager.getInstance().createBook(bookmarkId, "", "", 0, "", new String[]{}, null, 0)
                                : type.equalsIgnoreCase("Movie") ? BookmarkManager.getInstance().createMovie(bookmarkId, "", null, 0, new String[]{}, new String[]{}, null, 0)
                                : BookmarkManager.getInstance().createWebLink(bookmarkId, "", null, "", "");
                        BookmarkManager.getInstance().saveUserBookmarkedItem(bookmark, user);
                        dispatchBookmarks(request, userId, false, null);

                    } else if (request.getServletPath().contains("mybooks")) {
                        if (request.getServletPath().contains("mybooks/delete")) {
                            //delete item from my books
                            dispatcher = request.getRequestDispatcher("/mybooks.jsp");
                            Long bookmarkId = Long.valueOf(request.getParameter("bid"));
                            String type = request.getParameter("type");

                            User user = UserManager.getInstance().createUser(userId, "", "", "", "", null, null);
                            Bookmark bookmark = type.equalsIgnoreCase("Book") ? BookmarkManager.getInstance().createBook(bookmarkId, "", "", 0, "", new String[]{}, null, 0)
                                    : type.equalsIgnoreCase("Movie") ? BookmarkManager.getInstance().createMovie(bookmarkId, "", null, 0, new String[]{}, new String[]{}, null, 0)
                                    : BookmarkManager.getInstance().createWebLink(bookmarkId, "", null, "", "");
                            BookmarkManager.getInstance().deleteUserBookmarkedItem(bookmark, user);
                            dispatchBookmarks(request, userId, true, null);

                        } else if (request.getServletPath().contains("mybooks/review")) {

                            if (request.getServletPath().contains("/submitReview")) {
                                String review = request.getParameter("review");
                                long uid = (long) request.getSession().getAttribute("userId");
                                long bid = Long.parseLong(request.getParameter("bid"));
                                String type = request.getParameter("type");

                                saveUserReview(UserManager.getInstance().createUser(uid), BookmarkManager.getInstance().createBookmark(bid,type), review);
                                dispatcher = request.getRequestDispatcher("/userReview.jsp");
                            } else if (request.getServletPath().contains("myReviews")) {
                                dispatcher = request.getRequestDispatcher("/userReview.jsp");
                                String contentFilter = request.getParameter("show");
                                dispatchReviewedBookmarks(request, userId, true, contentFilter);

                            } else {
                                dispatcher = request.getRequestDispatcher("/reviewForm.jsp");
                            }

                        } else {
                            // show saved bookmarks
                            dispatcher = request.getRequestDispatcher("/mybooks.jsp");
                            String contentFilter = request.getParameter("show");
                            dispatchBookmarks(request, userId, true, contentFilter);
                        }
                    } else {
                        //show all bookmarks for guest and other users
                        dispatcher = request.getRequestDispatcher("/browse.jsp");
                        dispatchBookmarks(request, userId, false, null);
                    }
                }
            } else {
                //features available to all users
                if (request.getServletPath().equals("/browse")) {
                    //show all bookmarks for guest and other users
                    dispatcher = request.getRequestDispatcher("/browse.jsp");
                    dispatchBookmarks(request, userId, false, null);
                } else {
                    //for any attempt to save or go to my books will ask the user to sign in first
                    dispatcher = request.getRequestDispatcher("/index.jsp");
                }
            }
        } else {
            //session expires so forward to login page
            dispatcher = request.getRequestDispatcher("/index.jsp");
        }

        dispatcher.forward(request, response);
    }

    private void dispatchReviewedBookmarks(HttpServletRequest request, long userId, boolean isBookmarked, String contentFilter) {
        contentFilter = contentFilter == null || contentFilter.length() <= 0 ? "all" : contentFilter;
        Collection<UserReview> booksList = null;
        Collection<UserReview> moviesList = null;
        Collection<UserReview> weblinksList = null;

        if (contentFilter.equalsIgnoreCase("books")) {
            booksList = BookmarkManager.getInstance().getReviewedBooks(userId);
        } else if (contentFilter.equalsIgnoreCase("movies")) {
            moviesList = BookmarkManager.getInstance().getReviewedMovies(userId);
        } else if (contentFilter.equalsIgnoreCase("weblinks")) {
            weblinksList = BookmarkManager.getInstance().getReviewedWeblinks(userId);
        } else {
            booksList = BookmarkManager.getInstance().getReviewedBooks(userId);
            moviesList = BookmarkManager.getInstance().getReviewedMovies(userId);
            weblinksList = BookmarkManager.getInstance().getReviewedWeblinks(userId);
        }

        request.setAttribute("booksList", booksList);
        request.setAttribute("moviesList", moviesList);
        request.setAttribute("weblinksList", weblinksList);
    }

    private void dispatchBookmarks(HttpServletRequest request, long userId, boolean isBookmarked, String contentFilter) {
        contentFilter = contentFilter == null || contentFilter.length() <= 0 ? "all" : contentFilter;
        Collection<Bookmark> booksList = null;
        Collection<Bookmark> moviesList = null;
        Collection<Bookmark> weblinksList = null;

        if (contentFilter.equalsIgnoreCase("books")) {
            booksList = BookmarkManager.getInstance().getBooks(isBookmarked, userId);
        } else if (contentFilter.equalsIgnoreCase("movies")) {
            moviesList = BookmarkManager.getInstance().getMovies(isBookmarked, userId);
        } else if (contentFilter.equalsIgnoreCase("weblinks")) {
            weblinksList = BookmarkManager.getInstance().getWeblinks(isBookmarked, userId);
        } else {
            booksList = BookmarkManager.getInstance().getBooks(isBookmarked, userId);
            moviesList = BookmarkManager.getInstance().getMovies(isBookmarked, userId);
            weblinksList = BookmarkManager.getInstance().getWeblinks(isBookmarked, userId);
        }

        request.setAttribute("booksList", booksList);
        request.setAttribute("moviesList", moviesList);
        request.setAttribute("weblinksList", weblinksList);
    }

    /**
     * this method loads data from datastore and request data from models
     */
    public static void loadData() {
        System.out.println("+++++++++++++++++++++ Loading Data ++++++++++++++++++++++");
        DataStore.loadData();

        users = UserManager.getInstance().getUsers();
        bookmarks = BookmarkManager.getInstance().getBookmarks();

        System.out.println("+++++++++++++++++++++ Printing data +++++++++++++++++++++");
        printUserData();
        printBookmarkData();
    }

    private static void printBookmarkData() {
        for (List<Bookmark> bookmark : bookmarks)
            for (Bookmark obj : bookmark)
                System.out.println(obj.toString());
    }


    private static void printUserData() {
        for (User user : users) System.out.println(user.toString());
    }


    private static void startBookmarking() {
        System.out.println("\n+++++++++++++++++++++ Bookmarking ++++++++++++++++++++++");

        //bookmarking for each user and saving bookmarks
        for (User user : users) {
            view.browse(user, bookmarks);
        }
    }

    public static void setKidFriendlyStatus(KidFriendlyStatus kidFriendlyStatus, Bookmark bookmark, User user) {
        BookmarkManager.getInstance().setKidFriendlyStatus(kidFriendlyStatus, bookmark, user);
    }

    public void saveUserBookmarkedItem(Bookmark bookmark, User user) {
        BookmarkManager.getInstance().saveUserBookmarkedItem(bookmark, user);
    }

    public void share(User user, Bookmark bookmark) {
        BookmarkManager.getInstance().share(user, bookmark);
    }

    public void saveUserReview(User user, Bookmark bookmark, String review) {
        BookmarkManager.getInstance().saveUserReview(user, bookmark, review);
    }

    public static void main(String[] args) {
        DataStore.clearExistingData();
        loadData();
        startBookmarking();
        System.out.println("\n+++++++++++++++++++++ User Reviews ++++++++++++++++++++++");
        DataStore.printUserReviews();
    }

    //this method returns updated userReviews for every editorial person on request
    public List<UserReview> getPendingUserReviews() {
        return BookmarkManager.getInstance().getPendingUserReviews();
    }

    public <T extends Editor> void markReviewApproved(UserReview userReview, T reviewedBy) {
        BookmarkManager.getInstance().markReviewApproved(userReview, reviewedBy);
    }

    public <T extends Editor> void markReviewRejected(UserReview userReview, T reviewedBy) {
        BookmarkManager.getInstance().markReviewRejected(userReview, reviewedBy);
    }
}
