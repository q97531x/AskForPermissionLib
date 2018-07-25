package com.example.weeboos.permissionlib;

import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by weeboos
 * on 2018/7/23
 */
public class PermissionFragment extends Fragment {
    private static final int PERMISSIONS_REQUEST_CODE = 42;
    private static PermissionRequest.PermissionListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.permission_lib_fragment_permission,container,false);
        return view;
    }

    public void requestPermissions(String[] permissions, PermissionRequest.PermissionListener listener) {
        this.listener = listener;
        if(!PermissionUtils.hasPermission(getContext(),permissions)) {
            requestPermissions(permissions, PERMISSIONS_REQUEST_CODE);
        }else {
            listener.permissionGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != PERMISSIONS_REQUEST_CODE) return;
        if(listener!=null) {
            if (PermissionUtils.hasPermission(getContext(), permissions)) {
                listener.permissionGranted();
            } else {
                if (PermissionUtils.isAsk(getContext(), permissions)) {
                    listener.permissionNeverAsk(PermissionUtils.getNeverAskPermissions(getContext(), permissions));
                } else {
                    listener.permissionDenied(PermissionUtils.getDeniedPermissions(getContext(), permissions));
                }
            }
        }
    }
}
