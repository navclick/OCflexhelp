package com.example.naveed.ocf.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.example.naveed.ocf.MainActivity.OCFLEXTOKEN;

public class TokenHelper {

    // Declarations
    private final Context context;
    private final SharedPreferences sharedPreferences;

    public TokenHelper(Context context){
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean SetToken(String access_token){
        SharedPreferences.Editor editor = sharedPreferences.edit().putString("token", access_token);
        editor.commit();
        return true;
    }

    public String GetToken(){
        String restoredText = sharedPreferences.getString("token", null);
        return restoredText;
    }

    public boolean removeALL() {
        android.content.SharedPreferences.Editor prefsEditor = sharedPreferences.edit();

        prefsEditor.clear();
        return prefsEditor.commit();
    }


}
