package mx.tec.wodable;

public class ADT_Usuarios {

    private int id_usuario;
    private String username;
    private String password;
    private String correo;
    private String nombre;
    private String apellido;


    public ADT_Usuarios(){}


    public ADT_Usuarios(int id_usuario, String username, String password, String correo, String nombre, String apellido) {
        this.id_usuario = id_usuario;
        this.username = username;
        this.password = password;
        this.correo = correo;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
