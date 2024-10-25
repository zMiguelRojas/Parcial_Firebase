/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Conexion;

import Vista.Biblioteca;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import javax.swing.table.DefaultTableModel;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FirestoreException;




public class Controlador {
    private Biblioteca vista;
    private Firestore db;

    public Controlador(Biblioteca vista) {
        this.vista = vista;
        this.db = Conexion.getConexion(); // Obtener la conexión a Firestore
    }
    
      public void guardar() {
    // Obtener los datos del autor
    String nombre = vista.getNombre();
    String nacionalidad = vista.getNacionalidad();
    Date fechaNacimiento = vista.getFechaNacimiento();

    // Validar que no sea null
    if (fechaNacimiento == null) {
        JOptionPane.showMessageDialog(vista, "Por favor, seleccione una fecha de nacimiento.");
        return;
    }

    // Crear un nuevo documento para el autor
    Map<String, Object> autorData = new HashMap<>();
    autorData.put("nombre", nombre);
    autorData.put("nacionalidad", nacionalidad);
    autorData.put("fecha_nacimiento", fechaNacimiento);

    // Guardar el autor en Firestore
    DocumentReference autorRef = db.collection("autores").document(String.valueOf(getNextId("autores"))); // Usar ID numérico
    ApiFuture<WriteResult> future = autorRef.set(autorData);

    try {
        future.get();
        JOptionPane.showMessageDialog(vista, "Autor guardado exitosamente.");
    } catch (InterruptedException | ExecutionException e) {
        JOptionPane.showMessageDialog(vista, "Error al guardar los datos: " + e.getMessage());
    }

    // Obtener los datos del libro
    String titulo = vista.getTitulo();
    Date fechaPublicacion = vista.getFechaPublicacion();
    String isbn = vista.getIsbn();

    // Validar fecha de publicación
    if (fechaPublicacion == null) {
        JOptionPane.showMessageDialog(vista, "Por favor, seleccione una fecha de publicación.");
        return;
    }

    // Crear un nuevo documento para el libro
    Map<String, Object> libroData = new HashMap<>();
    libroData.put("titulo", titulo);
    libroData.put("id_autor", autorRef.getId());
    libroData.put("fecha_publicacion", fechaPublicacion);
    libroData.put("isbn", isbn);

    // Guardar el libro en Firestore
    DocumentReference libroRef = db.collection("libros").document(String.valueOf(getNextId("libros"))); // Usar ID numérico
    future = libroRef.set(libroData);

    try {
        future.get();
        JOptionPane.showMessageDialog(vista, "Libro guardado exitosamente.");
    } catch (InterruptedException | ExecutionException e) {
        JOptionPane.showMessageDialog(vista, "Error al guardar el libro: " + e.getMessage());
    }

    // Obtener los datos del préstamo
    Date fechaPrestamo = vista.getFechaPrestamo();
    Date fechaDevolucion = vista.getFechaDevolucion();

    // Validar fechas de préstamo y devolución
    if (fechaPrestamo == null || fechaDevolucion == null) {
        JOptionPane.showMessageDialog(vista, "Por favor, seleccione las fechas de préstamo y devolución.");
        return;
    }

    // Crear un nuevo documento para el préstamo
    Map<String, Object> prestamoData = new HashMap<>();
    prestamoData.put("id_libro", libroRef.getId());
    prestamoData.put("fecha_prestamo", fechaPrestamo);
    prestamoData.put("fecha_devolucion", fechaDevolucion);

    // Guardar el préstamo en Firestore
    DocumentReference prestamoRef = db.collection("prestamos").document(String.valueOf(getNextId("prestamos"))); // Usar ID numérico
    future = prestamoRef.set(prestamoData);

    try {
        future.get();
        JOptionPane.showMessageDialog(vista, "Préstamo guardado exitosamente.");
    } catch (InterruptedException | ExecutionException e) {
        JOptionPane.showMessageDialog(vista, "Error al guardar el préstamo: " + e.getMessage());
    }

    // Limpiar los campos
    vista.limpiarCampos();
}

// Método para obtener el siguiente ID numérico
private int getNextId(String collectionName) {
    // Lógica para obtener el siguiente ID numérico de la colección
    // Esto puede incluir una consulta para contar los documentos existentes y sumar uno
    // Asegúrate de manejar posibles errores y concurrentes en la lógica

    // Ejemplo simple:
    int count = 0;
    try {
        count = (int) db.collection(collectionName).get().get().size();
    } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
    }
    return count + 1;
}

      
   public void editar() {
    int filaSeleccionada = vista.getFilaSeleccionada();

    // Validar si hay una fila seleccionada
    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(vista, "Por favor, seleccione una fila para editar.");
        return;
    }

    // Obtener los datos actuales de la fila seleccionada
    String idAutor = vista.getValorTabla(filaSeleccionada, 0);
    String nombre = vista.getValorTabla(filaSeleccionada, 1);
    String nacionalidad = vista.getValorTabla(filaSeleccionada, 2);
    String fechaNacimiento = vista.getValorTabla(filaSeleccionada, 3);
    String idLibro = vista.getValorTabla(filaSeleccionada, 4);
    String titulo = vista.getValorTabla(filaSeleccionada, 5);
    String fechaPublicacion = vista.getValorTabla(filaSeleccionada, 6);
    String isbn = vista.getValorTabla(filaSeleccionada, 7);
    String idPrestamo = vista.getValorTabla(filaSeleccionada, 8);
    String fechaPrestamo = vista.getValorTabla(filaSeleccionada, 9);
    String fechaDevolucion = vista.getValorTabla(filaSeleccionada, 10);

    // Solicitar nuevos valores para cada campo
    String nuevoNombre = vista.solicitarNuevoDato("Editar Nombre:", nombre);
    String nuevaNacionalidad = vista.solicitarNuevoDato("Editar Nacionalidad:", nacionalidad);
    String nuevaFechaNacimiento = vista.solicitarNuevoDato("Editar Fecha de Nacimiento (yyyy-MM-dd):", fechaNacimiento);
    String nuevoTitulo = vista.solicitarNuevoDato("Editar Título del Libro:", titulo);
    String nuevaFechaPublicacion = vista.solicitarNuevoDato("Editar Fecha de Publicación (yyyy-MM-dd):", fechaPublicacion);
    String nuevoIsbn = vista.solicitarNuevoDato("Editar ISBN:", isbn);
    String nuevaFechaPrestamo = vista.solicitarNuevoDato("Editar Fecha de Préstamo (yyyy-MM-dd):", fechaPrestamo);
    String nuevaFechaDevolucion = vista.solicitarNuevoDato("Editar Fecha de Devolución (yyyy-MM-dd):", fechaDevolucion);

    // Actualizar autor
    DocumentReference autorRef = db.collection("autores").document(idAutor);
    Map<String, Object> autorData = new HashMap<>();
    autorData.put("nombre", nuevoNombre);
    autorData.put("nacionalidad", nuevaNacionalidad);
    autorData.put("fecha_nacimiento", nuevaFechaNacimiento);

    ApiFuture<WriteResult> futureAutor = autorRef.update(autorData);
    try {
        futureAutor.get(); // Esperar a que se complete la operación
        vista.getTablaBiblioteca().setValueAt(nuevoNombre, filaSeleccionada, 1);
        vista.getTablaBiblioteca().setValueAt(nuevaNacionalidad, filaSeleccionada, 2);
        vista.getTablaBiblioteca().setValueAt(nuevaFechaNacimiento, filaSeleccionada, 3);
        JOptionPane.showMessageDialog(vista, "Autor actualizado exitosamente.");
    } catch (InterruptedException | ExecutionException e) {
        JOptionPane.showMessageDialog(vista, "Error al actualizar el autor: " + e.getMessage());
    }

    // Actualizar libro
    DocumentReference libroRef = db.collection("libros").document(idLibro);
    Map<String, Object> libroData = new HashMap<>();
    libroData.put("titulo", nuevoTitulo);
    libroData.put("fecha_publicacion", nuevaFechaPublicacion);
    libroData.put("isbn", nuevoIsbn);

    ApiFuture<WriteResult> futureLibro = libroRef.update(libroData);
    try {
        futureLibro.get(); // Esperar a que se complete la operación
        vista.getTablaBiblioteca().setValueAt(nuevoTitulo, filaSeleccionada, 5);
        vista.getTablaBiblioteca().setValueAt(nuevaFechaPublicacion, filaSeleccionada, 6);
        vista.getTablaBiblioteca().setValueAt(nuevoIsbn, filaSeleccionada, 7);
        JOptionPane.showMessageDialog(vista, "Libro actualizado exitosamente.");
    } catch (InterruptedException | ExecutionException e) {
        JOptionPane.showMessageDialog(vista, "Error al actualizar el libro: " + e.getMessage());
    }

    // Actualizar préstamo
    DocumentReference prestamoRef = db.collection("prestamos").document(idPrestamo);
    Map<String, Object> prestamoData = new HashMap<>();
    prestamoData.put("fecha_prestamo", nuevaFechaPrestamo);
    prestamoData.put("fecha_devolucion", nuevaFechaDevolucion);

    ApiFuture<WriteResult> futurePrestamo = prestamoRef.update(prestamoData);
    try {
        futurePrestamo.get(); // Esperar a que se complete la operación
        vista.getTablaBiblioteca().setValueAt(nuevaFechaPrestamo, filaSeleccionada, 9);
        vista.getTablaBiblioteca().setValueAt(nuevaFechaDevolucion, filaSeleccionada, 10);
        JOptionPane.showMessageDialog(vista, "Préstamo actualizado exitosamente.");
    } catch (InterruptedException | ExecutionException e) {
        JOptionPane.showMessageDialog(vista, "Error al actualizar el préstamo: " + e.getMessage());
    }
}
        
public void eliminarRegistro() {
    int filaSeleccionada = vista.getTablaBiblioteca().getSelectedRow();

    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(vista, "Por favor, seleccione una fila para eliminar.");
        return;
    }

    int respuesta = JOptionPane.showConfirmDialog(vista, "¿Estás seguro de que deseas eliminar este registro?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
    if (respuesta != JOptionPane.YES_OPTION) {
        return; // Salir si el usuario no confirma
    }

    // Obtener los IDs de autor, libro y préstamo de la fila seleccionada
    String idAutor = vista.getTablaBiblioteca().getValueAt(filaSeleccionada, 0).toString();
    String idLibro = vista.getTablaBiblioteca().getValueAt(filaSeleccionada, 4).toString();
    String idPrestamo = vista.getTablaBiblioteca().getValueAt(filaSeleccionada, 8).toString();

    Firestore db = Conexion.getConexion(); // Usar la conexión desde la clase Conexion

    // Referencias a documentos
    DocumentReference autorRef = db.collection("autores").document(idAutor);
    DocumentReference libroRef = db.collection("libros").document(idLibro);
    DocumentReference prestamoRef = db.collection("prestamos").document(idPrestamo);

    // Iniciar una transacción
    ApiFuture<Void> futureTransaction = db.runTransaction(txn -> {
        // Eliminar los documentos en orden
        if (!idPrestamo.isEmpty()) {
            txn.delete(prestamoRef);
        }

        if (!idLibro.isEmpty()) {
            txn.delete(libroRef);
        }

        if (!idAutor.isEmpty()) {
            txn.delete(autorRef);
        }
        
        return null; // Transacción exitosa
    });

    try {
        // Esperar a que la transacción se complete
        futureTransaction.get();
        
        // Actualizar la tabla
        ((DefaultTableModel) vista.getTablaBiblioteca().getModel()).removeRow(filaSeleccionada);
        JOptionPane.showMessageDialog(vista, "Datos eliminados exitosamente.");

    } catch (InterruptedException | ExecutionException | FirestoreException e) {
        JOptionPane.showMessageDialog(vista, "Error: " + e.getMessage());
    }
}


    
public void listarDatos() {
    DefaultTableModel modeloTabla = (DefaultTableModel) vista.getTablaBiblioteca().getModel();
    modeloTabla.setRowCount(0); // Limpiar la tabla antes de llenarla

    // Obtener referencia a la colección de autores
    CollectionReference autoresRef = db.collection("autores");
    CollectionReference librosRef = db.collection("libros");
    CollectionReference prestamosRef = db.collection("prestamos");

    // Obtener todos los autores
    ApiFuture<QuerySnapshot> autoresSnapshot = autoresRef.get();
    
    try {
        for (DocumentSnapshot autorDoc : autoresSnapshot.get().getDocuments()) {
            String idAutor = autorDoc.getId();
            String nombre = autorDoc.getString("nombre");
            String nacionalidad = autorDoc.getString("nacionalidad");
            Date fechaNacimiento = autorDoc.getDate("fecha_nacimiento");

            // Obtener libros relacionados con el autor
            ApiFuture<QuerySnapshot> librosSnapshot = librosRef.whereEqualTo("id_autor", idAutor).get();
            
            for (DocumentSnapshot libroDoc : librosSnapshot.get().getDocuments()) {
                String idLibro = libroDoc.getId();
                String titulo = libroDoc.getString("titulo");
                Date fechaPublicacion = libroDoc.getDate("fecha_publicacion");
                String isbn = libroDoc.getString("isbn");

                // Obtener préstamos relacionados con el libro
                ApiFuture<QuerySnapshot> prestamosSnapshot = prestamosRef.whereEqualTo("id_libro", idLibro).get();
                
                for (DocumentSnapshot prestamoDoc : prestamosSnapshot.get().getDocuments()) {
                    String idPrestamo = prestamoDoc.getId();
                    Date fechaPrestamo = prestamoDoc.getDate("fecha_prestamo");
                    Date fechaDevolucion = prestamoDoc.getDate("fecha_devolucion");

                    // Llenar la tabla con los datos obtenidos
                    Object[] fila = new Object[11];
                    fila[0] = idAutor; // Puedes cambiar a String si deseas
                    fila[1] = nombre;
                    fila[2] = nacionalidad;
                    fila[3] = fechaNacimiento;
                    fila[4] = idLibro; // Puedes cambiar a String si deseas
                    fila[5] = titulo;
                    fila[6] = fechaPublicacion;
                    fila[7] = isbn;
                    fila[8] = idPrestamo; // Puedes cambiar a String si deseas
                    fila[9] = fechaPrestamo;
                    fila[10] = fechaDevolucion;

                    modeloTabla.addRow(fila);
                }
            }
        }
        
        JOptionPane.showMessageDialog(vista, "Datos listados exitosamente.");

    } catch (InterruptedException | ExecutionException e) {
        JOptionPane.showMessageDialog(vista, "Error al listar los datos: " + e.getMessage());
    }
}

        
public void crearPDF() {
    Document documento = new Document();

   try {
    String ruta = System.getProperty("user.home") + "/Desktop/Reporte_Biblioteca.pdf"; // Ruta del escritorio
    PdfWriter.getInstance(documento, new FileOutputStream(ruta));
    documento.open();
  

        PdfPTable tabla = new PdfPTable(4); // Cambia el número de columnas según tu necesidad
        tabla.addCell("Id Autor");
        tabla.addCell("Nombre");
        tabla.addCell("Nacionalidad");
        tabla.addCell("Fecha de Nacimiento");

        // Obtener referencia a la colección de autores
        CollectionReference autoresRef = db.collection("autores");
        
        // Obtener todos los autores
        ApiFuture<QuerySnapshot> autoresSnapshot = autoresRef.get();

        if (autoresSnapshot.get().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "No hay datos en la colección de autores.");
        } else {
            for (DocumentSnapshot autorDoc : autoresSnapshot.get().getDocuments()) {
                String idAutor = autorDoc.getId(); // ID del documento
                String nombre = autorDoc.getString("nombre");
                String nacionalidad = autorDoc.getString("nacionalidad");
                Date fechaNacimiento = autorDoc.getDate("fecha_nacimiento");

                tabla.addCell(idAutor);
                tabla.addCell(nombre);
                tabla.addCell(nacionalidad);
                tabla.addCell(fechaNacimiento != null ? fechaNacimiento.toString() : "N/A");
            }

            documento.add(tabla);
            JOptionPane.showMessageDialog(vista, "Reporte creado en: " + ruta);
        }
    } catch (DocumentException | FileNotFoundException e) {
        JOptionPane.showMessageDialog(vista, "Error al crear el PDF: " + e.getMessage());
    } catch (InterruptedException | ExecutionException e) {
        JOptionPane.showMessageDialog(vista, "Error al obtener datos: " + e.getMessage());
    } finally {
        documento.close();
    }
}
}
