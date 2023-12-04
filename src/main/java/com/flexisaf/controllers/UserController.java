package com.flexisaf.controllers;

import com.flexisaf.models.user;
import com.flexisaf.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.Date;
@WebServlet("/user")
public class UserController extends HttpServlet {
    private UserService userService;
    public void init(){
        userService = new UserService();
    }

    @Operation(summary = "Create a new user", description = "Endpoint to create a new user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Schema(name = "User", description = "User details")
    protected void doPost(HttpServletRequest request , HttpServletResponse response) throws ServletException , IOException {
        String action = request.getParameter("action");
        if ("signup".equals(action)){
            createUser(request, response);
        }
    }


    private void createUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String hashedPassword = req.getParameter("hashedPassword");
        String dob = req.getParameter("DOB");
        String userType = req.getParameter("UserType");

        Date DOB;
        try {
            DOB = Date.valueOf(dob);
        } catch (IllegalArgumentException | NullPointerException e){
            resp.getWriter().println("Invalid date format ");
            return;
        }

        user.UserType userType1;
        try {
            userType1 = user.UserType.valueOf(userType);
        } catch (IllegalArgumentException | NullPointerException e){
            resp.getWriter().println("Invalid user type");
            return;
        }

        user newUser = new user(0, fullname, email, DOB, userType1, hashedPassword);
        boolean success = userService.createUser(newUser);
        if (success){
            resp.getWriter().println("User created " + newUser.toString());
        } else {
            resp.getWriter().println("Failed to create user");
        }

    }

}
