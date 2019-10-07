package com.dey.sayantan.thrillio;

import com.dey.sayantan.thrillio.constants.BookGenre;
import com.dey.sayantan.thrillio.constants.Gender;
import com.dey.sayantan.thrillio.constants.MovieGenre;
import com.dey.sayantan.thrillio.constants.UserType;
import com.dey.sayantan.thrillio.entities.*;
import com.dey.sayantan.thrillio.managers.BookmarkManager;
import com.dey.sayantan.thrillio.managers.UserManager;

import java.io.BufferedReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//TODO: use executeBatch for multiple queries
public class DataStore {
    public static final int TOTAL_USER_COUNT = 5;
    public static final int BOOKMARK_TYPE_COUNT = 3;
    public static final int BOOKMARK_COUNT_PER_TYPE = 5;
    public static final int USER_BOOKMARK_LIMIT = 5;
    public static final int REVIEW_LIMIT_PER_USER = 3;
    public static BufferedReader reader;
    //connection string : <protocol>:<sub-protocol>:<data-source details>
    public static final String url = "jdbc:mysql://localhost:3306/jid_thrillio?userSSL = false";
    public static final String uid = "root";
    public static final String password = "developer";

    //TODO: make deep copy for every data structure
    private static List<User> users = new ArrayList<>(TOTAL_USER_COUNT);
    private static List<List<Bookmark>> bookmarks = new ArrayList<>();

    private static List<UserBookmark> userBookmarks = new ArrayList<>(USER_BOOKMARK_LIMIT);
    private static List<UserReview> userReviews = new ArrayList<>();

    public static void loadData() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            //TODO: read url, uid, and password from config file
            try (Connection conn = DriverManager.getConnection(url, uid, password);
                 Statement stmt = conn.createStatement()) {
                loadUsers(stmt);
                loadWebLinks(stmt);
                loadMovies(stmt);
                loadBooks(stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void loadWebLinks(Statement stmt) throws SQLException {
        String query = "SELECT\n" +
                "  WEBLINK_ID,\n" +
                "  WEBLINK_TITLE,\n" +
                "  WEBLINK_URL,\n" +
                "  WEBLINK_HOST\n" +
                "FROM weblink;";
        ResultSet rs = stmt.executeQuery(query);
        List<Bookmark> bookmark = new ArrayList<>();
        while (rs.next()) {
            long id = rs.getLong("WEBLINK_ID");
            String title = rs.getString("WEBLINK_TITLE");
            String url = rs.getString("WEBLINK_URL");
            String host = rs.getString("WEBLINK_HOST");

            bookmark.add(BookmarkManager.getInstance().createWebLink(id, title, null, url, host));
        }
        bookmarks.add(bookmark);
    }

    private static void loadMovies(Statement stmt) throws SQLException {
        String query = "SELECT\n" +
                "  MOVIE_ID,\n" +
                "  MOVIE_TITLE,\n" +
                "  MOVIE_RELEASE_YEAR,\n" +
                "  GROUP_CONCAT(DISTINCT ACTOR_NAME SEPARATOR \",\") AS CAST_NAMES,\n" +
                "  GROUP_CONCAT(DISTINCT DIRECTOR_NAME SEPARATOR \",\") AS DIRECTOR_NAMES,\n" +
                "  MOVIE_IMDB_RATING,\n" +
                "  MOVIE_GENRE_ID\n" +
                "FROM movie,actor,movie_actor,director,movie_director\n" +
                "WHERE MOVIE_ID = MOVIE_ACTOR_MOVIE_ID AND MOVIE_ACTOR_ACTOR_ID = ACTOR_ID AND MOVIE_ID = MOVIE_DIRECTOR_MOVIE_ID " +
                "AND MOVIE_DIRECTOR_DIRECTOR_ID = DIRECTOR_ID\n" +
                "GROUP BY MOVIE_ID";

        ResultSet rs = stmt.executeQuery(query);
        List<Bookmark> bookmark = new ArrayList<>();
        while (rs.next()) {
            long id = rs.getLong("MOVIE_ID");
            String title = rs.getString("MOVIE_TITLE");
            int releaseYear = rs.getInt("MOVIE_RELEASE_YEAR");
            String[] cast = rs.getString("CAST_NAMES").split(",");
            String[] directors = rs.getString("DIRECTOR_NAMES").split(",");
            int imbdRating = rs.getInt("MOVIE_IMDB_RATING");
            MovieGenre genre = MovieGenre.values()[rs.getInt("MOVIE_GENRE_ID")];

            bookmark.add(BookmarkManager.getInstance().createMovie(id, title, null, releaseYear, cast, directors, genre, imbdRating));
        }
        bookmarks.add(bookmark);
    }

    private static void loadUsers(Statement stmt) throws SQLException {
        String query = "SELECT\n" +
                "  USER_ID,\n" +
                "  USER_EMAIL,\n" +
                "  USER_PASSWORD,\n" +
                "  USER_FIRST_NAME,\n" +
                "  USER_LAST_NAME,\n" +
                "  USER_GENDER_ID,\n" +
                "  USER_TYPE_ID\n" +
                "FROM user";

        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            long id = rs.getLong("USER_ID");
            String email = rs.getString("USER_EMAIL");
            String password = rs.getString("USER_PASSWORD");
            String firstName = rs.getString("USER_FIRST_NAME");
            String lastName = rs.getString("USER_LAST_NAME");
            Gender gender = Gender.values()[rs.getInt("USER_GENDER_ID")];
            UserType userType = UserType.values()[rs.getInt("USER_TYPE_ID")];

            users.add(UserManager.getInstance().createUser(id, email, password, firstName, lastName, gender, userType));
        }
    }

    private static void loadBooks(Statement stmt) throws SQLException {
        String query = "SELECT\n" +
                "  BOOK_ID,\n" +
                "  BOOK_TITLE,\n" +
                "  BOOK_PUBLICATION_YEAR,\n" +
                "  PUBLISHER_NAME,\n" +
                "  group_concat(AUTHOR_NAME SEPARATOR ',') AS AUTHOR_NAMES,\n" +
                "  BOOK_GENRE_ID,\n" +
                "  BOOK_AMAZON_RATING,\n" +
                "  BOOK_ENTRY_CREATION_TIME\n" +
                "FROM book, publisher, author, book_author\n" +
                "WHERE BOOK_PUBLISHER_ID = PUBLISHER_ID AND BOOK_ID = BOOK_AUTHOR_BOOK_ID AND BOOK_AUTHOR_AUTHOR_ID = AUTHOR_ID\n" +
                "GROUP BY BOOK_ID\n" +
                "ORDER BY BOOK_AMAZON_RATING DESC";

        ResultSet rs = stmt.executeQuery(query);
        List<Bookmark> bookmark = new ArrayList<>();
        while (rs.next()) {
            long id = rs.getLong("BOOK_ID");
            String title = rs.getString("BOOK_TITLE");
            int publicationYear = rs.getInt("BOOK_PUBLICATION_YEAR");
            String publisher = rs.getString("PUBLISHER_NAME");
            String[] authors = rs.getString("AUTHOR_NAMES").split(",");
            int genre_id = rs.getInt("BOOK_GENRE_ID");
            BookGenre genre = BookGenre.values()[genre_id];
            double amazonRating = rs.getDouble("BOOK_AMAZON_RATING");
            Date createDate = rs.getDate("BOOK_ENTRY_CREATION_TIME");
            Timestamp timestamp = rs.getTimestamp(8);

            bookmark.add(BookmarkManager.getInstance().createBook(id, title, null, publicationYear,
                    publisher, authors, genre, amazonRating));
        }
        bookmarks.add(bookmark);
    }

    public static List<User> getUsers() {
        return users;
    }

    public static List<List<Bookmark>> getBookmarks() {
        return bookmarks;
    }

//    /**
//     * this function will store the userBookmark data in data structure
//     *
//     * @param userBookmark: contains User and List<Bookmark>
//     */
//    //TODO: insert user Bookmark data in data base
//    public static void addUserBookmarks(UserBookmark userBookmark) {
//        userBookmarks.add(userBookmark);
//        System.out.println(userBookmark.getUser().getEmail() + " has bookmarked " + userBookmark.getBookmark());
//    }
//
//    public static void addUserReviews(UserReview userReview) {
//        userReviews.add(userReview);
//        System.out.println(userReview.getUser().getEmail() + " has reviewed " + userReview.getBookmark().getTitle() + "\nREVIEW: "
//                + userReview.getReview());
//
//    }


    public static void updateUserReviews(UserReview approvedReview) {
        if (userReviews.contains(approvedReview)) {
            userReviews.set(userReviews.indexOf(approvedReview), approvedReview);
        }
    }

    //this methods are for testing
//    public static List<UserBookmark> getUserBookmarks() {
//        return userBookmarks;
//    }
    //this method is for testing

    public static void printUserReviews() {
        for (UserReview userReview : userReviews) {
            System.out.println("USER"+userReview.getUser().getId() + " reviewed "+userReview.getBookmark().getClass()+ userReview.getBookmark().getId()
                    + ", as " + userReview.getReview() + ", approved: " + userReview.isApproved() +
                    ", is approved by: " + userReview.getRequestReviewedBy());
        }
    }

    public static List<UserReview> getPendingUserReviews() {
        String query1 = "SELECT\n" +
                "  USER_BOOK_USER_ID,\n" +
                "  USER_BOOK_BOOK_ID,\n" +
                "  USER_BOOK_REVIEW,\n" +
                "  USER_BOOK_REVIEW_STATUS\n" +
                "  FROM user_book, user, book\n" +
                "WHERE USER_BOOK_USER_ID = USER_ID AND\n" +
                "USER_BOOK_BOOK_ID = BOOK_ID AND\n" +
                "USER_BOOK_REVIEW IS NOT NULL AND\n"+
                "USER_BOOK_REVIEW_STATUS = 'Not Reviewed' AND\n"+
                "USER_BOOK_REVIEWED_BY IS NULL";
        String query2 = "SELECT\n" +
                "  USER_MOVIE_USER_ID,\n" +
                "  USER_MOVIE_MOVIE_ID,\n" +
                "  USER_MOVIE_REVIEW,\n" +
                "  USER_MOVIE_REVIEW_STATUS\n" +
                "FROM USER_MOVIE, USER, MOVIE\n" +
                "WHERE USER_MOVIE_USER_ID = USER_ID AND\n" +
                "      USER_MOVIE_MOVIE_ID = MOVIE_ID AND\n" +
                "      USER_MOVIE_REVIEW IS NOT NULL AND\n" +
                "      USER_MOVIE_REVIEW_STATUS = 'Not Reviewed' AND\n" +
                "      USER_MOVIE_REVIEWED_BY IS NULL";
        String query3 = "SELECT\n" +
                "  USER_WEBLINK_USER_ID,\n" +
                "  USER_WEBLINK_WEBLINK_ID,\n" +
                "  USER_WEBLINK_REVIEW,\n" +
                "  USER_WEBLINK_REVIEW_STATUS\n" +
                "FROM USER_WEBLINK, USER, WEBLINK\n" +
                "WHERE USER_WEBLINK_USER_ID = USER_ID AND\n" +
                "      USER_WEBLINK_WEBLINK_ID = WEBLINK_ID AND\n" +
                "      USER_WEBLINK_REVIEW IS NOT NULL AND\n" +
                "      USER_WEBLINK_REVIEW_STATUS = 'Not Reviewed' AND\n" +
                "      USER_WEBLINK_REVIEWED_BY IS NULL";

        try (Connection conn = DriverManager.getConnection(url, uid, password);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query1);

            while(rs.next()){
                long userId = rs.getLong("USER_BOOK_USER_ID");
                long bookid = rs.getLong("USER_BOOK_BOOK_ID");
                String review = rs.getString("USER_BOOK_REVIEW");
                String reviewStatus = rs.getString("USER_BOOK_REVIEW_STATUS");
                User user = UserManager.getInstance().createUser(userId,"","","","",null,null);
                Bookmark book = BookmarkManager.getInstance().createBook(bookid, "","",0,"",new String[]{}, null, 0);
                boolean isApproved = reviewStatus.equalsIgnoreCase("approved") ? true : false;

                UserReview userReview = new UserReview(user, book, review).setApproved(isApproved);
                userReviews.add(userReview);
            }

            rs = stmt.executeQuery(query2);
            while(rs.next()){
                long userId = rs.getLong("USER_MOVIE_USER_ID");
                long movieId = rs.getLong("USER_MOVIE_MOVIE_ID");
                String review = rs.getString("USER_MOVIE_REVIEW");
                String reviewStatus = rs.getString("USER_MOVIE_REVIEW_STATUS");
                User user = UserManager.getInstance().createUser(userId,"","","","",null,null);
                Bookmark movie = BookmarkManager.getInstance().createMovie(movieId, "", null,0, new String[]{}, new String[]{}, null, 0);
                boolean isApproved = reviewStatus.equalsIgnoreCase("approved") ? true : false;

                UserReview userReview = new UserReview(user, movie, review).setApproved(isApproved);
                userReviews.add(userReview);
            }

            rs = stmt.executeQuery(query3);
            while(rs.next()){
                long userId = rs.getLong("USER_WEBLINK_USER_ID");
                long weblinkId = rs.getLong("USER_WEBLINK_WEBLINK_ID");
                String review = rs.getString("USER_WEBLINK_REVIEW");
                String reviewStatus = rs.getString("USER_WEBLINK_REVIEW_STATUS");
                User user = UserManager.getInstance().createUser(userId,"","","","",null,null);
                Bookmark webLink = BookmarkManager.getInstance().createWebLink(weblinkId, "",null,"", "");
                boolean isApproved = reviewStatus.equalsIgnoreCase("approved") ? true : false;

                UserReview userReview = new UserReview(user, webLink, review).setApproved(isApproved);
                userReviews.add(userReview);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userReviews;
    }

    //This method is for testing and will wipe all the user bookmark related data
    public static void clearExistingData() {
        try (Connection conn = DriverManager.getConnection(url, uid, password);
             Statement stmt = conn.createStatement()) {
            String query1 = "TRUNCATE user_book";
            String query2 = "TRUNCATE user_movie";
            String query3 = "TRUNCATE user_weblink";

            System.out.println("[EXECUTING CLEANUP SQL:"+query1+"]");
            stmt.executeUpdate(query1);
            System.out.println("[EXECUTING CLEANUP SQL:"+query2+"]");
            stmt.executeUpdate(query2);
            System.out.println("[EXECUTING CLEANUP SQL:"+query3+"]");
            stmt.executeUpdate(query3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /*public static void printUserBookmarkData() {
        for (UserBookmark userBookmark: userBookmarks) {
            System.out.println(""+userBookmark.getUser().getEmail()+" has bookmarked "+userBookmark.getBookmark());
        }
    }*/
    /*public static void main(String[] args) {
        loadData();
        for (Bookmark[] bookmark: bookmarks) {
            for (Bookmark object: bookmark) System.out.println(object.toString());
        }
    }*/
}

