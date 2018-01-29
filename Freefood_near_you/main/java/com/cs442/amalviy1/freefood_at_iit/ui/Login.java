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
import com.mysql.jdbc.log.Log;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Timer;

/**
 * Created by Ankit on 11/23/16.
 */
public class Login extends Fragment {

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

    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
            View view;
            view= inflater.inflate(R.layout.register_login_page,container,false);



        LOGIN = (ImageButton) view.findViewById(R.id.login_btn);
        USER_LOGIN_USERNAME=(EditText)view.findViewById(R.id.login_username);
        USER_LOGIN_PASSWORD=(EditText)view.findViewById(R.id.login_password);
        str1 = "@iit.edu";
        str2 = "@hawk.iit.edu";

        LOGIN.setOnClickListener(new View.OnClickListener() {
          @Override
             public void onClick(View v) {
                    login_username=USER_LOGIN_USERNAME.getText().toString();
                    login_password=USER_LOGIN_PASSWORD.getText().toString();

                     if (login_username.length() == 0 || login_password.length() == 0)
                     {
                                Snackbar snackbar1 = Snackbar.make(v, "Please enter all the feilds", Snackbar.LENGTH_LONG);

                                snackbar1.show();
                                return;
                            } else if (login_username.toLowerCase().contains(str1) || login_username.toLowerCase().contains(str2)){

                         login(login_username,login_password,v);
                         USER_LOGIN_USERNAME.setText("");
                         USER_LOGIN_PASSWORD.setText("");

              }
                            else {

                         Snackbar snackbar1 = Snackbar.make(v, "Invalid Email Id", Snackbar.LENGTH_LONG);

                         snackbar1.show();
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


    private void login(String login_username,String login_password,View view) {
        int k;
                for (k = 0; k < Database_login_Register.userdetailsHash.size(); k++) {
                    if (Database_login_Register.userdetailsHash.get(k).get("username").equals(login_username)) {
                        if (Database_login_Register.userdetailsHash.get(k).get("password").equals(login_password)) {
                            Database_login_Register display_username= new Database_login_Register(getActivity());
                            display_username.execute(login_username);

                            Intent intent = new Intent(getActivity(), RetriveData.class);
                            this.startActivity(intent);
                        } else {
                            Snackbar snackbar1 = Snackbar.make(view, "Wrong Password! Try Again", Snackbar.LENGTH_LONG);

                            snackbar1.show();
                            return;
                        }

                    }
                }

    }


}
