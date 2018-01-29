package com.cs442.amalviy1.freefood_at_iit.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.cs442.amalviy1.freefood_at_iit.Pojo.Current_User_Pojo;
import com.cs442.amalviy1.freefood_at_iit.Pojo.FoodPOJO;
import com.mysql.jdbc.Statement;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ankit on 11/4/16.
 */
public class Database_login_Register extends AsyncTask<String,Void,Object> {
    Context context;
    Connection conn;
    Statement stmt;
    FoodPOJO pojo;
    public static ArrayList<HashMap<String, String>> userdetailsHash = new ArrayList<>();


    @Override
    protected Object doInBackground(String... params) {

        try {
            getallUserdetails();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(params.length==1)
        {
            try {


                getCurrentUser(params[0]);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (params.length == 2) {
            String user_username = params[0];
            String user_password = params[1];
            try {
                selectQuery(user_username, user_password);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } if (params.length == 4) {
            String user_firstname = params[0];
            String user_lastname = params[1];
            String user_email = params[2];
            String user_password = params[3];
            try {
                insertQuery(user_firstname, user_lastname, user_email, user_password);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }


        return null;
    }

    private void getCurrentUser(String param) throws SQLException, ClassNotFoundException {

        dbConnection();
        ResultSet rs = stmt.executeQuery("SELECT * from register where username="+"'"+param+"'");

        while (rs.next()) {

            new Current_User_Pojo(rs.getString("username"),rs.getString("first_name"));


        }

    }

    private void getallUserdetails() throws SQLException, ClassNotFoundException {
        dbConnection();
        ResultSet rs = stmt.executeQuery("SELECT * from register");
        while (rs.next()) {
            HashMap<String, String> hashmap = new HashMap<>();
            String dbUsername = rs.getString("username");
            String dbPassowrd = rs.getString("password");
            String dbName = rs.getString("first_name");
            hashmap.put("username", dbUsername);
            hashmap.put("password", dbPassowrd);
            hashmap.put("firstName", dbName);

            userdetailsHash.add(hashmap);

        }
    }


    private void dbConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(
                "jdbc:mysql://ankit1.c9vfq8had7xm.us-west-2.rds.amazonaws.com:3306/android?user=ankit1&password=ankitankit");
        stmt = (Statement) conn.createStatement();

    }

    private void insertQuery(String user_firstname, String user_lastname, String user_email, String user_password) throws SQLException, ClassNotFoundException {
        dbConnection();

        stmt.execute("INSERT INTO register (first_name, last_name,username,password ) VALUES ('" + user_firstname + "','" + user_lastname + "','" + user_email + "','" + user_password + "');");

    }

    private void selectQuery(String user_username, String user_password) throws SQLException, ClassNotFoundException {
        dbConnection();
        ResultSet rs = stmt.executeQuery("SELECT password from register where username='" + user_username + "';");
        while (rs.next()) {
            String dbPassowrd = rs.getString("password");
            if (dbPassowrd.equals(user_password)) {
                Log.d("Successfully Loged in", "Page");
            } else {
                Log.d("Wrong username/password", "Page");
            }
        }
    }

    public Database_login_Register(Context ctx) {
        context = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
