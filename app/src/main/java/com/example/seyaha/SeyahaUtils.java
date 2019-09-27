package com.example.seyaha;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;


public class SeyahaUtils {


    public static Boolean checkInternetConnectivity(Activity context) {
        ConnectionDetector connectionCheck = new ConnectionDetector(context);

        if (connectionCheck.isConnectingToInternet()) {

            return true;
        }
        return false;
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static void saveLocale(String lang, Activity context) {
        String langPref = "Language";
        SharedPreferences prefs = context.getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
    }


    public static void loadLocale(Activity context) {

        String language = getLocale(context);
        Locale myLocale = new Locale(language);
        Locale.setDefault(myLocale);
        Configuration config = new Configuration();
        config.locale = myLocale;
        context.getBaseContext()
                .getResources()
                .updateConfiguration(
                        config,
                        context.getBaseContext().getResources()
                                .getDisplayMetrics());
    }

    public static String getLocale(Activity context) {
        String langPref = "Language";

        SharedPreferences prefs = context.getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        return prefs.getString(langPref, "0");
    }

}
