/**
 *
 * Copyright 2019 paolo mococci
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package local.example.frog;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static android.hardware.SensorManager.AXIS_MINUS_X;
import static android.hardware.SensorManager.AXIS_MINUS_Y;
import static android.hardware.SensorManager.AXIS_X;
import static android.hardware.SensorManager.AXIS_Y;
import static android.hardware.SensorManager.SENSOR_DELAY_NORMAL;
import static android.hardware.SensorManager.getOrientation;
import static android.hardware.SensorManager.getRotationMatrix;
import static android.hardware.SensorManager.remapCoordinateSystem;
import static local.example.frog.R.id;
import static local.example.frog.R.layout;
import static local.example.frog.R.string.float_format;

public class MainActivity
        extends AppCompatActivity
        implements SensorEventListener {

    private SensorManager sensorManager;

    private Sensor sensorAccelerometer;
    private Sensor sensorMagnetometer;

    private TextView textSensorAzimuth;
    private TextView textSensorPitch;
    private TextView textSensorRoll;

    private ImageView dotTop;
    private ImageView dotBottom;
    private ImageView dotLeft;
    private ImageView dotRight;

    private Display display;

    private float[] accelerometerData = new float[3];
    private float[] magnetometerData = new float[3];
    private static final float VALUE_DRIFT = 0.005F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        textSensorAzimuth = findViewById(id.value_azimuth);
        textSensorPitch = findViewById(id.value_pitch);
        textSensorRoll = findViewById(id.value_roll);

        dotTop = findViewById(id.dot_top);
        dotBottom = findViewById(id.dot_bottom);
        dotLeft = findViewById(id.dot_left);
        dotRight = findViewById(id.dot_right);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMagnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sensorAccelerometer != null) {
            sensorManager
                    .registerListener(this, sensorAccelerometer, SENSOR_DELAY_NORMAL);
        }
        if (sensorMagnetometer != null) {
            sensorManager
                    .registerListener(this, sensorMagnetometer, SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();

        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                accelerometerData = sensorEvent.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                magnetometerData = sensorEvent.values.clone();
                break;
            default:
                return;
        }
        float[] rotationMatrix = new float[9];
        boolean rotationIsOk = getRotationMatrix(
                rotationMatrix,
                null, accelerometerData,
                magnetometerData
        );

        float[] rotationMatrixAdjusted = new float[9];
        switch (display.getRotation()) {
            case Surface.ROTATION_0:
                rotationMatrixAdjusted = rotationMatrix.clone();
                break;
            case Surface.ROTATION_90:
                remapCoordinateSystem(
                        rotationMatrix,
                        AXIS_Y,
                        AXIS_MINUS_X,
                        rotationMatrixAdjusted
                );
                break;
            case Surface.ROTATION_180:
                remapCoordinateSystem(
                        rotationMatrix,
                        AXIS_MINUS_X,
                        AXIS_MINUS_Y,
                        rotationMatrixAdjusted
                );
                break;
            case Surface.ROTATION_270:
                remapCoordinateSystem(
                        rotationMatrix,
                        AXIS_MINUS_Y,
                        AXIS_X,
                        rotationMatrixAdjusted
                );
                break;
        }

        float[] orientationValues = new float[3];
        if (rotationIsOk) {
            getOrientation(rotationMatrixAdjusted, orientationValues);
        }

        float azimuth = orientationValues[0];
        float pitch = orientationValues[1];
        float roll = orientationValues[2];

        if (Math.abs(pitch) < VALUE_DRIFT) {
            pitch = 0;
        }
        if (Math.abs(roll) < VALUE_DRIFT) {
            roll = 0;
        }

        textSensorAzimuth.setText(getResources()
                .getString(float_format, azimuth));
        textSensorPitch.setText(getResources()
                .getString(float_format, pitch));
        textSensorRoll.setText(getResources()
                .getString(float_format, roll));

        dotTop.setAlpha(0f);
        dotBottom.setAlpha(0f);
        dotLeft.setAlpha(0f);
        dotRight.setAlpha(0f);

        if (pitch > 0) {
            dotBottom.setAlpha(pitch);
        } else {
            dotTop.setAlpha(Math.abs(pitch));
        }
        if (roll > 0) {
            dotLeft.setAlpha(roll);
        } else {
            dotRight.setAlpha(Math.abs(roll));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
