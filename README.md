# AskForPermissionLib
动态权限获取库

this library is use for ask for permissions.
# SetUp
To use this library your minSdkVersion must be >= 11.
```
allprojects {
    repositories {
        google()
        jcenter()
    }
}
implementation 'com.example.weeboos:permissionLib:1.1.4'
```
# Useage
create a PermissionRequest instance
```
PermissionRequest request = new PermissionRequest(this); // where this is an Activity or Fragment instance
```
Example：request the CAMERA Permission
```
request.requestPermission(new PermissionRequest.PermissionListener() {
                    @Override
                    public void permissionGranted() {
                       //do Something when permission granted
                       Toast.makeText(MainActivity.this,"获取成功",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void permissionDenied(ArrayList<String> permissions) {
                       //do Something when permission denied
                       Toast.makeText(MainActivity.this,"获取拒绝",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void permissionNeverAsk(ArrayList<String> permissions) {
                       //do Something when permission never ask
                       Toast.makeText(MainActivity.this,"不再询问",Toast.LENGTH_SHORT).show();
                        PermissionUtils.showAlertDialog(MainActivity.this,PermissionUtils.translateArrayString(permissions));
                    }
                },Manifest.permission.CAMERA);
```
request some permissions like the CAMERA and WRITE_EXTERNAL_STORAGE
```
request.requestPermission(new PermissionRequest.PermissionListener() {
                    @Override
                    public void permissionGranted() {
                       //do Something when permission granted
                       Toast.makeText(MainActivity.this,"获取成功",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void permissionDenied(ArrayList<String> permissions) {
                       //do Something when permission denied
                       Toast.makeText(MainActivity.this,"获取拒绝",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void permissionNeverAsk(ArrayList<String> permissions) {
                       //do Something when permission never ask
                       Toast.makeText(MainActivity.this,"不再询问",Toast.LENGTH_SHORT).show();
                        PermissionUtils.showAlertDialog(MainActivity.this,PermissionUtils.translateArrayString(permissions));
                    }
                },new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE});
```
# Status
This library is still beta, so contributions are welcome. I'm currently using it in production since months without issue.
