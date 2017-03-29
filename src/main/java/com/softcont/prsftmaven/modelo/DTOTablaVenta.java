/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softcont.prsftmaven.modelo;

/**
 *
 * @author Gerlis A.C 
 */
public class DTOTablaVenta {
    String codigo,nombre,cantidad,vTotal;
    String precio;
    public DTOTablaVenta(String codigo, String nombre, String cantidad, String vTotal, String precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.vTotal = vTotal;
        this.precio = precio;
    }

    public String getvTotal() {
        return vTotal;
    }

    public void setvTotal(String vTotal) {
        this.vTotal = vTotal;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
    
    
}
