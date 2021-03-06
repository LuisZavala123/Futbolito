package com.example.futbolito;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView Equipo1;
    TextView Equipo2;
    TextView MarcadorEquipo1;
    TextView MarcadorEquipo2;

    ImageView Pelota;

    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;

    int ancho;
    int alto;
    int PuntajeEquipo1;
    int PuntajeEquipo2;

    DisplayMetrics displayMetrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Equipo1 = findViewById(R.id.lblEquipo1);
        Equipo2 = findViewById(R.id.lblEquipo2);
        MarcadorEquipo1 = findViewById(R.id.lblMarcadorequipo1);
        MarcadorEquipo2 = findViewById(R.id.lblMarcadorequipo2);

        Pelota= findViewById(R.id.imgPelota);

        displayMetrics =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        ancho = displayMetrics.widthPixels;
        alto = displayMetrics.heightPixels;

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor =sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        if (sensor==null){
            Toast.makeText(
                    this,
                    "No es posible utilizar el acelerometro del dispositivo.",
                    Toast.LENGTH_LONG)
                    .show();

            finish();
        }


    }
}