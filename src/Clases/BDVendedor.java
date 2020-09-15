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
 * @author andre
 */
public class BDVendedor {
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
        tm.addColumn("ID");
        tm.addColumn("Rut");
        tm.addColumn("Nombre");
        tm.addColumn("Apellido Paterno");
        tm.addColumn("Apellido Materno");
        tm.addColumn("Direccion");        
        tm.addColumn("Telefono");
        tm.addColumn("Comuna");
        tm.addColumn("Ciudad");
        tm.addColumn("Region");
        tm.addColumn("Sucursal");
        //Se genera consulta SQL para seleccionar todos los datos de la tabla:
        String sql = "SELECT vendedor.id_vendedor,vendedor.rut,vendedor.nombre,vendedor.apellido_p,vendedor.apellido_m,vendedor.direccion, vendedor.telefono,comuna.nom_comuna,ciudad.nom_ciudad,region.nom_region, sucursal.nombre FROM sucursal INNER JOIN comuna ON sucursal.id_comuna=comuna.id_comuna INNER JOIN ciudad ON comuna.id_ciudad=ciudad.id_ciudad INNER JOIN region ON ciudad.id_region=region.id_region INNER JOIN vendedor ON sucursal.id_sucursal=vendedor.id_sucursal";
        try{
            conn = BDConexion.getConexion();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            //Se obtiene cada registro y se agrega al modelo de tabla
            while(rs.next()){
                Object[] arr = new Object[11];
                arr[0] = rs.getInt(1);
                arr[1] = rs.getString(2);
                arr[2] = rs.getString(3);
                arr[3] = rs.getString(4); 
                arr[4] = rs.getString(5); 
                arr[5] = rs.getString(6); 
                arr[6] = rs.getString(7); 
                arr[7] = rs.getString(8); 
                arr[8] = rs.getString(9); 
                arr[9] = rs.getString(10);
                arr[10] = rs.getString(11); 
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
     * Método para agregar un nuevo vendedor a la base de datos
     * 
     * @param rut inserta rut
     * @param nom inserta nombre
     * @param pat inserta el apellido paterno
     * @param mat inserta el apellido materno
     * @param dir inserta la direccion  
     * @param tel inserta el telefono
     * @param com inserta la comuna
     * @param suc inserta el sucursal
     * 
     */
    //(id,rut,nom,pat,mat,dir,tel,com,suc)
    public static void insertar(String rut,String nom, String pat, String mat,String dir,String tel,String com,String suc){
        String sql = "INSERT INTO vendedor (rut, nombre,apellido_p,apellido_m,direccion,telefono,id_comuna,id_sucursal )"
                                                + "VALUES (?,?,?,?,?,?,?,?)";
        
        try{
            conn = BDConexion.getConexion();
            pst = conn.prepareStatement(sql);
            pst.setString(1, rut);
            pst.setString(2, nom);
            pst.setString(3, pat);
            pst.setString(4, mat);
            pst.setString(5, dir);
            pst.setString(6, tel);
            pst.setString(7, com);
            pst.setString(8, suc);
            
            pst.execute();
            
            JOptionPane.showMessageDialog(null,"Registro almacenado correctamente","INGRESO",1);
        }catch(Exception e){
            System.err.println("Excepción al agregar: " + e);
        }
    }
    /**
     * 
     * Método para modificarvendedor de la base de datos
     * @param id inserta rut
     * @param rut inserta rut
     * @param nom inserta nombre
     * @param pat inserta el apellido paterno
     * @param mat inserta el apellido materno
     * @param dir inserta la direccion  
     * @param tel inserta el telefono
     * @param com inserta la comuna
     * @param suc inserta el sucursal
     * 
     */
    public static void modificar(String id,String rut,String nom, String pat, String mat,String dir,String tel,String com,String suc){
        String sql = "UPDATE vendedor SET rut = ?, nombre= ?, apellido_p = ?, apellido_m = ?, direccion=?, telefono= ?, id_comuna= ?, id_sucursal= ? " + "WHERE " +id+ "= id_vendedor";
        try{
            conn = BDConexion.getConexion();
            pst = conn.prepareStatement(sql);
            pst.setString(1, rut);
            pst.setString(2, nom);
            pst.setString(3, pat);
            pst.setString(4, mat);
            pst.setString(5, dir);
            pst.setString(6, tel);
            pst.setString(7, com);
            pst.setString(8, suc);
            
            pst.execute();
            
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
        tm.addColumn("ID");
        tm.addColumn("Rut");
        tm.addColumn("Nombre");
        tm.addColumn("Apellido Paterno");
        tm.addColumn("Apellido Materno");
        tm.addColumn("Direccion");        
        tm.addColumn("Telefono");
        tm.addColumn("Comuna");
        tm.addColumn("Ciudad");
        tm.addColumn("Region");
        tm.addColumn("Sucursal_Direccion");
        
        //Se genera consulta SQL para seleccionar datos de la tabla segun campo y criterio
        String sql = "SELECT vendedor.id_vendedor,vendedor.rut,vendedor.nombre,vendedor.apellido_p,vendedor.apellido_m,vendedor.direccion, vendedor.telefono,comuna.nom_comuna,ciudad.nom_ciudad,region.nom_region, sucursal.direccion FROM sucursal INNER JOIN comuna ON sucursal.id_comuna=comuna.id_comuna INNER JOIN ciudad ON comuna.id_ciudad=ciudad.id_ciudad INNER JOIN region ON ciudad.id_region=region.id_region INNER JOIN vendedor ON sucursal.id_sucursal=vendedor.id_sucursal WHERE vendedor." + criterio + " LIKE '%" + texto + "%' ";
        try{
            conn = BDConexion.getConexion();
            st = conn.prepareStatement(sql);          
            rs = st.executeQuery(sql);
            //Se obtiene cada registro y se agrega al modelo de tabla
            while(rs.next()){
                Object[] arr = new Object[11];
                arr[0] = rs.getInt(1);
                arr[1] = rs.getString(2);
                arr[2] = rs.getString(3);
                arr[3] = rs.getString(4); 
                arr[4] = rs.getString(5); 
                arr[5] = rs.getString(6); 
                arr[6] = rs.getString(7); 
                arr[7] = rs.getString(8); 
                arr[8] = rs.getString(9); 
                arr[9] = rs.getString(10);
                arr[10] = rs.getString(11); 
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
        String sql = "DELETE FROM vendedor WHERE id_vendedor = ?"; 
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
