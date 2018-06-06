package com.mv.schelokov.car_rent.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class Login extends HttpServlet {
@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String login = request.getParameter("email");
        String pass = request.getParameter("pass");
        if ("Maxim".equals(login) && "1337".equals(pass)) {
            request.setAttribute("name", login);
            request.getRequestDispatcher("view/welcome.jsp").include(request, response);
        } else {
            request.setAttribute("errorLogin", 1);
            request.getRequestDispatcher("login.jsp").include(request, response);
        }        
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        req.setAttribute("errorLogin", 0);
        req.getRequestDispatcher("login.jsp").include(req, resp);
    }
    
    
}
