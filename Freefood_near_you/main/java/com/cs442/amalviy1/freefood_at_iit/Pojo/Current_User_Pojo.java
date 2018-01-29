package com.cs442.amalviy1.freefood_at_iit.Pojo;

/**
 * Created by Ankit on 11/13/16.
 */
public class Current_User_Pojo {

    static String name;
    static String username;

    public  Current_User_Pojo(String username, String name)
    {
        this.name=name;
        this.username=username;

    }
    public Current_User_Pojo()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
