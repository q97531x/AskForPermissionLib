package com.example.weeboos.permissionlib;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by weeboos
 * on 2018/7/24
 */
public class PermissionUtils {

    public static final String SYS_EMUI = "sys_emui";
    public static final String SYS_MIUI = "sys_miui";
    public static final String SYS_FLYME = "sys_flyme";
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    private static final String KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level";
    private static final String KEY_EMUI_VERSION = "ro.build.version.emui";
    private static final String KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion";

    /**
     * 判断用户是否拥有所需要的权限
     * @param permissions 申请的权限
     * @return      否拥有所需要的权限
     */
    public static boolean hasPermission(Context context, String[] permissions) {
        for(String permissionName : permissions){
            int result = PermissionChecker.checkSelfPermission(context, permissionName);
            if (result != PermissionChecker.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取是否点击了不再询问按钮，只在androidM生效,动态权限是android6.0以上需求
     * @param permissions       所有请求的权限
     * @return      是否点击了不再询问按钮
     */
    public static boolean isNeverAsk(Context context, @NonNull String... permissions) {
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            for(String permissionName : permissions) {
                if(((Activity)context).shouldShowRequestPermissionRationale(permissionName)){
                    return false;
                }
            }
            return true;
        }else {
            return true;
        }
    }

    /**
     * 获取点击了不再询问的permissions
     * @param permissions       所有请求的权限
     * @return      不再询问的permissions
     */
    public static ArrayList<String> getNeverAskPermissions(Context context,@NonNull String... permissions) {
        ArrayList<String> neverAskPermissions = new ArrayList<>();
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            for(String permissionName : permissions) {
                if(((Activity)context).shouldShowRequestPermissionRationale(permissionName)){
                    neverAskPermissions.add(permissionName);
                }
            }
            return neverAskPermissions;
        }else {
            return neverAskPermissions;
        }
    }

    public static boolean isXiaoMi() {
        return Build.MANUFACTURER.equals("Xiaomi");
    }

    /**
     * 获取被拒绝的权限
     * @param permissions       所有请求的权限
     * @return      被拒绝的权限
     */
    public static ArrayList<String> getDeniedPermissions(Context context, @NonNull String... permissions) {
        ArrayList<String> deniedPermissions = new ArrayList<>();
        for(String permissionName : permissions){
            int result = PermissionChecker.checkSelfPermission(context, permissionName);
            if (result != PermissionChecker.PERMISSION_GRANTED) {
                deniedPermissions.add(permissionName);
            }
        }
        return deniedPermissions;
    }

    public static void showAlertDialog(final Context context, String permissions) {
        new AlertDialog.Builder(context).setMessage(String.format("需要%s权限，请前往设置", permissions))
                .setPositiveButton("前往设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        goToSetting(context);
                    }
                }).show();
    }

    /**
     * 跳转前往设置
     */
    private static void goToSetting(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    /**
     * 将List转换成String字符
     * @param permissions 权限list
     * @return 拼接后的字符串
     */
    public static String translateArrayString(ArrayList<String> permissions) {
        if(permissions.size()==1)
            return permissions.get(0);
        String permissionNames = "";
        for (int i = 0; i < permissions.size();i++) {
            //最后一次循环
            if(i == permissions.size() - 1) {
                permissionNames = permissionNames + permissions.get(i);
            }
            permissionNames = permissionNames + permissions.get(i)+",";
        }
        return permissionNames;
    }
}
