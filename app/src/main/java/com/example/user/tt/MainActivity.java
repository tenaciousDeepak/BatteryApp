package com.example.user.tt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


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

        int batteryPct = level * 100 / scale;

        TextView tv = findViewById(R.id.batteryText);
        tv.setText(batteryPct + "%");
        TextView tv1 = findViewById(R.id.chargeState);

        if (isCharging == true) {

            if (usbCharge == true) {
                tv1.setText("Charging Over USB");
            }

            else if (acCharge == true) {
                tv1.setText("Charging Over AC ");
            }

        }
        else {
            if(batteryPct == 100)
                tv1.setText("Battery Full");
            else
                tv1.setText("Not Charging     ");
        }

        ProgressBar pv = findViewById(R.id.progressBar2);
        pv.setProgress(batteryPct);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {

            Intent intent = getIntent();
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish();
            overridePendingTransition(0, 0);
            startActivity(intent);
        }


//        final Handler handler = new Handler();
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = getIntent();
//                overridePendingTransition(0, 0);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                finish();
//                overridePendingTransition(0, 0);
//                startActivity(intent);
//                Log.i("Runnable has Run!", "a second must have passed");
//
//                handler.postDelayed(this, 1000);
//            }
//        };
//        handler.post(runnable);
        return super.onOptionsItemSelected(item);

        /*final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.i("Runnable has Run!","a second must have passed");
                handler.postDelayed(this,1000);
            }
        };
        handler.post(runnable);*/
    }

    }
