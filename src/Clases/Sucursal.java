/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Conexion.BDConexion;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author andre
 */
public class Sucursal {
    private int id_sucursal;
    private int id_comuna;
    private String direccion;
    private String correo;

    public int getId_sucursal() {
        return id_sucursal;
    }

    public void setId_sucursal(int id_sucursal) {
        this.id_sucursal = id_sucursal;
    }

    public int getId_comuna() {
        return id_comuna;
    }

    public void setId_comuna(int id_comuna) {
        this.id_comuna = id_comuna;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    @Override
    public String toString() {
        return this.direccion;
    }
    /**
     * 
     * Método para cargar el combobox de sucursal
     * 
     * 
     * @return datos 
     */
    public Vector<Sucursal> mostrarSucursal() {

        PreparedStatement ps ;
        ResultSet rs ;
        Connection conn =(Connection) BDConexion.getConexion();

        Vector<Sucursal> datos = new Vector<Sucursal>();
        Sucursal dat ;
        try {

            String sql = "SELECT * FROM sucursal";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            dat = new Sucursal();
            dat.setId_sucursal(0);
            dat.setDireccion("Selecciona sucursal");
            datos.add(dat);

            while (rs.next()) {
                dat = new Sucursal();
                dat.setId_sucursal(rs.getInt("id_sucursal"));
                dat.setDireccion(rs.getString("nombre"));
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
     * Método para seleccionar el item de la sucursal respecto a la tabla
     * @param nomsucursal Para asignar la region segun la tabla
     * 
     */
    public int mostrarsucursalV2(String nomsucursal) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        java.sql.Connection conn = BDConexion.getConexion();
        
        
        
        Sucursal dat = null;
        try {

            String sql = "SELECT * FROM sucursal WHERE nombre='" + nomsucursal+"'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            

            while (rs.next()) {
                dat = new Sucursal();
                dat.setId_sucursal(rs.getInt("id_sucursal"));
                dat.setDireccion(rs.getString("nombre"));
                
            }
            rs.close();
        } catch (SQLException ex) {
            System.err.println("Error consulta :" + ex.getMessage());
        }
        return dat.getId_sucursal();
    }
}
