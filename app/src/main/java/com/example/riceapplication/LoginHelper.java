package com.example.riceapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginHelper {

    Context context;
    SharedPreferences sharedPerfs;
    SharedPreferences.Editor editor;

    // Prefs Keys
    static String perfsName = "UserHelper";
    static int perfsMode = 0;

    public LoginHelper(Context context) {
        this.context = context;
        this.sharedPerfs = this.context.getSharedPreferences(perfsName, perfsMode);
        this.editor = sharedPerfs.edit();
    }

    public void createSession(String sUserName) {
        editor.putBoolean("LoginStatus", true);
        editor.putString("Username", sUserName);
        editor.commit();
    }

    public void deleteSession() {
        editor.clear();
        editor.commit();
    }

    public boolean getLoginStatus() {
        return sharedPerfs.getBoolean("LoginStatus", false);
    }

    public String getUserName() {
        return sharedPerfs.getString("Username", null);
    }

    public String getPassword() {
        return sharedPerfs.getString("Password", null);
    }
}
