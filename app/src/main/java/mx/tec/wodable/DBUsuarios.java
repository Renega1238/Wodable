package mx.tec.wodable;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBUsuarios {

    Connection cnx = null;
    Statement st = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    // Captura en un arraylist todos los usuarios
    public ArrayList<ADT_Usuarios> ListClients() throws ClassNotFoundException {
        ArrayList<ADT_Usuarios> cli = new ArrayList<>();
        try {
            cnx = DBConnection.getConnecction();
            st = cnx.createStatement();
            rs = st.executeQuery("SELECT id_usuario,username,password,correo,nombre,apellido"
                    + " FROM usuarios ORDER BY 1");
            while (rs.next()) {
                ADT_Usuarios c = new ADT_Usuarios();

                c.setId_usuario(rs.getInt(1));
                c.setUsername(rs.getString(2));
                c.setPassword(rs.getString(3));
                c.setCorreo(rs.getString(4));
                c.setNombre(rs.getString(5));
                c.setApellido(rs.getString(6));

                cli.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        DBConnection.close(cnx);
        return cli;
    }

    // Busca un usuario por su ID y regresa todos sus datos
    public ADT_Usuarios findClientById(String id_cliente) {
        ADT_Usuarios c = null;

        int id = Integer.parseInt(id_cliente);
        try {
            cnx = DBConnection.getConnecction();
            st = cnx.createStatement();
            // Ejemplo
            //SELECT id_usuario,username,password,correo,nombre,apellido FROM usuarios WHERE id_usuario = "2"
            rs = st.executeQuery("SELECT id_usuario,username,password,correo,nombre,apellido"
                    + " FROM usuarios "
                    + " WHERE id_usuario="+id+"");
            while (rs.next()) {
                c = new ADT_Usuarios();
                c.setId_usuario(rs.getInt(1));
                c.setUsername(rs.getString(2));
                c.setPassword(rs.getString(3));
                c.setCorreo(rs.getString(4));
                c.setNombre(rs.getString(5));
                c.setApellido(rs.getString(6));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        DBConnection.close(cnx);
        return c;
    }


    // Busca un usuario por su username y regresa todos sus datos
    public ADT_Usuarios findClientByUsername(String username) {
        ADT_Usuarios c = null;

        try {
            cnx = DBConnection.getConnecction();
            st = cnx.createStatement();
            // Ejemplo query
            //SELECT id_usuario,username,password,correo,nombre,apellido FROM usuarios WHERE username = "lvm3632
            rs = st.executeQuery("SELECT id_usuario,username,password,correo,nombre,apellido"
                    + " FROM usuarios "
                    + " WHERE username="+username+"");
            while (rs.next()) {
                c = new ADT_Usuarios();
                c.setId_usuario(rs.getInt(1));
                c.setUsername(rs.getString(2));
                c.setPassword(rs.getString(3));
                c.setCorreo(rs.getString(4));
                c.setNombre(rs.getString(5));
                c.setApellido(rs.getString(6));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        DBConnection.close(cnx);
        return c;
    }

    // Agregar un nuevo usuario
    public void InsertInto(ADT_Usuarios user) {
        try {
            cnx = DBConnection.getConnecction();

            // Ejemplo de query
            /*** INSERT INTO usuarios( id_usuario,username,password,correo,nombre,apellido ) VALUES(null,"usuarioPrueba","tec123","usuarioprueba@hotmail.com","NameTest","ApellidoTest") **/
            pst = cnx.prepareStatement("INSERT INTO usuarios("
                    + " id_usuario,username,password,correo,nombre,apellido ) "
                    + "VALUES(?,?,?,?,?,?)");
            pst.setString(1, null);
            pst.setString(2, user.getUsername());   // varchar(50)
            pst.setString(3, user.getPassword());   // varchar(50)
            pst.setString(4, user.getCorreo()); // varchar(50)
            pst.setString(5, user.getNombre());  // varchar(50)
            pst.setString(6, user.getApellido());// varchar(50)

            pst.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(DBUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBConnection.close(cnx);
    }

    public void deleteById(int id) {
        try {
            cnx = DBConnection.getConnecction();
            pst = cnx.prepareStatement("DELETE FROM usuarios "
                    + " WHERE id_usuario=?");
            pst.setInt(1, id);
            pst.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DBUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBConnection.close(cnx);
    }


    public void deleteByUsername(String username) {
        try {
            cnx = DBConnection.getConnecction();
            pst = cnx.prepareStatement("DELETE FROM usuarios "
                    + " WHERE username=?");
            pst.setString(1, username);
            pst.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DBUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBConnection.close(cnx);
    }


    public void Update(ADT_Usuarios usuario) {
        try {
            cnx = DBConnection.getConnecction();
            pst = cnx.prepareStatement("UPDATE usuarios SET "
                    + "id_usuario=?,username=?,password=?,correo=?,nombre=?, apellido=?"
                    + " WHERE id_usuario=?");

            pst.setInt(1, usuario.getId_usuario());
            pst.setString(2, usuario.getUsername());
            pst.setString(3, usuario.getPassword());
            pst.setString(4, usuario.getCorreo());
            pst.setString(5, usuario.getNombre());
            pst.setString(6, usuario.getApellido());


            pst.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DBUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBConnection.close(cnx);
    }




}