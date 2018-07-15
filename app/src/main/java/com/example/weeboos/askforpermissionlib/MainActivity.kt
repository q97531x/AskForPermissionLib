package com.example.weeboos.askforpermissionlib

import android.Manifest
import android.annotation.TargetApi
import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.StringDef
import android.widget.Toast
import com.example.weeboos.permissionlib.PermissionPool
import com.example.weeboos.permissionlib.PermissionRequest

class MainActivity : AppCompatActivity() {

    @TargetApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PermissionRequest(this).requestPermission(object:PermissionRequest.PermissionListener{
            override fun permissionGranted(permission: String) {
                Toast.makeText(this@MainActivity,"拥有权限",Toast.LENGTH_LONG).show()
                //获取到权限，进行下一步
            }

            override fun permissionDenied(permission: String) {
                requestPermissions(arrayOf(permission), PermissionPool.CAMERA)
                Toast.makeText(this@MainActivity,"未拥有权限",Toast.LENGTH_LONG).show()
            }

            override fun permissionNeverAsk(permission: String) {
                Toast.makeText(this@MainActivity,"已拒绝再次询问",Toast.LENGTH_LONG).show()
                //跳转设置页面设置权限
                goToSetting()
            }
        }, Manifest.permission.CAMERA)
    }

    /**
     * 跳转前往设置
     */
    fun goToSetting() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }
}
