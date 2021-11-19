package com.jaimerodriguez.prueba_unidad_n2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jaimerodriguez.prueba_unidad_n2.modelo.Sensores;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnAgregar, btnMapa;
    private ArrayAdapter<Sensores> arrayAdapter;
    private TextView tvPHeader;
    private List<Sensores> sensor;
    private ListView listSensores;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensor = new ArrayList<>();

        tvPHeader = new TextView(this);
        tvPHeader.setText("Lista de productos");
        tvPHeader.setTextSize(24f);
        tvPHeader.setTextColor(Color.CYAN);

        listSensores = findViewById(R.id.idListaSensores);
        listSensores.addHeaderView(tvPHeader);
        arrayAdapter = new ArrayAdapter<Sensores>(this, android.R.layout.simple_list_item_1, sensor);
        listSensores.setAdapter(arrayAdapter);




        listSensores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,  int i, long l) {
                if(i >=0){
                    Intent intent = new Intent(getApplicationContext(), DetalleSensor.class);
                    intent.putExtra("id_sensor", sensor.get(i-1).getIdSensor());
                    intent.putExtra("nom_sensor", sensor.get(i-1).getNameSensor());
                    intent.putExtra("valor_sensor", sensor.get(i-1).getValorSensor());
                    intent.setAction("VIEW");
                    startActivity(intent);
                }
            }
        });
        btnAgregar = (Button) findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), AgregarSensor.class);
                startActivity(intent);
            }
        });
        btnMapa = (Button) findViewById(R.id.btnMapa);
        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent( getApplicationContext(), MapsActivity.class);
                startActivity(intent2);
            }
        });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMapa:
                Intent mapaIntent = new Intent(this, MapsActivity.class);
                startActivity(mapaIntent);
                break;
        }

    }



    public void LoadSensores() {
        sensor.clear();
        SQLITE myDBSQLITE = new SQLITE(this, "SensoresDB", null, 1);
        SQLiteDatabase sensoresDB = myDBSQLITE.getWritableDatabase();

        Cursor cursor = sensoresDB.rawQuery("SELECT id_sensor, nom_sensor, fecha_sensor,campo_observacion,valor_sensor  FROM Sensores ORDER BY nom_sensor", null);
        Log.i("NUMERO REGISTRO",Integer.toString(cursor.getCount()));

        if(cursor.moveToFirst()) {
            do {
                Log.i("SENSORES", String.format("%d, %s", cursor.getInt(0),cursor.getString(1)));
                Sensores sensores = new Sensores(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
                sensor.add(sensores);
            }while (cursor.moveToNext());

            arrayAdapter.notifyDataSetChanged();
        }
        //SensoresDB.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadSensores();
    }
}
