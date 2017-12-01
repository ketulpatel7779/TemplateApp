package com.mastertemplate.utils.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.mastertemplate.data.DataManager;

/**
 * This activity will start the request permission process and send broadcast back to @{@link Permission} class with result
 */
public class PermissionActivity extends Activity {


    String Permission = "";

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        init(savedInstanceState);
        requestPermissions(new String[]{Permission},
                55);
    }

    private void init(Bundle state) {
        if (state != null) {
            Permission = state.getString("Permission");
        } else {
            Intent intent = getIntent();
            Permission = intent.getStringExtra("Permission");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        state.putString("Permission", Permission);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        DataManager.setPermissionValue(permissions[0], true);
        Intent intent = new Intent();
        // TODO avt : 8/8/17 change action string here and in manifest
        intent.setAction("com.template.PERMISSION_RESULT_INTENT");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            intent.putExtra("Permission", true);
        } else {
            intent.putExtra("Permission", false);
        }
        sendBroadcast(intent);
        finish();
    }
}
