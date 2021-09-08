package com.example.ciclo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.println(Log.INFO,"INFORMACION", "Metodo onStart()");
    }
    @Override
    protected void onPostResume(){
        super.onPostResume();
        Log.println(Log.INFO,"INFORMACION", "Metodo onPostResume()");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.println(Log.INFO, "INFORMACION", "Metodo onStop()!");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.println(Log.INFO, "INFORMACION", "Metodo onDestroy()!");
    }
}