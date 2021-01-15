package mx.tec.wodable;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBAtributosFisicos {

    Connection cnx = null;
    Statement st = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    // Busca un usuario por su ID y regresa todos sus datos (atributos físicos)

    public ADT_AtributosFisicos findAtributosFisicosByUserId(int id_usuario) {

        ADT_AtributosFisicos personaAtributos = null;

        try {
            cnx = DBConnection.getConnecction();
            st = cnx.createStatement();

            rs = st.executeQuery("SELECT atributos_fisicos.id_atributos," +
                    "atributos_fisicos.fecha_nacimiento,atributos_fisicos.peso," +
                    "atributos_fisicos.altura,atributos_fisicos.genero," +
                    "atributos_fisicos.id_usuario FROM usuarios inner join " +
                    "atributos_fisicos on usuarios.id_usuario = atributos_fisicos.id_usuario " +
                    "WHERE id_carrera="+id_usuario+"");
            while (rs.next()) {
                personaAtributos = new ADT_AtributosFisicos();

                personaAtributos.setId_atributos(rs.getInt(1));
                personaAtributos.setFecha_nacimiento(rs.getString(2));
                personaAtributos.setPeso(rs.getFloat(3));
                personaAtributos.setAltura(rs.getFloat(4));
                personaAtributos.setGenero(rs.getString(5));
                personaAtributos.setId_usuario(rs.getInt(6));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        DBConnection.close(cnx);

        return personaAtributos;
    }

    public void UpdateAtributosFisicos(ADT_AtributosFisicos usuario) {
        try {
            cnx = DBConnection.getConnecction();
            pst = cnx.prepareStatement("UPDATE atributos_fisicos SET "
                    + "fecha_nacimiento=?,peso=?,altura=?,genero=?"
                    + " WHERE id_usuario=?");

            pst.setString(1, usuario.getFecha_nacimiento());
            pst.setFloat(2, usuario.getPeso());
            pst.setFloat(3, usuario.getAltura());
            pst.setString(4, usuario.getGenero());
            pst.setInt(5, usuario.getId_usuario());

            pst.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(DBUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBConnection.close(cnx);
    }


    // Agregar nuevos atributos
    public void InsertIntoAtributosFisicos(ADT_AtributosFisicos usuario) {

        try {
            cnx = DBConnection.getConnecction();

            // Ejemplo de query
            /*** INSERT INTO usuarios( id_usuario,username,password,correo,nombre,apellido ) VALUES(null,"usuarioPrueba","tec123","usuarioprueba@hotmail.com","NameTest","ApellidoTest") **/
            pst = cnx.prepareStatement("INSERT INTO atributos_fisicos("
                    + " id_atributos,fecha_nacimiento,peso,altura,genero,id_usuario ) "
                    + "VALUES(?,?,?,?,?,?)");

            pst.setString(1, null);
            pst.setString(2, usuario.getFecha_nacimiento());
            pst.setFloat(3, usuario.getPeso());
            pst.setFloat(4, usuario.getAltura());
            pst.setString(5, usuario.getGenero());
            pst.setInt(6, usuario.getId_usuario());

            pst.executeQuery();


        } catch (SQLException ex) {
            Logger.getLogger(DBUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBConnection.close(cnx);

    }

}
