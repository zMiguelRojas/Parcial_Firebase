/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;

public class Conexion {

    private static Firestore db;

    // Método para inicializar Firebase y obtener la instancia de Firestore
    public static void conectar() {
        if (db != null) {
            return; // Ya está conectado
        }

        try {
            FileInputStream serviceAccount = new FileInputStream("examen-parcial.json");

            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

            FirebaseApp.initializeApp(options);

            // Obtenemos Firestore
            db = FirestoreClient.getFirestore();
            System.out.println("Conexión exitosa a Firebase!");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al conectar con Firebase: " + e.getMessage());
        }
    }

    // Método para obtener la conexión a Firestore
    public static Firestore getConexion() {
        if (db == null) {
            conectar(); // Inicializa Firebase si aún no se ha conectado
        }
        return db;
    }

    // Método para cerrar la conexión (no necesario para Firebase, pero se puede implementar)
    public static void cerrarConexion() {
        // Firebase no necesita cerrar la conexión explícitamente
        db = null; // Limpia la referencia para permitir recolección de basura
        System.out.println("Conexión a Firebase cerrada (referencia limpiada).");
    }
}