package com.softcont.prsftmaven.modelo;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.awt.HeadlessException;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import com.softcont.prsftmaven.clases.GenerarNumeros;
import com.softcont.prsftmaven.vista.VProductos;

/**
 * Clase encargada de toda la gestion de la interfaz Productos
 *
 * @author Gerlis A.C
 */
public class DAOProducto {

    Conexion conect;
    Connection con;
    VProductos vistaProductos;
    PreparedStatement pst;
    ResultSet rs;

    //Sentencias SQL
    final String SQL_INSERT = "INSERT INTO productos (CodProducto, nomProducto, Categoria_nombreCategoria, ruta, tamaño) VALUES (?,?,?,?,?)";
    final String SQL_UPDATE1 = "UPDATE productos SET cantProducto=? WHERE codProducto=?";
    final String SQL_UPDATE2 = "UPDATE productos SET nomProducto = ?,Categoria_nombreCategoria=?,ruta=?,tamaño=? WHERE codProducto = ?";
    final String SQL_UPDATE3 = "UPDATE productos SET nomProducto = ?,Categoria_nombreCategoria=?,ruta=? WHERE codProducto = ?";
    final String SQL_UPDATE4 = "UPDATE productos SET cantProducto = ?,precioCompra=?,precioProducto=?WHERE codProducto = ?";
    final String SQL_SEARCH1 = "SELECT cantProducto FROM productos WHERE codProducto=?";
    final String SQL_SEARCH2 = "SELECT MAX(codProducto) AS codProducto FROM productos";
    final String SQL_SEARCH3 = "select * from categoria";
    final String SQL_SEARCH4 = "SELECT * FROM productos";
    final String SQL_DELETE = "DELETE FROM productos WHERE codProducto= ?";
    
    /**
     * Metodo constructor de la clase DAOProducto
     * @throws InstantiationException Throws
     * @throws IllegalAccessException Throws 
     */
    public DAOProducto() throws InstantiationException, IllegalAccessException {
        conect = new Conexion();
        con = conect.getConnection();
    }

    /**
     * Metodo encargado de devolver la cantidad de un producto despues de la
     * anulacion de una venta o compra
     *
     * @param cantProducto Cantidad de producto
     * @param codProducto Codigo de producto
     */
    public void descontrarProducto(String cantProducto, String codProducto) {
        try {
            pst = con.prepareStatement(SQL_UPDATE1);
            pst.setString(1, cantProducto);
            pst.setString(2, codProducto);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(vistaProductos, "Ocurrio un error al descontar el producto(s)");
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo encargado de retornar la cantidad de dicho producto
     *
     * @param codProducto Codigo de Producto
     * @return Cantidad del producto
     */
    public int buscarCantProducto(String codProducto) {
        int cant = 0;
        try {
            pst = con.prepareStatement(SQL_SEARCH1);
            pst.setString(1, codProducto);
            rs = pst.executeQuery();
            while (rs.next()) {
                cant = rs.getInt("cantProducto");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(vistaProductos, "Ocurrio un error al buscar cantidad del producto");
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return cant;
    }

    /**
     * Metodo encargado de registrar datos de un producto
     *
     * @param codProducto Codigo de producto
     * @param nomProducto Nombre de producto
     * @param categoria Nombre de la categoria
     * @param ruta Ruta de la imagen del producto
     * @param tamaño Tamaño de la imagen del producto
     */
    public void registrarProducto(String codProducto, String nomProducto, String categoria, String ruta, FileInputStream tamaño) {

        try {
            pst = con.prepareStatement(SQL_INSERT);
            pst.setString(1, codProducto);
            pst.setString(2, nomProducto);
            pst.setString(3, categoria);
            pst.setString(4, ruta);
            pst.setBinaryStream(5, tamaño);
            int i = pst.executeUpdate();

            if (i > 0) {
                JOptionPane.showMessageDialog(this.vistaProductos, "Se guardo correctamente");
            }
            JOptionPane.showMessageDialog(this.vistaProductos, "El proceso de inserción termino exitosamente");
            //System.out.println("Insertado Correctamente");
        } catch (SQLException e) {
            Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(this.vistaProductos, e.getMessage() + " Error");
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo encargado de modificar o actualizar todos los datos de un producto
     *
     * @param codProducto Codigo de producto
     * @param nomProducto Nombre de producto
     * @param catgoria Categoria de producto
     * @param ruta Ruta de la imagen del producto
     * @param tamaño Tañaño de la imagen del producto 
     */
    public void modificarProducto(String codProducto, String nomProducto, String catgoria, String ruta, FileInputStream tamaño) {
        try {

            pst = con.prepareStatement(SQL_UPDATE2);
            pst.setString(1, nomProducto);
            pst.setString(2, catgoria);
            pst.setString(3, ruta);
            pst.setBinaryStream(4, tamaño);
            pst.setString(5, codProducto);

            int n = pst.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(this.vistaProductos, "Datos actualizados con exito");

            } else {
                JOptionPane.showMessageDialog(this.vistaProductos, "Hubo error en el momento de actualizar los datos");
            }
        } catch (SQLException e) {
            Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(this.vistaProductos, e.getMessage());
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo encargado de modificar o actualizar ciertos datos de un producto
     *
     * @param codProducto
     * @param nomProducto
     * @param catProducto
     * @param ruta
     */
    public void modificarProducto(String codProducto, String nomProducto, String catProducto, String ruta) {
        try {
            pst = con.prepareStatement(SQL_UPDATE3);
            pst.setString(1, nomProducto);
            pst.setString(2, catProducto);
            pst.setString(3, ruta);
            pst.setString(4, codProducto);

            int n = pst.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(this.vistaProductos, "Datos actualizados con exito");

            } else {
                JOptionPane.showMessageDialog(this.vistaProductos, "Hubo error en el momento de actualizar los datos");
            }
        } catch (SQLException e) {
            Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(this.vistaProductos, e.getMessage());
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo encargado de actualizar datos de un producto al realizar una
     * compra
     *
     * @param codProducto Codigo de producto
     * @param precioCompra Precio de compra de producto
     * @param precioProducto Precio de compra de producto
     * @param cantProducto Cantidad de producto
     */
    public void actualizarProducto(String codProducto, String precioCompra, String precioProducto, String cantProducto) {
        try {
            pst = con.prepareStatement(SQL_UPDATE4);
            pst.setString(1, cantProducto);
            pst.setString(2, precioCompra);
            pst.setString(3, precioProducto);
            pst.setString(4, codProducto);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(vistaProductos, "Ocurrio un error al actualizar productos luego de una compra");
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo encargado de Eliminar toda la informacion de un producto
     *
     * @param codProducto Codgio de producto
     * @return True en caso que se cumple el bloque try
     * @throws InstantiationException Throws
     * @throws IllegalAccessException Throws
     */
    public boolean EliminarProd(String codProducto) throws InstantiationException, IllegalAccessException {
        boolean validar = false;
        try {
            pst = con.prepareStatement(SQL_DELETE);
            pst.setString(1, codProducto);
            int n = pst.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(this.vistaProductos, "Los datos fueron eliminados con exito");
                validar = true;
            }
        }catch(MySQLIntegrityConstraintViolationException mysql){
            Logger.getLogger(DAOProducto.class.getName()).log(Level.OFF, codProducto, mysql);
            JOptionPane.showMessageDialog(vistaProductos, "No se puede eliminar producto luego de haberlo comprado");
        } 
        
        catch (HeadlessException | SQLException e) {
            Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(vistaProductos, "Se ha Producido un error inesperado");
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return validar;
    }

    /**
     * Metodo encargado de generar codigos de productos
     *
     * @param txtCodigo JTextField codigo de producto
     */ 
    public void numeros(JTextField txtCodigo) {
        int j;

        String c = "";
        try {
            pst = con.prepareStatement(SQL_SEARCH2);
            rs = pst.executeQuery();
            if (rs.next()) {
                c = rs.getString("codProducto");
            }

            if (c == null) {
                txtCodigo.setText("CP0001");
            } else {
                char r1 = c.charAt(2);
                char r2 = c.charAt(3);
                char r3 = c.charAt(4);
                char r4 = c.charAt(5);
                String juntar = "" + r1 + r2 + r3 + r4;
                int var = Integer.parseInt(juntar);
                GenerarNumeros gen = new GenerarNumeros();
                gen.generar(var);
                txtCodigo.setText(gen.serie());
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo encargado de mostrar todas las categorias existentes
     *
     * @param jcCategoria JComboBox Categoria
     */
    public void mostrarJComboCategoria(JComboBox jcCategoria) {
        try {
            pst = con.prepareStatement(SQL_SEARCH3);
            rs = pst.executeQuery();
            while (rs.next()) {
                jcCategoria.addItem(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(VProductos.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo encargado de mostrar informacion de productos en la tabla de
     * interfaz productos
     *
     * @param jTable1 JTable de la interfaz VProductos
     */
    public void cargar(JTable jTable1) {
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Codigo");
            model.addColumn("Nombre");
            model.addColumn("Cantidad");
            model.addColumn("Precio Compra.");
            model.addColumn("Precio Venta.");
            model.addColumn("Categoria");
            model.addColumn("Imagen");
            jTable1.setModel(model);

            String[] datos = new String[7];
            pst = con.prepareStatement(SQL_SEARCH4);
            rs = pst.executeQuery();
            while (rs.next()) {
                datos[0] = rs.getString("codProducto");
                datos[1] = rs.getString("nomProducto");
                datos[2] = rs.getString("cantProducto");
                datos[3] = rs.getString("precioCompra");
                datos[4] = rs.getString("precioProducto");
                datos[5] = rs.getString("Categoria_nombreCategoria");
                datos[6] = rs.getString("ruta");
                model.addRow(datos);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showInternalMessageDialog(vistaProductos, ex.getMessage());
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo encargado de añadir una nueva categoria al JComboBox de categorias
     *
     * @param jcCategoria
     */
    public void actualizarCategoria(JComboBox jcCategoria) {
        try {
            pst = con.prepareStatement(SQL_SEARCH3);
            rs = pst.executeQuery();
            jcCategoria.removeAllItems();
            while (rs.next()) {
                jcCategoria.addItem(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategoria.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
