package com.jaimerodriguez.prueba_android3;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.jaimerodriguez.prueba_android3.modelo.Sensor;

import java.util.List;

public class MyArrayAdapter<S> extends BaseAdapter {
    List<Sensor> sensorList;
    Context context;

    public MyArrayAdapter(List<Sensor> sensorList, Context context, LayoutInflater layoutInflater) {
        this.sensorList = sensorList;
        this.context = context;
        this.layoutInflater = layoutInflater;
    }
    LayoutInflater layoutInflater;
    @Override
    public int getCount() { return sensorList.size(); }

    @Override
    public Object getItem(int i) { return sensorList.get(i); }

    @Override
    public long getItemId(int i) { return 0; }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vHolder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.sensor_layout,null);

            vHolder = new ViewHolder();
            vHolder.tvid = view.findViewById(R.id.tv_id_sensor);
            vHolder.tvNomSensor = view.findViewById(R.id.tv_nom_sensor);
            vHolder.tvtipoSensor = view.findViewById(R.id.tv_tipo_sensor);
            vHolder.tvvalorSensor = view.findViewById(R.id.tv_valor_sensor);
            vHolder.tvubiSensor = view.findViewById(R.id.tv_ubi_sensor);
            vHolder.tvFechaIngreso = view.findViewById(R.id.tv_fecha_ingreso);
            vHolder.tvobservacion = view.findViewById(R.id.tv_observacion);
            view.setTag(vHolder);
        } else {
            vHolder = (ViewHolder) view.getTag();
        }
        Sensor s = sensorList.get(i);
        vHolder.tvid.setText(s.getSensorId());
        vHolder.tvNomSensor.setText(s.getNomSensor());
        vHolder.tvtipoSensor.setText(s.getTipoSensor());
        vHolder.tvvalorSensor.setText(s.getValorSensor());
        vHolder.tvubiSensor.setText(s.getUbiSensor());
        //vHolder.tvFechaIngreso.setText(LocalDateTime.ofInstant(s.getFechaIngreso().toInstant(), ZoneId.systemDefault()).toString());
        vHolder.tvFechaIngreso.setText(s.getFechaIngreso().toString());
        vHolder.tvobservacion.setText(s.getObservacion());
        return view;
    }
    static class ViewHolder{
        TextView tvid;
        TextView tvNomSensor;
        TextView tvtipoSensor;
        TextView tvvalorSensor;
        TextView tvubiSensor;
        TextView tvFechaIngreso;
        TextView tvobservacion;
    }
}
