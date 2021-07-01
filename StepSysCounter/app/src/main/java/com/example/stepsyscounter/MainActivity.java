package com.example.stepsyscounter;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    public static final String EXTRA_MESSAGE = "com.example.android.twoactivities.extra.MESSAGE";
    private TextView stepCounter;
    private SensorManager sensorManager;
    private Sensor sensor;
    private BackgroundService _service;
    private Intent _serviceIntent;
    Handler eventHandler = new Handler();
    int stepCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        stepCounter = findViewById(R.id.stepCounter);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null){
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        }
        else{
            stepCounter.setText("Counter sensor is not present");
        }

        _service = new BackgroundService();
        _serviceIntent = new Intent(this, BackgroundService.class);
        if(!isMyServiceRunning(_service.getClass()))
        {
            startService(_serviceIntent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (sensor != null) {
            sensorManager.registerListener(this, sensor,
                    Sensor.TYPE_STEP_COUNTER);
        }


    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("Service status", "Running");
                return true;
            }
        }
        Log.i ("Service status", "Not running");
        return false;
    }

    private Runnable eventController = new Runnable()
    {
        public void run()
        {
            eventHandler.postDelayed(this, stepCount);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor == sensor){
            stepCount = (int) event.values[0];
            stepCounter.setText(String.valueOf(stepCount));


            _serviceIntent.putExtra("steps", stepCount);
            startService(_serviceIntent);
            eventHandler.postDelayed(eventController, 0);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume(){
        super.onResume();
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null){
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();

        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null){
            sensorManager.unregisterListener(this, sensor);
        }
    }

    public void launchSecondActivity(View view) {
        Intent intent = new Intent(this, HealthActivity.class);
        String data = stepCounter.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, data);
        startActivity(intent);
    }

    public void launchAchievementsActivity(View view) {
        Intent intent = new Intent(this, Achievements.class);
        String data = stepCounter.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, data);
        startActivity(intent);
    }
}