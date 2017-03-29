/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softcont.prsftmaven.controlador;

import com.softcont.prsftmaven.modelo.DAOCliente;
import com.softcont.prsftmaven.modelo.DAOISesion;
import com.softcont.prsftmaven.modelo.DAOProducto;
import com.softcont.prsftmaven.modelo.DAOProveedor;
import com.softcont.prsftmaven.modelo.DAOUsuario;
import com.softcont.prsftmaven.modelo.DAOVenta;
import com.softcont.prsftmaven.modelo.DAOcompras;
import com.softcont.prsftmaven.vista.InvCompras;
import com.softcont.prsftmaven.vista.InvProductos;
import com.softcont.prsftmaven.vista.VCliente;
import com.softcont.prsftmaven.vista.VCompra;
import com.softcont.prsftmaven.vista.VISesion;
import com.softcont.prsftmaven.vista.VProductos;
import com.softcont.prsftmaven.vista.VUsuario;
import com.softcont.prsftmaven.vista.VMenu;
import com.softcont.prsftmaven.vista.VProveedor;
import com.softcont.prsftmaven.vista.VVentas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import com.softcont.prsftmaven.vista.VCPw;

/**
 * Clase controladora de la vista Menu
 * @author Gerlis Alvarez C.
 */
public class ControladorMenu implements ActionListener{
    VMenu vista;
    DAOISesion modelo;

    public ControladorMenu() {
    }
    
    public ControladorMenu(VMenu vista) throws SQLException, InstantiationException, IllegalAccessException{
        this.vista=vista;
        this.modelo = new DAOISesion();
        vista.setTitle("PRSFT - ( "+modelo.getTpUsuario()+" )");
        vista.setVisible(true);
        vista.btnProductos.addActionListener(this);
        vista.btnUsuarios.addActionListener(this);
        vista.btnProveedores.addActionListener(this);
        vista.btnClientes.addActionListener(this);
        vista.jLUser.setText(modelo.getNomUsuario());
        vista.btnVentas.addActionListener(this);
        vista.jMenuItem1.addActionListener(this);
        vista.btnCompras.addActionListener(this);
        vista.jMCambiarPw.addActionListener(this);
        
        vista.jTabbedPane1.add("Ventas", new com.softcont.prsftmaven.vista.InvVentas());
        vista.jTabbedPane1.add("Compras", new InvCompras());
        vista.jTabbedPane1.add("Productos", new InvProductos());
        validar();
    }
    
    /**
     * Metodo encargado de validar las restricciones del usuario vendedor
     */
    void validar(){
        try {
            if (new DAOISesion().getTpUsuario().equals("Vendedor")){
                this.vista.btnUsuarios.setVisible(false);
                this.vista.btnCompras.setVisible(false);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ControladorMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   /**
    * Se encarga de controlar los eventos de la vista Menu
    * @param e Obejto ActionEvent
    */ 
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource()==vista.btnProductos) {
            try {
                if (new DAOISesion().getTpUsuario().equals("Vendedor")) {
                    VProductos vistaProductos = new VProductos();
                ControladorProductos ctProductos = new ControladorProductos(vistaProductos, new DAOProducto());
                ctProductos.bloquearComponentes();
                }else{
                VProductos vistaProductos = new VProductos();
                ControladorProductos ctProductos = new ControladorProductos(vistaProductos, new DAOProducto());
                }
            } catch (InstantiationException | IllegalAccessException | SQLException ex) {
                Logger.getLogger(ControladorMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (e.getSource()==vista.btnUsuarios) {
            try {
                if (new DAOISesion().getTpUsuario().equals("Secretaria(o)")) {
                    VUsuario vUsuario = new VUsuario();
                    DAOUsuario mUsuario = new DAOUsuario();
                    ControladorUsuario controladorUsuario = new ControladorUsuario(vUsuario, mUsuario);
                    controladorUsuario.bloquearComponentes();
                }else{
                    VUsuario vUsuario = new VUsuario();
                    DAOUsuario mUsuario = new DAOUsuario();
                    ControladorUsuario controladorUsuario = new ControladorUsuario(vUsuario, mUsuario);
                }
            } catch (InstantiationException | IllegalAccessException | SQLException ex) {
                Logger.getLogger(ControladorMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (e.getSource()==vista.btnClientes) {
            try {
                VCliente vCliente = new VCliente();
                DAOCliente mCliente = new DAOCliente();
                ControladorCliente controladorCliente = new ControladorCliente(vCliente, mCliente);
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(ControladorMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (e.getSource()==vista.btnProveedores) {
            try {
                if (new DAOISesion().getTpUsuario().equals("Vendedor")) {
                VProveedor vProveedor = new VProveedor();
                DAOProveedor mProveedor = new DAOProveedor();
                ControladorProveedor controladorProveedor = new ControladorProveedor(vProveedor, mProveedor);
                controladorProveedor.bloquearComponentes();
                }else{
                VProveedor vProveedor = new VProveedor();
                DAOProveedor mProveedor = new DAOProveedor();
                ControladorProveedor controladorProveedor = new ControladorProveedor(vProveedor, mProveedor);
                }
            } catch (InstantiationException | IllegalAccessException | SQLException ex) {
                Logger.getLogger(ControladorMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (e.getSource()==vista.btnVentas) {
            try {
                VVentas vVenta = new VVentas();
                DAOVenta mVenta = new DAOVenta();
                Controladorventa controladorventa = new Controladorventa(vVenta, mVenta);
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(ControladorMenu.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
        
        if (e.getSource() == vista.jMenuItem1) {
            try {
                vista.dispose();
                ControladorISesion controladorISesion = new ControladorISesion(new VISesion(), new DAOISesion());
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(ControladorMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (e.getSource()==vista.jMCambiarPw) {
            VCPw vcPw = new VCPw(vista, true);
            vcPw.setLocationRelativeTo(null);
            vcPw.setVisible(true);
        }
        
        if (e.getSource()==vista.btnCompras) {
            try {
                VCompra vCompra= new VCompra();
                DAOcompras mCompra = new DAOcompras();
                ControladorCompra controladorCompra = new ControladorCompra(vCompra, mCompra);
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(ControladorMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
