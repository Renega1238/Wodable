package mx.tec.wodable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Recorrido {

    private int id_recorrido;
    private String distancia;
    private String pasos;
    private String tiempoInicioTotal;
    private String tiempoFinalTotal;
    private String tiempo_carrera;


    public Recorrido() {
    }


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
        this.distancia = String.format("%.2f",Float.parseFloat(distancia));
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
        String temp = fechalegible(tiempoInicioTotal);
        this.tiempoInicioTotal = unixSecondsToDate(temp);
    }

        public String getTiempoFinalTotal () {
            return tiempoFinalTotal;
        }

        public void setTiempoFinalTotal (String tiempoFinalTotal){
        String temp = fechalegible(tiempoFinalTotal);
            this.tiempoFinalTotal = unixSecondsToDate(temp);
        }

        private String fechalegible(String valor){
            String resultado = "";
            List<String> allMatches = new ArrayList<String>();
            Matcher m = Pattern.compile("[0-9]+")
                    .matcher(valor);
            while (m.find()) {
                allMatches.add(m.group());
                resultado = allMatches.get(0).toString();
            }
            return resultado;
        }

        private String unixSecondsToDate(String timeStamp){
            long unixSeconds = Long.parseLong(timeStamp);
            // convert seconds to milliseconds
            Date date = new java.util.Date(unixSeconds*1000L);
            // the format of your date
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // give a timezone reference for formatting (see comment at the bottom)
            sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-6"));
            String formattedDate = sdf.format(date);
            return formattedDate;
        }
    }