package com.example.doodlz;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class SensorEventListenerHelper implements SensorEventListener {
    private static SensorEventListenerHelper instance;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ShakeListener shakeListener;
    private boolean isRunning = false;
    private static final float SHAKE_THRESHOLD = 15f;

    public interface ShakeListener { void onShake(); }

    private SensorEventListenerHelper(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    public static synchronized SensorEventListenerHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SensorEventListenerHelper(context.getApplicationContext());
        }
        return instance;
    }

    public void setShakeListener(ShakeListener listener) {
        shakeListener = listener;
    }

    public void start() {
        if (accelerometer != null && !isRunning) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            isRunning = true;
        }
    }

    public void stop() {
        if (accelerometer != null && isRunning) {
            sensorManager.unregisterListener(this);
            isRunning = false;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        double acceleration = Math.sqrt(x * x + y * y + z * z);
        Log.i("teste", x + "/"+y+"/"+z);
        if (acceleration > SHAKE_THRESHOLD && shakeListener != null) {
            shakeListener.onShake();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }
}
