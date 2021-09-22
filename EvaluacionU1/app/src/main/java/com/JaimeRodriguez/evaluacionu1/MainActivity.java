package com.JaimeRodriguez.evaluacionu1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText mensajeConfirmar;
private TextView txtNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Confirmar(View view){
        mensajeConfirmar = findViewById(R.id.txtNombre);
        String mensaje = mensajeConfirmar.getText().toString();
        Toast.makeText(this, mensaje +", Pedido Confirmado!", Toast.LENGTH_LONG).show();
    }
}
