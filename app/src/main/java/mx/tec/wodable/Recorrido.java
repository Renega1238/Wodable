package mx.tec.wodable;

import java.util.Date;

public class Recorrido {

    private int id_recorrido;
    private String distancia;
    private String pasos;
    private String tiempoInicioTotal;
    private String tiempoFinalTotal;
    private String tiempo_carrera;



    public Recorrido(){}



    public int getId_recorrido() {
        return id_recorrido;
    }

    public void setId_recorrido(int id_recorrido) {
        this.id_recorrido = id_recorrido;
    }

    public String getTiempo_carrera() {
        return tiempo_carrera;
    }

    public void setTiempo_carrera(String tiempo_carrera) {
        this.tiempo_carrera = tiempo_carrera;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getPasos() {
        return pasos;
    }

    public void setPasos(String pasos) {
        this.pasos = pasos;
    }

    public String getTiempoInicioTotal() {
        return tiempoInicioTotal;
    }

    public Recorrido(int id_recorrido, String distancia, String pasos, String tiempoInicioTotal, String tiempoFinalTotal, String tiempo_carrera) {
        this.id_recorrido = id_recorrido;
        this.distancia = distancia;
        this.pasos = pasos;
        this.tiempoInicioTotal = tiempoInicioTotal;
        this.tiempoFinalTotal = tiempoFinalTotal;
        this.tiempo_carrera = tiempo_carrera;
    }

    public void setTiempoInicioTotal(String tiempoInicioTotal) {
        this.tiempoInicioTotal = tiempoInicioTotal;
    }

    public String getTiempoFinalTotal() {
        return tiempoFinalTotal;
    }

    public void setTiempoFinalTotal(String tiempoFinalTotal) {
        this.tiempoFinalTotal = tiempoFinalTotal;
    }
}
