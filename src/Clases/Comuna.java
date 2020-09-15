package Clases;

import Conexion.BDConexion;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
/**
 *
 * @author Andres
 * @version 2
 */
public class Comuna {

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
     * Método para cargar el combobox de las comunas respecto a la ciudad
     * @param idCiudad Para asignar la comuna segun la ciudad
     * 
     */
    public Vector<Comuna> mostrarComuna(int idCiudad) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = (Connection) BDConexion.getConexion();
        

        Vector<Comuna> datos = new Vector<Comuna>();
        Comuna dat = null;

        try {

            String sql = "SELECT * FROM comuna WHERE id_ciudad=" + idCiudad;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            dat = new Comuna();
            dat.setId(0);
            dat.setNombre("Seleccionar Comuna");
            datos.add(dat);

            while (rs.next()) {
                dat = new Comuna();
                dat.setId(rs.getInt("id_comuna"));
                dat.setNombre(rs.getString("nom_comuna"));
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
     * Método para asignar la comuna segun la ciudad
     * @param nomComuna Para asignar la comuna segun la ciudad
     * 
     */
    public int mostrarComunaV2(String nomComuna) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        java.sql.Connection conn = BDConexion.getConexion();
        
        Vector<Comuna> datos = new Vector<Comuna>();
        
        Comuna dat = null;
        try {

            String sql = "SELECT * FROM comuna WHERE nom_comuna='" + nomComuna+"'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            datos.add(dat);
            

            while (rs.next()) {
                dat = new Comuna();
                dat.setId(rs.getInt("id_comuna"));
                dat.setNombre(rs.getString("nom_comuna"));
                datos.add(dat);
            }
            rs.close();
        } catch (SQLException ex) {
            System.err.println("Error consulta :" + ex.getMessage());
        }
        return datos.indexOf(dat);
    }
}
