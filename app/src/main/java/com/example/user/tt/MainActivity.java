package com.example.user.tt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.i("Runnable has Run!", "a second must have passed");
                handler.postDelayed(this, 1000);

                IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                Intent batteryStatus = registerReceiver(null, ifilter);

                int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

                int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING;

// How are we charging?
                int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
                boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
                boolean wirelessCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_WIRELESS;

                int batteryPct = level * 100 / scale;

                TextView tv = findViewById(R.id.batteryText);
                tv.setText(batteryPct + "%");
                TextView tv1 = findViewById(R.id.chargeState);

                if (isCharging == true && batteryPct!=100) {

                    if (usbCharge == true) {
                        tv1.setText("Charging Over USB");
                    }
                    if (acCharge == true) {
                        tv1.setText("Charging Over AC ");
                    }
                    if (wirelessCharge==true)
                        tv1.setText("Charging Over Wireless");

                } else {
                    if (batteryPct == 100)
                        tv1.setText("Battery Full");
                    else
                        tv1.setText("Not Charging     ");
                }

                ProgressBar pv = findViewById(R.id.progressBar2);
                pv.setProgress(batteryPct);
            }
        };
        handler.post(runnable);
    }
}


