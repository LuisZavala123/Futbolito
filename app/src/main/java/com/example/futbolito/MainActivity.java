package com.example.futbolito;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
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

        sensorEventListener = new SensorEventListener(){
        @Override
            public void onSensorChanged(SensorEvent Event){
                float x = Event.values[0];
                float y = Event.values[1];
                float z = Event.values[2];

                if (x<-1 && Pelota.getX()<ancho-Pelota.getWidth()){
                        Pelota.setX(Pelota.getX()+5);
                }else if (x>1 && Pelota.getX()>1){
                    Pelota.setX(Pelota.getX()-5);
                }

                if (y<-1){
                    if(Pelota.getY()>0){
                        Pelota.setY(Pelota.getY()-5);
                    }else if(Pelota.getX()>(ancho/2)-30&&Pelota.getX()<(ancho/2)+30){
                        Puntaje(2);
                    }else{
                        Puntaje(0);
                    }
                }else if (y>1){
                    if(Pelota.getY()<ancho-Pelota.getHeight()+625){
                        Pelota.setY(Pelota.getY()+5);
                    }else if(Pelota.getX()>(ancho/2)-30&&Pelota.getX()<(ancho/2)+30){
                        Puntaje(1);
                    }else{
                        Puntaje(0);
                    }
                }

                if(z<-1){
                    Pelota.setMaxHeight(100);
                    Pelota.setMaxWidth(100);
                }else if(z>1){
                    Pelota.setMaxHeight(100);
                    Pelota.setMaxWidth(100);
                }

        }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };

        sensorManager.registerListener(sensorEventListener,sensor,sensorManager.SENSOR_DELAY_FASTEST);

    }

    private void Puntaje(int equipo){
        switch (equipo){
            case 1:
                PuntajeEquipo1++;
                MarcadorEquipo1.setText(String.valueOf(PuntajeEquipo1));
                break;
            case 2:
                PuntajeEquipo2++;
                MarcadorEquipo2.setText(String.valueOf(PuntajeEquipo2));
                break;
        }
        Pelota.setX(ancho/2);
        Pelota.setY(alto/2);
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onPause(){
        sensorManager.unregisterListener(sensorEventListener);
        super.onPause();
    }

    @Override
    protected void onResume(){
        sensorManager.registerListener(sensorEventListener,sensor,sensorManager.SENSOR_DELAY_FASTEST);
        super.onResume();
        Puntaje(0);
    }

}