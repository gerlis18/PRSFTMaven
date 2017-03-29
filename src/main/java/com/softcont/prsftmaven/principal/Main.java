/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softcont.prsftmaven.principal;

import com.softcont.prsftmaven.controlador.ControladorISesion;
import com.softcont.prsftmaven.modelo.DAOISesion;
import com.softcont.prsftmaven.vista.VISesion;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Clase Principal o ejecutable
 * @author Gerlis Alvarez C.
 * @version 1.0
 */
public class Main {

    /**
    * Metodo constructor del metodo Main o principal
     * @param args Main
     * @throws InstantiationException Throws
     * @throws IllegalAccessException Throws
    */
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {

        try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

            VISesion vista = new VISesion();
            DAOISesion modelo = new DAOISesion();
            ControladorISesion ct = new ControladorISesion(vista, modelo);

        } catch (ClassNotFoundException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
