package com.mastertemplate.data;

import com.mastertemplate.callback.OnResponse;
import com.mastertemplate.data.api.ApiManager;
import com.mastertemplate.data.api.model.ForgotPasswordResponse;
import com.mastertemplate.data.api.model.LoginResponse;
import com.mastertemplate.data.api.model.RegisterResponse;
import com.mastertemplate.data.api.model.UploadResponse;
import com.mastertemplate.data.prefrence.PreferenceKeys;
import com.mastertemplate.data.prefrence.SharedPreferenceManager;
import com.mastertemplate.data.sql.SQLManager;
import com.mastertemplate.data.sql.table.user.User;
import com.mastertemplate.utils.CommonUtils;

import java.io.File;
import java.util.HashMap;

/**
 * Class contains all the necessary methods to interact with any data set.
 * there are mainly 3 data sources @{@link ApiManager} , @{@link SharedPreferenceManager} or @{@link SQLManager}
 * This class is responsible for deciding data source for required data
 * All the data operations must go through this class.
 */
public class DataManager {

    /**
     * Calls @{@link ApiManager} to initiate Login request
     *
     * @param map        map containing email,password as parameters
     * @param onResponse @{@link OnResponse} listener to call on response received
     */
    public static void logInUser(HashMap<String, String> map, final OnResponse<LoginResponse> onResponse) {
        ApiManager.executeRequest(onResponse, ApiManager.getApiRepository().logInUser(map));
    }

    /**
     * Calls @{@link ApiManager} to initiate register request
     *
     * @param map        map containing email,password as parameters
     * @param onResponse @{@link OnResponse} listener to call on response received
     */
    public static void registerUser(HashMap<String, String> map, final OnResponse<RegisterResponse> onResponse) {
        ApiManager.executeRequest(onResponse, ApiManager.getApiRepository().registerUser(map));
    }

    /**
     * Calls @{@link ApiManager} to initiate Multipart request
     *
     * @param map        map containing email as parameters
     * @param onResponse @{@link OnResponse} listener to call on response received
     */
    public static void uploadFile(HashMap<String, String> paramMap,HashMap<String, File> fileMap, final OnResponse<UploadResponse> onResponse) {
        ApiManager.executeRequest(onResponse, ApiManager.getApiRepository().uploadFile(ApiManager.prepareFilePart(fileMap),paramMap));
    }

    /**
     * Calls @{@link ApiManager} to initiate Forgot password request
     *
     * @param map        map containing email as parameters
     * @param onResponse @{@link OnResponse} listener to call on response received
     */
    public static void forgotPassword(HashMap<String, String> map, final OnResponse<ForgotPasswordResponse> onResponse) {
        ApiManager.executeRequest(onResponse, ApiManager.getApiRepository().forgotPassword(map));
    }



    /**
     * Calls @{@link SQLManager} to store user in @{@link android.database.sqlite.SQLiteDatabase}
     * @param user @{@link User} object to add
     */
    public static void addUser(User user) {
        SQLManager.getUserTable().insertAll(user);
    }

    /**
     * Calls @{@link SharedPreferenceManager} to get access token from @{@link android.content.SharedPreferences}
     * @return current access token
     */
    public static String getAccessToken() {
        return SharedPreferenceManager.getString(PreferenceKeys.ACCESS_TOKEN);
    }

    /**
     * Calls @{@link SharedPreferenceManager} to save access token to @{@link android.content.SharedPreferences}
     * @param accessToken access token to save
     */
    public static void setAccessToken(String accessToken) {
        SharedPreferenceManager.setString(PreferenceKeys.ACCESS_TOKEN, accessToken);
    }

    /**
     * Calls @{@link SharedPreferenceManager} to save permission state to @{@link android.content.SharedPreferences}
     * @param permission permission name from @{@link com.mastertemplate.Manifest.permission}
     *  @param isTrue is permission asked or not
     */
    public static void setPermissionValue(String permission, boolean isTrue) {
        SharedPreferenceManager.setBoolean(permission, isTrue);
    }

    /**
     * Calls @{@link SharedPreferenceManager} to get permission state from @{@link android.content.SharedPreferences}
     * @return current permission state
     */
    public static boolean getPermissionValue(String permission) {
        return SharedPreferenceManager.getBoolean(permission);
    }

    /**
     * Calls @{@link SharedPreferenceManager} to save @{@link LoginResponse} object to @{@link android.content.SharedPreferences}
     * @param loginResponse object of @{@link LoginResponse}
     */
    public static void saveUserData(LoginResponse loginResponse) {
        SharedPreferenceManager.setString(PreferenceKeys.ACCESS_TOKEN, CommonUtils.getStringFromObject(loginResponse));
    }

    /**
     * Calls @{@link SharedPreferenceManager} to get @{@link LoginResponse} object from @{@link android.content.SharedPreferences}
     * @return @{@link LoginResponse} object
     */
    public static LoginResponse getUserData(LoginResponse loginResponse) {
        loginResponse = (LoginResponse) CommonUtils.getObjectListFromString(SharedPreferenceManager.getString(PreferenceKeys.ACCESS_TOKEN));
        return loginResponse;

    }
}