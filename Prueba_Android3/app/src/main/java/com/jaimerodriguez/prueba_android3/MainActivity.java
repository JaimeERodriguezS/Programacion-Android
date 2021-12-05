package com.jaimerodriguez.prueba_android3;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.functions.FirebaseFunctions;
import com.jaimerodriguez.prueba_android3.modelo.Sensor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {
    ListView usuarioListView;
    MyArrayAdapter arrayAdapter;
    private List<Sensor> listPerson = new ArrayList<Sensor>();
    ArrayAdapter<Sensor> arrayAdapterPersona;
    private DatabaseReference dbReference;
    private FirebaseDatabase firebaseDatabase;
    EditText etNomSensor, etTipoSensor, etvalorSensor, etubiSensor, etobservacion;
    private Sensor sensoractual;
    ListView listV_personas;
    private Button btnmqtt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listV_personas = findViewById(R.id.list_sensor);
        inicializarFirebase();
        listarDatos();
        arrayAdapter = new MyArrayAdapter(listPerson, getBaseContext(), getLayoutInflater());
        usuarioListView = (ListView) findViewById(R.id.list_sensor);
        usuarioListView.setAdapter(arrayAdapter);

        etNomSensor = (EditText) findViewById(R.id.nom_sensor);
        etTipoSensor = (EditText) findViewById(R.id.tipo_sensor);
        etvalorSensor = (EditText) findViewById(R.id.valor_sensor);
        etubiSensor = (EditText) findViewById(R.id.ubi_sensor);
        etobservacion = (EditText) findViewById(R.id.observacion);
        btnmqtt = (Button) findViewById(R.id.btnMQ);
        btnmqtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getApplicationContext(), MainMqtt.class);
                startActivity(intent);
            }
        });

        usuarioListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                etNomSensor.setText(listPerson.get(i).getNomSensor());
                etTipoSensor.setText(listPerson.get(i).getTipoSensor());
                etvalorSensor.setText(listPerson.get(i).getValorSensor());
                etubiSensor.setText(listPerson.get(i).getUbiSensor());
                etobservacion.setText(listPerson.get(i).getObservacion());
                sensoractual = listPerson.get(i);
            }
        });
    }

    private void listarDatos() {
        dbReference.child("sensor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listPerson.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                    Sensor p = objSnaptshot.getValue(Sensor.class);
                    listPerson.add(p);

                    arrayAdapterPersona = new ArrayAdapter<Sensor>(MainActivity.this, android.R.layout.simple_list_item_1, listPerson);
                    listV_personas.setAdapter(arrayAdapterPersona);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void deleteSensor(){
        dbReference.child("sensor").child(sensoractual.getSensorId()).removeValue();
        Toast.makeText(this, "Usuario eliminado!", Toast.LENGTH_SHORT).show();
    }
    private void updateSensor() {
        sensoractual.setObservacion(etobservacion.getText().toString());
        dbReference.child("sensor").child(sensoractual.getSensorId()).setValue(sensoractual);
        Toast.makeText(this, "Datos del sensor actualizado!", Toast.LENGTH_SHORT).show();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        dbReference = firebaseDatabase.getReference();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String nombre = etNomSensor.getText().toString();
        String tipo = etTipoSensor.getText().toString();
        String valor = etvalorSensor.getText().toString();
        String ubicacion = etubiSensor.getText().toString();
        String observacion = etobservacion.getText().toString();
        switch (item.getItemId()){
            case R.id.menu_item_agregar:{
                if (nombre.equals("")||tipo.equals("")||valor.equals("")||ubicacion.equals("")||observacion.equals("")){
                }
                else {
                    Sensor p = new Sensor();
                    p.setSensorId(UUID.randomUUID().toString());
                    p.setNomSensor(nombre);
                    p.setTipoSensor(tipo);
                    p.setValorSensor(valor);
                    p.setUbiSensor(ubicacion);
                    p.setObservacion(observacion);
                    dbReference.child("sensor").child(p.getSensorId()).setValue(p);
                    Toast.makeText(this, "Agregado datos del sensor", Toast.LENGTH_LONG).show();
                    limpiarCajas();
                }
                break;
            }
            case R.id.menu_item_save:{
                updateSensor();
                Toast.makeText(this,"Actualizado los datos del sensor", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.menu_item_eliminar:{
                deleteSensor();
                Toast.makeText(this,"Eliminado todos los datos del sensor", Toast.LENGTH_LONG).show();
                break;
            }
            default:break;
        }
        return true;
    }
    private void limpiarCajas() {
        etNomSensor.setText("");
        etTipoSensor.setText("");
        etvalorSensor.setText("");
        etubiSensor.setText("");
        etobservacion.setText("");
    }

}