package com.jaimerodriguez.prueba_android3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;

public class MainMqtt extends AppCompatActivity {
    private static final String serverUri = "tcp://test.mosquitto.org:1883";
    private static final String userName = "marcelo";
    private static final String password = "1235";
    private static final String appName = "app1";
    private static final String clientId = "marcelo";
    private static final String TAG = "MQTT Client 01";

    private MqttAndroidClient mqttAndroidClient;
    private ListView listView;
    private List<String> stringList;
    private ArrayAdapter<String> arrayAdapter;
    private EditText etNomSensor, etTipoSensor, etvalorSensor, etubiSensor, etobservacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mqtt);
        listView = (ListView) findViewById(R.id.lv_datos_mqtt);
        stringList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.lv_item, stringList);
        listView.setAdapter(arrayAdapter);
        etNomSensor = (EditText) findViewById(R.id.nom_sensor);
        etTipoSensor = (EditText) findViewById(R.id.tipo_sensor);
        etvalorSensor = (EditText) findViewById(R.id.valor_sensor);
        etubiSensor = (EditText) findViewById(R.id.ubi_sensor);
        etobservacion = (EditText) findViewById(R.id.observacion);

        mqttAndroidClient = new MqttAndroidClient(getApplicationContext(), serverUri, clientId);
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                if (reconnect) {
                    Log.d(TAG, "Reconectado a : " + serverURI);
                } else {
                    Log.d(TAG, "Conectado a: " + serverURI);
                }
            }

            @Override
            public void connectionLost(Throwable cause) {
                Log.d(TAG, "Se ha perdido la conexón!.");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d(TAG, "Mensaje recibido:" + message.toString());
                stringList.add(message.toString());
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {}
        });

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setUserName(userName);
        mqttConnectOptions.setPassword(password.toCharArray());

        try {
            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d(TAG, "Conectado a: " + serverUri);

                    try {
                        asyncActionToken.getSessionPresent();
                        Log.d(TAG, "Topicos: " + asyncActionToken.getTopics().toString());
                    } catch (Exception e) {
                        String message = e.getMessage();
                        Log.d(TAG, "Error el mensaje es nulo! " + String.valueOf(message == null));
                    }

                    Toast.makeText(MainMqtt.this, "Conectado a mosquitto!", Toast.LENGTH_SHORT).show();

                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);

                    try {
                        mqttAndroidClient.subscribe("sensores",2);
                        Toast.makeText(MainMqtt.this, "Subscrito a test01", Toast.LENGTH_SHORT).show();
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                    Log.d(TAG, "Falla al conectar a: " + serverUri);

                }
            });
        } catch (MqttException ex) {
            ex.printStackTrace();
        }
    }
}