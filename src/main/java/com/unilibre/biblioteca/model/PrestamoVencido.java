
package com.unilibre.biblioteca.model;

public class PrestamoVencido implements EstadoPrestamo {

    @Override
    public void renovar(Prestamo prestamo) {
        throw new IllegalStateException(
                "No se puede renovar un préstamo vencido."
        );
    }

    @Override
    public void devolver(Prestamo prestamo) {
        prestamo.getLibro().devolver();
        prestamo.registrarDevolucion();
        prestamo.setEstado(new PrestamoDevuelto());
    }

    @Override
    public String obtenerNombre() {
        return "VENCIDO";
    }
}
