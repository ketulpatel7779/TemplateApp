package com.mastertemplate.utils.permission;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.mastertemplate.R;
import com.mastertemplate.data.DataManager;
import com.mastertemplate.utils.CommonUtils;


/**
 * This class will provide all the necessary methods for @{@link RuntimePermission}
 */
public class Permission {

    private static PermissionListener listener;

    /**
     * Listener to notify permission request result
     */
    public interface PermissionListener {
        void granted();

        void denied();
    }

    /**
     * Check whether we have required permission or not.
     * if not it will ask for permission or display rational msg box if user restricted permission
     * by selecting "do not ask again"
     * All the @{@link RuntimePermission} must use this function to take permission.
     *
     * @param activity        Activity
     * @param permissions      @{@link android.Manifest.permission} String
     * @param rationalMessage msg to display
     * @param Onlistener      listener to call on result
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static void askPermission(final Activity activity, final String[] permissions, final String rationalMessage, final PermissionListener Onlistener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            boolean isGranted=true;
            for (String permission : permissions) {
                if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {

                    int hasWriteContactsPermission = activity.checkSelfPermission(permission);
                    if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                        if (activity.shouldShowRequestPermissionRationale(permission)) {
                            Intent intent = new Intent(activity.getApplicationContext(), PermissionActivity.class);
                            intent.putExtra("Permission", permission);
                            activity.startActivity(intent);
                            listener = Onlistener;
                        } else {
                            if (DataManager.getPermissionValue(permission)) {
                                CommonUtils.showConfirmationDialog(activity, "Permission", rationalMessage, "SETTINGS", "CANCEL", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                                                intent.setData(uri);
                                                activity.startActivityForResult(intent, 420);
                                                CommonUtils.showErrorToast(activity, activity.getString(R.string.please_grant_permission));
                                                dialog.dismiss();
                                            }
                                        },
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                            } else {
                                Intent intent = new Intent(activity.getApplicationContext(), PermissionActivity.class);
                                intent.putExtra("Permission", permission);
                                activity.startActivity(intent);
                                listener = Onlistener;
                            }
                        }



                    }
                    isGranted=false;
                }




            }
            if(isGranted)
                Onlistener.granted();

        } else {
            Onlistener.granted();
        }

    }


    public static class receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (intent.getBooleanExtra("Permission", false)) {
                    listener.granted();
                } else {
                    listener.denied();
                }
            } catch (Exception e) {

            }
        }
    }

}