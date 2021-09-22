package com.example.actividad_n2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Actividad2 extends AppCompatActivity {

    TextView N;
    TextView L;
    TextView LP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad2);

        N= (TextView) findViewById(R.id.txtN);
        L= (TextView) findViewById(R.id.txtL);
        LP= (TextView) findViewById(R.id.txtLP);
        Bundle Datos=this.getIntent().getExtras();
        String nombre=Datos.getString("nombre");
        N.setText(""+nombre);

        String Lenguaje=Datos.getString("Lenguajes");
        L.setText(""+Lenguaje);
        String LenguajePreferido=Datos.getString("LenguajePreferido");
        LP.setText(""+LenguajePreferido);
    }
    public void Regresar(View view){
        finish();
    }
}