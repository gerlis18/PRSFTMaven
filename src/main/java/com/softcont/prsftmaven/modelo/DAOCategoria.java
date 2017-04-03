/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softcont.prsftmaven.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Clase encargada de gestionar las categorias en la interfaz de ventas
 *
 * @author Gerlis A.C
 */
public class DAOCategoria {

    Connection cn;
    Conexion con;
    PreparedStatement pst;
    ResultSet rs;

    //Sentencias SQL
    final String SQL_SEARCH = "select * from Categoria";
    final String SQL_INSERT = "Insert into Categoria value (?)";
    final String SQL_DELETE = "delete  from categoria where nombreCategoria=?";

    public DAOCategoria() throws InstantiationException, IllegalAccessException {
        cn = Conexion.getConnection();
    }

    /**
     * Metodo encargado de mostrar categorias existentes
     * @param jList1 Componente JList 
     */
    public void mostrar(JList jList1) {
        try {
            DefaultListModel modelo = new DefaultListModel();
            pst = cn.prepareStatement(SQL_SEARCH);
            rs = pst.executeQuery();
            while (rs.next()) {
                modelo.addElement(rs.getString(1));
                jList1.setModel(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategoria.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOCategoria.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo para crear una nueva categoria
     *
     * @param txtCategoria JTextField Categoria
     * @param jList1 JList Categoria
     * @param vista Interfaz
     */
    public void crearCategoria(JTextField txtCategoria, JList jList1, JDialog vista) {
        if (txtCategoria.getText().equals("")) {
            JOptionPane.showMessageDialog(vista, "Inserte datos");
        } else {
            try {
                pst = cn.prepareStatement(SQL_INSERT);
                pst.setString(1, txtCategoria.getText());
                pst.executeUpdate();
                txtCategoria.setText("");
                mostrar(jList1);
            } catch (SQLException ex) {
                Logger.getLogger(DAOCategoria.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    pst.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAOCategoria.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Metodo para eliminar cateogorias de productos
     *
     * @param jList1 JList Categoria
     * @param vista Interfaz 
     */
    public void eliminarCategoria(JList jList1, JDialog vista) {
        if (jList1.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione una categoria");
        } else {
            try {
                DefaultListModel modelo = new DefaultListModel();
                pst = cn.prepareStatement(SQL_DELETE);
                pst.setString(1, jList1.getSelectedValue().toString());
                pst.executeUpdate();
                jList1.setModel(modelo);
                mostrar(jList1);
            } catch (SQLException ex) {
                Logger.getLogger(DAOCategoria.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    pst.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAOCategoria.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
