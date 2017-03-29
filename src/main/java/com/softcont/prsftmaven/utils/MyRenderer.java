/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.softcont.prsftmaven.utils;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * 
 * @author by Gerlis A.C 
 */
public class MyRenderer extends JLabel implements TableCellRenderer {
    boolean  isBordered =true;
    
    public MyRenderer(boolean isBordered){
        this.isBordered=isBordered;
        setOpaque(true);
    }
    
    @Override
    public Component    getTableCellRendererComponent(JTable tabla, Object color, boolean isSelected, boolean hasFocus, int row, int column){
            return new JButton("Abrir");
    }
}
