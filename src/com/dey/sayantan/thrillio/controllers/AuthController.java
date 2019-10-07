package com.dey.sayantan.thrillio.controllers;

import com.dey.sayantan.thrillio.managers.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/auth","/auth/logout","/guestLogin"})
public class AuthController extends HttpServlet {
    public static final long guestId = 0;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Servlet path: "+request.getServletPath());

        if(request.getServletPath().contains("guestLogin")) {
            //redirect guest to browse page
            request.getSession().setAttribute("userId", guestId);
            request.getRequestDispatcher("browse").forward(request, response);
        }else if(!request.getServletPath().contains("logout")){
            //log in user
            String email = request.getParameter("email");   //name of the field in form
            String password = request.getParameter("password");

            Long userId = UserManager.getInstance().authenticateUser(email, password);
            if(userId != -1){
                //forward control to browse
                HttpSession session = request.getSession();
                session.setAttribute("userId", userId);

                request.setAttribute("is_valid", true);
                request.getRequestDispatcher("browse").forward(request, response);
            }else{
                //send the user to login page
                request.setAttribute("is_valid", false);
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
        }else{
            //log user out and redirect to login page
            request.getSession().invalidate();  //invalidate session once log out
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}
