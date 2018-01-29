package com.cs442.amalviy1.freefood_at_iit;

import android.os.AsyncTask;
import android.util.Log;

import com.cs442.amalviy1.freefood_at_iit.Pojo.Current_User_Pojo;
import com.cs442.amalviy1.freefood_at_iit.Pojo.Token_Pojo;
import com.cs442.amalviy1.freefood_at_iit.asynctask.Database_StoreToken_Notification;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Ankit on 11/13/16.
 */
public class BackWork extends AsyncTask<String,String,String> {
ArrayList<String> arrayList = new ArrayList<>();



    @Override
    protected String doInBackground(String... params) {
        Token_Pojo token_pojo = new Token_Pojo();
       arrayList=  token_pojo.getTokenList();
        Log.d("Print Arraylist "+arrayList,"");
        for (int i = 0; i <= arrayList.size(); i++) {
            try {

                URL url = new URL("https://fcm.googleapis.com/fcm/send");
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Authorization", "key= AIzaSyD50TUmrAUpOpg_CJYK_Peyr-zWBvAARrM");



                JSONObject root = new JSONObject();
                root.put("to", arrayList.get(i));
                JSONObject notification = new JSONObject();
                notification.put("title", "Notification message is sending");
                notification.put("body", "Description of notification");
                root.put("notification", notification);
                JSONObject data = new JSONObject();
                data.put("title", "Title in data");
                data.put("description", "Discription in the message");
                root.put("data", data);



                DataOutputStream os = new DataOutputStream(conn.getOutputStream());

                //os.write(input.getBytes());
                Log.i("BackWork", root.toString());
                os.writeBytes(root.toString());
                os.flush();
                os.close();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                /*throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());*/

                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            (conn.getInputStream())));

                    String output;
                    System.out.println("Output from Server .... \n");
                    while ((output = br.readLine()) != null) {
                        System.out.println(output);
                    }
                } else {
                    Log.e("BackWork", "ERROR => " + conn.getResponseMessage());
                }


                conn.disconnect();

            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return null;
    }
}
