
package com.unilibre.biblioteca.model;

public class PrestamoDevuelto implements EstadoPrestamo {

    @Override
    public void renovar(Prestamo prestamo) {
        throw new IllegalStateException(
                "No se puede renovar un préstamo devuelto."
        );
    }

    @Override
    public void devolver(Prestamo prestamo) {
        throw new IllegalStateException(
                "Este préstamo ya fue devuelto."
        );
    }

    @Override
    public String obtenerNombre() {
        return "DEVUELTO";
    }
}
