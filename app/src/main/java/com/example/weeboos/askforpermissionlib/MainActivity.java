package com.example.weeboos.askforpermissionlib;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.weeboos.permissionlib.PermissionRequest;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    PermissionRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String[] permissionNames = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };
        request = new PermissionRequest(this);
        findViewById(R.id.tv_ask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request.requestPermission(new PermissionRequest.PermissionListener() {
                    @Override
                    public void permissionGranted() {
                        Toast.makeText(MainActivity.this,"获取成功",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void permissionDenied(ArrayList<String> permissions) {
                        Toast.makeText(MainActivity.this,"获取拒绝",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void permissionNeverAsk(ArrayList<String> permissions) {
                        Toast.makeText(MainActivity.this,"不再询问",Toast.LENGTH_SHORT).show();
                        showAlertDialog();
                    }
                },permissionNames);
            }
        });
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(this).setMessage("前往设置")
                .setPositiveButton("前往设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        goToSetting();
                    }
                }).show();
    }

    /**
     * 跳转前往设置
     */
    private void goToSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }


}