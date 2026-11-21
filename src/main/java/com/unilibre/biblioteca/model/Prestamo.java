
package com.unilibre.biblioteca.model;

import java.time.LocalDate;

public class Prestamo {

    private String id;
    private Usuario usuario;
    private Libro libro;
    private LocalDate fechaInicio;
    private LocalDate fechaLimite;
    private LocalDate fechaDevolucion;
    private int renovaciones;
    private EstadoPrestamo estado;

    private static final int MAX_RENOVACIONES = 2;
    private static final int DIAS_RENOVACION = 7;

    public Prestamo(String id, Usuario usuario, Libro libro,
                    LocalDate fechaLimite) {

        if (!libro.consultarDisponibilidad()) {
            throw new IllegalStateException(
                    "No hay ejemplares disponibles."
            );
        }

        this.id = id;
        this.usuario = usuario;
        this.libro = libro;
        this.fechaInicio = LocalDate.now();
        this.fechaLimite = fechaLimite;
        this.fechaDevolucion = null;
        this.renovaciones = 0;
        this.estado = new PrestamoActivo();

        this.libro.prestar();
    }

    public String getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Libro getLibro() {
        return libro;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public int getRenovaciones() {
        return renovaciones;
    }

    public EstadoPrestamo getEstado() {
        return estado;
    }

    public void renovar() {
        if (LocalDate.now().isAfter(fechaLimite)) {
            estado = new PrestamoVencido();
        }

        estado.renovar(this);
    }

    public void devolver() {
        if (LocalDate.now().isAfter(fechaLimite)) {
            estado = new PrestamoVencido();
        }

        estado.devolver(this);
    }

    public void setEstado(EstadoPrestamo estado) {
        this.estado = estado;
    }

    public void incrementarRenovaciones() {
        this.renovaciones++;
    }

    public void registrarDevolucion() {
        this.fechaDevolucion = LocalDate.now();
    }

    public void extenderFechaLimite() {
        this.fechaLimite = this.fechaLimite.plusDays(DIAS_RENOVACION);
    }

    public boolean puedeRenovarse() {
        return renovaciones < MAX_RENOVACIONES;
    }
}
