package com.cs442.amalviy1.freefood_at_iit.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.cs442.amalviy1.freefood_at_iit.R;

public class AttractionListActivity extends AppCompatActivity {

    private static final int PERMISSION_REQ = 0;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add( R.id.container, new AttractionListFragment())
                    .commit();
        }


//        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        // Setup refresh listener which triggers new data loading
  //      swipeContainer.setOnRefreshListener(() -> {
            // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
//               Intent intent = new Intent(getApplicationContext(), RetriveData.class);
//             startActivity(intent);
    //        swipeContainer.setRefreshing(true);

      //  });
        // Configure the refreshing colors
      //  swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
        //        android.R.color.holo_green_light,
          //      android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);



    }

    @Override
    protected void onResume() {
        super.onResume();
        //   UtilityService.requestLocation(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater mif= getMenuInflater();
        mif.inflate(R.menu.main_action_bar,menu);

        return  super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.refresh:

                startActivity(new Intent(getApplicationContext(),RetriveData.class));
                return true;

            case R.id.action_record:

                startActivity(new Intent(getApplicationContext(),PostNew.class));
                return true;

            case R.id.account:

                //  startActivity(new Intent(getApplicationContext(),account.class));


//                UtilityService.triggerWearTest(this, false);
//                showDebugDialog(R.string.action_test_notification,
//                        R.string.action_test_notification_dialog);
                return true;
            case R.id.setting:
                //    startActivity(new Intent(getApplicationContext(),settings_ui.class));
//                UtilityService.triggerWearTest(this, true);
//                showDebugDialog(R.string.action_test_microapp,
//                        R.string.action_test_microapp_dialog);
                return true;
            case R.id.logout:
                startActivity(new Intent(getApplicationContext(),Startup.class));
//                boolean geofenceEnabled = Utils.getGeofenceEnabled(this);
//                Utils.storeGeofenceEnabled(this, !geofenceEnabled);
//                Toast.makeText(this, geofenceEnabled ?
//                        "Debug: Geofencing trigger disabled" :
//                        "Debug: Geofencing trigger enabled", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Permissions request result callback
     */
    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQ:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fineLocationPermissionGranted();
                }
        }
    }

    /**
     * Request the fine location permission from the user
     */
    private void requestFineLocationPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQ);
    }

    /**
     * Run when fine location permission has been granted
     */
    private void fineLocationPermissionGranted() {
//        UtilityService.addGeofences(this);
//        UtilityService.requestLocation(this);
    }




    private void showDebugDialog(int titleResId, int bodyResId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(titleResId)
                .setMessage(bodyResId)
                .setPositiveButton(android.R.string.ok, null);
        builder.create().show();
    }


    public void postnew(View view)
    {
        Intent intent = new Intent(this, PostNew.class);
        startActivity(intent);
    }
    public void updateFeed(View view)
    {
        Intent intent = new Intent(this, RetriveData.class);
        startActivity(intent);
    }

    ActionBar.TabListener tblstnr = new ActionBar.TabListener(){

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

//            startActivity(new Intent(getApplicationContext(),RetriveData.class));

        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

        }

    };
    public void onBackPressed() {

    }


}