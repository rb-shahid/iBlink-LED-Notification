package com.byteshaft.iblinklednotification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.CompoundButton;
import android.widget.Switch;


public class MainActivity extends ActionBarActivity implements Switch.OnCheckedChangeListener {

    Switch mSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwitch = (Switch) findViewById(R.id.aSwitch);
        mSwitch.setOnCheckedChangeListener(this);

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
                if (mSwitch.isChecked()) {
                    if (FlashlightService.getInstance() == null) {
                        startService(new Intent(this, FlashlightService.class));
                    }
                } else {
                    stopService(new Intent(this, FlashlightService.class));
                }
        }
    }
}
