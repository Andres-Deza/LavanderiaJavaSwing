/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

//import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Clase que recibe una instancia del Panel y recorre cada elemento y lo deja vacío, aplica a campos de texto, combo box y campos de password
 * @author tridente
 */
public class Limpiar {
    
    /**
     * Método para limpiar los componentes de un JPanel (JTextField)
     * @param panel parametro de panel
     */
    public static void contenidoPanel(JPanel panel){
        for(int i = 0; panel.getComponents().length > i; i++){
            if(panel.getComponents()[i] instanceof JTextField){
                ((JTextField)panel.getComponents()[i]).setText("");
            }else if(panel.getComponents()[i] instanceof JPasswordField){
                ((JPasswordField)panel.getComponents()[i]).setText("");
            }else if(panel.getComponents()[i] instanceof JComboBox){
                ((JComboBox)panel.getComponents()[i]).setSelectedIndex(-1);
            }
        }
    }
}
