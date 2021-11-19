package com.jaimerodriguez.prueba_unidad_n2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AgregarSensor extends AppCompatActivity{
    private Button btnAgregar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_sensor2);
        btnAgregar = (Button) findViewById(R.id.SensorLuz);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), DetalleSensor.class);
                intent.setAction("ADD");
                startActivity(intent);


            }
        });
    }

}