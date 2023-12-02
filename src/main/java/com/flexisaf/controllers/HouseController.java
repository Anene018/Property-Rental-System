package com.flexisaf.controllers;

import com.flexisaf.models.house;
import com.flexisaf.service.HouseService;


import java.sql.Date;
import java.io.IOException;
import javax.servlet.ServletConfig;
import  javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/house/")
public class HouseController extends HttpServlet {
    private HouseService houseService;

    @Override
    public void init()  {
        houseService = new HouseService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("getHouseByHouseId".equals(action)){
            getHouseByHouseId(req,resp);
        } else if ("getHouseByUserId".equals(action)) {
            getHouseByUserId(req,resp);
        } else if ("getAllHouse".equals(action)) {
            getAllHouse(req,resp);
        } else if ("getHouseByRent".equals(action)){
            getHouseByRent(req,resp);
        } else if ("getHouseByNumberOfRoom".equals(action)){
            getHouseByNumberOfRoom(req,resp);
        } else if ("getHouseByLocality".equals(action)){
            getHouseByLocality(req,resp);
        }
    }

    private void getHouseByUserId (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        String Houses = houseService.getHouseByUserId(userId);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (Houses != null){
            resp.getWriter().println("This are the houses that are owned by userId: " );
            resp.getWriter().write(Houses);
        }else {
            resp.getWriter().println("User does not have house");
        }
    }
    private void getHouseByLocality(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
        String locality = req.getParameter("locality");
        String Houses = houseService.getHouseByLocality(locality);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (Houses != null){
            resp.getWriter().println("This are the houses in the locality: " );
            resp.getWriter().write(Houses);
        }else {
            resp.getWriter().println("House does not exist");
        }
    }

    private void getHouseByNumberOfRoom(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int numberOfRooms = Integer.parseInt(req.getParameter("numberOfRooms"));
        String Houses = houseService.getHouseByNumberOfRoom(numberOfRooms);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (Houses != null){
            resp.getWriter().println("This are the houses with number of rooms: " );
            resp.getWriter().write(Houses);
        }else {
            resp.getWriter().println("House does not exist");
        }
    }

    private void getHouseByRent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
        int rent = Integer.parseInt(req.getParameter("rent"));
        String Houses = houseService.getHouseByRent(rent);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (Houses != null){
            resp.getWriter().println("This are the houses with rent: " );
            resp.getWriter().write(Houses);
        }else {
            resp.getWriter().println("House does not exist");
        }
    }
    private void getHouseByHouseId (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int houseId = Integer.parseInt(req.getParameter("houseId"));
        house fetchedHouse = houseService.getHouseByHouseId(houseId);
        if (fetchedHouse != null){
            resp.getWriter().println("This is the house with houseId: " + fetchedHouse.toString());
        } else {
            resp.getWriter().println("HouseId does not have house");
        }
    }
    private void getAllHouse (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String Houses = houseService.getAllHouse();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(Houses);
    }

}
