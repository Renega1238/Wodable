package mx.tec.wodable;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBRecorridos {

    Connection cnx = null;
    Statement st = null;
    ResultSet rs = null;
    PreparedStatement pst = null;


    // Trae todos los recorridos existentes de todos los usuarios
    // En un ArrayList
    public ArrayList<ADT_Recorridos> ListaDeRecorridos() throws ClassNotFoundException {

        ArrayList<ADT_Recorridos> recorridos = new ArrayList<>();
        try {
            cnx = DBConnection.getConnecction();
            st = cnx.createStatement();

            // Trae todos los recorridos existentes de todos los usuarios
            rs = st.executeQuery("SELECT recorridos.id_carrera, recorridos.timestamp_inicio, recorridos.timestamp_final, recorridos.distancia, recorridos.pasos, recorridos.id_usuario\n" +
                    "from usuarios inner join recorridos on usuarios.id_usuario = recorridos.id_usuario\n" +
                    "order by 1;");

            while (rs.next()) {
                ADT_Recorridos recorrido = new ADT_Recorridos();

                recorrido.setId_carrera(rs.getInt(1));
                recorrido.setTimestamp_inicio(rs.getString(2));
                recorrido.setTimestamp_final(rs.getString(3));
                recorrido.setDistancia(rs.getInt(4));
                recorrido.setPasos(rs.getInt(5));
                recorrido.setId_usuario(rs.getInt(6));

                recorridos.add(recorrido);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        DBConnection.close(cnx);
        return recorridos;
    }

    // Trae todos los recorridos de ese usuario a una ArrayList
    public ArrayList<ADT_Recorridos> ListaDeRecorridosByUsername(String username) throws ClassNotFoundException {

        ArrayList<ADT_Recorridos> recorridos = new ArrayList<>();

        try {
            cnx = DBConnection.getConnecction();
            st = cnx.createStatement();

            // Trae todos los recorridos de ese usuario a una ArrayList
            rs = st.executeQuery("SELECT recorridos.id_carrera, recorridos.timestamp_inicio, recorridos.timestamp_final, " +
                    "recorridos.distancia, recorridos.pasos, recorridos.id_usuario from usuarios " +
                    "inner join recorridos on usuarios.id_usuario = recorridos.id_usuario " +
                    "WHERE usuarios.nombre = " + username + " order by 1");

            while (rs.next()) {
                ADT_Recorridos recorrido = new ADT_Recorridos();

                recorrido.setId_carrera(rs.getInt(1));
                recorrido.setTimestamp_inicio(rs.getString(2));
                recorrido.setTimestamp_final(rs.getString(3));
                recorrido.setDistancia(rs.getInt(4));
                recorrido.setPasos(rs.getInt(5));
                recorrido.setId_usuario(rs.getInt(6));

                recorridos.add(recorrido);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        DBConnection.close(cnx);
        return recorridos;
    }


    public ADT_Recorridos findRecorridoById(int id_carrera) {

        ADT_Recorridos recorrido = null;

        //int id = Integer.parseInt(id_carrera);
        //System.out.println(id);
        try {
            cnx = DBConnection.getConnecction();
            st = cnx.createStatement();
            rs = st.executeQuery("SELECT recorridos.id_carrera,recorridos.timestamp_inicio,recorridos.timestamp_final," +
                    "recorridos.distancia,recorridos.pasos,usuarios.id_usuario " +
                    "FROM usuarios inner join " +
                    "recorridos on recorridos.id_usuario = " +
                    "usuarios.id_usuario WHERE id_carrera="+id_carrera+"");
            while (rs.next()) {

                recorrido = new ADT_Recorridos();
                recorrido.setId_carrera(rs.getInt(1));
                recorrido.setTimestamp_inicio(rs.getString(2));
                recorrido.setTimestamp_final(rs.getString(3));
                recorrido.setDistancia(rs.getInt(4));
                recorrido.setPasos(rs.getInt(5));
                recorrido.setId_usuario(rs.getInt(6));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        DBConnection.close(cnx);
        return recorrido;
    }


    // El timestamp lleva este formato
    // "1970-01-01 00:00:01" -> Timestamp para tiempos
    // Example: "2021-12-28 23:59:59"
    public void AddNewRecorrido(ADT_Recorridos recorrido, int id_usuario) {

        try {
            cnx = DBConnection.getConnecction();

            pst = cnx.prepareStatement("INSERT INTO recorridos("
                    + " id_carrera,timestamp_inicio,timestamp_final,distancia,pasos,id_usuario) "
                    + "VALUES(?,?,?,?,?,?)");

            pst.setString(1, null);
            pst.setString(2, recorrido.getTimestamp_inicio());
            pst.setString(3, recorrido.getTimestamp_final());
            pst.setInt(4, recorrido.getDistancia());
            pst.setInt(5, recorrido.getPasos());
            pst.setInt(6, id_usuario);

            pst.executeQuery();


        } catch (SQLException ex) {
            Logger.getLogger(DBRecorridos.class.getName()).log(Level.SEVERE, null, ex);
        }
        DBConnection.close(cnx);

    }




}
