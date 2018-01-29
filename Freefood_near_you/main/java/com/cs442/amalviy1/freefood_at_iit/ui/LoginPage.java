package com.cs442.amalviy1.freefood_at_iit.ui;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.Manifest;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.cs442.amalviy1.freefood_at_iit.asynctask.Database_login_Register;
import com.cs442.amalviy1.freefood_at_iit.R;
import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;



public class LoginPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
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
    static java.sql.PreparedStatement ps = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        USER_FIRST_NAME=(EditText)findViewById(R.id.first_name);
        USER_LAST_NAME=(EditText)findViewById(R.id.last_name);
        USER_EMAIL= (EditText)findViewById(R.id.email);
        USER_PASSWORD=(EditText)findViewById(R.id.password);
        USER_CONFIRM_PASSWORD=(EditText)findViewById(R.id.confirm_password);
        USER_LOGIN_USERNAME=(EditText)findViewById(R.id.login_username);
        USER_LOGIN_PASSWORD=(EditText)findViewById(R.id.login_password);

      REGISTER= (ImageButton) findViewById(R.id.register_btn);
      LOGIN= (ImageButton) findViewById(R.id.login_btn);
        str1 = "@iit.edu";
        str2 = "@hawk.iit.edu";



//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//
//
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
        setContentView(R.layout.sign_in_fragment);
        NEWACCOUNT= (TextView)findViewById(R.id.new_account);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Login fragmentLogin= new Login();
        fragmentTransaction.replace(R.id.fragment_container,fragmentLogin);
        fragmentTransaction.addToBackStack("f1");
        fragmentTransaction.commit();
        loadMainScreen();



                NEWACCOUNT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        Register fragmentRegister= new Register();
                        fragmentTransaction.replace(R.id.fragment_container,fragmentRegister);
                        fragmentTransaction.addToBackStack("f2");
                        fragmentTransaction.commit();
                    }
                });


    }

    private void loadMainScreen() {

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        animation.reset();
        LinearLayout splashLayout = (LinearLayout) findViewById(R.id.sign_fragment);
        splashLayout.clearAnimation();
        splashLayout.startAnimation(animation);
        animation = AnimationUtils.loadAnimation(this, R.anim.translate);
        animation.reset();
                ImageView iv = (ImageView) findViewById(R.id.logo);
        iv.clearAnimation();
        iv.startAnimation(animation);
    }


    public void onBackPressed()
    {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else {

        }
    }
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
    //}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //   FragmentManager fm = getFragmentManager();
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            //  fm.beginTransaction().replace(R.id.content_frame, new ImportFragment()).commit();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}