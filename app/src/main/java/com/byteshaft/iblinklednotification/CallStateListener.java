package com.byteshaft.iblinklednotification;


import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class CallStateListener extends PhoneStateListener {

    public static boolean isCallIncoming = false;
    private static Flashlight flashlight = null;

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);
        // We want the flashlight thread object to be created
        // only once, that's why declare it a static and only
        // initialize Flashlight if the object is null.
        if (flashlight == null) {
            flashlight = new Flashlight();
        }
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                isCallIncoming = true;
                flashlight.start();
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                // The loop in Flashlight.java will just break if this
                // variable is set to false.
                isCallIncoming = false;
                // Make sure to destroy the thread by setting it null.
                flashlight = null;
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                isCallIncoming = false;
                flashlight = null;
                break;
        }
    }
}
