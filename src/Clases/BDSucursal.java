/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Conexion.BDConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Andres
 * @version 2
 */
public class BDSucursal {
    private static PreparedStatement pst;
    private static PreparedStatement atl;
    private static Statement st;
    private static Connection conn;
    private static ResultSet rs;
    public static DefaultTableModel tm;
    
    public static void listar(JTable tabla){
        tm =  new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        tm.addColumn("Sucursal");
        tm.addColumn("Nombre");
        tm.addColumn("Comuna");
        tm.addColumn("Ciudad");
        tm.addColumn("Region");
        tm.addColumn("Telefono");
        tm.addColumn("Telefono2");        
        tm.addColumn("Direccion");
        tm.addColumn("Correo");
        //Se genera consulta SQL para seleccionar todos los datos de la tabla:
        String sql = "SELECT sucursal.id_sucursal,sucursal.nombre, comuna.nom_comuna,ciudad.nom_ciudad,region.nom_region,tel_sucursal.telefono,tel_sucursal.telefono2, sucursal.direccion,sucursal.correo FROM sucursal INNER JOIN tel_sucursal ON tel_sucursal.id_sucursal=sucursal.id_sucursal INNER JOIN comuna ON sucursal.id_comuna=comuna.id_comuna INNER JOIN ciudad ON comuna.id_ciudad=ciudad.id_ciudad INNER JOIN region ON ciudad.id_region=region.id_region";
        try{
            conn = BDConexion.getConexion();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            //Se obtiene cada registro y se agrega al modelo de tabla
            while(rs.next()){
                Object[] arr = new Object[9];
                arr[0] = rs.getInt(1);
                arr[1] = rs.getString(2);
                arr[2] = rs.getString(3);
                arr[3] = rs.getString(4); 
                arr[4] = rs.getString(5); 
                arr[5] = rs.getString(6); 
                arr[6] = rs.getString(7); 
                arr[7] = rs.getString(8); 
                arr[8] = rs.getString(9); 
                tm.addRow(arr);                
            }            
            //Se carga modelo de tabla a la tabla principal
            tabla.setSelectionMode(0); //NO se pueden escoger multiples registros
            tabla.setModel(tm);            
        }catch(Exception e){
            System.err.println("Excepción al listar: " + e);
        }
    }
    /**
     * 
     * Método para agregar un nuevo registro a la base de datos
     * @param id_suc inserta el id a la sucursal automaticamente
     * @param nom inserta el nombre
     * @param com inserta la comuna a la sucursl 
     * @param tel1 inserta el telefono 1 a la sucursal 
     * @param telefono2 inserta el telefono 2 a la sucursal 
     * @param dir inserta la direccion a la sucursal
     * @param cor inserta el correo a la sucursal 
     * 
     */
    public static void insertar(String id_suc,String nom,String com, String tel1, String telefono2,String dir,String cor){
        String sql = "INSERT INTO sucursal (nombre,id_comuna, direccion, correo)"
                                                + "VALUES (?,?,?,?)";
        String sql2 = "INSERT INTO tel_sucursal (id_sucursal, telefono, telefono2)"
                                                + "VALUES (?,?,?)";
        
        try{
            conn = BDConexion.getConexion();
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, nom);
            pst.setString(2, com);
            pst.setString(3, dir);
            pst.setString(4, cor);
            pst.execute();
            atl= conn.prepareStatement(sql2);
            atl.setString(1, id_suc);
            atl.setString(2, tel1);
            atl.setString(3, telefono2);
            atl.executeUpdate();
            JOptionPane.showMessageDialog(null,"Registro almacenado correctamente","INGRESO",1);
        }catch(Exception e){
            System.err.println("Excepción al agregar: " + e);
        }
    }
    /**
     * 
     * Método para modificar sucursal de la base de datos
     * @param id inserta el id a la sucursal automaticamente
     * @param com inserta la comuna a la sucursl 
     * @param telefono inserta el telefono 1 a la sucursal 
     * @param telefono2 inserta el telefono 2 a la sucursal 
     * @param dir inserta la direccion a la sucursal
     * @param cor inserta el correo a la sucursal 
     * 
     */
    public static void modificar(int id, String nom,String com,String telefono,String telefono2, String dir, String cor){
        String sql = "UPDATE sucursal SET nombre=?,id_comuna = ?, direccion= ?, correo = ? " + "WHERE " +id+ "= id_sucursal";
        String sql2 = "UPDATE tel_sucursal SET id_sucursal = ?, telefono=?, telefono2 = ? " + "WHERE " +id+ "= id_telsuc";
        try{
            conn = BDConexion.getConexion();
            pst = conn.prepareStatement(sql);
            pst.setString(1, nom);
            pst.setString(2, com);
            pst.setString(3, dir);
            pst.setString(4, cor);
            pst.execute();
            atl=conn.prepareStatement(sql2);
            atl.setString(1,Integer.toString(id));
            atl.setString(2, telefono);
            atl.setString(3, telefono2);
            atl.execute();
            JOptionPane.showMessageDialog(null,"Registro almacenado correctamente","INGRESO",1);
        }catch(Exception e){
            System.err.println("Excepción al modificar: " + e);
        }
    }
     /**
     * 
     * Metódo para filtrar datos desde la base de datos a la tabla instanciada según criterio de búsqueda
     * @param tabla parametro de tabla
     * @param criterio parametro de criterio
     * @param texto parametro de texto
     * 
     */
    public static void filtrar(JTable tabla, String criterio, String texto){
        tm = new DefaultTableModel();
        tm.addColumn("Sucursal");
        tm.addColumn("Comuna");
        tm.addColumn("Telefono");
        tm.addColumn("Telefono2");        
        tm.addColumn("Direccion");
        tm.addColumn("Correo");
        
        //Se genera consulta SQL para seleccionar datos de la tabla segun campo y criterio
        String sql = "SELECT sucursal.id_sucursal, comuna.nom_comuna,tel_sucursal.telefono,tel_sucursal.telefono2, sucursal.direccion,sucursal.correo FROM sucursal INNER JOIN tel_sucursal ON tel_sucursal.id_sucursal=sucursal.id_sucursal INNER JOIN comuna ON sucursal.id_comuna=comuna.id_comuna WHERE sucursal." + criterio + " LIKE '%" + texto + "%' ";
        try{
            conn = BDConexion.getConexion();
            st = conn.prepareStatement(sql);          
            rs = st.executeQuery(sql);
            //Se obtiene cada registro y se agrega al modelo de tabla
            while(rs.next()){
                Object[] arr = new Object[6];
                arr[0] = rs.getInt(1);
                arr[1] = rs.getString(2);
                arr[2] = rs.getString(3);
                arr[3] = rs.getString(4); 
                arr[4] = rs.getString(5); 
                arr[5] = rs.getString(6); 
                tm.addRow(arr);                 
            }
            
            //Se carga modelo de tabla a la tabla principal
            tabla.setModel(tm);
        }catch(Exception e){
            System.err.println("Excepción al filtrar: " + e);
        }        
    }
    /**
     * 
     * Método para eliminar un registro de la base de datos
     * @param id  elimina el id del registro
     * 
     */
    public static void eliminar(int id){
        //String sql = "UPDATE Sucursal SET id_sucursal = 'null',id_comuna='null',direccion='null',correo='null' WHERE id_sucursal = ?"; 
        String sql = "DELETE FROM Sucursal WHERE id_sucursal = ?"; 
        try{
            conn = BDConexion.getConexion();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            pst.execute();
            JOptionPane.showMessageDialog(null,"Registro eliminado correctamente","INGRESO",1);
        }catch(Exception e){
            System.err.println("Excepción al eliminar: " + e);
        }
    }
}
