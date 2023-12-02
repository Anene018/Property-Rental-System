package com.flexisaf.service;

import com.flexisaf.models.user;
import com.flexisaf.doa.userDoa;

import java.util.List;
public class UserService {
    private final userDoa userDoa;
    public UserService (){
        this.userDoa = new userDoa();
    }
    public boolean createUser (user newUser){
        return userDoa.createUser(newUser);
    }
    public boolean updateUser (user User){
        return userDoa.updateUser(User);
    }
    public boolean deleteUser (int userId){
        return userDoa.deleteUser(userId);
    }
    public user getUserById ( int userId){
        return userDoa.getUserById(userId);
    }
    public String getAllUsers (){
        List<user> users = userDoa.getAllUsers();
        return userDoa.mapUserListToJson(users);
    }
    public user getUserByEmail(String email){
        return userDoa.getUserByEmail(email);
    }
}
