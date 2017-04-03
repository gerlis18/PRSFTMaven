/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softcont.prsftmaven.modelo;

import com.softcont.prsftmaven.vista.VTablaClientes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Clase encargada mostrar informacion de cliente para posteriormente añadirla a
 * una venta
 *
 * @author by Gerlis A.C
 */
public class DAOTCliente {

    Connection cn;
    PreparedStatement pst;
    ResultSet rs;

    final String SQL_SEARCH = "SELECT * FROM cliente";
    
    public DAOTCliente() throws InstantiationException, IllegalAccessException {
        cn = Conexion.getConnection();
    }

    /**
     * Metodo encargado de mostrar informacion de cliente en su respectiva tabla
     *
     * @param jTable1 Tabla de la interfaz TCliente
     */
    public void cargar(JTable jTable1) {
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Nombre");
            model.addColumn("Telefono");
            model.addColumn("Direccion");
            model.addColumn("Email");
            jTable1.setModel(model);

            String[] datos = new String[6];
            pst = cn.prepareStatement(SQL_SEARCH);
            rs = pst.executeQuery();
            while (rs.next()) {
                datos[0] = rs.getString("idCliente");
                datos[1] = rs.getString("nombreCO");
                datos[2] = rs.getString("telefonoCliente");
                datos[3] = rs.getString("dirCliente");
                datos[4] = rs.getString("emailCliente");
                model.addRow(datos);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VTablaClientes.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOTCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
