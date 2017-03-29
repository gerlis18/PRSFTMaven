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
public class DTOVentaReport {

    String item, nomProducto, cant, valorU, valorT;

    public DTOVentaReport(String item, String nomProducto, String cant, String valorU, String valorT) {
        this.item = item;
        this.nomProducto = nomProducto;
        this.cant = cant;
        this.valorU = valorU;
        this.valorT = valorT;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getNomProducto() {
        return nomProducto;
    }

    public void setNomProducto(String nomProducto) {
        this.nomProducto = nomProducto;
    }

    public String getCant() {
        return cant;
    }

    public void setCant(String cant) {
        this.cant = cant;
    }

    public String getValorU() {
        return valorU;
    }

    public void setValorU(String valorU) {
        this.valorU = valorU;
    }

    public String getValorT() {
        return valorT;
    }

    public void setValorT(String valorT) {
        this.valorT = valorT;
    }
}
