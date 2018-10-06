package com.example.lathifrdp.demoapp.helper;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.lathifrdp.demoapp.LoginActivity;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "IPBConnectPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_TOKEN = "token";
    public static final String KEY_ID = "_id";
    public static final String KEY_PHOTO = "photo";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_FULLNAME = "fullname";
    public static final String KEY_USERTYPE = "userType";
    public static final String KEY_PRODI = "name";
    // Email address (make variable public to access from outside)
    //public static final String KEY_EMAIL = "email";

    //public static final String KEY_NAMA_PASIEN = "nama_pasien";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String token,String _id,String photo,String email, String fullname, String userType, String name){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        // Storing to pref
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_ID, _id);
        editor.putString(KEY_PHOTO, photo);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_FULLNAME, fullname);
        editor.putString(KEY_USERTYPE, userType);
        editor.putString(KEY_PRODI, name);
        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }
    public String getKeyToken(){
        return pref.getString(KEY_TOKEN,"");
    }
    public String getKeyId(){ return pref.getString(KEY_ID,""); }
    public String getKeyPhoto(){ return pref.getString(KEY_PHOTO,""); }
    public String getKeyEmail(){ return pref.getString(KEY_EMAIL,""); }
    public String getKeyFullname(){ return pref.getString(KEY_FULLNAME,""); }
    public String getKeyUsertype(){ return pref.getString(KEY_USERTYPE,""); }
    public String getKeyProdi(){ return pref.getString(KEY_PRODI,""); }


    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // token
        user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));
        // id
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        user.put(KEY_PHOTO, pref.getString(KEY_PHOTO, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_FULLNAME, pref.getString(KEY_FULLNAME, null));
        user.put(KEY_USERTYPE, pref.getString(KEY_USERTYPE, null));
        user.put(KEY_PRODI, pref.getString(KEY_PRODI, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
