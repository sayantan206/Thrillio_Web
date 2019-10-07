<%@ page import="com.dey.sayantan.thrillio.constants.BookmarkType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Sayantan
  Date: 11/24/2018
  Time: 6:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>thrill.io</title>
</head>
<body>
<table style="font-family:Arial;font-size:20px;">
    <div style="height:65px;width:99%;align: center;background: #DB5227;font-family: Arial;color: white;position: fixed;margin-left: 2px;">
        <br><b>
        <a href="" style="font-family:garamond;font-size:34px;margin:0 0 0 10px;color:white;text-decoration: none;">thrill.io</a></b>
        <div style="height:25px;background: #DB5227;font-family: Arial;color: white;text-align: right;padding-right: 20px;">
            <b>
                <a href="<%=request.getContextPath()%>/browse"
                   style="font-size:16px;color:white;text-decoration:none;padding-right: 10px;">Browse</a>
                <a href="<%=request.getContextPath()%>/browse/mybooks?show=all"
                   style="font-size:16px;color:white;text-decoration:none;padding-right: 10px;">My Books</a>
                <a href="<%=request.getContextPath()%>/auth/logout"
                   style="font-size:16px;color:white;text-decoration:none;">Logout</a>
            </b>
        </div>
    </div>
    <br>

    <table>
        <td style="width: 199px;background-color: rgb(219,220,225);">
            <style>
                .sidenav {
                    width: 200px; /* Set a width if you like */
                    height: 100%; /* Full-height: remove this if you want "auto" height */
                    position: fixed; /* Fixed Sidebar (stay in place on scroll) */
                    z-index: 1; /* Stay on top */
                    top: 30%; /* Stay at the top */
                    left: 0;
                    background-color: #dbdcd7;
                    overflow-x: hidden; /* Disable horizontal scroll */
                    padding-top: 20px;
                    margin-left: 10px;
                }

                .sidenav a {
                    background-color: #eee; /* Grey background color */
                    color: black; /* Black text color */
                    display: block; /* Make the links appear below each other */
                    padding: 12px; /* Add some padding */
                    text-decoration: none; /* Remove underline from links */
                    font-size: 16px;
                }

                .sidenav a:hover {
                    background-color: #ccc; /* Dark grey background on mouse-over */
                    font-weight: bolder;
                }

                .sidenav a.active {
                    background-color: #DB5227; /* Add a green color to the "active/current" link */
                    color: white;
                    font-weight: bolder;
                }

                /* On smaller screens, where height is less than 450px, change the style of the sidebar (less padding and a smaller font size) */
                @media screen and (max-height: 450px) {
                    .sidenav {padding-top: 15px;}
                    .sidenav a {font-size: 18px;}
                }

                .item_desc_col{
                    color:gray;padding-top:5px;vertical-align:top;
                    padding-left: 5px;
                    padding-right: 5px;
                    text-align: center;
                }

                .item_desc_header{
                    padding-left: 20px;
                }
            </style>

            <div class="sidenav">
                <a href="#" id="all" class="menu active">All Reviews</a><br>
                <a href="#" class="menu">Approved Reviews</a><br>
                <a href="#" id="movies" class="menu">Pending Reviews</a>
            </div>
        </td>


        <td style="margin-left: 200px">
            <div style="font-size: 24px;color: #333333;padding: 15px 0px 0px;border-bottom: #333333 1px solid;clear: both;">
                Saved Items
            </div>
            <br><br>


            <c:choose>
                <c:when test="${!empty(booksList) || !empty(moviesList) || !empty(weblinksList)}">
                    <c:if test="${!empty(booksList)}">
                        <table>
                            <th class="item_desc_header">Cover</th>
                            <th class="item_desc_header">Title</th>
                            <th class="item_desc_header">Author</th>
                            <th class="item_desc_header">Rating</th>
                            <th class="item_desc_header">Publication Year</th>
                            <th class="item_desc_header">Review</th>
                            <th class="item_desc_header">Approved</th>

                            <c:forEach var="userReview" items="${booksList}">
                                <tr>
                                    <td>
                                        <img src="${userReview.bookmark.imageUrl}" width="90" height="120">
                                    </td>
                                    <td class="item_desc_col">
                                        <span style="color: #B13100;display: block;width: 155px;overflow: hidden;white-space: normal;">${userReview.bookmark.title}</span>
                                    </td>
                                    <td class="item_desc_col">
                                        <span style="color: #B13100;">${userReview.bookmark.authors[0]}</span>
                                    </td>
                                    <td class="item_desc_col">
                                        <span style="color: #B13100;">${userReview.bookmark.amazonRating}</span>
                                    </td>
                                    <td class="item_desc_col">
                                        <span style="color: #B13100;">${userReview.bookmark.publicationYear}</span>
                                    </td>
                                    <td class="item_desc_col">
                                        <span style="color: #B13100;">${userReview.review}</span>
                                    </td>
                                    <%--<td class="item_desc_col">
                                        <span style="color: #B13100;">${userReview.isApproved}</span>
                                    </td>--%>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:if>

                    <c:if test="${!empty(moviesList)}">
                        <table>
                            <th class="item_desc_header">Cover</th>
                            <th class="item_desc_header">Title</th>
                            <th class="item_desc_header">Director</th>
                            <th class="item_desc_header">Cast</th>
                            <th class="item_desc_header">Rating</th>
                            <th class="item_desc_header">Release Year</th>

                            <c:forEach var="movie" items="${moviesList}">
                                <tr>
                                    <td>
                                        <img src="${movie.imageUrl}" width="90" height="120">
                                    </td>
                                    <td class="item_desc_col">
                                        <span style="color: #B13100;display: block;width: 155px;overflow: visible;">${movie.title}</span>
                                    </td>
                                    <td class="item_desc_col">
                                        <span style="color: #B13100;">${movie.directors[0]}</span>
                                    </td>
                                    <td class="item_desc_col">
                                        <span style="color: #B13100;">${movie.cast[0]}</span>
                                    </td>
                                    <td class="item_desc_col">
                                        <span style="color: #B13100;">${movie.imdbRating}</span>
                                    </td>
                                    <td class="item_desc_col">
                                        <span style="color: #B13100;">${movie.releaseYear}</span>
                                    </td>
                                    <td class="item_desc_col">
                                        isApproved
                                    </td>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:if>

                    <c:if test="${!empty(weblinksList)}">
                        <table>
                            <th class="item_desc_header">Cover</th>
                            <th class="item_desc_header">Title</th>
                            <th class="item_desc_header">URL</th>
                            <th class="item_desc_header">Host</th>

                            <c:forEach var="weblink" items="${weblinksList}">
                                <tr>
                                    <td>
                                        <img src="${weblink.imageUrl}" width="90" height="120">
                                    </td>
                                    <td class="item_desc_col">
                                        <span style="color: #B13100;display: block;width: 155px;overflow: hidden;white-space: normal;">${weblink.title}</span>
                                    </td>
                                    <td class="item_desc_col">
                                        <span style="color: #B13100;display: block;width: 155px;overflow: hidden;white-space: normal;">${weblink.url}</span>
                                    </td>
                                    <td class="item_desc_col">
                                        <span style="color: #B13100;display: block;width: 155px;overflow: hidden;white-space: normal;">${weblink.host}</span>
                                    </td>
                                    <td class="item_desc_col">
                                        isApproved
                                    </td>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:if>

                </c:when>
                <c:otherwise>
                    <br><br>
                    <span style="font-size: 24px;color: #333333;margin:400px;">You haven't saved any items yet!</span>
                </c:otherwise>
            </c:choose>
        </td>
    </table>
</table>
</body>
</html>
