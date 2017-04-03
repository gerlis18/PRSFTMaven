package com.softcont.prsftmaven.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para la conexion a base de datos
 *
 * @author Gerlis
 * @version 1.0
 */
public class Conexion {

    private static final String servidor = "jdbc:mysql://us-cdbr-azure-east-c.cloudapp.net:3306/sistemabd";
    private static final String user = "b3c9af3d6ff690";
    private static final String pass = "176ed60d";
    private static final String driver = "com.mysql.jdbc.Driver";
    private static Connection conexion = null;

    public static Connection getConnection() throws InstantiationException, IllegalAccessException {

        try {
            Class.forName(driver).newInstance();
            conexion = DriverManager.getConnection(servidor, user, pass);
        } catch (ClassNotFoundException | SQLException | java.lang.NullPointerException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
            System.out.println(e.getMessage() + ": Conexion Fallida");
        }
        return conexion;
    }

}
