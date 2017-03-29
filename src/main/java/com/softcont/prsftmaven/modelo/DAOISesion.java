/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softcont.prsftmaven.modelo;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Clase encargada de la validacion de unuario en la base de datos
 *
 * @author Gerlis Alvarez C.
 */
public class DAOISesion {

    boolean valida = false;
    Connection con;
    static ResultSet rs;
    PreparedStatement pst;

    //Sentencias SQL
    final String SQL_VALIDATE = "SELECT * FROM usuarios WHERE identificacion=? && contraseña= ?";
    /**
     * Metodo Constructor de la clase DAOISesion
     * @throws InstantiationException Throws
     * @throws IllegalAccessException Throws
     */
    public DAOISesion() throws InstantiationException, IllegalAccessException {
        con = new Conexion().getConnection();
    }

    /**
     * Se encarga de validar un usuario en la base de datos
     * @param User Usuario
     * @param Pw Contraseña
     * @return true en caso de que sea verdadero
     */
    public  boolean acceder(String User, String Pw) {
        try {
            pst = con.prepareStatement(SQL_VALIDATE);
            pst.setString(1, User);
            pst.setString(2, Pw);
            rs = pst.executeQuery();
            if (rs.first()) {
                JOptionPane.showMessageDialog(null, "Usuario Validado Correctamente");
                valida = true;
            } else {
                JOptionPane.showMessageDialog(null, "Usuario no encontrado");
            }
        } catch (SQLException | HeadlessException e) {
            Logger.getLogger(DAOISesion.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, e.getMessage());

        }
        return valida;
    }

    /**
     * Se encarga de retornar el tipo de usuario
     * @return tipo de usuario
     * @throws SQLException Throws de tipo SQL
     */
    public String getTpUsuario() throws SQLException {
        String tipoUsuario = rs.getString(5);
        return tipoUsuario;
    }

    /**
     * Se encarga de retornar el nombre de usuario
     * @return nombre de usuario
     * @throws SQLException Throws de tipo SQL
     */
    public String getNomUsuario() throws SQLException {
        String nombreusuario = rs.getString(1);
        return nombreusuario;
    }
    
    /**
     * Metodo para retornar el id de usuario
     * @return Numero de identificacion de usuario
     * @throws SQLException 
     */
    public String getIdUsuario() throws SQLException{
        String idUsuario = rs.getString("identificacion");
        return idUsuario;
    }
    
    /**
     * Metodo para retornar contraseña de usuario
     * @return Contraseña de usuario
     * @throws SQLException 
     */
    public String getPass() throws SQLException{
        String Pass = rs.getString("contraseña");
        return Pass;
    }
}
