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
@WebServlet("/house/secured/")
public class HouseProtected extends HttpServlet{
    private HouseService houseService;

    @Override
    public void init()  {
        houseService = new HouseService();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if("delete".equals(action)){
            deleteHouse(req,resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("update".equals(action)) {
            updateHouse(req,resp);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("create".equals(action)){
            createHouse(req,resp);
        }
    }
    private void createHouse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        int rent = Integer.parseInt(req.getParameter("rent"));
        int numberOfRooms = Integer.parseInt(req.getParameter("numberOfRooms"));
        String locality = req.getParameter("locality");

        house newHouse = new house(0 , userId , rent, locality, numberOfRooms);
        boolean created = houseService.createHouse(newHouse);
        if (created){
            resp.getWriter().println("House created" + newHouse.toString());
        }else {
            resp.getWriter().println("Failed to create house");
        }
    }

    private void updateHouse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int houseId = Integer.parseInt(req.getParameter("houseId"));
        house existingHouse = houseService.getHouseByHouseId(houseId);

        if (existingHouse == null) {
            resp.getWriter().println("No house with houseId exists " + houseId);
            return;
        }

        int rent = Integer.parseInt(req.getParameter("rent"));
        int numberOfRooms = Integer.parseInt(req.getParameter("numberOfRooms"));
        String locality = req.getParameter("locality");

        house updatedHouse = new house(houseId, existingHouse.getUserId(), rent, locality, numberOfRooms);
        boolean updated = houseService.updateHouse(updatedHouse);

        if (updated) {
            resp.getWriter().println("House has been updated: " + updatedHouse.toString());
        } else {
            resp.getWriter().println("Failed to update house");
        }
    }
    private void deleteHouse (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int houseId = Integer.parseInt(req.getParameter("houseId"));
        boolean deletedHouse = houseService.deleteHouse(houseId);

        if (deletedHouse){
            resp.getWriter().println("House has been deleted");
        } else {
            resp.getWriter().println("Failed to delete house");
        }
    }
}
