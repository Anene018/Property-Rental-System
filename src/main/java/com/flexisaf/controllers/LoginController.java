package com.flexisaf.controllers;
import com.flexisaf.models.user;
import com.flexisaf.service.UserService;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginController extends HttpServlet{
    private UserService userService;

    public void init() {
        userService = new UserService();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (isValidUser(email,password)){
            user User = userService.getUserByEmail(email);
            HttpSession session = req.getSession();
            session.setAttribute("user",User);

            resp.getWriter().println("Login Successful");

        } else {
            resp.getWriter().println("Incorrect credentials ");
            System.out.println(email);
            System.out.println(password);
        }
    }
    private boolean isValidUser(String email , String password){
        user User = userService.getUserByEmail(email);
        return User != null && User.getHashedPassword().equals(password);
    }
}