package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String Username;
    private int Student_id;
    private String ID;
    private String Password;
    private String Email;
    private int is_professor;
    private int Time;
    private int CsCheck;

    public static List<User> userList;

    public static List<User> getUserList() {
        return userList;
    }

    public void Username(String username) {
        this.Username = username;
    }

    public void Student_id(int sid) {
        this.Student_id = sid;
    }

    public void ID(String id) {
        this.ID = id;
    }

    public void Password(String pwd) {
        this.Password = pwd;
    }

    public void Email(String email) {
        this.Email = email;
    }

    public void is_professor(int isp) {
        this.is_professor = isp;
    }

    public void Time(int time) {
        this.Time = time;
    }

    public void CsCheck(int csc) {
        this.CsCheck = csc;
    }

    // get 메소드
    public static String getUsername() {
        if (!userList.isEmpty()) {
            return userList.get(0).Username;
        }
        return null;
    }

    public static int getStudentId() {
        if (!userList.isEmpty()) {
            return userList.get(0).Student_id;
        }
        return -1;
    }

    public static String getID() {
        if (!userList.isEmpty()) {
            return userList.get(0).ID;
        }
        return null;
    }

    public static String getPassword() {
        if (!userList.isEmpty()) {
            return userList.get(0).Password;
        }
        return null;
    }

    public static String getEmail() {
        if (!userList.isEmpty()) {
            return userList.get(0).Email;
        }
        return null;
    }

    public static int getIsProfessor() {
        if (!userList.isEmpty()) {
            return userList.get(0).is_professor;
        }
        return -1;
    }

    public static int getTime() {
        if (!userList.isEmpty()) {
            return userList.get(0).Time;
        }
        return -1;
    }

    public static int getCsCheck() {
        if (!userList.isEmpty()) {
            return userList.get(0).CsCheck;
        }
        return -1;
    }

   /* public User(String Username,int Student_id,String ID,String Password,String Email,int is_professor,int Time,int CsCheck){
        this.Username =Username;
        this.Student_id=Student_id;
        this.ID=ID;
        this.Password=Password;
        this.Email=Email;
        this.is_professor=is_professor;
        this.Time=Time;
        this.CsCheck=CsCheck;
    }*/
}
