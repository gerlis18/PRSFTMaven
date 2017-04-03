/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softcont.prsftmaven.controlador;

import com.softcont.prsftmaven.modelo.DAOISesion;
import com.softcont.prsftmaven.vista.VISesion;
import com.softcont.prsftmaven.vista.VMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Clase encargada de validar usuarios 
 * @author Gerlis A.C
 */

public class ControladorISesion implements ActionListener{
    VISesion vista;
    DAOISesion modelo;
    VMenu vistaInicio;
    ControladorMenu inicio;
    public ControladorISesion(VISesion vista, DAOISesion modelo){
        this.vista=vista;
        this.modelo=modelo;
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
        vista.btnEntrar.addActionListener(this);
        vista.btnSalir.addActionListener(this);
        vista.txtPass.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPassKeyPressed(evt);
            }
        });
    }
    
    /**
     * Se encarga conrolar los eventos de la vista Iniciar Sesion
     * @param e Objeto ActionEvent
     */
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource()==vista.btnEntrar) {
            String user,pass;
        user = vista.txtUser.getText();
        pass = String.valueOf(vista.txtPass.getPassword());
            if(modelo.acceder(user, pass)==true){
                try {
                    vista.setVisible(false);
                    
                    String tipouser = modelo.getTpUsuario();
                    //JOptionPane.showMessageDialog(null, tipouser);
                    vistaInicio= new VMenu();
                        
                    switch(tipouser){
                        case "Secretaria(o)" :
                            inicio = new ControladorMenu(vistaInicio);
                            break;
                        case "Vendedor" :
                            inicio = new ControladorMenu(vistaInicio);
                            break;
                        case "Administrador" : 
                            inicio = new ControladorMenu(vistaInicio);
                            break;
                        default :
                            JOptionPane.showMessageDialog(vista, "Usuario no encontrado");
                    }
                    
                } catch (SQLException | InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(ControladorISesion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        if (e.getSource()==vista.btnSalir) {
            modelo.cerrarConexion();
            System.exit(0);
        }
        
        
    }
    /**
     * Se encarga de Controlar un evento de una tecla
     * @param evt Objeto KeyEvent
     */
    public void txtPassKeyPressed(KeyEvent evt){
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            String user,pass;
        user = vista.txtUser.getText();
        pass = String.valueOf(vista.txtPass.getPassword());
            if(modelo.acceder(user, pass)==true){
                try {
                    vista.setVisible(false);
                    
                    String tipouser = modelo.getTpUsuario();
                    //JOptionPane.showMessageDialog(null, tipouser);
                    vistaInicio= new VMenu();
                    switch(tipouser){
                        case "Secretaria(o)" :
                            inicio = new ControladorMenu(vistaInicio);
                            break;
                        case "Vendedor" :
                            inicio = new ControladorMenu(vistaInicio);
                            break;
                        case "Administrador" : 
                            inicio = new ControladorMenu(vistaInicio);
                            break;
                        default :
                            JOptionPane.showMessageDialog(vista, "Usuario no encontrado");
                    }
                } catch (SQLException | InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(ControladorISesion.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
    }
    
}
