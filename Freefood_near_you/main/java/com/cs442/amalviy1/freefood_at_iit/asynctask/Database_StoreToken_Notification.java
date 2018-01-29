package com.cs442.amalviy1.freefood_at_iit.asynctask;

import android.os.AsyncTask;

import com.mysql.jdbc.Statement;


import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Ankit on 11/13/16.
 */
public class Database_StoreToken_Notification extends AsyncTask<String,Void,String> {

    Connection conn;
    Statement stmt;
    @Override
    protected String doInBackground(String... params) {
        if (params.length!=0) {
            try {
                storeToken(params);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        else
        {
            try {
                getallToken();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return null;

    }

    private void storeToken(String[] params) throws SQLException, ClassNotFoundException {

        dbConnection();


        String INSERT_PICTURE = "insert Ignore into token(token_id) values (?)";
        java.sql.PreparedStatement stmt = conn.prepareStatement(INSERT_PICTURE);
//        conn.setAutoCommit(false);

//        ps= conn.prepareStatement( INSERT_PICTURE);
        // stmt.setInt( 1,Integer.parseInt(dateFormat.format(date)) );

        stmt.setString( 1,params[0] );
        stmt.setString( 2,null );

        stmt.executeUpdate();
        conn.commit();


        conn.close();






        conn.close();
    }

    public  ArrayList<String>  getallToken() throws SQLException, ClassNotFoundException {

ArrayList<String> tokenArray= new ArrayList<>();

        dbConnection();
        ResultSet rs = stmt.executeQuery("SELECT * from token");

        while (rs.next()) {

            String token = rs.getString("token_id");


            tokenArray.add(token);
        }

        return tokenArray;
    }

    private  void  dbConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(
                "jdbc:mysql://ankit1.c9vfq8had7xm.us-west-2.rds.amazonaws.com:3306/android?user=ankit1&password=ankitankit");
        // stmt = (Statement) conn.createStatement();
        conn.setAutoCommit(false);
    }
}
