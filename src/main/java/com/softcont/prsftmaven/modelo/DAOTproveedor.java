package com.softcont.prsftmaven.modelo;

import com.softcont.prsftmaven.vista.VTProveedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Clase encargada mostrar informacion de proveedor para posteriormente añadirla
 * a una compra
 *
 * @author by Gerlis A.C
 */
public class DAOTproveedor {

    Conexion cn;
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    final String SQL_SEARCH = "SELECT * FROM proveedores";
    
    /**
     * Metodo Constructor de la clase DAOTProveedor
     * @throws InstantiationException Throws
     * @throws IllegalAccessException Throws 
     */
    public DAOTproveedor() throws InstantiationException, IllegalAccessException {
        con = Conexion.getConnection();
    }

    /**
     * Metodo encargado de mostrar informacion de proveedor en su respectiva
     * tabla
     *
     * @param tbproveedores Tabla de la interfaz TProveedor
     */
    public void cargar(JTable tbproveedores) {
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Nombre");
            model.addColumn("Telefono");
            model.addColumn("Direccion");
            model.addColumn("Email");
            tbproveedores.setModel(model);
            
            String[] datos = new String[5];
            pst = con.prepareStatement(SQL_SEARCH);
            rs = pst.executeQuery();
            while (rs.next()) {
                datos[0] = rs.getString("nitProveedor");
                datos[1] = rs.getString("nomProveedor");
                datos[2] = rs.getString("telProveedor");
                datos[3] = rs.getString("emailProveedor");
                datos[4] = rs.getString("dirProveedor");
                model.addRow(datos);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VTProveedor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOTproveedor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
