package com.example.jeff.meowmix;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Song> songList;

    // Check for required permissions dynamically because newer APIs can deny access anytime
    private final static String[] REQUIRED_PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE};
    private final static int REQUEST_CODE_PERMISSION = 1;
    ArrayList<String> missingPermissions = new ArrayList<>();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissions();
        requestPermissions();
    }

    protected void checkPermissions() {
        // See which permissions the user hasn't accepted yet. Should add reasoning to each
        // permission as well
        for(String permission : REQUIRED_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);

            if(result != PackageManager.PERMISSION_GRANTED)
                missingPermissions.add(permission);
        }
    }

    protected void requestPermissions() {
        // We're missing some permissions. Request them
        if(!missingPermissions.isEmpty()) {
            String[] permissionArray = new String[missingPermissions.size()];
            permissionArray = missingPermissions.toArray(permissionArray);

            ActivityCompat.requestPermissions(this, permissionArray,
                    REQUEST_CODE_PERMISSION);
        }
    }
}
