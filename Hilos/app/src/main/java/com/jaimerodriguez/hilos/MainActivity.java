package com.jaimerodriguez.hilos;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private ProgressBar progressBar;
    private TareaAsyncrona tareaAsyncrona;

    private TextView txtS1;
    private TextView txtS2;
    private SensorManager sensorManager;
    private Sensor sensor1, sensor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        txtS1 = (TextView)  findViewById(R.id.txt_S1);
        txtS2 = (TextView)  findViewById(R.id.txt_S2);
        sensorManager =(SensorManager) getSystemService(SENSOR_SERVICE);


        sensor1 = (Sensor) sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(this, sensor1, sensorManager.SENSOR_DELAY_NORMAL);

        sensor2= (Sensor) sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorManager.registerListener(this, sensor2, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void llamarDormir(View view) {
        Toast.makeText(this, "Iniciando Sleep", Toast.LENGTH_SHORT).show();
        for (int i = 0; i <= 10; i++) {
            adormir();

        }
        Toast.makeText(this, "Termino Sleep", Toast.LENGTH_SHORT).show();
    }

    private void adormir() {
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void PrimerHilo(View view){
        Toast.makeText(this,"Iniciando Nuevo Hilo", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 10; i++) {
                    adormir();

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Finalizando Nuevo Hilo", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
        Toast.makeText(this, "Fin Nuevo Hilo ?", Toast.LENGTH_SHORT).show();

    }


    public void segundoHilo(View view){
        Toast.makeText(this,"Iniciando Segundo Hilo", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 10; i++) {
                    adormir();

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Finalizando Segundo Hilo", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        }).start();
        Toast.makeText(this, "Fin Segundo Hilo ?", Toast.LENGTH_SHORT).show();

    }

    public void tercerHilo(View view){
        Toast.makeText(this,"Iniciando Ultimo Hilo", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 10; i++) {
                    adormir();

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Finalizando Tercer Hilo", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
        Toast.makeText(this, "Fin Tercer Hilo ?", Toast.LENGTH_SHORT).show();
    }
    public void tareaasincrona (View v){
        if(tareaAsyncrona ==null){
        Toast.makeText(this, "iniciando tarea asincrona.", Toast.LENGTH_SHORT).show();
        tareaAsyncrona = new TareaAsyncrona(1, 10);
        tareaAsyncrona.execute();
        Toast.makeText(this, "Fin(llamada) tarea asincrona.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Estado :"+ tareaAsyncrona.getStatus().name(), Toast.LENGTH_SHORT).show();
            tareaAsyncrona.cancel(true);
            tareaAsyncrona = null;
        }
    }

    private class TareaAsyncrona extends AsyncTask<Void, Integer, Boolean>{
        private int desde, hasta;
        public TareaAsyncrona(int desde, int hasta) {
            super();
            this.desde = desde;
            this.hasta = hasta;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setProgress(0);

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Toast.makeText(getApplicationContext(), "Fin de tarea asincrona", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.incrementProgressBy(values[0].intValue());
        }

        @Override
        protected void onCancelled(Boolean aBoolean) {
            super.onCancelled(aBoolean);
            Log.i("Informacion", "onCancelled(Boolean aBoolean)");
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.i("Informacion", "onCancelled()");
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            for(int i=desde; i<=hasta; i++){
                adormir();
                publishProgress(1);
            }
            return null;

        }
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch(sensorEvent.sensor.getType()){
            case Sensor.TYPE_PROXIMITY:
                txtS1.setText(String.format("%.0f", sensorEvent.values[0]));
                break;
            case Sensor.TYPE_GRAVITY:
                txtS2.setText(String.format("%.0f", sensorEvent.values[0]));
                break;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}