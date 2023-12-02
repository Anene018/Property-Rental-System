package com.flexisaf.service;

import com.flexisaf.doa.houseDoa;
import com.flexisaf.models.house;

import java.util.List;

public class HouseService {
    private final houseDoa houseDoa;
    public HouseService(){
        this.houseDoa = new houseDoa();
    }
    public boolean createHouse(house newHouse){
        return houseDoa.createHouse(newHouse);
    }
    public boolean updateHouse(house House){
        return houseDoa.updateHouse(House);
    }
    public boolean deleteHouse(int houseId){
        return houseDoa.deleteHouse(houseId);
    }
    public house getHouseByHouseId(int houseId){
        return houseDoa.getHouseByHouseId(houseId);
    }
    public String getHouseByUserId(int userId){
        List<house> houses = houseDoa.getHouseByUserId(userId);
        return houseDoa.mapHouseToJson(houses);
    }
    public String getAllHouse(){
        List<house> houses = houseDoa.getAllHouse();
        return houseDoa.mapHouseToJson(houses);
    }
    public String getHouseByNumberOfRoom(int numberOfRooms){
        List<house> houses = houseDoa.getHouseByNumberOfRoom(numberOfRooms);
        return  houseDoa.mapHouseToJson(houses);
    }
    public  String getHouseByRent (int rent){
        List<house> houses = houseDoa.getHouseByRent(rent);
        return houseDoa.mapHouseToJson(houses);
    }
    public String getHouseByLocality(String locality){
        List<house> houses = houseDoa.getHouseByLocality(locality);
        return houseDoa.mapHouseToJson(houses);
    }
}
