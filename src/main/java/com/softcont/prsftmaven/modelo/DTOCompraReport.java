/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softcont.prsftmaven.modelo;

/**
 *
 * @author adsi1
 */
public class DTOCompraReport {

    String item, nomProducto, cant, valorU, valorT;
    
    /**
     * Constructor de la clase DTOCompraReport
     * @param item Numero de item por producto
     * @param nomProducto Nombre de producto
     * @param cant Cantidad de producto
     * @param valorU Valor de venta del producto 
     * @param valorT Valor todal de cada producto
     */
    public DTOCompraReport(String item, String nomProducto, String cant, String valorU, String valorT) {
        this.item = item;
        this.nomProducto = nomProducto;
        this.cant = cant;
        this.valorU = valorT;
        this.valorT = valorU;
    }
    
    /**
     * Metodo get de la variable item
     * @return item
     */
    public String getItem() {
        return item;
    }
    
    /**
     * Metodo set de la variable item
     * @param item 
     */
    public void setItem(String item) {
        this.item = item;
    }

    /**
     * Metodo get de la variable NomProducto
     * @return Nombre de producto
     */
    public String getNomProducto() {
        return nomProducto;
    }

    /**
     * Metodo set de la variable nomProducto
     * @param nomProducto 
     */
    public void setNomProducto(String nomProducto) {
        this.nomProducto = nomProducto;
    }

    /**
     * Metodo get de la variable cant
     * @return Cantidad de producto
     */
    public String getCant() {
        return cant;
    }

    /**
     * Metodo set de la variable cant
     * @param cant 
     */
    public void setCant(String cant) {
        this.cant = cant;
    }

    /**
     * Metodo get de la variable venta
     * @return Valor de venta
     */
    public String getValorU() {
        return valorU;
    }

    /**
     * Metodo set de la variable valorU
     * @param valorU 
     */
    public void setValorU(String valorU) {
        this.valorU = valorU;
    }

    /**
     * Metodo get de la variable valorT
     * @return Valor total
     */
    public String getValorT() {
        return valorT;
    }

    /**
     * Metodo set de la variable valorT
     * @param valorT 
     */
    public void setValorT(String valorT) {
        this.valorT = valorT;
    }
}
