package com.example.actividad_n2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String nombre;
    private String Leng;
    private String LengPref;
    private TextView txtNombre;
    private TextView txtApellido;
    private CheckBox chekJava;
    private CheckBox chekPHP;
    private CheckBox chekPhyton;
    private CheckBox chekCchar;
    private CheckBox chekCMM;
    private RadioGroup radioGroup1;
    private RadioButton seleccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtNombre = (TextView) findViewById(R.id.txtNombre);
        txtApellido = (TextView) findViewById(R.id.txtApellido);
        radioGroup1 = (RadioGroup) findViewById(R.id.RadioGroup);
        chekJava = (CheckBox) findViewById(R.id.chekJava);
        chekPHP = (CheckBox) findViewById(R.id.chekPHP);
        chekPhyton = (CheckBox) findViewById(R.id.chekPhyton);
        chekCchar = (CheckBox) findViewById(R.id.chekCchar);
        chekCMM = (CheckBox) findViewById(R.id.chekCMM);
    }
    public void MostrarDatos(View view){

        Intent intent = new Intent(MainActivity.this,Actividad2.class);
        nombre = txtNombre.getText()+ " " + txtApellido.getText();
        intent.putExtra("nombre", nombre);
        Leng = "";
        AddLenguajes(chekJava);
        AddLenguajes(chekPHP);
        AddLenguajes(chekPhyton);
        AddLenguajes(chekCchar);
        AddLenguajes(chekCMM);
        intent.putExtra("Lenguajes", Leng);
        seleccion = (RadioButton) findViewById(radioGroup1.getCheckedRadioButtonId());
        intent.putExtra("LenguajePreferido", seleccion.getText().toString());
        startActivity(intent);
    }
    private void AddLenguajes(CheckBox check){
        if (check.isChecked()){
            if(Leng != ""){
                Leng += ", " + check.getText().toString();
            }else {
                Leng = check.getText().toString();
            }
        }
    }
}