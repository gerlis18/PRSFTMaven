/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softcont.prsftmaven.utils;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author by Gerlis A.C
 */
public class MyEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {

    protected static final String EDIT = "edit";
    JTable tabla;
    Boolean currentValue;
    JButton boton;

    public MyEditor(JTable tabla) {
        boton = new JButton();
        boton.setActionCommand(EDIT);
        boton.addActionListener(this);
        boton.setBorderPainted(false);
        this.tabla = tabla;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        try {
            //JOptionPane.showMessageDialog(tabla, "Abriendo Archivo");
            JOptionPaneConTimeOut.visualizaDialogo(tabla, "Abriendo Archivo", "Abrir Archivo", 1000);
            int fila = tabla.getSelectedRow();
            File path = new File("C:\\Users\\Sena1\\Documents\\Reportes/Factura" + tabla.getValueAt(fila, 0).toString() + ".pdf");
            Desktop.getDesktop().open(path);
            System.out.println(tabla.getSelectedRow());

            fireEditingStopped();
        } catch (IOException ex) {
            Logger.getLogger(MyEditor.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "hubo un error al abrir el archivo");
        }
    }

    @Override
    public Object getCellEditorValue() {
        return currentValue;
    }

    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object value, boolean isSelected, int row, int column) {
        return boton;
    }

}
