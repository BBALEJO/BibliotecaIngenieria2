
package com.unilibre.biblioteca.model;

public interface EstadoPrestamo {

    void renovar(Prestamo prestamo);

    void devolver(Prestamo prestamo);

    String obtenerNombre();
}
