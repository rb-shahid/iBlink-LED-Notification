package com.byteshaft.iblinklednotification;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.preference.PreferenceManager;

import com.byteshaft.ezflashlight.*;
import com.byteshaft.ezflashlight.Flashlight;

public class SmsBlinking extends BroadcastReceiver implements CameraStateChangeListener {

    com.byteshaft.ezflashlight.Flashlight flashlight;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isSmsBlinkingEnabled(context)) {
            flashlight = new Flashlight(context);
            flashlight.setOnCameraStateChangedListener(this);
            flashlight.initializeCamera();
        }
    }

    public void blinkingMode() {
        flashlight.turnOn();
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flashlight.turnOff();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flashlight.turnOn();
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flashlight.releaseAllResources();
    }

    private boolean isSmsBlinkingEnabled(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean("smsblink", false);
    }

    @Override
    public void onCameraOpened() {

    }

    @Override
    public void onCameraViewSetup() {
        blinkingMode();
    }

    @Override
    public void onCameraBusy() {

    }

    @Override
    public void onFlashlightTurnedOn() {

    }

    @Override
    public void onFlashlightTurnedOff() {

    }
}