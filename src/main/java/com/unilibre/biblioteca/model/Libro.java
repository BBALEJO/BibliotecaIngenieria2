
package com.unilibre.biblioteca.model;

import java.util.ArrayList;
import java.util.List;

public class Libro {

    private String id;
    private String titulo;
    private String autor;
    private String isbn;
    private int ejemplaresDisponibles;

    private List<ObservadorDisponibilidad> observadores;

    public Libro(String id, String titulo, String autor, String isbn,
                 int ejemplaresDisponibles) {

        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.ejemplaresDisponibles = ejemplaresDisponibles;
        this.observadores = new ArrayList<>();
    }

    @Override
    public String toString() { return titulo + " (" + ejemplaresDisponibles + " disponibles)"; }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    /** Actualiza los datos descriptivos sin reemplazar el objeto ni alterar existencias. */
    public void actualizarDatos(String titulo, String autor, String isbn) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
    }

    public String getAutor() {
        return autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getEjemplaresDisponibles() {
        return ejemplaresDisponibles;
    }

    public boolean consultarDisponibilidad() {
        return ejemplaresDisponibles > 0;
    }

    public void prestar() {
        if (consultarDisponibilidad()) {
            ejemplaresDisponibles--;
        } else {
            throw new IllegalStateException(
                    "No hay ejemplares disponibles."
            );
        }
    }

    public void devolver() {
        ejemplaresDisponibles++;

        if (ejemplaresDisponibles > 0) {
            notificarObservadores();
        }
    }

    public void agregarObservador(ObservadorDisponibilidad observador) {
        if (!observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    public void eliminarObservador(ObservadorDisponibilidad observador) {
        observadores.remove(observador);
    }

    private void notificarObservadores() {
        // Se recorre una copia porque un observador puede darse de baja durante la notificacion.
        for (ObservadorDisponibilidad observador : new ArrayList<>(observadores)) {
            observador.actualizar(this);
        }
    }
}
