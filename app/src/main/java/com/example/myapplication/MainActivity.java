package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static int click =0;
    private TextView txtcontador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtcontador = findViewById(R.id.click1);
        txtcontador.setText(Integer.toString(click));
    }
    public void tostada(View view){
        Toast.makeText(this, "Mensaje tostada", Toast.LENGTH_LONG).show();
    }
    public void Aumentar(View view){
        click++;
        txtcontador.setText(Integer.toString(click));
    }
    public void Reducir(View view){
        click--;
        txtcontador.setText(Integer.toString(click));
    }
    public void Reset(View view){
        click = 0;
        txtcontador.setText(Integer.toString(click));
    }
}