package com.superapp.emergencyservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    Button police;
    Button ambulance;
    Button fireservice;
    private static final int REQUEST_CALL = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().setTitle("Dial Emergency");

        police=(Button) findViewById(R.id.police);
        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                policeCall();
            }
        });

        ambulance=(Button) findViewById(R.id.ambulance);
        ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ambulanceCall();
            }
        });

        fireservice=(Button) findViewById(R.id.fireservice);
        fireservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireserviceCall();
            }
        });


    }

    public void policeCall(){
        String police="911";
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) !=
                    PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);

        }else{
            String dail="tel:" + police;
            startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dail)));
        }
    }
    public void ambulanceCall(){
        String ambulance="912";
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);

        }else{
            String dail="tel:" + ambulance;
            startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dail)));
        }

    }
    public void fireserviceCall(){
        String fireservice="913";
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);

        }else{
            String dail="tel:" + fireservice;
            startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dail)));
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                policeCall();
                ambulanceCall();
                fireserviceCall();
            }
        }
    }
}
