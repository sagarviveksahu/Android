package com.cs442.amalviy1.freefood_at_iit.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.cs442.amalviy1.freefood_at_iit.R;
import com.cs442.amalviy1.freefood_at_iit.asynctask.Database_StoreToken_Notification;
import com.cs442.amalviy1.freefood_at_iit.asynctask.Database_login_Register;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by Ankit on 11/3/16.
 */
public class Startup extends AppCompatActivity {
    Animation fade_in, fade_out;
    ViewFlipper vwflpr;
    public int i;
    public int j;
    private static final int PERMS_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_startup_page);

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("NOTIFICATION", "Token: " + token);

        Database_StoreToken_Notification token_data=new Database_StoreToken_Notification();
        token_data.execute(token);



        Database_login_Register databaseLoginRegister = new Database_login_Register(this);
        databaseLoginRegister.execute();
        vwflpr = (ViewFlipper)this.findViewById(R.id.Vwflpr);

        fade_in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);

        vwflpr.setInAnimation(fade_in);
        vwflpr.setOutAnimation(fade_out);

        vwflpr.setAutoStart(true);

        vwflpr.setFlipInterval(800);
        vwflpr.startFlipping();

        vwflpr.getInAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                int displayedChild = vwflpr.getDisplayedChild();
                int childCount = vwflpr.getChildCount();

                if (displayedChild == childCount - 1) {
                    vwflpr.stopFlipping();

                    if (hasPermissions()){

                        startActivity(new Intent(getApplicationContext(),LoginPage.class));

                    } else {
                        requestperms();
                    }


                }
            }



            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    private boolean hasPermissions(){

        int res = 0;

        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION};
        for (String perms : permissions){
            res = checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)){
                return false;
            }
        }
        return true;

    }

    private void requestperms(){

        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permissions, PERMS_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;

        switch (requestCode){
            case PERMS_REQUEST_CODE:
                for (int res : grantResults){
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }
                break;

            default:

                allowed = false;
                break;
        }

        if (allowed){

            startActivity(new Intent(getApplicationContext(),LoginPage.class));

        }

        else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    Toast.makeText(this, "storage permission denied", Toast.LENGTH_SHORT).show();

            }

            }
        }
    }
}
