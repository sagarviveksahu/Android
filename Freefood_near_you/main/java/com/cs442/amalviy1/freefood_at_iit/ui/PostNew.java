package com.cs442.amalviy1.freefood_at_iit.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.cs442.amalviy1.freefood_at_iit.BackWork;
import com.cs442.amalviy1.freefood_at_iit.asynctask.*;
import com.cs442.amalviy1.freefood_at_iit.Pojo.FoodPOJO;
import com.cs442.amalviy1.freefood_at_iit.R;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PostNew extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ImageView imageView;
    EditText TITLE, DESCRIPTION,LOCATION;
    Button POST;
    File file;
    String foodTitle, foodDescription, foodLocation;
    FoodPOJO pojoObj;
    DateFormat dateFormat ;
    DateFormat datetime;
    Date date ;
    Date time;
    String photoName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.drawer2 );
        imageView = (ImageView)findViewById(R.id.imageView);
        TITLE= (EditText)findViewById( R.id.food_name);
        DESCRIPTION=(EditText)findViewById( R.id.description );
        LOCATION= (EditText)findViewById( R.id.location );
        POST=(Button)findViewById( R.id.post_Btn );

        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        DrawerLayout drawer1 = (DrawerLayout) findViewById(R.id.drawer_layout1);
        ActionBarDrawerToggle toggle1 = new ActionBarDrawerToggle(
                this, drawer1, toolbar1, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer1.setDrawerListener(toggle1);
        toggle1.syncState();

        NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_view2);
        navigationView1.setNavigationItemSelectedListener(this);

        POST.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodTitle= TITLE.getText().toString();
                foodDescription= DESCRIPTION.getText().toString();
                foodLocation=LOCATION.getText().toString();

                if (foodTitle.length() == 0 || foodDescription.length() == 0 || foodLocation.length() == 0) {

                    Snackbar snackbar = Snackbar.make(v, "Please enter all the fields", Snackbar.LENGTH_LONG);

                    snackbar.show();
                    return;
                }
                else {

                    Log.d("====" + file, "");
                    datetime = new SimpleDateFormat("H:mm a");
                    time = new Date();
                    Log.d("=datetime" + datetime, "");
                    pojoObj = new FoodPOJO(file, foodTitle, foodDescription, foodLocation, datetime.format(time));


                    post(pojoObj);
                }
            }
        } );
    }



    public void photo(View view) {

        Intent cam_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = getFile();
        cam_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(cam_intent,1);
    }

    private File getFile()
    {
         dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
         date = new Date();
         photoName= "cam_image"+dateFormat.format(date)+".jpg";

        File folder = new File(Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_DCIM)+"/freefood/");
        if (!folder.exists())
        {
                        folder.mkdir();
        }

        file = new File(folder,photoName);

        return file;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        String path = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_DCIM)+"/freefood/"+photoName;
        Bitmap d= BitmapFactory.decodeFile( path );
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        d.compress( Bitmap.CompressFormat.JPEG, 100, out );
        int newHeight= (int) (d.getHeight()*(500.0/d.getHeight()));
        Bitmap putImage= Bitmap.createScaledBitmap( d,500,newHeight,true );
        imageView.setImageBitmap( putImage );

    }

    protected void post(FoodPOJO pojo)
    {


            Database_New_Post databaseBackground = new Database_New_Post(this);
            databaseBackground.execute(  pojo );
            String token = FirebaseInstanceId.getInstance().getToken();
            Log.d("NOTIFICATION", "Token: " + token);
            BackWork backwork= new BackWork();
            backwork.execute();

            Intent intent = new Intent(this,RetriveData.class);
            this.startActivity(intent);


    }

    public void onBackPressed() {
        DrawerLayout drawer1 = (DrawerLayout) findViewById(R.id.drawer_layout1);
        if (drawer1.isDrawerOpen(GravityCompat.START)) {
            drawer1.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu1) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main1, menu1);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item1) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item1.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item1);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item1) {
        //   FragmentManager fm = getFragmentManager();
        int id = item1.getItemId();

        if (id == R.id.nav_camara) {
            //  fm.beginTransaction().replace(R.id.content_frame, new ImportFragment()).commit();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer1 = (DrawerLayout) findViewById(R.id.drawer_layout1);
        drawer1.closeDrawer(GravityCompat.START);
        return true;
    }
}
