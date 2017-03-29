/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softcont.prsftmaven.controlador;

import com.softcont.prsftmaven.modelo.DAOCliente;
import com.softcont.prsftmaven.modelo.DAOProducto;
import com.softcont.prsftmaven.modelo.DAOVenta;
import com.softcont.prsftmaven.modelo.DTOTablaVenta;
import com.softcont.prsftmaven.modelo.DTOVentaReport;
import com.softcont.prsftmaven.vista.InvVentas;
import com.softcont.prsftmaven.vista.VCliente;
import com.softcont.prsftmaven.vista.VTablaClientes;
import com.softcont.prsftmaven.vista.VVentas;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Clase encargada de toda la gestion de la interfaz <i>VVentas</i>
 *
 * @author by Gerlis A.C
 */
public class Controladorventa implements ActionListener {

    VVentas vista;
    DAOVenta modelo;
    ArrayList<DTOTablaVenta> lista = new ArrayList<>();
    Double suma = 0.0;
    DefaultTableModel model;

    public Controladorventa(VVentas vista, DAOVenta modelo) {
        this.modelo = modelo;
        this.vista = vista;
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);

        modelo.idventa(vista.txtidventa);
        vista.btnRegVenta.addActionListener(this);
        vista.btnRCliente.addActionListener(this);
        vista.btnBuscarCl.addActionListener(this);
        vista.btnCotizacion.addActionListener(this);
        vista.btnRetProd.addActionListener(this);
        vista.jcBuscar.addActionListener(this);
        vista.txtfechav.setText(fechaActual());
    }

    /**
     * Metodo encargado de retornar la fecha actual del sistema
     *
     * @return Fecha del dia
     */
    public static String fechaActual() {
        Date Fecha = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/YYYY");
        return ft.format(Fecha);
    }

    /**
     * Metodo encargado de mostrar informacion de productos para una venta
     */
    public void mostrar() {
        String m[][] = new String[lista.size()][5];
        for (int i = 0; i < lista.size(); i++) {
            m[i][0] = lista.get(i).getCodigo();
            m[i][1] = lista.get(i).getNombre();
            m[i][2] = lista.get(i).getCantidad();
            m[i][3] = lista.get(i).getPrecio();
            m[i][4] = lista.get(i).getvTotal();
        }
        vista.jTable1.setModel(new javax.swing.table.DefaultTableModel(
                m,
                new String[]{
                    "Codigo", "Nombre", "Cantidad", "Valor Unt.", "Valor Total"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, true, false, false
            };

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jCTabla();
    }

    void jCTabla() {
        JComboBox jcb = new JComboBox();
        jcb.setEditable(true);
        TableColumn tb = vista.jTable1.getColumnModel().getColumn(2);
        TableCellEditor tce = new DefaultCellEditor(jcb);
        tb.setCellEditor(tce);
    }

    /**
     * Metodo encargado de remover productos de la tabla prodcutos
     *
     * @param i Fila
     */
    public void removerProducto(int i) {
        String mat[][] = new String[lista.size()][5];
        //for (int i = 0; i < lista.size(); i++) {
        lista.remove(i);
        //}
    }

    /**
     * Metodo encargado de sumar el precio de venta de cada producto
     */
    public void sumarPrecio() {
        Double aux = 0.0;
        Double sumar = 0.0;
        for (int i = 0; i < vista.jTable1.getRowCount(); i++) {
            String valort = vista.jTable1.getValueAt(i, 4).toString();
            sumar = Double.parseDouble(valort);
            Double sm = 0.0;
            sm = sm + sumar;
            aux = aux + sm;
        }
        vista.txtPrecio.setText(aux.toString());
    }

    /**
     * Metodo encargado de restar el precio de total al retirar un producto de
     * la tabla productos
     *
     * @param fila Fila de la tabla
     */
    public void restarPrecio(int fila) {
        Double resta = 0.0;
        Double aux = 0.0;
        String valor = vista.jTable1.getValueAt(fila, 4).toString();
        Double cant = Double.parseDouble(valor);
        Double precActual = Double.parseDouble(vista.txtPrecio.getText());
        resta = precActual - cant;
        // aux = resta;
        vista.txtPrecio.setText(resta.toString());
        ((DefaultTableModel) vista.jTable1.getModel()).removeRow(fila);
        removerProducto(fila);
    }

    /**
     * Metodo encargado de multiplicar la cantidad del producto por el precio de
     * venta del producto
     */
    public void multCant() {
        for (int i = 0; i < vista.jTable1.getRowCount(); i++) {
            String cant = vista.jTable1.getValueAt(i, 2).toString();
            Double canti = Double.parseDouble(cant);
            Double mult = 0.0;
            String precio = vista.jTable1.getValueAt(i, 3).toString();
            Double prec = Double.parseDouble(precio);
            mult = canti * prec;
            vista.jTable1.setValueAt(mult.toString(), i, 4);
        }
    }

    /**
     * Metodo encargado de limpiar los campos de la interfaz
     */
    public void limpiarCampos() {
        vista.txtPrecio.setText("");
        vista.txtfechav.setText("");
        vista.txtidventa.setText("");
        VVentas.txtDireccion.setText("");
        VVentas.txtIdCliente.setText("");
        VVentas.txtNomCliente.setText("");
        VVentas.txtTelefono.setText("");
        modelo.removerProducto(vista.jTable1);
        for (int i = 0; i < vista.jTable1.getRowCount(); i++) {
            removerProducto(i);
        }

    }

    /**
     * Metodo encargado de los eventos de la interfaz
     *
     * @param ae Objeto ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == vista.jcBuscar) {
            int i = 0;
            try {
                modelo.cargar(vista.jTable1, vista.jcBuscar);
                int ct, c;
                for (i = 0; i < vista.jTable1.getRowCount(); i++) {
                    String cod = vista.jTable1.getValueAt(i, 0).toString();
                    String nom = vista.jTable1.getValueAt(i, 1).toString();
                    String cantidad = vista.jTable1.getValueAt(i, 2).toString();
                    ct = Integer.parseInt(cantidad);
                    String valor = vista.jTable1.getValueAt(i, 3).toString();

                    String cant = JOptionPane.showInputDialog(vista, "Ingrese Cantidad");
                    c = Integer.parseInt(cant);
                    model = new DefaultTableModel();
                    if (cant.equals("") || cant.equals("0")) {
                        //model.removeRow(i);
                        removerProducto(i);
                        //modelo.removerProducto(i);
                        JOptionPane.showMessageDialog(vista, "Error");
                    } else {
                        String vTotal = vista.jTable1.getValueAt(i, 4).toString();
                        DTOTablaVenta vt = new DTOTablaVenta(cod, nom, cant, vTotal, valor);
                        lista.add(vt);
                    }
                    if (c > ct) {
                        //model.removeRow(i);
                        removerProducto(i);

                        JOptionPane.showMessageDialog(vista, "Digite un valor menor o igual a la cantidad actual");
                    }
                }

                mostrar();
                multCant();
                sumarPrecio();
            } catch (NumberFormatException | HeadlessException e) {
                Logger.getLogger(Controladorventa.class.getName()).log(Level.SEVERE, null, e);
                JOptionPane.showMessageDialog(vista, "Inserte numeros");
                removerProducto(i);
                //modelo.removerProducto(i);
            }
        }

        if (ae.getSource() == vista.btnBuscarCl) {
            try {
                VTablaClientes vTCliente = new VTablaClientes(vista, true);
                vTCliente.setLocationRelativeTo(vista);
                vTCliente.setVisible(true);
                //DAOTCliente mTCliente = new DAOTCliente();
                //ControladorTCliente controladorTCliente = new ControladorTCliente(vTCliente, mTCliente);
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(Controladorventa.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (ae.getSource() == vista.btnRCliente) {
            try {
                VCliente vCliente = new VCliente();
                DAOCliente mCliente = new DAOCliente();
                ControladorCliente controladorCliente = new ControladorCliente(vCliente, mCliente);
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(Controladorventa.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (ae.getSource() == vista.btnRegVenta) {

            List venta = new ArrayList();
            Integer j = 1;
            for (int i = 0; i < vista.jTable1.getRowCount(); i++) {
                DTOVentaReport ventaReport = new DTOVentaReport(j.toString(), vista.jTable1.getValueAt(i, 1).toString(), vista.jTable1.getValueAt(i, 2).toString(),
                        vista.jTable1.getValueAt(i, 3).toString(), vista.jTable1.getValueAt(i, 4).toString());
                venta.add(ventaReport);
                j++;
            }
            try {
                if (vista.jTable1.getRowCount() != 0) {
                    String numFactura = "", Cliente = "", fecha = "", codProducto = "", Cantidad = "", precio = "";
                    numFactura = vista.txtidventa.getText();
                    Cliente = vista.getIdCliente();
                    fecha = vista.txtfechav.getText();
                    modelo.registrarVenta(numFactura, Cliente, fecha);
                    for (int f = 0; f < vista.jTable1.getRowCount(); f++) {
                        codProducto = vista.jTable1.getValueAt(f, 0).toString();
                        Cantidad = vista.jTable1.getValueAt(f, 2).toString();
                        precio = vista.jTable1.getValueAt(f, 4).toString();
                        modelo.registrarDetalleVenta(numFactura, codProducto, Cantidad, precio);
                        Integer cant = new DAOProducto().buscarCantProducto(codProducto);
                        int cant1 = Integer.parseInt(Cantidad);
                        cant -= cant1;
                        new DAOProducto().descontrarProducto(cant.toString(), codProducto);
                    }

                    //JasperReport reporte= (JasperReport) JRLoader.loadObject("src/Reportes/VentaReport.jasper");
                    Map parametro = new HashMap();
                    parametro.put("nomCliente", vista.getNomCliente());
                    parametro.put("idCliente", vista.getIdCliente());
                    parametro.put("formPago", vista.jComboBox2.getSelectedItem().toString());
                    parametro.put("dirCliente", vista.getDirCliente());
                    parametro.put("telCliente", vista.getTelClinte());
                    parametro.put("numFactura", vista.txtidventa.getText());
                    parametro.put("vTotal", vista.txtPrecio.getText());
                    JasperPrint jprint = JasperFillManager.fillReport(this.getClass().getClassLoader().getResourceAsStream("prsft/Reportes/Factura.jasper"), parametro, new JRBeanCollectionDataSource(venta));
                    //JasperViewer.viewReport(jprint, false);
                    JasperExportManager.exportReportToPdfFile(jprint, "C:\\Users\\Sena1\\Documents\\Reportes/Factura" + vista.txtidventa.getText() + ".pdf");
                    File path = new File("C:\\Users\\Sena1\\Documents\\Reportes/Factura" + vista.txtidventa.getText() + ".pdf");
                    Desktop.getDesktop().open(path);
                    JOptionPane.showMessageDialog(vista, "Venta Realizada Correctamente");
                    limpiarCampos();
                    new InvVentas().cargar();
                } else {
                    JOptionPane.showMessageDialog(vista, "Error!, Rellene Campos");
                }

            } catch (JRException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(Controladorventa.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Controladorventa.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(vista, "Error al abrir el archivo");
            }
        }

        if (ae.getSource() == vista.btnRetProd) {
            int fila = vista.jTable1.getSelectedRow();
            if (fila >= 0) {
                restarPrecio(fila);

            } else {
                JOptionPane.showMessageDialog(vista, "Elija una fila primero");

            }
        }
    }

}
