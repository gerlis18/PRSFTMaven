/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softcont.prsftmaven.controlador;

import com.softcont.prsftmaven.modelo.DAOTCliente;
import com.softcont.prsftmaven.vista.VTablaClientes;
import com.softcont.prsftmaven.vista.VVentas;

/**
 * Clase encargada inicializar la interfaz TCliente
 * @author by Gerlis A.C
 */
public class ControladorTCliente {
    VTablaClientes vista;
    DAOTCliente modelo;
    /**
     * Metodo constructor de la clase ControladorTCliente
     * @param vista Interfaz 
     * @param modelo Modelo
     * @throws InstantiationException Throws
     * @throws IllegalAccessException Throws
     */
    public ControladorTCliente(VTablaClientes vista, DAOTCliente modelo) throws InstantiationException, IllegalAccessException{
        this.modelo = modelo;
        this.vista = vista;
        vista.setVisible(true);
        vista.setLocationRelativeTo(new VVentas());
    }
    
}
