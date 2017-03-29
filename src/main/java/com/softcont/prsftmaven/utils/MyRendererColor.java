/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softcont.prsftmaven.utils;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Gerlis A.C
 */
public class MyRendererColor extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
/**
 * 
 */
        if ("Anulado".equals(table.getValueAt(row, 3))) {
            this.setOpaque(true);
            Color color = new Color(255, 51, 51);
            this.setBackground(color);
            this.setForeground(Color.WHITE);
        }else if ("Anulado".equals(table.getValueAt(row, 4))) {
            this.setOpaque(true);
            Color color = new Color(255, 51, 51);
            this.setBackground(color);
            this.setForeground(Color.WHITE);
        }
        
//
//        if (isSelected) {
//            cell.setBackground(Color.GREEN);
//            cell.setForeground(Color.WHITE);
       else{
            this.setOpaque(true);
            this.setBackground(Color.WHITE);
            this.setForeground(Color.BLACK);
        } 
        if (isSelected) {
            this.setOpaque(true);
            Color color = new Color(51, 153, 255);
            this.setBackground(color);
            this.setForeground(Color.WHITE);
        }

//        if ("0".equals(table.getValueAt(row, 2))) {
//            this.setOpaque(true);
//            Color color = new Color(255, 51, 51);
//            this.setBackground(color);
//            this.setForeground(Color.WHITE);
//        }
        return this;
    }
}
