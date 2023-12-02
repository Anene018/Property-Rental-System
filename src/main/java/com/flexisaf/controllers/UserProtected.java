package com.flexisaf.controllers;
import com.flexisaf.models.house;
import com.flexisaf.models.user;
import com.flexisaf.service.HouseService;
import com.flexisaf.service.UserService;


import java.sql.Date;
import java.io.IOException;
import javax.servlet.ServletConfig;
import  javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/user/secured/")
public class UserProtected extends HttpServlet {
    private UserService userService;
    public void init(){
        userService = new UserService();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("getByUserId".equals(action)){
            getUserByUserId(req,resp);
        } else if ("getAllUsers".equals(action)) {
            getAllUsers(req,resp);
        } else if ("getUserByEmail".equals(action)) {
            getUserByEmail(req,resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("update".equals(action)){
            updateUser(req,resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if("delete".equals(action)){
            deleteUser(req,resp);
        }
    }
    private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));

        // Fetch the existing user
        user existingUser = userService.getUserById(userId);
        if (existingUser == null) {
            resp.getWriter().println("User not found for userId: " + userId);
            return;
        }

        // Extract updated details from request
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String hashedPassword = req.getParameter("hashedPassword");
        String dob = req.getParameter("DOB");
        String userType = req.getParameter("userType");
        System.out.println("DOB parameter value: " + dob);

        // Check if DOB is present and not null or empty
        if (dob == null || dob.isEmpty()) {
            resp.getWriter().println("Invalid or missing DOB parameter");
            return;
        }

        // Parse date and user type
        Date DOB = Date.valueOf(dob);

        // Check if userType is present and not null or empty
        if (userType == null || userType.isEmpty()) {
            resp.getWriter().println("Invalid or missing userType parameter");
            return;
        }

        user.UserType userType1 = user.UserType.valueOf(userType);

        // Create updated user object
        user updatedUser = new user(userId, fullname, email, DOB, userType1, hashedPassword);

        // Update the user
        boolean success = userService.updateUser(updatedUser);

        if (success) {
            resp.getWriter().println("User has been updated: " + updatedUser.toString());
        } else {
            resp.getWriter().println("Failed to update User with userId: " + userId);
        }
    }

    private void getUserByUserId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        user fetchedUser = userService.getUserById(userId);
        if (fetchedUser != null){
            resp.getWriter().println("User found " + fetchedUser.toString());
        } else {
            resp.getWriter().println("User not found");
        }
    }
    private void getUserByEmail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        user fetchedUser = userService.getUserByEmail(email);
        if (fetchedUser != null){
            resp.getWriter().println("User found " + fetchedUser.toString());
        } else {
            resp.getWriter().println("User not found");
        }
    }
    private void getAllUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String Users = userService.getAllUsers();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(Users);

    }
    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        boolean success = userService.deleteUser(userId);
        if (success){
            resp.getWriter().println("User has been deleted");
        } else {
            resp.getWriter().println("Failed to delete user");
        }
    }
}
