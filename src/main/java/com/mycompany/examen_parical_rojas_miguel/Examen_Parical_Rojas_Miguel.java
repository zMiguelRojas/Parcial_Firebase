/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.examen_parical_rojas_miguel;

import Modelo.Conexion;
import Vista.Biblioteca;
import com.google.cloud.firestore.Firestore;


/**
 * Clase principal para la ejecución del sistema de inventario
 * 
 * @author miguel
 */
public class Examen_Parical_Rojas_Miguel {

    public static void main(String args[]) {
        // Inicializa la conexión con Firebase
        Conexion.conectar();

        // Aquí puedes realizar operaciones en Firestore, por ejemplo:
        Firestore firestore = Conexion.getConexion();
        // Usa 'firestore' para realizar operaciones con Firestore

        try {
            // Configurar el Look and Feel HiFi de JTattoo
            javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Examen_Parical_Rojas_Miguel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        // Lanzar la ventana principal de Inventario
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Biblioteca().setVisible(true);
            }
        });
        
        // Al finalizar, puedes cerrar la conexión si es necesario
        // Conexion.cerrarConexion();
    }
}
