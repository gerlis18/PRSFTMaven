package com.softcont.prsftmaven.modelo;


public class DTOcompras {
    
    String codigo,nombre,cantidad,precioCompra;
    String precioVenta, ValorT;
            
    public DTOcompras(String codigo, String nombre, String cantidad, String precioCompra, String precioVenta, String ValorT) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.ValorT = ValorT;
    }
    

     public String getValorT() {
        return ValorT;
    }

    public void setValorT(String ValorT) {
        this.ValorT = ValorT;
    }
    
    public String getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(String precioCompra) {
        this.precioCompra = precioCompra;
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

    public String getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(String precioVenta) {
        this.precioVenta = precioVenta;
    }
}
