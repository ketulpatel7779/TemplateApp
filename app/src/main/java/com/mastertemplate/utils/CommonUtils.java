package com.mastertemplate.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mastertemplate.BuildConfig;
import com.mastertemplate.R;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.os.Build.VERSION_CODES.M;
import static com.mastertemplate.utils.AppConstants.TAG;

/**
 * All the common utility methods defined here
 */
public final class CommonUtils {

    private static final String EMAIL = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Instance of progress dialog
     */
    static ProgressDialog pdlg;

    /**
     * For displaying log in logcat
     * @param msg is for print message
     */
    public static void log(String msg) {
        if (BuildConfig.IS_DEBUG) {
            Log.d(TAG, msg);
        }
    }

    /**
     * Converts pixel into density pixel
     * @param px for pixel to convert
     */
    public static float pxToDp(float px) {
        float densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi;
        return px / (densityDpi / 160f);
    }

    /**
     * Converts density pixel to pixel
     * @param dp for density to convert
     */
    public static int dpToPx(float dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    /**
     * Checks the internet connection
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /**
     * Display Success Toast message
     * @param message is for display message
     */
    public static void showSuccessToast(Context ct, String message) {
        showToast(ct, message, false);
    }

    /**
     * Display Error Toast message
     * @param message is for display message
     */
    public static void showErrorToast(Context ct, String message) {
        showToast(ct, message, true);
    }

    /**
     * Display Toast message
     * @param message is for display message
     */
    private static void showToast(Context ct, String message, boolean isError) {
        try {
            LayoutInflater inflater = LayoutInflater.from(ct);
            View layout = inflater.inflate(R.layout.layout_toast_message, null);
            TextView textV = (TextView) layout.findViewById(R.id.lbl_toast);
            if (isError) {
                textV.setBackgroundResource(R.drawable.toast_error_background);
            }
            textV.setText(message);
            Toast toast = new Toast(ct);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Start Showing progress bar.
     */
    public static void showLoading(Context c) {
        if (pdlg == null) {
            pdlg = new ProgressDialog(c, R.style.AppCompatAlertDialogStyle);
//        pdlg.setMessage(s);
            pdlg.setCancelable(false);
            pdlg.show();
            Window window = pdlg.getWindow();
            window.setLayout(1000, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    /**
     * Stop progress bar.
     */
    public static void closeLoading() {
        if (pdlg != null) {
            pdlg.dismiss();
            pdlg = null;
        }
    }

    /**
     * checks whether email address is valid or not
     *
     * @param input email address to check
     */
    public static boolean isEmailValid(CharSequence input) {
        if (input == null)
            return false;
        Pattern pattern = Pattern.compile(EMAIL);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    /**
     * Method to hide keyboard
     */
    public static void hideSoftInput(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Displays the dialog box for confirmation
     *
     * @param title              for display title message
     * @param msg                for display dialog message
     * @param btnPositiveTitle   for display positive button text
     * @param btnNegativeTitle   for display negative button text
     * @param onPositiveBtnClick is click listener for click event
     */
    public static void showConfirmationDialog(final Context context, String title, String msg
            , String btnPositiveTitle, String btnNegativeTitle,
                                              final DialogInterface.OnClickListener onPositiveBtnClick,
                                              final DialogInterface.OnClickListener onNegativeBtnClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(true);
        builder.setPositiveButton(
                btnPositiveTitle,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (onPositiveBtnClick != null) {
                            onPositiveBtnClick.onClick(dialog, id);
                        } else {
                            dialog.dismiss();
                        }
                    }
                });

        builder.setNegativeButton(
                btnNegativeTitle,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (onNegativeBtnClick != null) {
                            onNegativeBtnClick.onClick(dialog, id);
                        } else {
                            dialog.dismiss();
                        }
                    }
                });

        final AlertDialog alert = builder.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                button.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                button.setTextColor(context.getResources().getColor(R.color.red));
                button.setAllCaps(false);
                button = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                button.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                button.setTextColor(Color.BLACK);
                button.setAllCaps(false);
            }
        });
        alert.show();
    }

    /**
     * Convert object into string using @{@link Gson}
     *
     * @param object to convert into string
     * @return string from object with gson
     */

    public static String getStringFromObject(Object object) {

        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    /**
     * Convert String into Object using @{@link Gson}
     *
     * @param mListStr to convert into string
     * @return object from string with gson
     */

    public static Object getObjectListFromString(String mListStr) {
        Object obj = null;
        Type type = new TypeToken<Object>() {
        }.getType();
        obj = new Gson().fromJson(mListStr, type);
        return obj;
    }

    /** Method to get Path from URI
     * @param contentURI to convert into string
     * @return String path from URI
     */
    public static String getRealPathFromURI(Uri contentURI, Context act) {
        String result;
        Cursor cursor = act.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    /**
     * Method to start native camera intent to capture image
     * @return URI
     */

    public static Uri openCamera(Activity activity) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri dataUri = null;
        try {
            if (Build.VERSION.SDK_INT > M) {
                dataUri = FileProvider.getUriForFile(activity,
                        BuildConfig.APPLICATION_ID + ".provider",
                        createImageFile());
            } else
                dataUri = Uri.fromFile(createImageFile());

        } catch (IOException e) {
            e.printStackTrace();
        }
//        Uri file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, dataUri);
        activity.startActivityForResult(intent, 1);
        return dataUri;
    }

    /**
     * Method to start Gallery intent to select image
     */
    public static void openGallery(Activity activity) {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, 2);
    }

    /**
     * method to save image file in application directory
     * @return @{@link File}
     */
    public static File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera_pictures");
        if (!storageDir.exists()) {
            boolean created = storageDir.mkdirs();
        }
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        log(image.getAbsolutePath());
        return image;
    }


    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }





}