/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softcont.prsftmaven.modelo;

import com.softcont.prsftmaven.vista.VCliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Clase DAO encargada de toda la gestion de la interfaz Cliente
 *
 * @author Gerlis A.C
 */
public class DAOCliente {

    Connection con;
    VCliente vista;
    ResultSet t;
    PreparedStatement pst;

    //Sentecnias SQL
    final String SQL_SEARCH1 = "SELECT * FROM cliente";
    final String SQL_SEARCH2 = "SELECT * FROM cliente WHERE nombreCO LIKE CONCAT(?,'%')";
    final String SQL_INSERT = "INSERT INTO cliente  VALUES (?, ?, ?, ?, ?)";
    final String SQL_UPDATE = "UPDATE cliente SET nombreCO= ?, telefonoCliente= ?, dirCliente= ?,emailCliente= ? WHERE idCliente=?";

    /**
     * Constructor de la clase DAOCliente
     *
     * @throws InstantiationException Throws
     * @throws IllegalAccessException Throws
     */
    public DAOCliente() throws InstantiationException, IllegalAccessException {
        con = Conexion.getConnection();
    }

    /**
     * Metodo encargado de mostrar informacion en la tabla de la interfaz
     * Cliente
     *
     * @param cadena Nombre de cliente
     * @param tbcliente JTable
     * @throws InstantiationException Throws
     * @throws IllegalAccessException Throws
     */
    public void tabla(String cadena, JTable tbcliente) throws InstantiationException, IllegalAccessException {

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("IDENTIFICACION");
        model.addColumn("NOMBRE");
        model.addColumn("DIRRECCION");
        model.addColumn("TELEFONO");
        model.addColumn("EMAIL");
        tbcliente.setModel(model);
        try {
            //  comparacion para mostrar datos de todas las columnas

            if (cadena.equals("")) {
                pst = con.prepareStatement(SQL_SEARCH1);
            } else {
                pst = con.prepareStatement(SQL_SEARCH2);
                pst.setString(1, cadena);
            }

            String[] datos = new String[5];
            t = pst.executeQuery();
            while (t.next()) {
                datos[0] = t.getString(1);
                datos[1] = t.getString(2);
                datos[2] = t.getString(4);
                datos[3] = t.getString(3);
                datos[4] = t.getString(5);
                model.addRow(datos);
            }
            tbcliente.setModel(model);
        } catch (SQLException e) {
            Logger.getLogger(DAOCliente.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(new VCliente(), e.getMessage());
        } finally {
            try {
                pst.close();
                t.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo encargado de registrar informacion de un cliente
     *
     * @param nombre Nombre de cliente
     * @param id Numero de identificacion de cliente
     * @param telefono Numero de telefono de cliente
     * @param dirreccion Direccion de cliente
     * @param email email de cliente
     */
    public void registrarCliente(JTextField nombre, JTextField id, JTextField telefono, JTextField dirreccion, JTextField email) {
        try {
            pst = con.prepareStatement(SQL_INSERT);
            pst.setString(1, id.getText());
            pst.setString(2, nombre.getText());
            pst.setString(3, telefono.getText());
            pst.setString(4, dirreccion.getText());
            pst.setString(5, email.getText());
            int i = pst.executeUpdate();
            if (i > 0) {
                JOptionPane.showMessageDialog(vista, "Registrado Correctamente");
            } else {
                JOptionPane.showMessageDialog(vista, "Ocurrio un error durante el registro");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCliente.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(vista, "Ocurrio un Error en: " + ex.getMessage());
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo para modificar informacion de un cliente
     *
     * @param nombre Nombre de cliente
     * @param dirreccion Direccion de cliente
     * @param telefono Numero de telefono de cliente
     * @param mail Email de cliente
     * @param id Numero de identificacion de cliente
     * @param tabla JTable
     */
    public void modificarCliente(JTextField nombre, JTextField dirreccion, JTextField telefono, JTextField mail, JTextField id, JTable tabla) {
        try {
            pst = con.prepareStatement(SQL_UPDATE);
            pst.setString(1, nombre.getText());
            pst.setString(2, telefono.getText());
            pst.setString(3, dirreccion.getText());
            pst.setString(4, mail.getText());
            pst.setString(5, id.getText());
            int i = pst.executeUpdate();
            if (i > 0) {
                JOptionPane.showMessageDialog(vista, "Actualizado Correctamente");
            } else {
                JOptionPane.showMessageDialog(vista, "Ocurrio un error durante la Actualizacion");
            }
            tabla("", tabla);
        } catch (SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DAOCliente.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(vista, "Ocurrio un Error en: " + ex.getMessage());
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo para buscar cliente
     *
     * @param buscar Nombre de cliente
     * @param tabla JTable
     */
    public void buscarCliente(JTextField buscar, JTable tabla) {
        try {
            tabla(buscar.getText(), tabla);
            buscar.setText("");
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DAOCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
