package com.softcont.prsftmaven.utils;

/**
 * Clase encargada de mostrar un JOptionPane con un tiempo determinado
 *
 * @author chuidiang
 */
import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * Muestra un JOptionPane con un timeout para que se cierre automaticamente.
 *
 * @author chuidiang
 *
 */
public class JOptionPaneConTimeOut {

    private static JOptionPane option = new JOptionPane("",
            JOptionPane.INFORMATION_MESSAGE);

    private static JDialog dialogo = null;

    public static void visualizaDialogo(Component padre, String texto,
            String titulo, final long timeout) {
        option.setMessage(texto);
        if (null == dialogo) {
            dialogo = option.createDialog(padre, titulo);
        } else {
            dialogo.setTitle(titulo);
        }

        Thread hilo = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(timeout);
                    if (dialogo.isVisible()) {
                        dialogo.setVisible(false);
                    }
                } catch (InterruptedException e) {
                    Logger.getLogger(JOptionPaneConTimeOut.class.getName()).log(Level.WARNING, "Interrupted!", e);
                    Thread.currentThread().interrupt();
                }
            }
        };
        hilo.start();

        dialogo.setVisible(true);
    }
}
