/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author miguel
 */


public class Libro {
    private String idLibro;
    private String titulo;
    private String idAutor;  // Relación con la tabla Autores
    private String genero;
    private int FechaPublicacion;
    private String isbn;

    // Constructor vacío
    public Libro() {
    }

    // Constructor con parámetros
    public Libro(String idLibro, String titulo, String idAutor, String genero, int anioPublicacion, String isbn) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.idAutor = idAutor;
        this.genero = genero;
        this.FechaPublicacion = anioPublicacion;
        this.isbn = isbn;
    }

    // Getters y Setters
    public String getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(String idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(String idAutor) {
        this.idAutor = idAutor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAnioPublicacion() {
        return FechaPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.FechaPublicacion = anioPublicacion;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
