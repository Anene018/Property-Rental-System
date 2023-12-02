package com.flexisaf.models;

public class house {

    private int houseId;
    private int userId;
    private int rent;
    private String locality;
    private int numberOfRooms;

    public house() {

    }

    public house(int rent, int numberOfRooms, String locality) {
        setRent(rent);
        setNumberOfRooms(numberOfRooms);
        setLocality(locality);
    }

    @Override
    public String toString() {
        return "house{" +
                "houseId=" + houseId +
                ", userId=" + userId +
                ", rent=" + rent +
                ", locality='" + locality + '\'' +
                ", numberOfRooms=" + numberOfRooms +
                '}';
    }

    public void setLocality(String locality) {
        if (locality != null && !locality.isEmpty()) {
            this.locality = locality;
        } else {
            throw new IllegalArgumentException("Locality cannot be empty");
        }
    }

    public String getLocality() {
        return locality;
    }
    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public house(int houseId, int userId, int rent, String locality, int numberOfRooms) {
        this.houseId = houseId;
        this.userId = userId;
        setRent(rent);
        setLocality(locality);
        setNumberOfRooms(numberOfRooms);
    }


    public int getHouseId() {
        return houseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        if (rent >= 0) {
            this.rent = rent;
        } else {
            throw new IllegalArgumentException("Rent cannot be less than zero");
        }
    }
    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        if (numberOfRooms >= 0) {
            this.numberOfRooms = numberOfRooms;
        } else {
            throw new IllegalArgumentException("Number of rooms cannot be less than zero");
        }
    }

}
