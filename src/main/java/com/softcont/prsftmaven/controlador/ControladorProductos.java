/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softcont.prsftmaven.controlador;

import com.softcont.prsftmaven.modelo.DAOCategoria;
import com.softcont.prsftmaven.modelo.DAOProducto;
import com.softcont.prsftmaven.vista.VCategoriaProd;
import com.softcont.prsftmaven.vista.VProductos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Clase controladora de la vista Productos
 * @author Gerlis 
 */
public class ControladorProductos implements ActionListener {

    VProductos vista;
    DAOProducto modelo;
    
    public ControladorProductos(VProductos vista, DAOProducto modelo) throws InstantiationException, IllegalAccessException {
        this.vista = vista;
        this.modelo = modelo;
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        vista.btnRegistrar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnNuevo.addActionListener(this);
        vista.btnCrearCategoria.addActionListener(this);
        vista.btnACategoria.addActionListener(this);

        vista.btnCambiarimagen.setVisible(false);
        vista.btnActualizar.setEnabled(false);
        vista.btnEliminar.setEnabled(false);
        vista.btnNuevo.setEnabled(false);

        modelo.numeros(vista.txtCodigo);
        modelo.cargar(vista.jTable1);
        modelo.mostrarJComboCategoria(vista.jcCategoria);

    }
    
    public ControladorProductos(VProductos vista) {
        this.vista=vista;
    }

    
   /**
    * Metodo para limpiar campos en la vista
    */ 
    public void limpiarCampos() {
        vista.txtNombre.setText(null);
        vista.txtPrecioCompra.setText(null);
        vista.txtPrecioVenta.setText(null);
        vista.vRuta.setText("");
        vista.jLimagen.setIcon(null);
    }
    
    /***
     * Metodo para desbloquear los respectivos botones 
     */
    
    public void desbloquear() {
        vista.btnActualizar.setEnabled(true);
        vista.btnEliminar.setEnabled(true);
        vista.btnNuevo.setEnabled(true);
        vista.btnRegistrar.setEnabled(false);
    }
    
    /**
     * Metodo para bloquear los respectivos botones
     */
    
    public void bloquear() {
        vista.btnActualizar.setEnabled(false);
        vista.btnEliminar.setEnabled(false);
        vista.btnNuevo.setEnabled(false);
        vista.btnRegistrar.setEnabled(true);
    }
    
   /**
    * Metodo para bloquear los respectivos componentes
    */
    
    public void bloquearComponentes(){
        bloquear();
        vista.btnRegistrar.setEnabled(false);
        vista.btnBuscarImagen.setEnabled(false);
        vista.btnCambiarimagen.setEnabled(false);
        vista.btnCrearCategoria.setEnabled(false);
        vista.jSpcantidad.setEnabled(false);
        vista.jcCategoria.setEnabled(false);
        vista.txtNombre.setEnabled(false);
        vista.txtPrecioCompra.setEnabled(false);
        vista.txtPrecioVenta.setEnabled(false);
    }
    
    /**
     * Se encarga de retornar el nombre del producto
     * @return Nombre Producto
     */
    
    public String getNomProducto() {
        return vista.txtNombre.getText();
    }
    
    /**
     * Se encarga de retornar el codigo del producto
     * @return Codigo Producto
     */
    
    public String getCodProducto() {
        return vista.txtCodigo.getText();
    }
    
    /**
     * Metodo para retornar cantidad de cada producto
     * @return Cantidad del producto
     */
    
    public int getcantProducto() {
        return (int) vista.jSpcantidad.getValue();
    }
    
    /**
     * Metodo para retornar el nombre de la categoria del producto
     * @return Nombre Categoria
     */
    
    public String getCategoria() {
        return (String) vista.jcCategoria.getSelectedItem();
    }
    
    /**
     * Metodo para retornar la ruta de cada producto
     * @return Ruta de la imagen
     */
    
    public String getRuta() {
        return vista.vRuta.getText();
    }
    
    /**
     * Metodo para los eventos de la interfaz
     * @param e Objeto ActionEvent
     */
    
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.btnACategoria) {
            modelo.actualizarCategoria(vista.jcCategoria);
        }

        if (e.getSource() == vista.btnCrearCategoria) {
            try {
                VCategoriaProd vCategoria = new VCategoriaProd(vista, true);
                DAOCategoria mCategoria = new DAOCategoria();
                ControladorCategorias controladorCategorias = new ControladorCategorias(vCategoria, mCategoria);
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource() == vista.btnRegistrar) {
            if (!"".equals(vista.txtNombre.getText())) {
                 try {
                String nomp = getNomProducto();
                String codp = getCodProducto();
                String preciop = vista.txtPrecioVenta.getText();
                String precioCompra = vista.txtPrecioCompra.getText();
                int cantp = getcantProducto();
                String cat = getCategoria();
                if ("".equals(vista.vRuta.getText())) {
                    String ruta = "no imagen";
                    FileInputStream tamaño = null;
                    modelo = new DAOProducto();
                    modelo.registrarProducto(codp, nomp,cat,ruta, tamaño);
                    modelo.numeros(vista.txtCodigo);
                    modelo.cargar(vista.jTable1);
                    limpiarCampos();

                } else {
                    String ruta = getRuta();
                    FileInputStream tamaño = new FileInputStream(getRuta());
                    modelo = new DAOProducto();
                    modelo.registrarProducto(codp, nomp,cat, ruta, tamaño);
                    modelo.numeros(vista.txtCodigo);
                    modelo.cargar(vista.jTable1);
                    limpiarCampos();
                }
                vista.btnBuscarImagen.setVisible(true);
                vista.btnCambiarimagen.setVisible(false);

            } catch (InstantiationException | IllegalAccessException | FileNotFoundException ex) {
                Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(vista, "Error");
            }
            } else {
                JOptionPane.showMessageDialog(vista, "Error!, Ingrese nombre");
            }
           
        }

        

        if (e.getSource() == vista.btnNuevo) {
            bloquear();
            vista.txtNombre.requestFocus();
            limpiarCampos();
            modelo.numeros(vista.txtCodigo);
        }
    }
}
