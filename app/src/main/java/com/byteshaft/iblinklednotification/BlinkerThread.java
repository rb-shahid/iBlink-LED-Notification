package com.byteshaft.iblinklednotification;

import java.util.Timer;
import java.util.TimerTask;

public class BlinkerThread {

    private com.byteshaft.ezflashlight.Flashlight flashlight;

    public BlinkerThread(com.byteshaft.ezflashlight.Flashlight flashlight) {
        this.flashlight = flashlight;
    }

    void blink() {
        Timer mTimer = new Timer();
        // `isCallIncoming` becomes true as soon as an incoming call
        // notification starts and becomes false when call is picked/dropped.
        // So we can rely on it to do our blinking.
        flashlight.turnOn();
        mTimer.schedule(getFlashlightOffTimerTask(), 150);
        mTimer.schedule(getFlashlightOnTimerTask(), 150 + 300);
        mTimer.schedule(getFlashlightOffTimerTask(), 150 + 300 + 500);
        
//        while (CallStateListener.isCallIncoming) {
//            blinkFlash(150, 300);
//        }
        // Make sure to release any camera resources so that it
        // can be used by other Apps.
        flashlight.releaseAllResources();
    }

//    private void blinkFlash(int onPeriod, int offPeriod) {
//        flashlight.turnOn();
//        causeSleep(onPeriod);
//        flashlight.turnOff();
//        causeSleep(offPeriod);
//        flashlight.turnOn();
//        causeSleep(500);
//        flashlight.turnOff();
//    }

    private TimerTask getFlashlightOffTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                flashlight.turnOff();
            }
        };
    }

    private TimerTask getFlashlightOnTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                flashlight.turnOn();
            }
        };
    }


}
