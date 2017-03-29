/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softcont.prsftmaven.controlador;

import com.softcont.prsftmaven.modelo.DAOCliente;
import com.softcont.prsftmaven.vista.VCliente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Controlador de la vista Cliente
 * @author Jose Salgado
 */
public class ControladorCliente implements ActionListener{
    VCliente vista;
    DAOCliente modelo;
    public static boolean visible = false;
    
    /**
     * Metodo Constructor de la clase ControladorCliente
     * @param vista Interfaz VCliente
     * @param modelo Clase DAO
     */
    public ControladorCliente(VCliente vista, DAOCliente modelo){
        this.modelo = modelo;
        this.vista = vista;
        vista.setLocationRelativeTo(null);
        
        vista.btnRegistrar.addActionListener(this);
        vista.btnModificar.addActionListener(this);
        vista.btnBuscar.addActionListener(this);
        vista.btnNuevo.addActionListener(this);
        
        if (visible==false) {
            vista.setVisible(true);
            visible = true;
        }else
            JOptionPane.showMessageDialog(vista, "Ventana actualmente en uso");
        Logger.getLogger(ControladorCliente.class.getName()).log(Level.OFF, null, visible);
    }
    
    
    /**
     * Limpia campos de la vista cliente
     */
    
    public void limpiar(){
    vista.txtId.setText("");
    vista.txtNombre.setText("");
    vista.txtDireccion.setText("");
    vista.txtTelefono.setText("");
    vista.txtMail.setText("");  
    }
    
    /**
     * Bloquea campos de la vista cliente
     */
    
    public void bloquear(){
        vista.btnRegistrar.setEnabled(true);
        vista.btnModificar.setEnabled(false);
        vista.btnNuevo.setEnabled(false);
        vista.btnBuscar.setEnabled(true);
    }
    
    /**
     * Se encarga de validar que los campos no esten vacios
     * @return true en caso de que sea verdadero
     */
    
    public boolean validarCampos(){
        boolean validar = false;
        if ("".equals(vista.txtId.getText()) && "".equals(vista.txtNombre.getText()) && "".equals(vista.txtTelefono.getText())
                && "".equals(vista.txtDireccion.getText())) {
            JOptionPane.showMessageDialog(vista, "Error, Rellene Campos");
        }else if ("".equals(vista.txtId.getText())) {
            JOptionPane.showMessageDialog(vista, "Inserte Identificacion");
        }else if ("".equals(vista.txtNombre.getText())) {
            JOptionPane.showMessageDialog(vista, "Inserte Nombre");
        }else if ("".equals(vista.txtTelefono.getText())) {
            JOptionPane.showMessageDialog(vista, "Inserte Telefono");
        }else if ("".equals(vista.txtDireccion.getText())) {
            JOptionPane.showMessageDialog(vista, "Inserte Direccion");
        }else
            validar = true;
        return validar;
    }
    
    /**
     * Metodo encargado de los eventos de la vista VCliente
     * @param e Objeto ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource()==vista.btnRegistrar) {
            if (validarCampos()) {
                try {
                modelo.registrarCliente(vista.txtNombre, vista.txtId, vista.txtTelefono, vista.txtDireccion, vista.txtMail);
                limpiar();
                modelo.tabla("", vista.tbcliente);
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            
        }
        
        if (e.getSource()==vista.btnModificar) {
            try {
                modelo.modificarCliente(vista.txtNombre, vista.txtDireccion, vista.txtTelefono, vista.txtMail, vista.txtId, vista.tbcliente);
                modelo.tabla("", vista.tbcliente);
                limpiar();
                vista.btnNuevo.setEnabled(true);
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(ControladorCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (e.getSource()==vista.btnNuevo) {
            bloquear();
        }
        
        if (e.getSource()==vista.btnBuscar) {
            modelo.buscarCliente(vista.txtBuscar, vista.tbcliente);
            vista.txtBuscar.setText("");
        }
        
    }
    
    
}
