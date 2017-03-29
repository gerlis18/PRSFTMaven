/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softcont.prsftmaven.controlador;

import com.softcont.prsftmaven.modelo.DAOUsuario;
import com.softcont.prsftmaven.vista.VUsuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

/**
 * Clase Controladora de la vista Usuarios
 *
 * @author Gerlis A.C
 */
public class ControladorUsuario implements ActionListener {

    VUsuario vista;
    DAOUsuario modelo;

    public ControladorUsuario() {
    }

    /**
     * Metodo Constructor 1 de la clase ControladorUsuario
     *
     * @param vista Intefaz
     */
    public ControladorUsuario(VUsuario vista) {
        this.vista = vista;
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
    }

    /**
     * Metodo Constructor 2 de la clase ControladorUsuario
     *
     * @param vista
     * @param modelo
     */
    public ControladorUsuario(VUsuario vista, DAOUsuario modelo) {
        this.vista = vista;
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        this.modelo = modelo;
        vista.btnAgregar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnGuardar.addActionListener(this);
        vista.jMNewUser.addActionListener(this);
        vista.btnGuardar.setEnabled(false);
        vista.btnEliminar.setEnabled(false);
        vista.jLreq1.setVisible(false);
        vista.jLreq2.setVisible(false);
        vista.jLreq3.setVisible(false);
        vista.jLreq4.setVisible(false);

        eventoJCombo(modelo);
    }

    /**
     * Meotodo encargado de bloquear componentes
     */
    public void bloquearComponentes() {
        vista.btnEliminar.setEnabled(false);

    }

    /**
     * Metodo encargado de mostrar nombre de usuario en JComboBox
     *
     * @param modelo Modelo DAO
     */
    public void eventoJCombo(DAOUsuario modelo) {
        vista.jcBuscar.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                String cadena = vista.jcBuscar.getEditor().getItem().toString();
                if (evt.getKeyCode() >= 65 && evt.getKeyCode() <= 90 || evt.getKeyCode() >= 96 && evt.getKeyCode() <= 105 || evt.getKeyCode() == 8) {

                    vista.jcBuscar.setModel(modelo.obtenerLista(cadena));
                    if (vista.jcBuscar.getItemCount() > 0) {
                        vista.jcBuscar.showPopup();
                        if (evt.getKeyCode() != 8) {
                            ((JTextComponent) vista.jcBuscar.getEditor().getEditorComponent()).select(cadena.length(), vista.jcBuscar.getEditor().getItem().toString().length());
                        } else {
                            vista.jcBuscar.getEditor().setItem(cadena);
                        }
                    } else {
                        vista.jcBuscar.addItem(cadena);
                    }

                }
            }
        });
    }

    /**
     * Metodo encargado de retornar nombre de usuario de un JTextField
     *
     * @return Nombre de usuario
     */
    public String getNombre() {
        return vista.txtnombre.getText();
    }

    /**
     * Metodo encargado de retornar tipo de identificacion de un JTextField
     *
     * @return Tipo de identificacion
     */
    public String getTipoID() {
        String id = (String) vista.jCtipoid.getSelectedItem();
        return id;
    }

    /**
     * Metodo encargado de retornar rol de usuario de un JTextField
     *
     * @return Rol de usuario
     */
    public String getRol() {
        String rol = (String) vista.jCrol.getSelectedItem();
        return rol;
    }

    /**
     * Metodo encargado de retornar numero de identificacion de un JTextField
     *
     * @return Numero de identificacion de usuario
     */
    public String getID() {
        return vista.txtid.getText();
    }

    /**
     * Metodo encargado de retornar email de usuario de un JTextField
     *
     * @return Email de usuario
     */
    public String getCorreo() {
        return vista.txtcorreo.getText();
    }

    /**
     * Metodo encargado de retornar contraseña de usuario de un JTextField
     *
     * @return Contraseña de usuario
     */
    public String getContraseña() {
        return String.valueOf(vista.txtRpass.getPassword());
    }

    /**
     * Metodo encargado de retornar numero de telefono de un JTextField
     *
     * @return Numero de telefono de usuario
     */
    public String getTelefono() {
        return vista.txttelefono.getText();
    }

    /**
     * Metodo encargado de limpiar campos de la interfaz usuario
     */
    public void limpiarCampos() {
        vista.txtnombre.setText("");
        vista.txtid.setText("");
        vista.txtcorreo.setText("");
        vista.txtRpass.setText("");
        vista.txtPass.setText("");
        vista.txttelefono.setText("");
        vista.jcBuscar.setSelectedItem("");
    }

    /**
     * Metodo encargado de validar los campos de la interfaz usuario
     *
     * @return True si los campos requeridos no estan vacios
     */
    public boolean valida() {
        boolean valida = false;
        int cont = 0;
        if (vista.txtnombre.getText().equals("")) {
            vista.jLreq1.setVisible(true);
        } else {
            vista.jLreq1.setVisible(false);
            cont++;
        }
        if (vista.txttelefono.getText().equals("")) {
            vista.jLreq2.setVisible(true);
        } else {
            vista.jLreq2.setVisible(false);
            cont++;
        }
        if (vista.txtPass.getText().equals("")) {
            vista.jLreq4.setVisible(true);
        } else {
            vista.jLreq4.setVisible(false);
            cont++;
        }
        if (vista.txtid.getText().equals("")) {
            vista.jLreq3.setVisible(true);
        } else {
            vista.jLreq3.setVisible(false);
            cont++;
        }
        if (cont == 4) {
            valida = true;
        }
        return valida;
    }

    /**
     * Metodo que valida ciertos campos de la interfaz
     *
     * @return true
     */
    public boolean valida1() {
        boolean valida = false;
        int cont = 0;
        if (vista.txtnombre.getText().equals("")) {
            vista.jLreq1.setVisible(true);
        } else {
            vista.jLreq1.setVisible(false);
            cont++;
        }
        if (vista.txttelefono.getText().equals("")) {
            vista.jLreq2.setVisible(true);
        } else {
            vista.jLreq2.setVisible(false);
            cont++;
        }
        if (vista.txtid.getText().equals("")) {
            vista.jLreq3.setVisible(true);
        } else {
            vista.jLreq3.setVisible(false);
            cont++;
        }
        if (cont == 3) {
            valida = true;
        }
        return valida;
    }

    /**
     * Metodo encargado de los eventos de la interfaz usuario
     *
     * @param e Objeto ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.btnAgregar) {
            boolean valida = false;

            if (valida() == true) {
                String nom = getNombre();
                String tpid = getTipoID();
                String rol = getRol();
                String id = getID();
                String email = getCorreo();
                String pass = getContraseña();
                String tel = getTelefono();
                if (vista.txtPass.getText().equals(vista.txtRpass.getText())) {
                    modelo.registrarUsuario(nom, tpid, id, pass, rol, tel, email);
                    limpiarCampos();
                    vista.jLvpass.setText(null);
                } else {
                    JOptionPane.showMessageDialog(this.vista, "Error!, verifique contraseña");
                }
            } else {
                //JOptionPane.showMessageDialog(this, "L");
                //txtRpass.setText(null);
                //txtPass.setText(null);
                valida();
            }
        }

        if (e.getSource() == vista.btnGuardar) {
            boolean valida = false;
            if (valida1() == true) {
                vista.btnAgregar.setEnabled(false);
                int resp = JOptionPane.showConfirmDialog(vista, "¿Desea modifcar usuario?", "Modificar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (resp == JOptionPane.OK_OPTION) {
                    String nom = getNombre();
                    String tpid = getTipoID();
                    String rol = getRol();
                    String id = getID();
                    String email = getCorreo();
                    String pass = getContraseña();
                    String tel = getTelefono();
                    modelo.modificarUsuario(nom, tpid, id, rol, tel, email);
                    limpiarCampos();
                    vista.jLvpass.setText(null);
                }
            }

        }

        if (e.getSource() == vista.btnEliminar) {
            int resp = JOptionPane.showConfirmDialog(vista, "¿Desea eliminar usuario?", "Eliminar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (JOptionPane.OK_OPTION == resp) {
                String id = getID();
                System.out.println(vista.txtid.getText());
                modelo.eliminarUsuario(id);
                limpiarCampos();
            }
        }

        if (e.getSource() == vista.jMNewUser) {
            limpiarCampos();
            vista.btnAgregar.setEnabled(true);
            vista.btnGuardar.setEnabled(false);
            vista.btnEliminar.setEnabled(false);
        }

    }
}
