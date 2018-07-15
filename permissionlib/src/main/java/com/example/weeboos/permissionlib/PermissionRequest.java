package com.example.weeboos.permissionlib;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;

/**
 * 权限申请类
 * */
public class PermissionRequest {
    private Context context;

    public PermissionRequest(Context context) {
        this.context = context;
    }

    /**
     * 请求权限，根据是否拥有权限，是否拒绝再次请求等调用不同回调
     * @param listener
     * @param permission
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermission(@NonNull PermissionListener listener, String permission) {
        if(hasPermission(permission)) {
            //已获取该权限
            listener.permissionGranted(permission);
        }else if (isNeverAsk(permission)){
            listener.permissionNeverAsk(permission);
        } else {
            listener.permissionDenied(permission);
        }
    }

    private boolean hasPermission(String permission) {
        int result = PermissionChecker.checkSelfPermission(context, permission);
        if (result != PermissionChecker.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    /**
     * 获取是否点击了不再询问按钮，只在androidM生效,动态权限是android6.0以上需求
     * @param permissionName
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isNeverAsk(@NonNull String permissionName) {
        return ((Activity)context).shouldShowRequestPermissionRationale(permissionName);
    }

    /**
     * 权限请求结果回调
     */
    public interface PermissionListener {
        /**
         * 通过授权
         * @param permission
         */
        void permissionGranted(@NonNull String permission);

        /**
         * 拒绝授权
         * @param permission
         */
        void permissionDenied(@NonNull String permission);

        /**
         * 勾选不再询问按钮，做一些提示
         */
        void permissionNeverAsk(@NonNull String permission);
    }
}
