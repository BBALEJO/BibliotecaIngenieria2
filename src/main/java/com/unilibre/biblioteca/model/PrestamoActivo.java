
package com.unilibre.biblioteca.model;

public class PrestamoActivo implements EstadoPrestamo {

    @Override
    public void renovar(Prestamo prestamo) {

        if (!prestamo.puedeRenovarse()) {
            throw new IllegalStateException(
                    "Se alcanzo el maximo de renovaciones."
            );
        }

        prestamo.extenderFechaLimite();
        prestamo.incrementarRenovaciones();
    }

    @Override
    public void devolver(Prestamo prestamo) {
        prestamo.getLibro().devolver();
        prestamo.registrarDevolucion();
        prestamo.setEstado(new PrestamoDevuelto());
    }

    @Override
    public String obtenerNombre() {
        return "ACTIVO";
    }
}
