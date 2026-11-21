package com.unilibre.biblioteca.model;

import java.util.ArrayList;
import java.util.List;

/** Almacén compartido en memoria para los módulos de la aplicación. */
public class BibliotecaDatos {
    private final List<Libro> libros = new ArrayList<>();
    private final List<Usuario> usuarios = new ArrayList<>();
    private final List<Prestamo> prestamos = new ArrayList<>();
    private final List<Multa> multas = new ArrayList<>();
    private final ServicioListaEspera servicioListaEspera = new ServicioListaEspera();

    public List<Libro> getLibros() { return libros; }
    public List<Usuario> getUsuarios() { return usuarios; }
    public List<Prestamo> getPrestamos() { return prestamos; }
    public List<Multa> getMultas() { return multas; }
    public ServicioListaEspera getServicioListaEspera() { return servicioListaEspera; }
}
