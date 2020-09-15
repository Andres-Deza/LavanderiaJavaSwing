package Clases;

import Conexion.BDConexion;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Ciudad {

    int id;
    String nombre;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
    
    /**
     * 
     * Método para cargar el combobox de las ciudad respecto a la ciudad
     * @param idRegion Para asignar la ciudad segun la region
     * 
     */
    public Vector<Ciudad> mostrarCiudad(int idRegion) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        java.sql.Connection conn = BDConexion.getConexion();
        

        Vector<Ciudad> datos = new Vector<Ciudad>();
        Ciudad dat = null;
        try {

            String sql = "SELECT * FROM ciudad WHERE id_region=" + idRegion;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            dat = new Ciudad();
            dat.setId(0);
            dat.setNombre("Seleccionar Ciudad");
            datos.add(dat);

            while (rs.next()) {
                dat = new Ciudad();
                dat.setId(rs.getInt("id_ciudad"));
                dat.setNombre(rs.getString("nom_ciudad"));
                datos.add(dat);
            }
            rs.close();
        } catch (SQLException ex) {
            System.err.println("Error consulta :" + ex.getMessage());
        }
        return datos;
    }
    /**
     * 
     * Método para seleccionar el item de la ciudad respecto a la region
     * @param nomCiudad Para asignar la comuna segun la ciudad
     * 
     */
    public int mostrarCiudadV2(String nomCiudad) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        java.sql.Connection conn = BDConexion.getConexion();
        

        Vector<Ciudad> datos = new Vector<Ciudad>();
        Ciudad dat = null;
        try {

            String sql = "SELECT * FROM ciudad INNER JOIN region ON ciudad.id_region=region.id_region WHERE nom_region='" + nomCiudad+"'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            datos.add(dat);
            

            while (rs.next()) {
                dat = new Ciudad();
                dat.setId(rs.getInt("id_ciudad"));
                dat.setNombre(rs.getString("nom_ciudad"));
                datos.add(dat);
            }
            rs.close();
        } catch (SQLException ex) {
            System.err.println("Error consulta :" + ex.getMessage());
        }
        return datos.indexOf(dat);
    }
}
