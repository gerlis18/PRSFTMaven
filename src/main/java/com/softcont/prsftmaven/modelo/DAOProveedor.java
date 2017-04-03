/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softcont.prsftmaven.modelo;

import com.softcont.prsftmaven.vista.VProveedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Clase encargada de toda la gestion de la interfaz <i>VProveedor</i>
 *
 * @author by Gerlis A.C
 */
public class DAOProveedor {

    Connection con;
    ResultSet rs;
    PreparedStatement pst;

    // Consultas SQL
    final String SQL_INSERT = "INSERT INTO proveedores VALUES (?,?,?,?,?)";
    final String SQL_UPDATE = "UPDATE proveedores SET nomProveedor=?, telProveedor=?, emailProveedor=?, dirProveedor=? WHERE nitProveedor=?";
    final String SQL_DELETE = "DELETE FROM proveedores WHERE nitProveedor=?";
    final String SQL_SEARCH1 = "SELECT * FROM proveedores";
    final String SQL_SEARCH2 = "SELECT * FROM  proveedores  WHERE nomProveedor LIKE CONCAT(?,'%')";

    /**
     * Constructor de la clase
     *
     * @throws InstantiationException Throws
     * @throws IllegalAccessException Throws
     */
    public DAOProveedor() throws InstantiationException, IllegalAccessException {
        con = Conexion.getConnection();
    }

    /**
     * Metodo encargado de mostrar informacion de proveedor en su respectiva
     * tabla
     *
     * @param cadena Nombre de proveedor
     * @param tbProveedor Tabla de la interfaz proveedor
     */
    public void visualisarardatos(String cadena, JTable tbProveedor) {
        //variable de tipo defaault
        DefaultTableModel model = new DefaultTableModel();
        // dar nombre de columnas
        model.addColumn("NIT");
        model.addColumn("Nombre");
        model.addColumn("Telefono");
        model.addColumn("Email");
        model.addColumn("Direccion");

        //imprimir titulos de columnas
        tbProveedor.setModel(model);
        //sentencia a ejecutar para buuscar la consilta
        //comparacion  para mostrar todo los datos que es tan en la tabla de bd
        try {
            if (cadena.equals("")) {
                pst = con.prepareStatement(SQL_SEARCH1);
            } else {
                //Comparacion del dato que se vaa a enviar en jlabel buscar
                pst = con.prepareStatement(SQL_SEARCH2);
                pst.setString(1, cadena);
            }
            //vector para mostrar los datos de la consulta
            String[] mdatos = new String[5];
            //mostrar datos de bd a nb

            //almacenar los datos de la consulta en nb
            rs = pst.executeQuery();
            //mostrar datos de la consulta
            while (rs.next()) {
                //dar valor a los datos
                mdatos[0] = rs.getString(1);
                mdatos[1] = rs.getString(2);
                mdatos[2] = rs.getString(3);
                mdatos[3] = rs.getString(4);
                mdatos[4] = rs.getString(5);
                //agrgar columna
                model.addRow(mdatos);

            }
            //visualuisar datos en la tabla
            //tbProveedor.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(VProveedor.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOProveedor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Metodo encargado de registrar informacion de proveedor
     *
     * @param Nit Nit de proveedor
     * @param Nombre Nombre de proveedor
     * @param Telefono Telefono de proveedor
     * @param Email Email de proveedor
     * @param Direccion Direccion de proveedor
     * @param tbProveedor Tabla de la interfaz proveedor
     * @return True en caso de cumplirse el bloque "try"
     */
    public boolean registrarProveedor(String Nit, String Nombre, String Telefono, String Email, String Direccion, JTable tbProveedor) {
        boolean valida = false;
        try {
            pst = con.prepareStatement(SQL_INSERT);
            pst.setString(1, Nit);
            pst.setString(2, Nombre);
            pst.setString(3, Telefono);
            pst.setString(4, Email);
            pst.setString(5, Direccion);
            //ejecutarsentencia
            int i = pst.executeUpdate();
            if (i > 0) {
                return valida = true;
            }
            //pára visualisar los datos en la tabla
        } catch (SQLException ex) {
            Logger.getLogger(DAOProveedor.class.getName()).log(Level.SEVERE, null, ex);
            valida = false;

        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOProveedor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return valida;
    }

    /**
     * Metodo encargado de modificar o actualizar informacion de proveedor
     *
     * @param Nombre Nombre de proveedor
     * @param Telefono Telefono de proveedor
     * @param Email Email de proveedor
     * @param Direccion Direccion de proveedor
     * @param Nit Nit de proveedor
     * @param tbProveedor Tabla de la interfaz proveedor
     * @return True en caso que se cumpla el bloque "try"
     */
    public boolean modificarProveedor(String Nombre, String Telefono, String Email, String Direccion, String Nit, JTable tbProveedor) {
        boolean valida = false;
        try {
            pst = con.prepareStatement(SQL_UPDATE);
            pst.setString(1, Nombre);
            pst.setString(2, Telefono);
            pst.setString(3, Email);
            pst.setString(4, Direccion);
            pst.setString(5, Nit);
            int i = pst.executeUpdate();
            if (i > 0) {
                return valida = true;
            }
            //visualisar en la tabla lo ingresado (actualizacion)
        } catch (SQLException e) {
            Logger.getLogger(DAOProveedor.class.getName()).log(Level.SEVERE, null, e);
            System.out.print(e.getMessage());
            valida = false;
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOProveedor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return valida;
    }

    /**
     * Metodo encargado de buscar todos los proveedores registrados
     *
     * @param buscar Cadena de texto o nombre de proveedor
     * @param tbProveedor Tabla de la interfaz proveedor
     */
    public void buscarProveedor(String buscar, JTable tbProveedor) {
        //para buscaar en la base de datos en lo ingresado por (txtbuscar)
        if (!"".equals(buscar)) {
            visualisarardatos(buscar, tbProveedor);
        }

    }

    /**
     * Meodo encargado de eliminar toda la informacion de proveedor
     *
     * @param nitProveedor Nit de proveedor
     * @return True en caso que se elimine proveedor
     */
    public boolean eliminarProveedor(String nitProveedor) {
        boolean valida = false;
        try {
            pst = con.prepareStatement(SQL_DELETE);
            pst.setString(1, nitProveedor);
            int i = pst.executeUpdate();
            if (i > 0) {
                valida = true;
            }
        } catch (SQLIntegrityConstraintViolationException sql){
                        JOptionPane.showMessageDialog(null, "Este proveedor ya ha se registrado en compras");
                    } 
        catch (SQLException ex) {
            Logger.getLogger(DAOProveedor.class.getName()).log(Level.SEVERE, null, ex);
            valida = false;
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOProveedor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return valida;
    }
}
