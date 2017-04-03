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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import com.softcont.prsftmaven.utils.GenerarIdVenta;
import com.softcont.prsftmaven.vista.VCompra;

/**
 * Clase encargada de toda la gestion de la interfaz <i>VCompra</i>
 *
 * @author <i>Gerlis A.C</i>
 */
public class DAOcompras {

    Connection con;
    VCompra vista;
    PreparedStatement pst;
    ResultSet rs;
    DefaultTableModel model;

    //CONSULTAS SQL
    String SQL_SEARCH_PRODS = "SELECT * from detallecompra";

    public DAOcompras() throws InstantiationException, IllegalAccessException {
        con = Conexion.getConnection();
        //vista = new VCompra();
    }

    /**
     * Metodo encargado de generar IDs de compra
     *
     * @param txtidcompra JTextField id de compra
     */
    public void idCompra(JTextField txtidcompra) {
        int j;

        String c = "";
        String SQL = "SELECT MAX(idcompra) AS idcompra FROM compra";
        try {
            pst = con.prepareStatement(SQL);
            rs = pst.executeQuery();
            if (rs.next()) {
                c = rs.getString("idcompra");
            }
            System.out.print(c);

            if (c == null) {
                txtidcompra.setText("000001");
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
                txtidcompra.setText(gen.serie());
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOcompras.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOcompras.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Metodo encargado de mostrar datos de productos en su respectiva tabla
     *
     * @param tbproducto Tabla para productos
     * @param buscar JComboBox para buscar productos por su nombre
     */
    public void cargar(JTable tbproducto, JComboBox buscar, boolean valida) {
        model = new DefaultTableModel();
        model.addColumn("Codigo");
        model.addColumn("Nombre");
        model.addColumn("Cantidad");
        model.addColumn("Precio Compra");
        model.addColumn("Precio Venta");
        model.addColumn("Valor Total");
        tbproducto.setModel(model);
        try {
            String consultar = "SELECT * FROM productos WHERE nomProducto='" + buscar.getSelectedItem() + "';";
            String[] datos = new String[5];
            pst = con.prepareStatement(consultar);
            rs = pst.executeQuery();
            if (valida == true) {
                while (rs.next()) {
                    datos[0] = rs.getString("codProducto");
                    datos[1] = rs.getString("nomProducto");
                    datos[2] = "0";
                    datos[3] = rs.getString("precioCompra");
                    datos[4] = rs.getString("precioProducto");
                    //datos[5] = "0.0";
                    model.addRow(datos);
                }
            } else if (valida == false) {
                while (rs.next()) {
                    datos[0] = rs.getString("codProducto");
                    datos[1] = rs.getString("nomProducto");
                    datos[2] = "0.0";
                    model.addRow(datos);
                }
            }
        } catch (SQLException | NullPointerException ex) {
            JOptionPane.showMessageDialog(vista, ex.getMessage());
            Logger.getLogger(DAOcompras.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOcompras.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo encargado de remover productos de la tabla productos
     *
     * @param tabla Tabla de productos
     */
    public void removerProducto(JTable tabla) {

        for (int j = 0; j < tabla.getRowCount(); j++) {
            ((DefaultTableModel) tabla.getModel()).removeRow(0);
        }

    }

    /**
     * Metodo encargado de buscar y mostrar productos por su nombre
     *
     * @param cadena Nombre de producto
     * @return Modelo JComboBox
     */
    public DefaultComboBoxModel obtenerLista(String cadena) {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        String consultar = "SELECT `nomProducto` FROM productos WHERE `nomProducto` LIKE '" + cadena + "%';";
        try {
            pst = con.prepareStatement(consultar);
            rs = pst.executeQuery();
            modelo.removeAllElements();
            while (rs.next()) {
                modelo.addElement(rs.getString("nomProducto"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOcompras.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(vista, ex.getMessage());
            System.out.println("Error");
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOcompras.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return modelo;
    }

    ///7//////////////////////////////////////////////////////////////////////////////////////////////////////////77
    /**
     * Metodo para registrar datos basicos de una compra
     *
     * @param codCompra Codigo de Compra
     * @param Proveedor Nit de proveedor
     * @param valorTotal Valor total de compra
     * @param fecha Fecha de la realizacion de la compra
     * @throws InstantiationException Throws
     * @throws IllegalAccessException Throws
     */
    public void registrarCompra(String codCompra, String Proveedor, String valorTotal, String fecha) throws InstantiationException, IllegalAccessException {
        try {
            String SQL = "INSERT INTO compra VALUES (?,?,?,?,?)";
            pst = con.prepareStatement(SQL);
            pst.setString(1, codCompra);
            pst.setString(2, Proveedor);
            pst.setString(3, valorTotal);
            pst.setString(4, fecha);
            pst.setString(5, "Activa");
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DAOcompras.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOcompras.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo encargado de registrar detalle de cada venta
     *
     * @param codCompra Codigo de Compra
     * @param codProducto Codigo de producto
     * @param Cantidad Cantidad de producto
     * @param precioCompra Precio de compra del producto
     * @param precioVenta Precio de venta del producto
     */
    public void registrarDetalleCompra(String codCompra, String codProducto, String Cantidad, String precioCompra, String precioVenta) {
        try {
            String SQL1 = "INSERT INTO detalleCompra VALUES(?,?,?,?,?)";
            pst = con.prepareStatement(SQL1);
            pst.setString(1, codCompra);
            pst.setString(2, codProducto);
            pst.setString(3, Cantidad);
            pst.setString(4, precioCompra);
            pst.setString(5, precioVenta);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOcompras.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOcompras.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo encargado de actualizar el estado de una compra a Anulado
     *
     * @param idcompra Codigo de compra
     */
    public void actualizarEstado(String idcompra) {
        try {
            pst = con.prepareStatement("UPDATE compra SET estadoCompra = 'Anulado' where idcompra = '" + idcompra + "'");
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DAOVenta.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOcompras.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo encargado de buscar todos los codigos de productos de la tabla
     * productos
     *
     * @return Lista de codigos de productos
     */
    public ArrayList codigos() {
        ArrayList codigos = new ArrayList();
        try {
            pst = con.prepareStatement(SQL_SEARCH_PRODS);
            rs = pst.executeQuery();
            while (rs.next()) {
                if ("".equals(rs.getString("productos_codProducto"))) {
                    codigos = null;
                } else {
                    codigos.add(rs.getString("productos_codProducto"));
                }
            }
            System.out.println(codigos);
        } catch (SQLException ex) {
            Logger.getLogger(DAOcompras.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOcompras.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return codigos;
    }

    /**
     * Metodo encargado de actualizar la cantidad del producto luego de una
     * compra con el mismo id de producto
     *
     * @param codProducto Codigo de Producto
     * @param cantProducto Cantidad de producto
     */
    public void actualizarProducto(String codProducto, int cantProducto) {
        try {
            String cantidad = "";
            pst = con.prepareStatement("SELECT * from productos where codProducto = '" + codProducto + "' ");
            rs = pst.executeQuery();
            while (rs.next()) {
                cantidad = rs.getString("cantProducto");
            }

            Integer cant = Integer.parseInt(cantidad);
            Integer c = cant + cantProducto;

            pst = con.prepareStatement("UPDATE productos SET cantProducto = ? where codProducto = '" + codProducto + "' ");
            pst.setString(1, c.toString());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOcompras.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOcompras.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

//    public static void main(String[] args) {
//        try {
//            ArrayList cd = new DAOcompras().codigos();
//        } catch (InstantiationException | IllegalAccessException ex) {
//            Logger.getLogger(DAOcompras.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
