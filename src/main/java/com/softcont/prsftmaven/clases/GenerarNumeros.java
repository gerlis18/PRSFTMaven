
package com.softcont.prsftmaven.clases;
/**
 * Clase encargada de generas codigos de productos
 * @author by Gerlis A.C 
 */
public class GenerarNumeros {
    private int dato;
    private int cont=1;
    private String num="";

    /**
     * Metodo encargado de generar codigos
     * @param dato Dato
     */
    public void generar(int dato) {
        this.dato = dato;
         
           if((this.dato>=1000) || (this.dato<10000)) 
           {
               int can=cont+this.dato;
               num = "CP" + can; 
           }
           if((this.dato>=100) || (this.dato<1000))
           {
               int can=cont+this.dato;
               num = "CP0"  + can; 
           }
           if((this.dato>=9) && (this.dato<100)) 
           {
                int can=cont+this.dato;
               num = "CP00"  + can; 
           }
           if((this.dato>=1) && (this.dato<9)) 
           {
               int can=cont+this.dato;
               num = "CP000" + can; 
           }
           if(this.dato==0)
           {
              int can=cont+this.dato;
               num = "CP000" + can; 
           }
    }

    /**
     * Metodo encargado de retornar codigos de productos
     * @return Codigo de producto
     */
    public String serie()
    {
        return this.num;
    }
    
}
