package com.cs442.amalviy1.freefood_at_iit.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.cs442.amalviy1.freefood_at_iit.Pojo.DisplayPojo;
import com.cs442.amalviy1.freefood_at_iit.Pojo.ListPojo;
import com.cs442.amalviy1.freefood_at_iit.Pojo.Token_Pojo;
import com.mysql.jdbc.Statement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Ankit on 11/8/16.
 */
public class RetriveData extends Activity {

    private Handler handler;
    private ProgressDialog dialog;
    Context context;
    Connection conn;
    Statement stmt;
    File file;
    DateFormat dateFormat;
    Date date;
    String photoName;
    static java.sql.PreparedStatement ps = null;
    static java.sql.PreparedStatement token_ps = null;
    int i=1;
    ArrayList<DisplayPojo> listofitems = new ArrayList<DisplayPojo>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait!!");
        dialog.setCancelable(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();


        new Thread() {
            public void run() {
                // Do operation here.


                try {
                    dbConnection();

                Log.d( "loop of getallid ","  ds" );
                byte b[];
                Blob blob;


                    ps = conn.prepareStatement("select * from freefood_post order by id DESC");

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    createFolder();
                    blob=rs.getBlob("image");
                    InputStream in = blob.getBinaryStream();
                    OutputStream out = new FileOutputStream(file);
                    byte[] buff = new byte[4096];  // how much of the blob to read/write at a time
                    int len = 0;

                    while ((len = in.read(buff)) != -1) {
                        out.write(buff, 0, len);
                    }

                    DisplayPojo displayPojo= new DisplayPojo( rs.getInt( "id" ),rs.getString( "item_name" ),rs.getString( "food_description" ),file,rs.getString( "location" ),rs.getInt( "likes" ),rs.getString("time") );


                    token_ps= conn.prepareStatement("select * from token");


                    ArrayList<String> tokenArray= new ArrayList<String>();
                    ResultSet resultSets= token_ps.executeQuery();
                    while (resultSets.next()) {

                        String token = resultSets.getString("token_id");
                        String blank = resultSets.getString("blank");

                        tokenArray.add(token);
                    }
                    Token_Pojo token_pojo = new Token_Pojo();
                    token_pojo.setTokenList(tokenArray);

                    listofitems.add( displayPojo );
                    ListPojo.setArrayItem( listofitems );
             //       Log.d( "This is list "+ListPojo.getArrayItem().size(),"++++" );

                }
//                    Log.d( "This is Final size "+ListPojo.getArrayItem().size(),"=========================" );
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                //                and then mark handler to notify to main thread to  remove  progressbar
                handler.sendEmptyMessage(0);

            //    Or if you want to access UI elements here then

                runOnUiThread(new Runnable() {

                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), AttractionListActivity.class);

                        startActivity(intent);

                    }
                });

            }
        }.start();

        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                dialog.dismiss();
            };
        };


    }

    public  void createFolder()
    {

        dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        date = new Date();
        photoName= "/cam_image"+dateFormat.format(date)+""+i+++" "+".jpg";
        Log.d( " create folder ","=" );
        File folder = new File( Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_DCIM)+"/cache5/");
        Log.d( " try "+photoName,"  lost" );
        if (!folder.exists())
        {
            folder.mkdir();
        }
        file = new File(folder,photoName);

    }
    private  void  dbConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(
                "jdbc:mysql://ankit1.c9vfq8had7xm.us-west-2.rds.amazonaws.com:3306/android?user=ankit1&password=ankitankit");
        // stmt = (Statement) conn.createStatement();
        conn.setAutoCommit(false);

    }
}
