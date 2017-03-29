
package com.softcont.prsftmaven.controlador;

import com.softcont.prsftmaven.modelo.DAOCategoria;
import com.softcont.prsftmaven.vista.VCategoriaProd;
import com.softcont.prsftmaven.vista.VProductos;

/**
 * Clase encargada controladora de la vista Categorias
 * @author Gerlis Alvarez .C 
 */
public class ControladorCategorias{
    VCategoriaProd vista;
    DAOCategoria modelo;
    
    /**
     * Metodo Constructor de la clase ControladorCategorias
     * @param vista Interfaz VCategoria
     * @param modelo Clase DAO
     * @throws InstantiationException Throws
     * @throws IllegalAccessException Throws
     */
    public ControladorCategorias(VCategoriaProd vista, DAOCategoria modelo) throws InstantiationException, IllegalAccessException{
        this.vista = new VCategoriaProd(new VProductos(), true);
        this.vista=vista;
        this.modelo=modelo;
        vista.setLocationRelativeTo(new VProductos().btnCrearCategoria);
        vista.setVisible(true);
    }
}
