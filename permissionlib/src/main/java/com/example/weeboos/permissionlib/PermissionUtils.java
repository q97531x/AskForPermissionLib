package com.example.weeboos.permissionlib;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.PermissionChecker;

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
    public static boolean isAsk(Context context, @NonNull String... permissions) {
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

    public static String getSystem(){
        String SYS = "";
        try {
            Properties prop= new Properties();
            prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            if(prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null){
                SYS = SYS_MIUI;//小米
            }else if(prop.getProperty(KEY_EMUI_API_LEVEL, null) != null
                    ||prop.getProperty(KEY_EMUI_VERSION, null) != null
                    ||prop.getProperty(KEY_EMUI_CONFIG_HW_SYS_VERSION, null) != null){
                SYS = SYS_EMUI;//华为
            }else if(getMeizuFlymeOSFlag().toLowerCase().contains("flyme")){
                SYS = SYS_FLYME;//魅族
            }
        } catch (IOException e){
            e.printStackTrace();
            return SYS;
        }
        return SYS;
    }

    public static String getMeizuFlymeOSFlag() {
        return getSystemProperty("ro.build.display.id", "");
    }

    private static String getSystemProperty(String key, String defaultValue) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method get = clz.getMethod("get", String.class, String.class);
            return (String)get.invoke(clz, key, defaultValue);
        } catch (Exception e) {
        }
        return defaultValue;
    }
}