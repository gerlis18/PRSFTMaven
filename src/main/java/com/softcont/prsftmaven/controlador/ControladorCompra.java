package com.softcont.prsftmaven.controlador;

import com.softcont.prsftmaven.modelo.DAOProducto;
import com.softcont.prsftmaven.modelo.DAOProveedor;
import com.softcont.prsftmaven.modelo.DAOcompras;
import com.softcont.prsftmaven.modelo.DTOCompraReport;
import com.softcont.prsftmaven.modelo.DTOcompras;
import com.softcont.prsftmaven.vista.InvCompras;
import com.softcont.prsftmaven.vista.VCompra;
import com.softcont.prsftmaven.vista.VProductos;
import com.softcont.prsftmaven.vista.VProveedor;
import com.softcont.prsftmaven.vista.VTProveedor;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Clase Controladora de la vista Compra
 *
 * @author Gerlis Alvarez C.
 * @author Jose Salgado O.
 */
public class ControladorCompra implements ActionListener {

    ArrayList<DTOcompras> lista = new ArrayList<>();
    VCompra vista;
    DAOcompras modelo;
    DefaultTableModel model;
    Double suma = 0.0;

    /**
     * Metodo Constructor 1 de la clase ControladorCompra
     *
     * @param vista Interfaz VCompra
     * @param modelo Clase DAO
     */
    public ControladorCompra(VCompra vista, DAOcompras modelo) {

        this.modelo = modelo;
        this.vista = vista;
        vista.setLocationRelativeTo(null);
        modelo.idCompra(vista.txtIdCompra);
        vista.setVisible(true);

        vista.btnRegCompra.addActionListener(this);
        vista.buscar.addActionListener(this);
        vista.btnBProveedor.addActionListener(this);
        vista.btnRegProveedor.addActionListener(this);
        vista.btnRetProducto.addActionListener(this);
        vista.txtFecha.setText(fechaActual());
        vista.btnBProducto.addActionListener(this);
    }

    /**
     * Metodo Constructor 2 de la clase ControladorCompra
     *
     * @param vista Interfaz
     */
    public ControladorCompra(VCompra vista) {
        this.vista = vista;
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////7
    /**
     * Metodo para sumar el precio de cada producto al total
     */
    public void sumarPrecio() {
        Double aux = 0.0;
        Double sumar = 0.0;
        for (int i = 0; i < vista.tbproducto.getRowCount(); i++) {
            String valort = vista.tbproducto.getValueAt(i, 5).toString();
            sumar = Double.parseDouble(valort);
            Double sm = 0.0;
            sm = sm + sumar;
            aux = aux + sm;
        }
        vista.txtValorTotal.setText(aux.toString());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////77
    /**
     * Se encarga de restar precio en la vista Compra
     *
     * @param fila Fila seleccionada
     */
    public void restarPrecio(int fila) {
        Double resta;
        String valor = vista.tbproducto.getValueAt(fila, 5).toString();
        Double cant = Double.parseDouble(valor);
        Double precActual = Double.parseDouble(vista.txtValorTotal.getText());
        resta = precActual - cant;
        vista.txtValorTotal.setText(resta.toString());
        ((DefaultTableModel) vista.tbproducto.getModel()).removeRow(fila);
        removerProducto();
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////77
    /**
     * Metodo que se encarga de Multiplicar la cantidad de cada producto
     */
    public void multCant() {
        for (int i = 0; i < vista.tbproducto.getRowCount(); i++) {
            String cant = vista.tbproducto.getValueAt(i, 2).toString();
            Double canti = Double.parseDouble(cant);
            Double mult = 0.0;
            String precio = vista.tbproducto.getValueAt(i, 3).toString();
            Double prec = Double.parseDouble(precio);
            mult = canti * prec;
            vista.tbproducto.setValueAt(mult.toString(), i, 5);
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////777
    /**
     * Se encarga de retornar la fecha actual del sistema
     *
     * @return Fecha actual
     */
    public static String fechaActual() {
        Date fecha = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/YYYY");
        return ft.format(fecha);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////77
    /**
     * Metodo para remover el producto de la tabla productos
     */
    public void removerProducto() {
        for (int i = 0; i < lista.size(); i++) {
            lista.remove(i);
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////777777
    /**
     * Metodo para limpiar los campos de la vista compras
     */
    public void limpiar() {
        VCompra.txtNit.setText("");
        VCompra.txtNombre.setText("");
        VCompra.txtDireccion.setText("");
        VCompra.txtTelefono.setText("");
        vista.txtIdCompra.setText("");
        vista.txtValorTotal.setText("");

        modelo.removerProducto(vista.tbproducto);
        removerProducto();

    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////7
    /**
     * Metodo para mostrar datos en la tabla
     */
    public void mostrar() {
        String[][] m = new String[lista.size()][6];
        for (int i = 0; i < lista.size(); i++) {
            m[i][0] = lista.get(i).getCodigo();
            m[i][1] = lista.get(i).getNombre();
            m[i][2] = lista.get(i).getCantidad();
            m[i][3] = lista.get(i).getPrecioCompra();
            m[i][4] = lista.get(i).getPrecioVenta();
            m[i][5] = lista.get(i).getValorT();
        }
        vista.tbproducto.setModel(new javax.swing.table.DefaultTableModel(
                m,
                new String[]{
                    "Codigo", "Nombre", "Cantidad", "Precio Compra", "Precio Venta", "Valor Total"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false
            };

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////77
    /**
     * Se encarga de controlar los eventos de la vista Compra
     *
     * @param e Objeto ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.buscar) {
            int i = 0;
            try {
                int ct, c;
                String cant = "";
                ArrayList prods = modelo.codigos();
                Object obj = modelo.codigos();
                String array = String.valueOf(obj);
                if (array.equals("[]")) {
                    modelo.cargar(vista.tbproducto, vista.buscar, false);
                    String cod = vista.tbproducto.getValueAt(i, 0).toString();
                    String nom = vista.tbproducto.getValueAt(i, 1).toString();
                    cant = JOptionPane.showInputDialog(vista, "Ingrese Cantidad");
                    vista.tbproducto.setValueAt(cant, i, 2);

                    String precioCompra = JOptionPane.showInputDialog(vista, "Ingrese Precio de Compra del producto");
                    vista.tbproducto.setValueAt(precioCompra, i, 3);

                    String precioVenta = JOptionPane.showInputDialog(vista, "Ingrese precio de Venta");
                    vista.tbproducto.setValueAt(precioVenta, i, 4);
                    multCant();
                    c = Integer.parseInt(cant);
                    model = new DefaultTableModel();
                    if (cant.equals("") || cant.equals("0")) {
                        removerProducto();
                        //modelo.removerProducto(i);
                        JOptionPane.showMessageDialog(vista, "Error");
                    } else {
                        String vTotal = vista.tbproducto.getValueAt(i, 5).toString();
                        DTOcompras vt = new DTOcompras(cod, nom, cant, precioCompra, precioVenta, vTotal);
                        lista.add(vt);
                    }
                }

                String cod = "";
                String nom = "";
                String codigo = "";
                for (Object prod : prods) {
                    String cods = String.valueOf(prod);
                    modelo.cargar(vista.tbproducto, vista.buscar, false);
                    cod = vista.tbproducto.getValueAt(i, 0).toString();
                    nom = vista.tbproducto.getValueAt(i, 1).toString();
                    if (prod.equals(cod)) {
                        codigo = prod.toString();
                    }
                }
                if (!codigo.equals(cod)) {
                    System.err.println("Estoy entrando por el segundo si con el codigo " + codigo);
                    cant = JOptionPane.showInputDialog(vista, "Ingrese Cantidad");
                    vista.tbproducto.setValueAt(cant, i, 2);

                    String pCompra = JOptionPane.showInputDialog(vista, "Ingrese Precio de Compra del producto");
                    vista.tbproducto.setValueAt(pCompra, i, 3);

                    String pVenta = JOptionPane.showInputDialog(vista, "Ingrese precio de Venta");
                    vista.tbproducto.setValueAt(pVenta, i, 4);
                    multCant();
                    c = Integer.parseInt(cant);
                    model = new DefaultTableModel();
                    if (cant.equals("") || cant.equals("0")) {
                        removerProducto();
                        //modelo.removerProducto(i);
                        JOptionPane.showMessageDialog(vista, "Error");
                    } else {
                        String vTotal = vista.tbproducto.getValueAt(i, 5).toString();
                        DTOcompras vt = new DTOcompras(cod, nom, cant, pCompra, pVenta, vTotal);
                        lista.add(vt);
                    }
                } else {
                    //JOptionPane.showMessageDialog(vista, prod);
                    modelo.cargar(vista.tbproducto, vista.buscar, true);
                    String code = vista.tbproducto.getValueAt(i, 0).toString();
                    String name = vista.tbproducto.getValueAt(i, 1).toString();
                    cant = JOptionPane.showInputDialog(vista, "Ingrese Cantidad");
                    vista.tbproducto.setValueAt(cant, i, 2);

                    String precioCompra = vista.tbproducto.getValueAt(i, 3).toString();
                    String precioVenta = vista.tbproducto.getValueAt(i, 4).toString();
                    multCant();
                    c = Integer.parseInt(cant);
                    model = new DefaultTableModel();
                    if (cant.equals("") || cant.equals("0")) {
                        removerProducto();
                        //modelo.removerProducto(i);
                        JOptionPane.showMessageDialog(vista, "Error");
                    } else {
                        String vTotal = vista.tbproducto.getValueAt(i, 5).toString();
                        DTOcompras vt = new DTOcompras(code, name, cant, precioCompra, precioVenta, vTotal);
                        lista.add(vt);
                    }
                }
                mostrar();
                sumarPrecio();

            } catch (NumberFormatException | HeadlessException ex) {
                Logger.getLogger(ControladorCompra.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(vista, "Inserte numeros");
                removerProducto();
            }
        }

        if (e.getSource() == vista.btnBProveedor) {
            try {
                VTProveedor vproveedor = new VTProveedor(vista, true);
                vproveedor.setLocationRelativeTo(vista.btnBProveedor);
                vproveedor.setVisible(true);
                //DAOTproveedor mTProveedor = new DAOTproveedor();
                //ControladorTproveedor ControladorProveedor = new ControladorTproveedor(vproveedor, mTProveedor);
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(ControladorCompra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource() == vista.btnRegProveedor) {
            try {
                VProveedor vproveedor = new VProveedor();
                DAOProveedor mproveedor = new DAOProveedor();
                ControladorProveedor controladorCliente = new ControladorProveedor(vproveedor, mproveedor);
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(ControladorCompra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource() == vista.btnRetProducto) {
            int fila = vista.tbproducto.getSelectedRow();
            if (fila >= 0) {
                restarPrecio(fila);

            } else {
                JOptionPane.showMessageDialog(vista, "Elija una fila primero");

            }
        }

        if (e.getSource() == vista.btnRegCompra) {

            List venta = new ArrayList();
            Integer j = 1;
            for (int i = 0; i < vista.tbproducto.getRowCount(); i++) {
                DTOCompraReport ventaReport = new DTOCompraReport(j.toString(), vista.tbproducto.getValueAt(i, 1).toString(), vista.tbproducto.getValueAt(i, 2).toString(),
                        vista.tbproducto.getValueAt(i, 3).toString(), vista.tbproducto.getValueAt(i, 4).toString());
                venta.add(ventaReport);
                j++;
            }
            try {
                if (vista.tbproducto.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(vista, "Error!, Rellene Campos");
                } else {
                    ArrayList list = modelo.codigos();
                    String codCompra = "", Proveedor = "", fecha = "", codProducto = "", Cantidad = "", precioCompra = "", valorTotal = "", precioVenta = "";
                    codCompra = vista.txtIdCompra.getText();
                    Proveedor = vista.getidp();
                    fecha = vista.txtFecha.getText();
                    valorTotal = vista.txtValorTotal.getText();
                    String codigo = "";
                    modelo.registrarCompra(codCompra, Proveedor, valorTotal, fecha);
                    for (int f = 0; f < vista.tbproducto.getRowCount(); f++) {
                        codProducto = vista.tbproducto.getValueAt(f, 0).toString();
                        Cantidad = vista.tbproducto.getValueAt(f, 2).toString();
                        precioCompra = vista.tbproducto.getValueAt(f, 3).toString();
                        precioVenta = vista.tbproducto.getValueAt(f, 4).toString();
                        Object object = list;
                        String obj = String.valueOf(object);
                        if ("[]".equals(obj)) {
                            modelo.registrarDetalleCompra(codCompra, codProducto, Cantidad, precioCompra, precioVenta);
                            new DAOProducto().actualizarProducto(codProducto, precioCompra, precioVenta, Cantidad);
                        } else {
                            Object[] array = list.toArray();
                            String code = String.valueOf(array);
                            for (int i = 0; i < array.length; i++) {
                                
                                if (codProducto.equals(array[i].toString())) {

                                    codigo = array[i].toString();
                                    System.err.println(codigo);

                                }
                            }
                                if (codProducto.equals(codigo)) {

                                    System.err.println("Estoy entrando en el 1ro");
                                    modelo.actualizarProducto(codigo, Integer.parseInt(Cantidad));
                                    modelo.registrarDetalleCompra(codCompra, codProducto, Cantidad, precioCompra, precioVenta);
                                } else {
                                    System.err.println("Estoy en el 2do");
                                    modelo.registrarDetalleCompra(codCompra, codProducto, Cantidad, precioCompra, precioVenta);
                                    new DAOProducto().actualizarProducto(codProducto, precioCompra, precioVenta, Cantidad);
                                }
                            
                        }
                    }
                    limpiar();
                    JOptionPane.showMessageDialog(vista, "Compra guardada correctamente");
                    new InvCompras().cargar();
                    modelo.idCompra(vista.txtIdCompra);
                }

            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(ControladorCompra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource() == vista.btnBProducto) {
            try {
                ControladorProductos controladorProductos = new ControladorProductos(new VProductos(), new DAOProducto());
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(ControladorCompra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
