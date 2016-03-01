package com.example.sturmgewehr44.democrazy;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;

/**
 * Created by Sturmgewehr44 on 3/1/16.
 */
public class ShakeReceptor implements SensorEventListener {

    private static final float SHAKE_THRESHOLD_GRAVITY = .1F;

    private OnShakeListener mListener;
    private long mShakeTimestamp;
    private int mShakeCount;

    public void setOnShakeListener(OnShakeListener listener) {
        this.mListener = listener;
    }

    public interface OnShakeListener {
        public void onShake(int count);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onSensorChanged(SensorEvent event) {
        System.out.println("null son");
        if (mListener != null) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            System.out.println(x);
            System.out.println(y);
            System.out.println(z);

            if (x + y + z > .1F) {
                System.out.println("doge");
                mListener.onShake(10);
            }
        }

    }
}
