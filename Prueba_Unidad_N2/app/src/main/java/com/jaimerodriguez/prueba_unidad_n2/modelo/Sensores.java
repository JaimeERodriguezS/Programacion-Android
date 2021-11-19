package com.jaimerodriguez.prueba_unidad_n2.modelo;

import androidx.annotation.NonNull;

public class Sensores {
    private Integer idSensor;
    private String nameSensor;
    private String fechaSensor;
    private String obsSensor;
    private String valorSensor;

    public Sensores(Integer idSensor, String nameSensor, String fechaSensor, String obsSensor, String valorSensor) {
        this.idSensor = idSensor;
        this.nameSensor = nameSensor;
        this.fechaSensor = fechaSensor;
        this.obsSensor = obsSensor;
        this.valorSensor = valorSensor;
    }

    public Integer getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(Integer idSensor) {
        this.idSensor = idSensor;
    }

    public String getNameSensor() {
        return nameSensor;
    }

    public void setNameSensor(String nameSensor) {
        this.nameSensor = nameSensor;
    }

    public String getFechaSensor() {
        return fechaSensor;
    }

    public void setFechaSensor(String fechaSensor) {
        this.fechaSensor = fechaSensor;
    }

    public String getObsSensor() {
        return obsSensor;
    }

    public void setObsSensor(String obsSensor) {
        this.obsSensor = obsSensor;
    }

    public String getValorSensor() {
        return valorSensor;
    }

    public void setValorSensor(String valorSensor) {
        this.valorSensor = valorSensor;
    }
    @NonNull
    @Override
    public String toString() { return String.format("%d. %s %s %s", idSensor, nameSensor,fechaSensor,obsSensor); }
}
