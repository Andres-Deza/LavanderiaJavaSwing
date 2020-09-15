/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

	/**
	 * La clase BDConexion es la encargada de realizar la conexión a la base de datos de la biblioteca
	 * @author tridente
	 * Fecha creación 20/05/19
	 */
public class BDConexion {
    private static String NOMBRE_BD = "lavanderia";
    private static String USUARIO_BD = "root";

    private static String CLAVE_BD = "";

    private static String DRIVER_BD = "com.mysql.jdbc.Driver";
    private static Connection conn;
    
    public static Connection getConexion() {
        try {
            Class.forName(DRIVER_BD);
            if(conn == null)conn = DriverManager.getConnection("jdbc:mysql://localhost/" + NOMBRE_BD, USUARIO_BD, CLAVE_BD);

            return conn;            
        }catch(Exception e){            
            System.err.println("Excepción en ConexionBD.getConexion: " + e);
            JOptionPane.showMessageDialog(null, "Error de conexión a la base de datos","ERROR",0);
            return null;
        }
    }

    	 /**
	 * Método que realiza la validación de los usuarios al conectarse a la base de datos
	 * @param nom validacion de nombre en el inicio de sesion
	 * @param rut  validacion de rut en el inicio de sesion
     * @return  parametro de retornar
	 */
    
    public static boolean validaUsuario(String nom, String rut){
        String v_nom = "";
        String v_rut = "";
        try{
            String sql = "select nombre, rut "
                    + "from vendedor "
                    + "where nombre = '"+nom+"' "
                    + "and rut='"+rut+"'";
            Statement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                v_nom = rs.getString("nombre").trim();
                v_rut = rs.getString("rut").trim();
            }
            if (nom.equals(v_nom) && rut.equals(v_rut)){
                return true;
            }
        }catch(Exception e){}
        return false;
        
    }
    
}
