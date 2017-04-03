/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softcont.prsftmaven.modelo;

import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author Gerlis A.C
 */
public class DAOInvCompras {

    Conexion con;
    Connection cn;
    PreparedStatement pst;
    ResultSet rs;
    
    final String SQL_SEARCH = "SELECT * FROM detallecompra where compra_idcompra=?";
    final String SQL_DELETE = "DELETE FROM detallecompra where compra_idcompra=?";
    final String SQL_UPDATE = "UPDATE productos SET cantProducto = ? WHERE codProducto = ?";
    /**
     * 
     * @throws InstantiationException Throws
     * @throws IllegalAccessException Throws 
     */
    public DAOInvCompras() throws InstantiationException, IllegalAccessException {
        cn = Conexion.getConnection();
    }
    /**
     * Metodo encargado de anular compras 
     * @param tabla JTable
     * @param vista Interfaz 
     */
    public void anularCompra(JTable tabla, Component vista) {
        try {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                String idCompra = tabla.getValueAt(fila, 0).toString();
                pst = cn.prepareStatement(SQL_SEARCH);
                pst.setString(1, idCompra);
                rs = pst.executeQuery();
                String datos[] = new String[4];
                while (rs.next()) {

                    String cantidadPcompra = rs.getString("cantidad");
                    int cantpc = Integer.parseInt(cantidadPcompra);
                    int cantidadProducto = new DAOProducto().buscarCantProducto(rs.getString("Productos_codProducto"));
                    Integer cantidadTotal = cantidadProducto - cantpc;

                    regresarCantProducto(rs.getString("Productos_codProducto"), cantidadTotal.toString());
                }
                
                
                pst = cn.prepareStatement(SQL_DELETE);
                pst.setString(1, idCompra);

                int valida = pst.executeUpdate();
                if (valida > 0) {
                    new DAOcompras().actualizarEstado(idCompra);
                    JOptionPane.showMessageDialog(vista, "Anulado correctamente");
                } else {
                    JOptionPane.showMessageDialog(vista, "Ocurrio un error");
                }

            } else {
                JOptionPane.showMessageDialog(vista, "Elija fila primero...");
            }
        } catch (SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DAOInvCompras.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOInvCompras.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Metodo para regresar productos vendidos a la tabla productos
     * @param codProducto Codigo de producto
     * @param cantidad Cantidad de producto
     */
    public void regresarCantProducto(String codProducto, String cantidad) {
        try {
            pst = cn.prepareStatement(SQL_UPDATE);
            pst.setString(1, cantidad);
            pst.setString(2, codProducto);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOInvVentas.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOInvVentas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
}
