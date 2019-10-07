package com.dey.sayantan.thrillio.controllers;

import com.dey.sayantan.thrillio.Util.DBUtility;
import com.dey.sayantan.thrillio.managers.UserManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/new&user", "/user&register"})
public class UserController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Servlet path: " + request.getServletPath());

        if (request.getServletPath().contains("user&register")) {
            //redirect to registration page
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else if (request.getServletPath().contains("/new&user")) {
            //fetch and store user information in database
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            int gender = Integer.parseInt(request.getParameter("gender"));
            int userType = Integer.parseInt(request.getParameter("userType"));

            if (UserManager.getInstance().getUser(email) != null) {              //check for duplicate email
                request.setAttribute("duplicate_email", true);
                request.getSession().setAttribute("firstName", firstName);
                request.getSession().setAttribute("lastName", lastName);
                request.getSession().setAttribute("email", email);
                request.getRequestDispatcher(request.getContextPath() + "/user&register").forward(request, response);
            } else {        //do new user registration
                UserManager.getInstance().registerNewUser(firstName, lastName, email, password, gender, userType);
                //end session containing saved user registration info
                request.getSession().invalidate();
//                response.sendRedirect("/index.jsp");
                //create new session for new user to browse bookmarks
                long userId = UserManager.getInstance().getUser(email).getId();
                request.getSession().setAttribute("userId", userId);
                request.setAttribute("is_valid", true);
                request.setAttribute("reg_success",true);
                request.getRequestDispatcher("browse").forward(request, response);
            }
        }
    }
}
