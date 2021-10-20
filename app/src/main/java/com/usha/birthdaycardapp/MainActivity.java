package com.usha.birthdaycardapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

public class MainActivity extends AppCompatActivity {
    String[] permission = {READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE};


   private ActivityResultLauncher<Intent> activityResultLauncher;
    EditText message;
    Button saveInfo;
    Button displayInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
        message = findViewById(R.id.tvNameInput);

        saveInfo = findViewById(R.id.saveInfo);
        displayInfo = findViewById(R.id.displayInfo);
        displayInfo.setText(Environment.getExternalStorageDirectory().getAbsolutePath().toString());

        saveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkpermission()){
                    if(!message.getText().toString().isEmpty()){
                        saveInfoInFile(message.getText().toString());
                        Toast.makeText(MainActivity.this, "Information Inserted", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MainActivity.this, "Enter Information", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    RequestPermission();
                }


            }
        });

        displayInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkpermission()){
                    displaySaveInfo();
                }else {
                    RequestPermission();
                }

            }
        });
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(SDK_INT >= Build.VERSION_CODES.R){
                            if(Environment.isExternalStorageManager()){
                                Toast.makeText(MainActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();

                            }
                        } 
                    }
                });
    }


    private void displaySaveInfo() {
        FileInputStream file = null;
        String info = null;
        StringBuilder data = new StringBuilder();
        try {
            File f = new File(Environment.getExternalStoragePublicDirectory("download"),"info.txt");
            file = new FileInputStream(f);
            InputStreamReader input = new InputStreamReader(file);
            BufferedReader br = new BufferedReader(input);
            while ((info= br.readLine())!=null){
               data.append(info);
            }
            message.setText(data.toString());
            file.close();
        }catch (FileNotFoundException e){
            Toast.makeText(this, "File Not Found", Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            Toast.makeText(this, "IO Exception", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveInfoInFile(String info) {
        FileOutputStream file= null;

        try {
            File f = new File(Environment.getExternalStoragePublicDirectory("download"),"info.txt");
            f.createNewFile();
            file = new FileOutputStream(f);
            file.write(info.getBytes());
        }catch (FileNotFoundException e){
            Toast.makeText(this, "File Not Found", Toast.LENGTH_SHORT).show();

        }catch (IOException e){
            Toast.makeText(this, "IO Exception", Toast.LENGTH_SHORT).show();

        }
        throw new RuntimeException("Force Crash");
    }


    public void buttonCreateCardClick(View view) {

        File mediaStorageDir;
        if (SDK_INT < 29) {

            mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "Cam");

            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                }
            }

        } else {
            if (SDK_INT >= 30) {

                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                } else {
                    RequestPermission();
                }

            } else {
                mediaStorageDir = this.getExternalFilesDir("Cam");
                if (!mediaStorageDir.exists()) {
                    if (!mediaStorageDir.mkdirs()) {
                    }
                }


            }

        }

//        String inputMsg = message.getText().toString();
//        Intent intent = new Intent(this,BirthdayGreetingActivity.class);
//        intent.putExtra("msg",inputMsg);
//        startActivity(intent);

    }

    public boolean checkpermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) { // R is Android 11
            return Environment.isExternalStorageManager();//it will return true for android 11
        } else {
            int writepermission = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
            int readpermission = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);

            return writepermission == PackageManager.PERMISSION_GRANTED
                    && readpermission == PackageManager.PERMISSION_GRANTED;//retrun true for lower than 11
        }
    }

    public void RequestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
//            try {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
//                intent.addCategory("android.intent.category.DEFAULT");
//                intent.setData(Uri.parse(String.format("package:%s", new Object[]{getApplicationContext().getPackageName()})));
////                                startActivityForResult(intent, 2000);
//                activityResultLauncher.launch(intent);
//            } catch (Exception e) {
//                Intent intent= new Intent();
//                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
////                                startActivityForResult(obj, 2000);
//                activityResultLauncher.launch(intent);
//            }
//        }else {
//            ActivityCompat.requestPermissions(MainActivity.this,permission,30);
//        }
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Allow BirthdayCardApp to access all files.");
            alertDialogBuilder.setPositiveButton("Allow",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            try {
                                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                                intent.addCategory("android.intent.category.DEFAULT");
                                intent.setData(Uri.parse(String.format("package:%s", new Object[]{getApplicationContext().getPackageName()})));
//                                startActivityForResult(intent, 2000);
                                activityResultLauncher.launch(intent);
                            } catch (Exception e) {
                                Intent intent= new Intent();
                                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
//                                startActivityForResult(obj, 2000);
                                activityResultLauncher.launch(intent);
                            }
                        }
                    });

            alertDialogBuilder.setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    //your code here
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }else {
            ActivityCompat.requestPermissions(MainActivity.this,permission,30);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 30:
                if(grantResults.length>0){
                    boolean readper = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writerper = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if(readper && writerper){
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "You Denied permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}


