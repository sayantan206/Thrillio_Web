package com.dey.sayantan.thrillio.dao;


import com.dey.sayantan.thrillio.DataStore;
import com.dey.sayantan.thrillio.Util.DBUtility;
import com.dey.sayantan.thrillio.constants.BookGenre;
import com.dey.sayantan.thrillio.constants.MovieGenre;
import com.dey.sayantan.thrillio.entities.*;
import com.dey.sayantan.thrillio.managers.BookmarkManager;
import com.dey.sayantan.thrillio.managers.UserManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BookmarkDao {
    private static final String url = "jdbc:mysql://localhost:3306/jid_thrillio?userSSL = false";
    private static final String uid = "root";
    private static final String password = "developer";

    public BookmarkDao() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    public List<List<Bookmark>> getBookmarks() {
        return DataStore.getBookmarks();
    }

    /**
     * sets the new bookmarks added by the user in the data store
     * in more advanced versions this will be some sql queries that will insert bookmark data in db
     *
     * @param userBookmark
     */
    public void saveUserBookmarkedItem(UserBookmark userBookmark) {
//        DataStore.addUserBookmarks(userBookmark);
        System.out.println(userBookmark.getUser().getEmail().equals("") ? "User-" + userBookmark.getUser().getId() : userBookmark.getUser().getEmail() +
                " is bookmarking " + userBookmark.getBookmark());

        try (Connection conn = DriverManager.getConnection(url, uid, password);
             Statement stmt = conn.createStatement()) {
            if (userBookmark.getBookmark() instanceof Book) {
                saveUserBook(userBookmark, stmt);
            } else if (userBookmark.getBookmark() instanceof Movie) {
                saveUserMovie(userBookmark, stmt);
            } else {
                saveUserWebLink(userBookmark, stmt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUserBookmarkedItem(UserBookmark userBookmark) {
        System.out.println(userBookmark.getUser().getEmail() + " deleting bookmark " + userBookmark.getBookmark());

        try (Connection conn = DriverManager.getConnection(url, uid, password);
             Statement stmt = conn.createStatement()) {
            if (userBookmark.getBookmark() instanceof Book) {
                deleteUserBook(userBookmark, stmt);
            } else if (userBookmark.getBookmark() instanceof Movie) {
                deleteUserMovie(userBookmark, stmt);
            } else {
                deleteUserWebLink(userBookmark, stmt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveUserWebLink(UserBookmark userBookmark, Statement stmt) throws SQLException {
        String query = "INSERT INTO USER_WEBLINK (USER_WEBLINK_USER_ID, USER_WEBLINK_WEBLINK_ID) " +
                "VALUES(" + userBookmark.getUser().getId() + "," + userBookmark.getBookmark().getId() + ")";
        stmt.executeUpdate(query);
    }

    private void saveUserMovie(UserBookmark userBookmark, Statement stmt) throws SQLException {
        String query = "INSERT INTO USER_MOVIE (USER_MOVIE_USER_ID, USER_MOVIE_MOVIE_ID) " +
                "VALUES(" + userBookmark.getUser().getId() + "," + userBookmark.getBookmark().getId() + ")";
        stmt.executeUpdate(query);
    }

    private void saveUserBook(UserBookmark userBookmark, Statement stmt) throws SQLException {
        String query = "INSERT INTO USER_BOOK (USER_BOOK_USER_ID, USER_BOOK_BOOK_ID) " +
                "VALUES(" + userBookmark.getUser().getId() + "," + userBookmark.getBookmark().getId() + ")";
        stmt.executeUpdate(query);
    }

    private void deleteUserBook(UserBookmark userBookmark, Statement stmt) throws SQLException {
        String query = "DELETE FROM USER_BOOK WHERE USER_BOOK_USER_ID =" + userBookmark.getUser().getId() + " AND USER_BOOK_BOOK_ID =" + userBookmark.getBookmark().getId();
        stmt.executeUpdate(query);
    }

    private void deleteUserMovie(UserBookmark userBookmark, Statement stmt) throws SQLException {
        String query = "DELETE FROM USER_MOVIE WHERE USER_MOVIE_USER_ID =" + userBookmark.getUser().getId() + " AND USER_MOVIE_MOVIE_ID =" + userBookmark.getBookmark().getId();
        stmt.executeUpdate(query);
    }

    private void deleteUserWebLink(UserBookmark userBookmark, Statement stmt) throws SQLException {
        String query = "DELETE FROM USER_WEBLINK WHERE USER_WEBLINK_USER_ID =" + userBookmark.getUser().getId() + " AND USER_WEBLINK_WEBLINK_ID =" + userBookmark.getBookmark().getId();
        stmt.executeUpdate(query);
    }

    public void saveUserReview(UserReview userReview) {
//        DataStore.addUserReviews(userReview);

        try (Connection conn = DriverManager.getConnection(url, uid, password)) {
            if (userReview.getBookmark() instanceof Book) {
                saveUserBookReview(userReview, conn);
            } else if (userReview.getBookmark() instanceof Movie) {
                saveUserMovieReview(userReview, conn);
            } else {
                saveUserWebLinkReview(userReview, conn);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveUserWebLinkReview(UserReview userReview, Connection conn) throws SQLException {
        String query = "UPDATE USER_WEBLINK SET USER_WEBLINK_REVIEW = ?, USER_WEBLINK_REVIEW_STATUS = 'Not Reviewed'" +
                "WHERE USER_WEBLINK_USER_ID = ? AND USER_WEBLINK_WEBLINK_ID = ?";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, userReview.getReview());
        stmt.setLong(2, userReview.getUser().getId());
        stmt.setLong(3, userReview.getBookmark().getId());
        stmt.executeUpdate();
        stmt.close();
    }

    private void saveUserMovieReview(UserReview userReview, Connection conn) throws SQLException {
        String query = "UPDATE USER_MOVIE SET USER_MOVIE_REVIEW = ?, USER_MOVIE_REVIEW_STATUS = 'Not Reviewed'" +
                "WHERE USER_MOVIE_USER_ID = ? AND USER_MOVIE_MOVIE_ID = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, userReview.getReview());
        stmt.setLong(2, userReview.getUser().getId());
        stmt.setLong(3, userReview.getBookmark().getId());
        stmt.executeUpdate();
        stmt.close();
    }

    private void saveUserBookReview(UserReview userReview, Connection conn) throws SQLException {
        String query = "UPDATE USER_BOOK SET USER_BOOK_REVIEW = ?, USER_BOOK_REVIEW_STATUS = 'Not Reviewed'" +
                "WHERE USER_BOOK_USER_ID = ? AND USER_BOOK_BOOK_ID = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, userReview.getReview());
        stmt.setLong(2, userReview.getUser().getId());
        stmt.setLong(3, userReview.getBookmark().getId());
        stmt.executeUpdate();
        stmt.close();
    }

    public List<UserReview> getPendingUserReviews() {
        return DataStore.getPendingUserReviews();
    }

    public void updateUserReviews(UserReview userReview) {
//        DataStore.updateUserReviews(userReview);
        try (Connection conn = DriverManager.getConnection(url, uid, password)) {
            String query;
            if (userReview.getBookmark() instanceof Book) {
                query = "UPDATE USER_BOOK SET USER_BOOK_REVIEW_STATUS = ? , USER_BOOK_REVIEWED_BY = ? " +
                        "WHERE USER_BOOK_USER_ID = ? AND USER_BOOK_BOOK_ID = ?";
            } else if (userReview.getBookmark() instanceof Movie) {
                query = "UPDATE USER_MOVIE SET USER_MOVIE_REVIEW_STATUS = ? , USER_MOVIE_REVIEWED_BY = ? " +
                        "WHERE USER_MOVIE_USER_ID = ? AND USER_MOVIE_MOVIE_ID = ?";
            } else {
                query = "UPDATE USER_WEBLINK SET USER_WEBLINK_REVIEW_STATUS = ? , USER_WEBLINK_REVIEWED_BY = ? " +
                        "WHERE USER_WEBLINK_USER_ID = ? AND USER_WEBLINK_WEBLINK_ID = ?";
            }
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, userReview.isApproved() ? "Approved" : "Rejected");
            stmt.setLong(2, userReview.getRequestReviewedBy().getId());
            stmt.setLong(3, userReview.getUser().getId());
            stmt.setLong(4, userReview.getBookmark().getId());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateKidFriendlyStatus(Bookmark bookmark) {
        int kidFriendlyStatus = bookmark.getKidFriendlyStatus().ordinal();
        long userId = bookmark.getKidFriendlyMarkedBy().getId();

        String table = "BOOK";
        if (bookmark instanceof Movie)
            table = "MOVIE";
        else if (bookmark instanceof WebLink)
            table = "WEBLINK";


        try (Connection conn = DriverManager.getConnection(url, uid, password);
             Statement stmt = conn.createStatement()) {
            String query = "UPDATE " + table + " SET " + table + "_KID_FRIENDLY_STATUS =" + kidFriendlyStatus + "," + table + "_KID_FRIENDLY_MARKED_BY =" +
                    userId + " WHERE " + table + "_ID = " + bookmark.getId();
            stmt.executeUpdate(query);
            System.out.println("executed sql = " + query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSharedByInfo(Bookmark bookmark) {
        long userId = bookmark.getSharedBy().getId();
        String table = bookmark instanceof Book ? "BOOK" : "WEBLINK";

        try (Connection conn = DriverManager.getConnection(url, uid, password);
             Statement stmt = conn.createStatement()) {
            String query = "UPDATE " + table + " SET " + table + "_SHARED_BY = " + userId + " WHERE " + table + "_ID = " + bookmark.getId();

            stmt.executeUpdate(query);
            System.out.println("executed sql = " + query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Collection<Bookmark> getBooks(boolean isBookmarked, long userId) {
        Collection<Bookmark> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, uid, password);
             Statement stmt = conn.createStatement()) {
            String query = "";
            if (!isBookmarked) {
                //query to fetch bookmarks which not have been bookmarked by user
                query = "SELECT\n" +
                        "  BOOK_ID,\n" +
                        "  BOOK_TITLE,\n" +
                        "  BOOK_IMAGE_URL,\n" +
                        "  BOOK_PUBLICATION_YEAR,\n" +
                        "  GROUP_CONCAT(AUTHOR_NAME SEPARATOR ',') AS AUTHORS,\n" +
                        "  BOOK_GENRE_ID,\n" +
                        "  BOOK_AMAZON_RATING\n" +
                        "FROM BOOK\n" +
                        "  JOIN BOOK_AUTHOR ON BOOK_ID = BOOK_AUTHOR_BOOK_ID\n" +
                        "  JOIN AUTHOR ON BOOK_AUTHOR_AUTHOR_ID = AUTHOR_ID AND\n" +
                        "                 BOOK_ID NOT IN (SELECT USER_BOOK_BOOK_ID\n" +
                        "                                 FROM USER\n" +
                        "                                   JOIN USER_BOOK ON USER_BOOK_USER_ID = USER_ID AND USER_ID = " + userId + ") GROUP BY BOOK_ID";
            } else {
                //query to fetch bookmarks which have been bookmarked by user
                query = "SELECT\n" +
                        "  BOOK_ID,\n" +
                        "  BOOK_TITLE,\n" +
                        "  BOOK_IMAGE_URL,\n" +
                        "  BOOK_PUBLICATION_YEAR,\n" +
                        "  GROUP_CONCAT(AUTHOR_NAME SEPARATOR ',') AS AUTHORS,\n" +
                        "  BOOK_GENRE_ID,\n" +
                        "  BOOK_AMAZON_RATING\n" +
                        "FROM BOOK\n" +
                        "  JOIN BOOK_AUTHOR ON BOOK_ID = BOOK_AUTHOR_BOOK_ID\n" +
                        "  JOIN AUTHOR ON BOOK_AUTHOR_AUTHOR_ID = AUTHOR_ID AND\n" +
                        "                 BOOK_ID IN (SELECT USER_BOOK_BOOK_ID\n" +
                        "                                 FROM USER\n" +
                        "                                   JOIN USER_BOOK ON USER_BOOK_USER_ID = USER_ID AND USER_ID = " + userId + ") GROUP BY BOOK_ID";

            }
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                long id = rs.getLong("BOOK_ID");
                String title = rs.getString("BOOK_TITLE");
                String imageUrl = rs.getString("BOOK_IMAGE_URL");
                int publicationYear = rs.getInt("BOOK_PUBLICATION_YEAR");
                String[] authors = rs.getString("AUTHORS").split(",");
                BookGenre genre = BookGenre.values()[rs.getInt("BOOK_GENRE_ID")];
                double amazonRating = rs.getDouble("BOOK_AMAZON_RATING");

                Book book = BookmarkManager.getInstance().createBook(id, title, imageUrl, null, publicationYear, null, authors, genre, amazonRating);
                result.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Collection<Bookmark> getMovies(boolean isBookmarked, long userId) {
        Collection<Bookmark> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, uid, password);
             Statement stmt = conn.createStatement()) {
            String query = "";
            if (!isBookmarked) {
                //query to fetch bookmarks which not have been bookmarked by user
                query = "SELECT\n" +
                        "  MOVIE_ID,\n" +
                        "  MOVIE_TITLE,\n" +
                        "  MOVIE_IMAGE_URL,\n" +
                        "  MOVIE_RELEASE_YEAR,\n" +
                        "  GROUP_CONCAT(DISTINCT ACTOR_NAME SEPARATOR ',')    AS CAST_NAMES,\n" +
                        "  GROUP_CONCAT(DISTINCT DIRECTOR_NAME SEPARATOR ',') AS DIRECTOR_NAMES,\n" +
                        "  MOVIE_IMDB_RATING,\n" +
                        "  MOVIE_GENRE_ID\n" +
                        "FROM movie, actor, movie_actor, director, movie_director\n" +
                        "WHERE MOVIE_ID = MOVIE_ACTOR_MOVIE_ID AND MOVIE_ACTOR_ACTOR_ID = ACTOR_ID AND MOVIE_ID = MOVIE_DIRECTOR_MOVIE_ID\n" +
                        "      AND MOVIE_DIRECTOR_DIRECTOR_ID = DIRECTOR_ID AND MOVIE_ID NOT IN(SELECT USER_MOVIE_MOVIE_ID\n" +
                        "                                                                       FROM USER\n" +
                        "                                                                         JOIN USER_MOVIE ON USER_MOVIE_USER_ID = USER_ID AND USER_ID = " + userId + ")\n" +
                        "GROUP BY MOVIE_ID";
            } else {
                //query to fetch bookmarks which have been bookmarked by user
                query = "SELECT\n" +
                        "  MOVIE_ID,\n" +
                        "  MOVIE_TITLE,\n" +
                        "  MOVIE_IMAGE_URL,\n" +
                        "  MOVIE_RELEASE_YEAR,\n" +
                        "  GROUP_CONCAT(DISTINCT ACTOR_NAME SEPARATOR ',')    AS CAST_NAMES,\n" +
                        "  GROUP_CONCAT(DISTINCT DIRECTOR_NAME SEPARATOR ',') AS DIRECTOR_NAMES,\n" +
                        "  MOVIE_IMDB_RATING,\n" +
                        "  MOVIE_GENRE_ID\n" +
                        "FROM movie, actor, movie_actor, director, movie_director\n" +
                        "WHERE MOVIE_ID = MOVIE_ACTOR_MOVIE_ID AND MOVIE_ACTOR_ACTOR_ID = ACTOR_ID AND MOVIE_ID = MOVIE_DIRECTOR_MOVIE_ID\n" +
                        "      AND MOVIE_DIRECTOR_DIRECTOR_ID = DIRECTOR_ID AND MOVIE_ID IN(SELECT USER_MOVIE_MOVIE_ID\n" +
                        "                                                                       FROM USER\n" +
                        "                                                                         JOIN USER_MOVIE ON USER_MOVIE_USER_ID = USER_ID AND USER_ID = " + userId + ")\n" +
                        "GROUP BY MOVIE_ID";

            }
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                long id = rs.getLong("MOVIE_ID");
                String title = rs.getString("MOVIE_TITLE");
                String imageUrl = rs.getString("MOVIE_IMAGE_URL");
                int releaseYear = rs.getInt("MOVIE_RELEASE_YEAR");
                String[] cast = rs.getString("CAST_NAMES").split(",");
                String[] director = rs.getString("DIRECTOR_NAMES").split(",");
                MovieGenre genre = MovieGenre.values()[rs.getInt("MOVIE_GENRE_ID")];
                double imdbRating = rs.getDouble("MOVIE_IMDB_RATING");

                Movie movie = BookmarkManager.getInstance().createMovie(id, title, imageUrl, null, releaseYear, cast, director, genre, imdbRating);
                result.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    public Collection<Bookmark> getWeblinks(boolean isBookmarked, long userId) {
        Collection<Bookmark> result = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, uid, password);
             Statement stmt = conn.createStatement()) {
            String query = "";
            if (!isBookmarked) {
                //query to fetch bookmarks which not have been bookmarked by user
                query = "SELECT\n" +
                        "  WEBLINK_ID,\n" +
                        "  WEBLINK_TITLE,\n" +
                        "  WEBLINK_IMAGE_URL,\n" +
                        "  WEBLINK_URL,\n" +
                        "  WEBLINK_HOST\n" +
                        "FROM WEBLINK where WEBLINK_ID NOT IN(SELECT USER_WEBLINK_WEBLINK_ID\n" +
                        "                                     FROM USER\n" +
                        "                                       JOIN USER_WEBLINK\n" +
                        "                                         ON USER_WEBLINK_USER_ID = USER_ID AND\n" +
                        "                                            USER_ID =  " + userId + ")";
            } else {
                //query to fetch bookmarks which have been bookmarked by user
                query = "SELECT\n" +
                        "  WEBLINK_ID,\n" +
                        "  WEBLINK_TITLE,\n" +
                        "  WEBLINK_IMAGE_URL,\n" +
                        "  WEBLINK_URL,\n" +
                        "  WEBLINK_HOST\n" +
                        "FROM WEBLINK where WEBLINK_ID IN(SELECT USER_WEBLINK_WEBLINK_ID\n" +
                        "                                     FROM USER\n" +
                        "                                       JOIN USER_WEBLINK\n" +
                        "                                         ON USER_WEBLINK_USER_ID = USER_ID AND\n" +
                        "                                            USER_ID =  " + userId + ")";

            }
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                long id = rs.getLong("WEBLINK_ID");
                String title = rs.getString("WEBLINK_TITLE");
                String imageUrl = rs.getString("WEBLINK_IMAGE_URL");
                String url = rs.getString("WEBLINK_URL");
                String host = rs.getString("WEBLINK_HOST");

                WebLink webLink = BookmarkManager.getInstance().createWebLink(id, title, imageUrl, null, url, host);
                result.add(webLink);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    public Bookmark getBookmark(long userId, long bid, String type) {
        String query = "";
        Bookmark bookmark = null;
        try (Connection conn = DriverManager.getConnection(url, uid, password); Statement stmt = conn.createStatement()) {
            if (type.equalsIgnoreCase("Book")) {
                //query to fetch bookmarks which have been bookmarked by user
                query = "SELECT\n" +
                        "  BOOK_ID,\n" +
                        "  BOOK_TITLE,\n" +
                        "  BOOK_IMAGE_URL,\n" +
                        "  BOOK_PUBLICATION_YEAR,\n" +
                        "  GROUP_CONCAT(AUTHOR_NAME SEPARATOR ',') AS AUTHORS,\n" +
                        "  BOOK_GENRE_ID,\n" +
                        "  BOOK_AMAZON_RATING\n" +
                        "FROM BOOK\n" +
                        "  JOIN BOOK_AUTHOR ON BOOK_ID = BOOK_AUTHOR_BOOK_ID\n" +
                        "  JOIN AUTHOR ON BOOK_AUTHOR_AUTHOR_ID = AUTHOR_ID AND\n" +
                        "                 BOOK_ID IN (SELECT USER_BOOK_BOOK_ID\n" +
                        "                                 FROM USER\n" +
                        "                                   JOIN USER_BOOK ON USER_BOOK_USER_ID = USER_ID AND USER_ID = " + userId + ") AND BOOK_ID = " + bid + " GROUP BY BOOK_ID";

                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    long id = rs.getLong("BOOK_ID");
                    String title = rs.getString("BOOK_TITLE");
                    String imageUrl = rs.getString("BOOK_IMAGE_URL");
                    int publicationYear = rs.getInt("BOOK_PUBLICATION_YEAR");
                    String[] authors = rs.getString("AUTHORS").split(",");
                    BookGenre genre = BookGenre.values()[rs.getInt("BOOK_GENRE_ID")];
                    double amazonRating = rs.getDouble("BOOK_AMAZON_RATING");

                    bookmark = BookmarkManager.getInstance().createBook(id, title, imageUrl, null, publicationYear, null, authors, genre, amazonRating);
                }

            } else if (type.equalsIgnoreCase("Movie")) {
                //query to fetch bookmarks which have been bookmarked by user
                query = "SELECT\n" +
                        "  MOVIE_ID,\n" +
                        "  MOVIE_TITLE,\n" +
                        "  MOVIE_IMAGE_URL,\n" +
                        "  MOVIE_RELEASE_YEAR,\n" +
                        "  GROUP_CONCAT(DISTINCT ACTOR_NAME SEPARATOR ',')    AS CAST_NAMES,\n" +
                        "  GROUP_CONCAT(DISTINCT DIRECTOR_NAME SEPARATOR ',') AS DIRECTOR_NAMES,\n" +
                        "  MOVIE_IMDB_RATING,\n" +
                        "  MOVIE_GENRE_ID\n" +
                        "FROM movie, actor, movie_actor, director, movie_director\n" +
                        "WHERE MOVIE_ID = MOVIE_ACTOR_MOVIE_ID AND MOVIE_ACTOR_ACTOR_ID = ACTOR_ID AND MOVIE_ID = MOVIE_DIRECTOR_MOVIE_ID\n" +
                        "      AND MOVIE_DIRECTOR_DIRECTOR_ID = DIRECTOR_ID AND MOVIE_ID IN(SELECT USER_MOVIE_MOVIE_ID\n" +
                        "                                                                       FROM USER\n" +
                        "                                                                         JOIN USER_MOVIE ON USER_MOVIE_USER_ID = USER_ID AND USER_ID = " + userId + ")\n" +
                        "AND MOVIE_ID = " + bid + " GROUP BY MOVIE_ID";

                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    long id = rs.getLong("MOVIE_ID");
                    String title = rs.getString("MOVIE_TITLE");
                    String imageUrl = rs.getString("MOVIE_IMAGE_URL");
                    int releaseYear = rs.getInt("MOVIE_RELEASE_YEAR");
                    String[] cast = rs.getString("CAST_NAMES").split(",");
                    String[] director = rs.getString("DIRECTOR_NAMES").split(",");
                    MovieGenre genre = MovieGenre.values()[rs.getInt("MOVIE_GENRE_ID")];
                    double imdbRating = rs.getDouble("MOVIE_IMDB_RATING");

                    bookmark = BookmarkManager.getInstance().createMovie(id, title, imageUrl, null, releaseYear, cast, director, genre, imdbRating);
                }
            } else {
                //query to fetch bookmarks which have been bookmarked by user
                query = "SELECT\n" +
                        "  WEBLINK_ID,\n" +
                        "  WEBLINK_TITLE,\n" +
                        "  WEBLINK_IMAGE_URL,\n" +
                        "  WEBLINK_URL,\n" +
                        "  WEBLINK_HOST\n" +
                        "FROM WEBLINK where WEBLINK_ID IN(SELECT USER_WEBLINK_WEBLINK_ID\n" +
                        "                                     FROM USER\n" +
                        "                                       JOIN USER_WEBLINK\n" +
                        "                                         ON USER_WEBLINK_USER_ID = USER_ID AND\n" +
                        "                                            USER_ID =  " + userId + ") AND WEBLINK_ID = " + bid;

                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    long id = rs.getLong("WEBLINK_ID");
                    String title = rs.getString("WEBLINK_TITLE");
                    String imageUrl = rs.getString("WEBLINK_IMAGE_URL");
                    String url = rs.getString("WEBLINK_URL");
                    String host = rs.getString("WEBLINK_HOST");

                    bookmark = BookmarkManager.getInstance().createWebLink(id, title, imageUrl, null, url, host);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookmark;
    }

    public Collection<UserReview> getReviewedBooks(long id) {
        List<UserReview> booksList = new ArrayList<>();
        String query = "SELECT\n" +
                "  BOOK_ID,\n" +
                "  BOOK_TITLE,\n" +
                "  BOOK_IMAGE_URL,\n" +
                "  BOOK_PUBLICATION_YEAR,\n" +
                "  BOOK_AMAZON_RATING,\n" +
                "  GROUP_CONCAT(AUTHOR_NAME SEPARATOR ',') AS AUTHORS,\n" +
                "  USER_BOOK_REVIEW\n" +
                "  USER_BOOK_REVIEW_STATUS\n" +
                "FROM BOOK\n" +
                "  JOIN BOOK_AUTHOR ON BOOK_ID = BOOK_AUTHOR_BOOK_ID\n" +
                "  JOIN AUTHOR ON BOOK_AUTHOR_AUTHOR_ID = AUTHOR_ID JOIN\n" +
                "USER_BOOK ON BOOK_ID = USER_BOOK_BOOK_ID AND USER_BOOK_REVIEW_STATUS is not NULL AND USER_BOOK_USER_ID ='"+id+"' GROUP BY BOOK_ID";

        try (Connection conn = DriverManager.getConnection(url, uid, password); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                long book_id = rs.getLong("BOOK_ID");
                String title = rs.getString("BOOK_TITLE");
                String imageUrl = rs.getString("BOOK_IMAGE_URL");
                int publicationYear = rs.getInt("BOOK_PUBLICATION_YEAR");
                String[] authors = rs.getString("AUTHORS").split(",");
                double amazonRating = rs.getDouble("BOOK_AMAZON_RATING");
                String review = rs.getString("USER_BOOK_REVIEW");
                String reviewStatus = rs.getString("USER_BOOK_REVIEW_STATUS");
                UserReview userReview = new UserReview(UserManager.getInstance().createUser(id),
                        BookmarkManager.getInstance().createBook(book_id,title,imageUrl,null,publicationYear,null,authors,null,amazonRating),review);
                userReview.setApproved(reviewStatus.equalsIgnoreCase("Approved")?true:false);

                booksList.add(userReview);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booksList;
    }

    public Collection<UserReview> getReviewedMovies(long id) {
        return null;
    }

    public Collection<UserReview> getReviewedWeblinks(long id) {
        return null;
    }
}
