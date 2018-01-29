package com.cs442.amalviy1.freefood_at_iit.ui;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cs442.amalviy1.freefood_at_iit.R;
import com.cs442.amalviy1.freefood_at_iit.asynctask.Database_login_Register;
import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ankit on 11/24/16.
 */
public class Register extends Fragment {
    EditText USER_FIRST_NAME,USER_LAST_NAME, USER_EMAIL, USER_PASSWORD,USER_CONFIRM_PASSWORD,MESSAGE_SIGNUP;
    EditText USER_LOGIN_USERNAME,USER_LOGIN_PASSWORD;
    ImageButton REGISTER;
    ImageButton LOGIN;
    TextView NEWACCOUNT;
    String user_first_name, user_last_name,user_email,user_password,user_confirm_password,login_username,login_password;
    String usernameDB;
    private Timer timer = new Timer();
    private String str1;
    private String str2;
    String dbUsername;
    String dbPassword;
    Connection conn;
    Statement stmt;
    ArrayList<String> username= new ArrayList<>();
    ArrayList<String> password= new ArrayList<>();

    @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
View view;
        view=  inflater.inflate(R.layout.register_in_fragment, container, false);



        USER_FIRST_NAME=(EditText)view.findViewById(R.id.first_name);
        USER_LAST_NAME=(EditText)view.findViewById(R.id.last_name);
        USER_EMAIL= (EditText)view.findViewById(R.id.email);
        USER_PASSWORD=(EditText)view.findViewById(R.id.password);
        USER_CONFIRM_PASSWORD=(EditText)view.findViewById(R.id.confirm_password);
        USER_LOGIN_USERNAME=(EditText)view.findViewById(R.id.login_username);
        USER_LOGIN_PASSWORD=(EditText)view.findViewById(R.id.login_password);

        REGISTER= (ImageButton)view.findViewById(R.id.register_btn);

        str1 = "@iit.edu";
        str2 = "@hawk.iit.edu";

        REGISTER.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               user_first_name= USER_FIRST_NAME.getText().toString();
               user_last_name=USER_LAST_NAME.getText().toString();
               user_email=USER_EMAIL.getText().toString();
               user_password=USER_PASSWORD.getText().toString();
               user_confirm_password=USER_CONFIRM_PASSWORD.getText().toString();


               if (user_first_name.length() == 0 || user_last_name.length() == 0 || user_email.length() == 0 || user_password.length() == 0 || user_confirm_password.length() == 0)
               {

                   Snackbar snackbar = Snackbar.make(v, "Please enter all the fields", Snackbar.LENGTH_LONG);

                   snackbar.show();
                   return;

               }


               if (user_email.toLowerCase().contains(str1) || user_email.toLowerCase().contains(str2))
               {
                   if (!username.contains(user_email)) {
                       if (user_password.equals(user_confirm_password)) {

                           signup(user_first_name, user_last_name, user_email, user_password);
                           USER_FIRST_NAME.setText("");
                           USER_LAST_NAME.setText("");
                           USER_EMAIL.setText("");
                           USER_PASSWORD.setText("");
                           USER_CONFIRM_PASSWORD.setText("");

                       }
                       else {
                           timer.schedule(new TimerTask() {
                               @Override
                               public void run() {
                                   getActivity().runOnUiThread(new Runnable() {
                                       public void run() {
                                           MESSAGE_SIGNUP.setText("Password not matched!!!");
                                           Snackbar snackbar2 = Snackbar.make(v, "Password error", Snackbar.LENGTH_LONG);

                                           snackbar2.show();
                                           return;
                                       }
                                   });
                               }
                           }, 2000);

                       }

                   }
                else
                   {
                       Snackbar snackbar = Snackbar.make(v, " Email already registered", Snackbar.LENGTH_LONG);

                       snackbar.show();
                       return;
                   }

               }
               else {

                   Snackbar snackbar = Snackbar.make(v, "invalid email type ", Snackbar.LENGTH_LONG);

                   snackbar.show();
                   return;

               }
           }
       });

        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        return view;
    }


    public void signup(String user_first_name,String user_last_name,String user_email,String user_password) {

        Database_login_Register databaseLoginRegister = new Database_login_Register(getActivity());
        databaseLoginRegister.execute(user_first_name,user_last_name,user_email,user_password);
        Intent intent = new Intent(getActivity(),RetriveData.class);
        this.startActivity(intent);

    }

}
