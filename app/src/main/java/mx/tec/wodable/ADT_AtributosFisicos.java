package mx.tec.wodable;

public class ADT_AtributosFisicos {

    private int id_atributos;
    private String fecha_nacimiento;
    private float peso;
    private float altura;
    private String genero;
    private int id_usuario;


    public ADT_AtributosFisicos(){}

    public ADT_AtributosFisicos(int id_atributos, String fecha_nacimiento, float peso, float altura, String genero, int id_usuario) {
        this.id_atributos = id_atributos;
        this.fecha_nacimiento = fecha_nacimiento;
        this.peso = peso;
        this.altura = altura;
        this.genero = genero;
        this.id_usuario = id_usuario;
    }

    public int getId_atributos() {
        return id_atributos;
    }

    public void setId_atributos(int id_atributos) {
        this.id_atributos = id_atributos;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}
