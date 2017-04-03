/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softcont.prsftmaven.modelo;

import com.softcont.prsftmaven.utils.GenerarIdVenta;
import com.softcont.prsftmaven.vista.VVentas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Clase encargada de toda la gestion de la interfaz <i>VVentas</i>
 *
 * @author by Gerlis A.C
 */
public class DAOVenta {

    Connection cn;
    PreparedStatement pst;
    ResultSet rs;
    DefaultTableModel model;
    VVentas vista;
    
    //Sentencias SQL
    final String SQL_SEARCH1 = "SELECT * FROM productos WHERE nomProducto=?;";
    final String SQL_SEARCH2 = "SELECT MAX(numFactura) AS numFactura FROM factura";
    final String SQL_INSERT1 = "INSERT INTO factura VALUES (?,?,?,?)";
    final String SQL_INSERT2 = "INSERT INTO detalle VALUES(?,?,?,?)";
    final String SQL_UPDATE = "UPDATE factura SET estadoFactura = 'Anulado' where numFactura = ?";
    final String SQL_SEARCH3 = "SELECT `nomProducto` FROM productos WHERE `nomProducto` LIKE CONCAT(?,'%');";
    public DAOVenta() throws InstantiationException, IllegalAccessException {
        cn = Conexion.getConnection();
    }

    /**
     * Metodo encargado de generar codigos de venta
     *
     * @param txtidventa TextField codigo de venta
     */
    public void idventa(JTextField txtidventa) {
        String c = "";
        try {
            pst = cn.prepareStatement(SQL_SEARCH2);
            rs = pst.executeQuery();
            if (rs.next()) {
                c = rs.getString("numFactura");
            }
            System.out.print(c);

            if (c == null) {
                txtidventa.setText("000001");
            } else {
                char r1 = c.charAt(2);
                char r2 = c.charAt(3);
                char r3 = c.charAt(4);
                char r4 = c.charAt(5);
                System.out.print("" + r1 + r2 + r3 + r4);
                String juntar = "" + r1 + r2 + r3 + r4;
                int var = Integer.parseInt(juntar);
                System.out.print("\n este lo que vale numericamente" + var);
                GenerarIdVenta gen = new GenerarIdVenta();
                gen.generar(var);
                txtidventa.setText(gen.serie());
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOVenta.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOVenta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo encargado de mostrar datos del producto en su respectiva tabla
     *
     * @param jTable1 Jtable de la interfaz VVentas
     * @param jcBuscar JComboBox Buscar producto
     */
    public void cargar(JTable jTable1, JComboBox jcBuscar) {
        model = new DefaultTableModel();
        model.addColumn("Codigo");
        model.addColumn("Nombre");
        model.addColumn("Cantidad");
        model.addColumn("Valor Unt.");
        model.addColumn("Valor Total");
        jTable1.setModel(model);
        try {
            String[] datos = new String[5];
            pst = cn.prepareStatement(SQL_SEARCH1);
            pst.setString(1, jcBuscar.getSelectedItem().toString());
            rs = pst.executeQuery();
            while (rs.next()) {
                datos[0] = rs.getString("codProducto");
                datos[1] = rs.getString("nomProducto");
                datos[2] = rs.getString("cantProducto");
                datos[3] = rs.getString("precioProducto");
                datos[4] = "0.0";
                model.addRow(datos);
            }
        } catch (SQLException | NullPointerException ex) {
            Logger.getLogger(DAOVenta.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(vista, ex.getMessage());
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOVenta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo para remover productos de la tabla
     *
     * @param tabla Tabla de la interfaz
     */
    public void removerProducto(JTable tabla) {

        for (int j = 0; j < tabla.getRowCount(); j++) {
            ((DefaultTableModel) tabla.getModel()).removeRow(j);
        }

    }

    /**
     * Metodo encargado de registrar datos basicos de ventas
     *
     * @param numFactura Numero de factura
     * @param Cliente Numero de identificacion de cliente
     * @param fecha Fecha del dia que se realizó la venta
     */
    public void registrarVenta(String numFactura, String Cliente, String fecha) {
        try {
            pst = cn.prepareStatement(SQL_INSERT1);
            pst.setString(1, numFactura);
            pst.setString(2, Cliente);
            pst.setString(3, fecha);
            pst.setString(4, "Activa");
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOVenta.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOVenta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo encargado de registrar el detalle de cada venta
     *
     * @param numFactura Numero de factura
     * @param codProducto Codigo de producto
     * @param Cantidad Cantidad de Producto
     * @param precio Precio de venta de producto
     */
    public void registrarDetalleVenta(String numFactura, String codProducto, String Cantidad, String precio) {
        try {
            pst = cn.prepareStatement(SQL_INSERT2);
            pst.setString(1, numFactura);
            pst.setString(2, codProducto);
            pst.setString(3, Cantidad);
            pst.setString(4, precio);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOVenta.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOVenta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo encargado de buscar y mostrar productos por su nombre
     *
     * @param cadena Nombre de producto
     * @return Modelo para JComboBox de buscar productos
     */
    public DefaultComboBoxModel obtenerLista(String cadena) {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        try {
            pst = cn.prepareStatement(SQL_SEARCH3);
            pst.setString(1, cadena);
            rs = pst.executeQuery();
            modelo.removeAllElements();
            while (rs.next()) {
                modelo.addElement(rs.getString("nomProducto"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOVenta.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(vista, ex.getMessage());
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOVenta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return modelo;
    }

    /**
     * Metodo encargado de actualizar el estado de una venta
     *
     * @param numFactura Numero de Factura
     */
    public void actualizarEstado(String numFactura) {
        try {
            pst = cn.prepareStatement(SQL_UPDATE);
            pst.setString(1, numFactura);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOVenta.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOVenta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
