package com.flexisaf.controllers;
import com.flexisaf.models.user;
import com.flexisaf.service.UserService;


import java.sql.Date;
import java.io.IOException;
import  javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;
@WebServlet("/user/secured/")
public class UserProtected extends HttpServlet {
    private UserService userService;
    public void init(){
        userService = new UserService();
    }

    @Operation(summary = "Get user by ID", description = "Get user details by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @Schema(implementation = user.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @io.swagger.v3.oas.annotations.media.Content) })
    @Override
    protected void doGet(@Parameter(description = "Action parameter", required = true) HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("getByUserId".equals(action)){
            getUserByUserId(req,resp);
        } else if ("getAllUsers".equals(action)) {
            getAllUsers(req,resp);
        } else if ("getUserByEmail".equals(action)) {
            getUserByEmail(req,resp);
        }
    }

    @Operation(summary = "Update user", description = "Update user details by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "text/plain", schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @io.swagger.v3.oas.annotations.media.Content),
            @ApiResponse(responseCode = "500", description = "Failed to update user", content = @io.swagger.v3.oas.annotations.media.Content) })

    @Override
    protected void doPut( @Parameter(description = "Action parameter", required = true) HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("update".equals(action)){
            updateUser(req,resp);
        }
    }

    @Operation(summary = "Delete user", description = "Delete user by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "text/plain", schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @io.swagger.v3.oas.annotations.media.Content),
            @ApiResponse(responseCode = "500", description = "Failed to delete user", content = @io.swagger.v3.oas.annotations.media.Content)
    })
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
    @Operation(summary = "Get user by user ID", description = "Get user details by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @Schema(implementation = user.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @io.swagger.v3.oas.annotations.media.Content) })
    private void getUserByUserId(@Parameter(description = "Email parameter", required = true) HttpServletRequest req,  HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        user fetchedUser = userService.getUserById(userId);
        if (fetchedUser != null){
            resp.getWriter().println("User found " + fetchedUser.toString());
        } else {
            resp.getWriter().println("User not found");
        }
    }

    @Operation(summary = "Get user by email", description = "Get user details by email address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @Schema(implementation = user.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @io.swagger.v3.oas.annotations.media.Content) })
    private void getUserByEmail(@Parameter(description = "Email parameter", required = true) HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
