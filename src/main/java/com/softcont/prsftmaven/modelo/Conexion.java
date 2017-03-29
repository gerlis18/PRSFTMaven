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

    private static final String servidor = "jdbc:mysql://localhost:3306/sistemabd";
    private static final String user = "root";
    private static final String pass = "123456";
    private static final String driver = "com.mysql.jdbc.Driver";
    private static Connection conexion = null;

    public Conexion() throws InstantiationException, IllegalAccessException {

        try {
            Class.forName(driver).newInstance();
            conexion = DriverManager.getConnection(servidor, user, pass);
        } catch (ClassNotFoundException | SQLException | java.lang.NullPointerException e) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
            System.out.println(e.getMessage() + ": Conexion Fallida");
        }
    }

    /**
     * Connection que retorna la conexion
     *
     * @return conexion
     */
    public Connection getConnection() {
        return conexion;
    }

}
