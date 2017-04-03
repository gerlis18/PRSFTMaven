/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softcont.prsftmaven.modelo;

import com.softcont.prsftmaven.vista.VUsuario;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Clase encargada de toda la gestion de la interfaz <i>VUsuario</i>
 *
 * @author Gerlis A.C
 */
public class DAOUsuario {

    Connection cn;
    VUsuario vista;
    PreparedStatement pst;
    ResultSet rs;

    final String SQL_SEARCH1 = "SELECT * FROM usuarios WHERE nomCO=?;";
    final String SQL_SEARCH2 = "SELECT `nomCO` FROM usuarios WHERE `nomCO` LIKE CONCAT(?,'%');";
    final String SQL_INSERT = "INSERT INTO usuarios VALUES (?,?,?,?,?,?,?)";
    final String SQL_UPDATE = "UPDATE usuarios SET nomCO = ?, tipoId = ?, rol=?, telefono=?, email=? WHERE identificacion = ?";
    final String SQL_DELETE = "DELETE FROM usuarios WHERE identificacion=?";
    final String SQL_UPDATEPW = "UPDATE usuarios SET contraseña=? WHERE identificacion=?";
    
    /**
     * Metodo Constructor de la clase DAOUsuario
     * @throws InstantiationException Throws
     * @throws IllegalAccessException Throws
     */
    public DAOUsuario() throws InstantiationException, IllegalAccessException {
        cn = Conexion.getConnection();
        vista = new VUsuario();
    }

    /**
     * Metodo encargado de mostrar informacion de usuario
     *
     * @param txtnombre TextField nombre de usuario
     * @param txtid TextField identificacion de usuario
     * @param txtcorreo TextField email de usuario
     * @param txttelefono TextField telefono de usuario
     * @param jCtipoid JcomboBox tipo de identificacion de usuario
     * @param jCrol JComboBox rol de usuario
     * @param jcBuscar JComboBox buscar usuario
     */
    public void cargar(JTextField txtnombre, JTextField txtid, JTextField txtcorreo, JTextField txttelefono, JComboBox jCtipoid, JComboBox jCrol, JComboBox jcBuscar) {
        try {
            pst = cn.prepareStatement(SQL_SEARCH1);
            pst.setString(1, jcBuscar.getSelectedItem().toString());
            rs = pst.executeQuery();
            while (rs.next()) {
                txtnombre.setText(rs.getString("nomCO"));
                jCtipoid.setSelectedItem(rs.getString("tipoId"));
                txtid.setText(rs.getString("identificacion"));
                txtcorreo.setText(rs.getString("email"));
                jCrol.setSelectedItem(rs.getString("rol"));
                txttelefono.setText(rs.getString("telefono"));

                if (vista.jCrol.getSelectedItem().equals("Administrador")) {
                    vista.icono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/x128-administador.png")));
                } else {
                    vista.icono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/x128-user.png")));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUsuario.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(vista, "Ocurrio un error al buscar usuario");
        } finally {
            try {
                pst.close();
                //rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo encargado de mostrar usuarios por su nombre
     *
     * @param cadena Cadena de nombre de usuario
     * @return modelo para JComoBox buscar
     */
    public DefaultComboBoxModel obtenerLista(String cadena) {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        try {
            pst = cn.prepareStatement(SQL_SEARCH2);
            pst.setString(1, cadena);
            rs = pst.executeQuery();
            modelo.removeAllElements();
            while (rs.next()) {
                modelo.addElement(rs.getString("nomCO"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUsuario.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(vista, ex.getMessage());
            System.out.println("Error");
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return modelo;
    }

    /**
     * Metodo para Registrar usuario en el sistema
     *
     * @param nomCO Nombre de usuario
     * @param tipoid Tipo de identificacion de usuario
     * @param id Identificacion de usuario
     * @param contraseña Contraseña de usuario
     * @param rol Rol de usuario
     * @param telefono Telefono de usario
     * @param email Email de usuario
     */
    public void registrarUsuario(String nomCO, String tipoid, String id, String contraseña, String rol, String telefono, String email) {
        try {
            pst = cn.prepareStatement(SQL_INSERT);
            pst.setString(1, nomCO);
            pst.setString(2, tipoid);
            pst.setString(3, id);
            pst.setString(4, contraseña);
            pst.setString(5, rol);
            pst.setString(6, telefono);
            pst.setString(7, email);
            int j = pst.executeUpdate();

            if (j > 0) {
                JOptionPane.showMessageDialog(this.vista, "Se guardo correctamente");
            } else {
                JOptionPane.showMessageDialog(this.vista, "Hubo un error");
            }
        } catch (SQLIntegrityConstraintViolationException slq) {
            Logger.getLogger(DAOUsuario.class.getName()).log(Level.SEVERE, null, slq);
            JOptionPane.showMessageDialog(vista, "Ya existe un usuario con ese numero de identificacion");
        } catch (SQLException | HeadlessException e) {
            Logger.getLogger(DAOUsuario.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(this.vista, e.getMessage());
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo encargado de modificar o actualizar informacion de usuario
     *
     * @param nom Nombre de usuario
     * @param tipoid Tipo de identificacion de usuario
     * @param id Identificacion de usuario
     * @param rol Rol de usuario
     * @param telefono Telefono de usuario
     * @param email Email de usuario
     */
    public void modificarUsuario(String nom, String tipoid, String id, String rol, String telefono, String email) {
        try {

            pst = cn.prepareStatement(SQL_UPDATE);
            pst.setString(1, nom);
            pst.setString(2, tipoid);
            pst.setString(3, rol);
            pst.setString(4, telefono);
            pst.setString(5, email);
            pst.setString(6, id);

            int n = pst.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(this.vista, "Datos actualizados con exito");

            } else {
                JOptionPane.showMessageDialog(this.vista, "Hubo error en el momento de actualizar los datos");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUsuario.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this.vista, ex.getMessage());
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metodo encargado de eliminar toda la informacion de usuario
     *
     * @param id Numero de identificacion de usuario
     */
    public void eliminarUsuario(String id) {
        try {
            pst = cn.prepareStatement(SQL_DELETE);
            pst.setString(1, id);
            int n = pst.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(new VUsuario(), "Los datos fueron eliminados con exito");
            } else {
                JOptionPane.showMessageDialog(new VUsuario(), "Hubo Problemas al querer eliminar datos");
            }
        } catch (SQLException | InstantiationException | IllegalAccessException | HeadlessException ex) {
            Logger.getLogger(DAOUsuario.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(vista, ex.getMessage());
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Metodo para cambiar contraseña de usuario
     * @param id Numero de identificacion
     * @param contraseña Contraseña
     */
    public void cambiarContraseña(String id, String contraseña){
        try {
            pst = cn.prepareStatement(SQL_UPDATEPW);
            pst.setString(1, contraseña);
            pst.setString(2, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
