package com.jaimerodriguez.prueba_unidad_n2;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class DetalleSensor extends AppCompatActivity implements SensorEventListener{

    private TextView etIdSensor, etNomSensor, etValorSensor, etFechaSensor, etCampoObservacion;
    private Button btnGuardar;
    private TextView txtProximidad;
    private TextView txtLuz;

    private SensorManager sensorManager;

    private Sensor sensorP, sensorLZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_sensores);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("DetalleSensor");
        }


        etIdSensor = findViewById(R.id.et_id_sensor2);
        etNomSensor = findViewById(R.id.et_nom_sensor2);
        etFechaSensor = findViewById(R.id.et_fecha2);
        etCampoObservacion =findViewById(R.id.et_Campo_de_Observacion2);
        etValorSensor = findViewById(R.id.et_valor_sensor2);

        switch (getIntent().getAction().toUpperCase(Locale.ROOT)) {
            case "VIEW":
                etIdSensor.setFocusable(false);
                etNomSensor.setFocusable(false);
                etValorSensor.setFocusable(false);
                etCampoObservacion.setFocusable(false);
                etFechaSensor.setFocusable(false);
            case "EDIT":
                etIdSensor.setText(String.format("%d", getIntent().getIntExtra("id_sensor",0)));
                etNomSensor.setText(getIntent().getStringExtra("nom_sensor"));
                etCampoObservacion.setText(getIntent().getStringExtra("campo_observacion"));


                break;
            case "ADD":
                etIdSensor.setText("");
                etNomSensor.setText("");
                etValorSensor.setText("");
                etCampoObservacion.setText("");
                etFechaSensor.setText("");
                getSupportActionBar().setTitle("Nuevo sensor");
                break;
        }
        btnGuardar = findViewById(R.id.btn_guardar2);
        btnGuardar.setVisibility(View.VISIBLE);
        txtProximidad = (TextView)  findViewById(R.id.et_valor_sensor2);

        sensorManager =(SensorManager) getSystemService(SENSOR_SERVICE);

        sensorP = (Sensor) sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener((SensorEventListener) this, sensorP, SensorManager.SENSOR_DELAY_NORMAL);

        sensorLZ= (Sensor) sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener((SensorEventListener) this, sensorLZ, SensorManager.SENSOR_DELAY_NORMAL);


    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch(sensorEvent.sensor.getType()){
            case Sensor.TYPE_PROXIMITY:
                txtProximidad.setText(String.format("%.0f", sensorEvent.values[0]));
                break;
        }
    }
    public void guardar (View v) {

        SQLITE myDBSQLITE = new SQLITE(DetalleSensor.this,"SensoresDB", null,1);
        SQLiteDatabase SensorDB = myDBSQLITE.getWritableDatabase();

        String id = etIdSensor.getText().toString();
        String nomSensor= etNomSensor.getText().toString();
        String valorSensor = etValorSensor.getText().toString();
        String campoObservacion = etCampoObservacion.getText().toString();
        String fecha = etFechaSensor.getText().toString();


        if(!nomSensor.isEmpty() && !valorSensor.isEmpty()) {

            ContentValues rowSensores = new ContentValues();
            rowSensores.put("id_sensor", id);
            rowSensores.put("nom_sensor", nomSensor);
            rowSensores.put("fecha_sensor", fecha);
            rowSensores.put("campo_observacion", campoObservacion);
            rowSensores.put("valor_sensor", valorSensor);

                if(getIntent().getAction().equals("ADD")) {
                    long idx = SensorDB.insert("Sensores", null, rowSensores);
                    etIdSensor.setText(Long.toString(idx + 1));
                    etNomSensor.setText(""+"Sensor de Proximidad");
                    etFechaSensor.setText("");
                    etCampoObservacion.setText("");
                    etValorSensor.setText(" "+ txtProximidad);


                } else {
                    SensorDB.update("Sensores",rowSensores,"id_sensor=?",new String[]{id});
                    Toast.makeText(getBaseContext(),"Datos guardados!",Toast.LENGTH_SHORT).show();

                }
                Toast.makeText(getBaseContext(),"Datos almacenados!", Toast.LENGTH_LONG).show();
            SensorDB.close();

        } else {
            Toast.makeText(getApplicationContext(),"ERROR: falta uno o mas datos!", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_1, menu);
        if (getIntent().getAction().equals("ADD")) {
            for (int i = 0; i < menu.size(); i++)
                menu.getItem(i).setVisible(false);
        }
        return true;
    }

    private void eliminar()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Eliminar!")
                .setMessage("Eliminar sensor actual?")
                .setIcon(android.R.drawable.ic_delete)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        SQLITE myDBSQLITE = new SQLITE(DetalleSensor.this, "SensoresDB", null, 1);
                        SQLiteDatabase SensorDB = myDBSQLITE.getWritableDatabase();
                        SensorDB.delete("Sensores", "id_sensor=?",new String[]{etIdSensor.getText().toString()});
                        Toast.makeText(getBaseContext(), "Registro de sensor eliminado!",Toast.LENGTH_LONG).show();
                        onBackPressed();

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {dialog.dismiss(); }
                })
                .create();
        alertDialog.show();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_edit:
                etIdSensor.setFocusable(false);
                etNomSensor.setFocusable(false);
                etNomSensor.setFocusableInTouchMode(true);
                etNomSensor.setSelectAllOnFocus(true);
                etNomSensor.requestFocus();
                etFechaSensor.setFocusable(false);
                etCampoObservacion.setFocusable(false);
                etCampoObservacion.setFocusableInTouchMode(true);
                etCampoObservacion.setSelectAllOnFocus(true);
                etCampoObservacion.requestFocus();
                etValorSensor.setFocusable(false);
                break;
            case R.id.menu_item_delete:
                eliminar();
                break;
            case R.id.home:
                onBackPressed();
                break;
            default:
                throw new IllegalStateException("Unexpected value:"+ item.getItemId());
        }
        return true;
    }
}
