package com.jaimerodriguez.prueba_android3.modelo;

import java.util.Date;

public class Sensor {
    private String sensorId;
    private String nomSensor;
    private String tipoSensor;
    private String valorSensor;
    private String ubiSensor;
    private Date fechaIngreso;
    private String observacion;

    public Sensor() {
        this.sensorId = sensorId;
        this.nomSensor = nomSensor;
        this.tipoSensor = tipoSensor;
        this.valorSensor = valorSensor;
        this.ubiSensor = ubiSensor;
        this.fechaIngreso = fechaIngreso;
        this.observacion = observacion;
    }

    public String getSensorId() { return sensorId; }

    public void setSensorId(String sensorId) { this.sensorId = sensorId; }

    public String getNomSensor() {return nomSensor; }

    public void setNomSensor(String nomSensor) { this.nomSensor = nomSensor; }

    public String getTipoSensor() { return tipoSensor; }

    public void setTipoSensor(String tipoSensor) { this.tipoSensor = tipoSensor; }

    public String getValorSensor() { return valorSensor; }

    public void setValorSensor(String valorSensor) { this.valorSensor = valorSensor; }

    public String getUbiSensor() { return ubiSensor; }

    public void setUbiSensor(String ubiSensor) { this.ubiSensor = ubiSensor; }

    public Date getFechaIngreso() { return fechaIngreso; }

    public void setFechaIngreso(Date fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    public String getObservacion() { return observacion; }

    public void setObservacion(String observacion) { this.observacion = observacion; }
    @Override
    public String toString(){return nomSensor +"  "+ valorSensor;}
}
