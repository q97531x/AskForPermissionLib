package com.example.weeboos.permissionlib;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.PermissionChecker;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限申请类
 * */
public class PermissionRequest {
    private static final String TAG = PermissionRequest.class.getSimpleName();
    private PermissionFragment fragment;

    public PermissionRequest(FragmentActivity activity) {
        if(fragment == null) {
            fragment = getFragmentInstance(activity.getSupportFragmentManager());
        }
    }

    public PermissionRequest(Fragment fragment) {
        this.fragment = getFragmentInstance(fragment.getChildFragmentManager());
    }


    /**
     * 获取fragment单例
     * @param fragmentManager
     * @return
     */
    private PermissionFragment getFragmentInstance(FragmentManager fragmentManager) {
        if(findFragment(fragmentManager)==null) {
            fragment = new PermissionFragment();
            fragmentManager.beginTransaction()
                    .add(fragment,TAG).commitNow();
            return fragment;
        }
        return findFragment(fragmentManager);
    }

    private PermissionFragment findFragment(FragmentManager fragmentManager) {
        return (PermissionFragment)fragmentManager.findFragmentByTag(TAG);
    }

    /**
     * 请求权限，根据是否拥有权限，是否拒绝再次请求等调用不同回调
     * @param listener          请求回调
     * @param permissions       请求的权限数组
     */
    public void requestPermission(@NonNull PermissionListener listener, String[] permissions) {
        fragment.requestPermissions(permissions,listener);
    }



    /**
     * 权限请求结果回调
     */
    public interface PermissionListener {
        /**
         * 通过授权
         */
        void permissionGranted();

        /**
         * 拒绝授权
         * @param permissions       被拒绝的权限
         */
        void permissionDenied(ArrayList<String> permissions);

        /**
         * 勾选不再询问按钮，做一些提示
         * @param permissions       勾选不再询问的权限
         */
        void permissionNeverAsk(ArrayList<String> permissions);
    }
}
