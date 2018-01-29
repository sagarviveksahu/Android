package com.cs442.amalviy1.freefood_at_iit.asynctask;

import android.os.AsyncTask;

import com.mysql.jdbc.Statement;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Ankit on 11/13/16.
 */
public class Database_store_entry extends AsyncTask<Integer,Void,Object> {

    Connection conn;
    Statement stmt;
    @Override
    protected Object doInBackground(Integer... params) {
        if(params.length==2)
        {
            try {
                enterLikes(params[0],params[1]);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void enterLikes(Integer param, Integer like ) throws SQLException, ClassNotFoundException {

        dbConnection();


        java.sql.PreparedStatement ps = conn.prepareStatement("UPDATE freefood_post SET likes=" + like + "WHERE id="+ param );
        ps.execute();






        conn.close();
    }

    private  void  dbConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(
                "jdbc:mysql://ankit1.c9vfq8had7xm.us-west-2.rds.amazonaws.com:3306/android?user=ankit1&password=ankitankit");
        // stmt = (Statement) conn.createStatement();
        conn.setAutoCommit(false);
    }


}
