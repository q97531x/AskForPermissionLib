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
import com.example.weeboos.permissionlib.PermissionUtils;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String[] permissionNames = new String[]{
                Manifest.permission.INSTALL_PACKAGES,
        };
        findViewById(R.id.tv_ask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionRequest.getInstance().build(MainActivity.this).requestPermission(new PermissionRequest.PermissionListener() {
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
                        PermissionUtils.showAlertDialog(MainActivity.this,PermissionUtils.translateArrayString(permissions));
                    }
                },permissionNames);
            }
        });
    }


}
