package com.byteshaft.iblinklednotification;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.widget.CompoundButton;
import android.widget.Switch;


public class MainActivity extends ActionBarActivity implements Switch.OnCheckedChangeListener {

    Switch mSwitch;
    Switch mSwitch1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwitch = (Switch) findViewById(R.id.aSwitch);
        mSwitch1 = (Switch) findViewById(R.id.aSwitch1);
        mSwitch.setOnCheckedChangeListener(this);
        mSwitch1.setOnCheckedChangeListener(this);

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (FlashlightService.getInstance() != null) {
            mSwitch.setChecked(true);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.aSwitch:
                if (isChecked) {
                    if (FlashlightService.getInstance() == null) {
                        startService(new Intent(this, FlashlightService.class));
                        saveServiceStateEnabled(true);
                    }
                } else {
                    stopService(new Intent(this, FlashlightService.class));
                    saveServiceStateEnabled(false);
                }
                break;
            case R.id.aSwitch1:
                if (isChecked) {
                    enableSmsBlink(true);
                } else {
                    enableSmsBlink(false);
                }
        }
    }

    private void saveServiceStateEnabled(boolean ENABLED) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putBoolean("enabled", ENABLED).apply();
    }

    private void enableSmsBlink(boolean enable) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putBoolean("smsblink", enable).apply();
    }

}
