package com.mastertemplate.data.prefrence;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.mastertemplate.AppClass.getAppContext;

/**
 * Class contains all the necessary generic methods to save and retrieve data from @{@link SharedPreferences}
 */
public class SharedPreferenceManager {

    /**
     * Get instance of default @{@link SharedPreferences}
     */
    private static SharedPreferences getSharedPreferenceInstance() {
        return PreferenceManager.getDefaultSharedPreferences(getAppContext());
    }

    /**
     * Get instance of default @{@link SharedPreferences} Editor
     */
    public static SharedPreferences.Editor getSharedPreferenceEditor() {
        return getSharedPreferenceInstance().edit();
    }

    /**
     * Get saved string from @{@link SharedPreferences}
     * @param key any string from @{@link PreferenceKeys}
     */
    public static String getString(String key) {
        return getSharedPreferenceInstance().getString(key, "");
    }

    /**
     * Save string to @{@link SharedPreferences}
     * @param key any string from @{@link PreferenceKeys}
     * @param value value to store
     */
    public static void setString(String key, String value) {
        getSharedPreferenceEditor().putString(key, value).commit();
    }

    /**
     * Get saved boolean from @{@link SharedPreferences}
     * @param key any string from @{@link PreferenceKeys}
     */
    public static boolean getBoolean(String key) {
        return getSharedPreferenceInstance().getBoolean(key, false);
    }

    /**
     * Save boolean to @{@link SharedPreferences}
     * @param key any string from @{@link PreferenceKeys}
     * @param value value to store
     */
    public static void setBoolean(String key, boolean value) {
        getSharedPreferenceEditor().putBoolean(key, value).commit();
    }

    /**
     * Get saved integer from @{@link SharedPreferences}
     * @param key any string from @{@link PreferenceKeys}
     */
    public static int getInt(String key) {
        return getSharedPreferenceInstance().getInt(key, 0);
    }

    /**
     * save integer to @{@link SharedPreferences}
     * @param key any string from @{@link PreferenceKeys}
     * @param value value to store
     */
    public static void setInt(String key, int value) {
        getSharedPreferenceEditor().putInt(key, value).commit();
    }

}