package com.example.akbar.testcodeqr;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission_group.CAMERA;

public class ReaderActivity2 extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkPermission()){
                Toast.makeText(ReaderActivity2.this, "Permission is granted", Toast.LENGTH_LONG).show();
            }else {
                requestPermission();
            }
        }
    }
    private boolean checkPermission(){
        return (ContextCompat.checkSelfPermission(ReaderActivity2.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

     private void requestPermission(){
         ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
     }

     public void onRequestPermissionsResult(int requestCode, String permission[], int grantResult[]){
        switch (requestCode)
        {
            case REQUEST_CAMERA :
                if (grantResult.length > 0)
                {
                    boolean cameraAccepted = grantResult[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted)
                    {
                        Toast.makeText(ReaderActivity2.this, "Permission Granted", Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        Toast.makeText(ReaderActivity2.this, "Permission Denied", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        {
                            if (shouldShowRequestPermissionRationale(CAMERA))
                            {
                                displayAlertMessage("You need to allow access for both permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
     }

     public void onResume() {

         super.onResume();
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
         {
             if (checkPermission())
             {
                 if (scannerView == null)
                 {
                     scannerView = new ZXingScannerView(this);
                     setContentView(scannerView);
                 }
                 scannerView.setResultHandler(this);
                 scannerView.startCamera();
             }
             else
             {
                 requestPermission();
             }
         }
     }
     @Override
     public void onDestroy() {
         super.onDestroy();
         scannerView.stopCamera();
     }

     public void displayAlertMessage(String message, DialogInterface.OnClickListener listener)
     {
         new AlertDialog.Builder(ReaderActivity2.this)
                 .setMessage(message)
                 .setPositiveButton("OK", listener)
                 .setNegativeButton("Cancel", null)
                 .create()
                 .show();
     }

    @Override
    public void handleResult(final Result result) {
        final String scanResult = result.getText();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                scannerView.resumeCameraPreview(ReaderActivity2.this);
            }
        });
        builder.setNeutralButton("Visit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent =  new Intent(Intent.ACTION_VIEW, Uri.parse(scanResult));
                startActivity(intent);
            }
        });
        builder.setMessage(scanResult);
        AlertDialog alert = builder.create();
        alert.show();
    }
}
