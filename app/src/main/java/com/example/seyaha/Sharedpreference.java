package com.example.seyaha;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Sharedpreference {
    SharedPreferences.Editor editor;
    SharedPreferences prefs;

    public Sharedpreference(Context context, String P_KEY) {
        prefs = context.getSharedPreferences(P_KEY,
                Activity.MODE_PRIVATE);

    }

    public void setboolPrefs(String Child_value, Boolean Value) {
        editor = prefs.edit();
        editor.putBoolean(Child_value, Value);
        editor.commit();
    }

    public Boolean getboolPrefs(String Child_value, Boolean Default) {
        return prefs.getBoolean(Child_value, Default);

    }

    public void setintPrefs(String Child_value, int Value) {
        editor = prefs.edit();
        editor.putInt(Child_value, Value);
        editor.commit();
    }

    public Integer getintPrefs(String Child_value, int Default) {
        return prefs.getInt(Child_value, Default);

    }

    public void setstringPrefs(String Child_value, String Value) {
        editor = prefs.edit();
        editor.putString(Child_value, Value);
        editor.commit();
    }

    public String getStrigPrefs(String Child_value, String Default) {
        return prefs.getString(Child_value, Default);

    }

}
