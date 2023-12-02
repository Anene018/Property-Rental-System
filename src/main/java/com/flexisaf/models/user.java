package com.flexisaf.models;
import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class user {
    public user(String fullname, String email, Date dob, UserType userType1, String hashedPassword) {
        setFullname(fullname);
        setEmail(email);
        DOB = dob;
        this.userType = userType1;
        setHashedPassword(hashedPassword);
    }

    public enum UserType{
    admin,
    owner,
    customer
    }

    private int userId;
    private String fullname;
    private String email;
    private Date DOB;
    private UserType userType;
    private String hashedPassword;

    public int getUserId() {
        return userId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        if (fullname != null && !fullname.isEmpty()) {
            this.fullname = fullname;
        } else {
            throw new IllegalArgumentException("Please provide name");
        }
    }

    public String getEmail() {
        return email;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDOB(Date dOB) {
        DOB = dOB;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        if (hashedPassword != null && hashedPassword.length() >= 8) {
            this.hashedPassword = hashedPassword;
        } else {
            throw new IllegalArgumentException("Please provide password");
        }
    }


    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean validateEmail(String email) {
        if (email == null || email.isEmpty()){
            return false;
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public void setEmail(String email) {
        if (validateEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Please provide email");
        }
    }


    @Override
    public String toString() {
        return "user [userId=" + userId + ", fullname=" + fullname + ", email=" + email + ", DOB=" + DOB + ", userType="
                + userType +  "]";
    }

    public user(int userId, String fullname, String email, Date dOB, UserType userType, String hashedPassword) {
        this.userId = userId;
        setFullname(fullname);
        setEmail(email);
        DOB = dOB;
        this.userType = userType;
        setHashedPassword(hashedPassword);
    }

    public user(int userId, String fullname,  UserType userType) {
        this.userId = userId;
        setFullname(fullname);
        this.userType = userType;
    }

    public user() {
    }

}
