package mx.tec.wodable;

public class ADT_Recorridos {

    private int id_carrera;
    private String timestamp_inicio;
    private String timestamp_final;
    private int distancia;
    private int pasos;
    private int id_usuario;

    public ADT_Recorridos(){}

    public ADT_Recorridos(int id_carrera, String timestamp_inicio, String timestamp_final, int distancia, int pasos, int id_usuario) {
        this.id_carrera = id_carrera;
        this.timestamp_inicio = timestamp_inicio;
        this.timestamp_final = timestamp_final;
        this.distancia = distancia;
        this.pasos = pasos;
        this.id_usuario = id_usuario;
    }

    public int getId_carrera() {
        return id_carrera;
    }

    public void setId_carrera(int id_carrera) {
        this.id_carrera = id_carrera;
    }

    public String getTimestamp_inicio() {
        return timestamp_inicio;
    }

    public void setTimestamp_inicio(String timestamp_inicio) {
        this.timestamp_inicio = timestamp_inicio;
    }

    public String getTimestamp_final() {
        return timestamp_final;
    }

    public void setTimestamp_final(String timestamp_final) {
        this.timestamp_final = timestamp_final;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public int getPasos() {
        return pasos;
    }

    public void setPasos(int pasos) {
        this.pasos = pasos;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}
