/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softcont.prsftmaven.controlador;

import java.awt.Color;
import com.softcont.prsftmaven.modelo.DAOProveedor;
import com.softcont.prsftmaven.utils.ValidatorUtil;
import com.softcont.prsftmaven.vista.VProveedor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

/**
 * Clase controladora de la vista Proveedor
 *
 * @author Edinson Marimon P.
 */
public class ControladorProveedor implements ActionListener {

    VProveedor vista;
    DAOProveedor modelo;

    public ControladorProveedor(VProveedor vista, DAOProveedor modelo) {
        this.modelo = modelo;
        this.vista = vista;
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
        vista.btnGuardar.addActionListener(this);
        vista.btnbuscar.addActionListener(this);
        vista.btnModificar.addActionListener(this);
        vista.btnMostrar.addActionListener(this);
        vista.btnNuevo.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
    }

    /**
     * Se encarga de Limpiar los campos de la vista Proveedor
     */
    void limpiar() {
        vista.txtNombre.setText("");
        vista.txtDireccion.setText("");
        vista.txtTelefono.setText("");
        vista.txtNit.setText("");
        vista.txtElectronico.setText("");
    }

    /**
     * Se encarga de bloquear los componentes de la vista Proveedor
     */
    public void bloquearComponentes() {
        vista.btnModificar.setEnabled(false);
        vista.btnGuardar.setEnabled(false);
        vista.txtDireccion.setEnabled(false);
        vista.txtElectronico.setEnabled(false);
        vista.txtNit.setEnabled(false);
        vista.txtNombre.setEnabled(false);
        vista.txtTelefono.setEnabled(false);
    }

    /**
     * Se encarga de que los campos no esten vacios
     *
     * @return true en caso de que los campos no esten vacios
     */
    public boolean validarCampos() {
        boolean validar = false;
        if ("".equals(vista.txtNombre.getText()) && "".equals(vista.txtNit.getText()) && "".equals(vista.txtElectronico.getText())) {
            JOptionPane.showMessageDialog(vista, "Error!, Rellene Campos");
        } else if ("".equals(vista.txtNombre.getText())) {
            JOptionPane.showMessageDialog(vista, "Inserte nombre");
        } else if ("".equals(vista.txtNit.getText())) {
            JOptionPane.showMessageDialog(vista, "Inserte NIT");
        } else if ("".equals(vista.txtElectronico.getText())) {
            JOptionPane.showMessageDialog(vista, "Inserte Email");
        } else if ("".equals(vista.txtTelefono.getText())) {
            JOptionPane.showMessageDialog(vista, "Inserte Telefono");
        } else {
            validar = true;
        }
        return validar;
    }

    /**
     * Se encarga de controlar los eventos de la vista Proveedor
     *
     * @param ae Objeto ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == vista.btnGuardar) {
            if (validarCampos() == true) {
                boolean validar = ValidatorUtil.validateEmail(vista.txtElectronico.getText());
                if (validar == false) {
                    JOptionPane.showMessageDialog(vista, "Correo mal escrito");
                } else if (modelo.registrarProveedor(vista.txtNit.getText(), vista.txtNombre.getText(), vista.txtTelefono.getText(),
                        vista.txtElectronico.getText(), vista.txtDireccion.getText(), vista.tbProveedor)) {
                    JOptionPane.showMessageDialog(vista, "Registro exitoso");
                    limpiar();
                    modelo.visualisarardatos("", vista.tbProveedor);
                } else {
                    JOptionPane.showMessageDialog(vista, "Revise Campos");
                    vista.txtElectronico.setBorder(new LineBorder(Color.red));
                }

            }

        }

        if (ae.getSource() == vista.btnbuscar) {
            modelo.buscarProveedor(vista.txtbuscar.getText(), vista.tbProveedor);
            vista.txtbuscar.setText("");
        }

        if (ae.getSource() == vista.btnMostrar) {
            modelo.visualisarardatos("", vista.tbProveedor);
        }

        if (ae.getSource() == vista.btnModificar) {
            if (true == validarCampos()) {
                if (0 >= vista.tbProveedor.getSelectedRow()) {
                    int resp = JOptionPane.showConfirmDialog(vista, "¿Desea modificar proveedor?", "Modificar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (JOptionPane.OK_OPTION == resp) {
                        if (modelo.modificarProveedor(vista.txtNombre.getText(), vista.txtTelefono.getText(), vista.txtElectronico.getText(),
                                vista.txtDireccion.getText(), vista.txtNit.getText(), vista.tbProveedor) == true) {
                            JOptionPane.showMessageDialog(vista, "Proveedor actualizado correctamente");
                            limpiar();
                            modelo.visualisarardatos("", vista.tbProveedor);
                        } else {
                            JOptionPane.showMessageDialog(vista, "Error");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(vista, "Elija Fila...", "ERROR", JOptionPane.ERROR_MESSAGE);
                }

            }

        }

        if (ae.getSource() == vista.btnEliminar) {
            if (true == validarCampos()) {
                if (vista.tbProveedor.getSelectedRow() >= 0) {
                    int resp = JOptionPane.showConfirmDialog(vista, "¿Desea Eliminar proveedor?", "Eliminar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (JOptionPane.OK_OPTION == resp) {
                        if (modelo.eliminarProveedor(vista.txtNit.getText()) == true) {
                            JOptionPane.showMessageDialog(vista, "Proveedor Eliminado correctamente");
                            limpiar();
                            modelo.visualisarardatos("", vista.tbProveedor);
                        } 
                        
                    }
                } else {
                    JOptionPane.showMessageDialog(vista, "Elija Fila...", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }

        }

        if (ae.getSource() == vista.btnNuevo) {
            vista.btnGuardar.setEnabled(true);
            vista.btnModificar.setEnabled(false);
            vista.btnNuevo.setEnabled(false);
            vista.btnEliminar.setEnabled(false);
            vista.txtNit.setEnabled(true);
            limpiar();
        }

    }

}
