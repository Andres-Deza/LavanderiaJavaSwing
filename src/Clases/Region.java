package Clases;

import Conexion.BDConexion;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Region {

    int id_region;
    String nom_region;

    public int getId() {
        return id_region;
    }

    public void setId(int id_region) {
        this.id_region = id_region;
    }

    public String getNombre() {
        return nom_region;
    }

    public void setNombre(String nom_region) {
        this.nom_region = nom_region;
    }

    @Override
    public String toString() {
        return this.nom_region;
    }
    /**
     * 
     * Método para cargar el combobox de region
     * 
     * 
     * @return datos 
     */
    public Vector<Region> mostrarRegion() {

        PreparedStatement ps ;
        ResultSet rs ;
        Connection conn =(Connection) BDConexion.getConexion();

        Vector<Region> datos = new Vector<Region>();
        Region dat ;
        try {

            String sql = "SELECT * FROM region";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            dat = new Region();
            dat.setId(0);
            dat.setNombre("Selecciona region");
            datos.add(dat);

            while (rs.next()) {
                dat = new Region();
                dat.setId(rs.getInt("id_region"));
                dat.setNombre(rs.getString("nom_region"));
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
     * Método para seleccionar el item de la region respecto a la tabla
     * @param nomRegion Para asignar la region segun la tabla
     * 
     */
    public int mostrarRegionV2(String nomRegion) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        java.sql.Connection conn = BDConexion.getConexion();
        

        
        Region dat = null;
        try {

            String sql = "SELECT * FROM region WHERE nom_region='" + nomRegion+"'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            

            while (rs.next()) {
                dat = new Region();
                dat.setId(rs.getInt("id_region"));
                dat.setNombre(rs.getString("nom_region"));
                
            }
            rs.close();
        } catch (SQLException ex) {
            System.err.println("Error consulta :" + ex.getMessage());
        }
        return dat.getId();
    }
   
    
    
}
