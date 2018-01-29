package com.cs442.amalviy1.freefood_at_iit.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cs442.amalviy1.freefood_at_iit.Pojo.FoodPOJO;
import com.mysql.jdbc.Statement;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ankit on 11/5/16.
 */
public class Database_New_Post extends AsyncTask<Object,Void,String> {
    Context context;
    Connection conn;
    Statement stmt;

    DateFormat dateFormat = new SimpleDateFormat("mss");
    Date date = new Date();
    @Override
    protected String doInBackground(Object... params) {

        FoodPOJO pojo= (FoodPOJO) params[0];

        try {
            enterDetails(pojo);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void enterLikes(Object param) throws SQLException, ClassNotFoundException {


    }

    public Database_New_Post(Context ctx)
    {
        context=ctx;
    }
    private  void  dbConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(
                "jdbc:mysql://ankit1.c9vfq8had7xm.us-west-2.rds.amazonaws.com:3306/android?user=ankit1&password=ankitankit");
       // stmt = (Statement) conn.createStatement();
        conn.setAutoCommit(false);
    }


    private void enterDetails(FoodPOJO pojo) throws SQLException, ClassNotFoundException, IOException {
//        FileInputStream fis = null;
//        java.sql.PreparedStatement ps = null;
       dbConnection();
        Log.d("Date "+pojo.getDate(),"");
        String INSERT_PICTURE = "insert into freefood_post(image,item_name, food_description,location,likes,time) values ( ?,?,?,?,?,?)";
        java.sql.PreparedStatement stmt = conn.prepareStatement(INSERT_PICTURE);
//        conn.setAutoCommit(false);
        FileInputStream fis = new FileInputStream(pojo.getFile());
//        ps= conn.prepareStatement( INSERT_PICTURE);
       // stmt.setInt( 1,Integer.parseInt(dateFormat.format(date)) );
        stmt.setBinaryStream( 1,fis,(int) pojo.getFile().length() );
        stmt.setString( 2,pojo.getFoodTitle() );
        stmt.setString( 3,pojo.getFoodDescription() );
        stmt.setString( 4,pojo.getFoodLocation() );
        stmt.setInt( 5,pojo.getLikes() );
        stmt.setString(6,pojo.getDate());
        stmt.executeUpdate();
        conn.commit();

        fis.close();
        conn.close();
        // stmt.execute("INSERT INTO food (image,item_name, food_description,location) VALUES ('"+pojo.getFile()+"','"+pojo.getFoodTitle()+"','"+pojo.getFoodDescription()+"','"+pojo.getFoodLocation()+"');");
       // stmt.execute("INSERT INTO food (item_name, food_description,location) VALUES ('"+pojo.getFoodTitle()+"','"+pojo.getFoodDescription()+"','"+pojo.getFoodLocation()+"');");

    }
}