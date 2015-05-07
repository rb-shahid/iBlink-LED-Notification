package com.byteshaft.iblinklednotification;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Helpers extends ContextWrapper{
    public Helpers(Context base) {
        super(base);
    }

    boolean isSmsBlinkingEnabled(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean("smsblink", false);
    }
}
