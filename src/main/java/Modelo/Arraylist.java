/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.util.ArrayList;
/**
 *
 * @author miguel
 */

public class Arraylist {
    
    // Lista de libros
    private ArrayList<Libro> libros;
    
    // Lista de autores
    private ArrayList<Autor> autores;
    
    // Lista de préstamos
    private ArrayList<Prestamo> prestamos;

    // Constructor para inicializar las listas
    public  Arraylist() {
        libros = new ArrayList<>();
        autores = new ArrayList<>();
        prestamos = new ArrayList<>();
    }

    // Métodos para la gestión de libros
    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    public ArrayList<Libro> obtenerLibros() {
        return libros;
    }

    public void eliminarLibro(String idLibro) {
        libros.removeIf(libro -> libro.getIdLibro().equals(idLibro));
    }

    // Métodos para la gestión de autores
    public void agregarAutor(Autor autor) {
        autores.add(autor);
    }

    public ArrayList<Autor> obtenerAutores() {
        return autores;
    }

    public void eliminarAutor(String idAutor) {
        autores.removeIf(autor -> autor.getIdAutor().equals(idAutor));
    }

    // Métodos para la gestión de préstamos
    public void agregarPrestamo(Prestamo prestamo) {
        prestamos.add(prestamo);
    }

    public ArrayList<Prestamo> obtenerPrestamos() {
        return prestamos;
    }

    public void eliminarPrestamo(String idPrestamo) {
        prestamos.removeIf(prestamo -> prestamo.getIdPrestamo().equals(idPrestamo));
    }
}
