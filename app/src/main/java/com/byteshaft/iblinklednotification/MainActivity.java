package com.byteshaft.iblinklednotification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends ActionBarActivity implements Switch.OnCheckedChangeListener {
    private Helpers mHelpers;
    private Switch mCallSwitch;
    private Switch mSmsSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelpers = new Helpers(getApplicationContext());
        setContentView(R.layout.activity_main);
        mCallSwitch = (Switch) findViewById(R.id.call_switch);
        mSmsSwitch = (Switch) findViewById(R.id.sms_switch);
        mCallSwitch.setOnCheckedChangeListener(this);
        mSmsSwitch.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.call_switch:
                mHelpers.enableCallBlink(isChecked);
                if (isChecked) {
                    if (FlashlightService.getInstance() == null) {
                        startService(new Intent(this, FlashlightService.class));
                    }
                } else {
                    stopService(new Intent(this, FlashlightService.class));
                }
                mHelpers.saveServiceStateEnabled(isChecked);
                break;
            case R.id.sms_switch:
                mHelpers.enableSmsBlink(isChecked);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCallSwitch.setChecked(mHelpers.isCallBlinkingEnabled());
        mSmsSwitch.setChecked(mHelpers.isSmsBlinkingEnabled());
    }
}
