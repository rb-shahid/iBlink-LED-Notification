package com.byteshaft.iblinklednotification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;

public class MainActivity extends ActionBarActivity implements Switch.OnCheckedChangeListener,
        Button.OnClickListener, RadioGroup.OnCheckedChangeListener {
    
    private Helpers mHelpers;
    private Switch mCallSwitch;
    private Switch mSmsSwitch;
    private RadioGroup mPatternGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHelpers = new Helpers(getApplicationContext());
        Button closeButton = (Button) findViewById(R.id.close_button);
        mPatternGroup = (RadioGroup) findViewById(R.id.pattern_group);
        mPatternGroup.setOnCheckedChangeListener(this);
        closeButton.setOnClickListener(this);
        mCallSwitch = (Switch) findViewById(R.id.call_switch);
        mSmsSwitch = (Switch) findViewById(R.id.sms_switch);
        mCallSwitch.setOnCheckedChangeListener(this);
        mSmsSwitch.setOnCheckedChangeListener(this);
        setFinishOnTouchOutside(false);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_button:
                finish();
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.normal_frequency:
                mHelpers.savePatternSetting("normal");
                break;
            case R.id.slow_frequency:
                mHelpers.savePatternSetting("slow");
                break;
            case R.id.fast_frequency:
                mHelpers.savePatternSetting("fast");
                break;
            case R.id.very_fast_frequency:
                mHelpers.savePatternSetting("very_fast");
                break;
        }
    }
}
