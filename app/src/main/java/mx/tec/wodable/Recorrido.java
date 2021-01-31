package mx.tec.wodable;

public class Recorrido {

    private String tiempoInicio;
    private String tiempoFinal;
    private String distancia;
    private String pasos;

    public Recorrido(){}

    public Recorrido(String tiempoInicio, String tiempoFinal, String distancia, String pasos) {
        this.tiempoInicio = tiempoInicio;
        this.tiempoFinal = tiempoFinal;
        this.distancia = distancia;
        this.pasos = pasos;
    }

    public String getTiempoInicio() {
        return tiempoInicio;
    }

    public void setTiempoInicio(String tiempoInicio) {
        this.tiempoInicio = tiempoInicio;
    }

    public String getTiempoFinal() {
        return tiempoFinal;
    }

    public void setTiempoFinal(String tiempoFinal) {
        this.tiempoFinal = tiempoFinal;
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
}
