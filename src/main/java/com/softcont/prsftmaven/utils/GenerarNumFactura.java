
package com.softcont.prsftmaven.utils;
/**
 * Clase encargada de generar codigos de facturas
 * @author by Gerlis A.C
 */
public class GenerarNumFactura {
    private int dato;
    private int cont=1;
    private String num="";
    
    /**
     * Metodo encargado de generar codigos de ventas
     * @param dato Dato
     */

    public void generar(int dato) {
        this.dato = dato;
         
           if((this.dato>=1000) || (this.dato<10000)) 
           {
               int can=cont+this.dato;
               num = "00" + can; 
           }
           if((this.dato>=100) || (this.dato<1000))
           {
               int can=cont+this.dato;
               num = "000"  + can; 
           }
           if((this.dato>=9) && (this.dato<100)) 
           {
                int can=cont+this.dato;
               num = "0000"  + can; 
           }
           if((this.dato>=1) && (this.dato<9)) 
           {
               int can=cont+this.dato;
               num = "00000" + can; 
           }
           if(this.dato==0)
           {
              int can=cont+this.dato;
               num = "00000" + can; 
           }
    }
    
    /**
     * Metodo encargado de retornar un codigo de venta
     * @return codigo de venta
     */
    public String serie()
    {
        return this.num;
    }
    
}
