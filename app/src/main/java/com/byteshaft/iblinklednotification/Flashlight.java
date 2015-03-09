package com.byteshaft.iblinklednotification;

import android.hardware.Camera;

@SuppressWarnings("deprecation")
public class Flashlight extends Thread {

    private Camera mCamera;

    @Override
    public void run() {
        super.run();
        // `isCallIncoming` becomes true as soon as an incoming call
        // notification starts and becomes false when call is picked/dropped.
        // So we can rely on it to do our blinking.
        while (CallStateListener.isCallIncoming) {
            blinkFlash(100, 300);
        }
        // As soon as the incoming call state ends, stop the thread
        // so that the flashlight does not keep blinking.
        interrupt();
        // Also make sure to release any camera resources so that it
        // can be used by other Apps.
        releaseCamera();
    }

    private void blinkFlash(int onPeriod, int offPeriod) {
        turnOnFlash();
        causeSleep(onPeriod);
        turnOffFlash();
        causeSleep(offPeriod);
    }

    private void causeSleep(int delay) {
        try {
            sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void turnOnFlash() {
        if (mCamera == null) {
            openCamera();
        }
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        mCamera.setParameters(parameters);
        mCamera.startPreview();
    }

    private void turnOffFlash() {
        if (mCamera != null) {
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(parameters);
            mCamera.stopPreview();
        }
    }

    private void openCamera() {
        mCamera = Camera.open();
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }
}
