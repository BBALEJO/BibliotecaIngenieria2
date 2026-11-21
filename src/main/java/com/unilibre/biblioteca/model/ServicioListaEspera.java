package com.unilibre.biblioteca.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicioListaEspera implements ObservadorDisponibilidad {
    private final Map<String, ListaEspera> listas = new HashMap<>();
    private final List<SolicitudEspera> solicitudes = new ArrayList<>();

    public void registrarInteresado(Libro libro, UsuarioRegular usuario) {
        boolean duplicado = solicitudes.stream().anyMatch(s ->
                s.getLibro().getId().equals(libro.getId())
                && s.getUsuario().getId().equals(usuario.getId())
                && "EN ESPERA".equals(s.getEstado()));
        if (duplicado) throw new IllegalStateException("Este usuario ya está en la lista de espera de ese libro.");

        listas.putIfAbsent(libro.getId(), new ListaEspera());
        listas.get(libro.getId()).agregarUsuario(usuario);
        solicitudes.add(new SolicitudEspera(libro, usuario));
        libro.agregarObservador(this);
    }

    public List<SolicitudEspera> getSolicitudes() {
        return new ArrayList<>(solicitudes);
    }

    public boolean tieneSolicitudesPendientes(String idLibro) {
        return solicitudes.stream().anyMatch(s ->
                s.getLibro().getId().equals(idLibro) && "EN ESPERA".equals(s.getEstado()));
    }

    @Override
    public void actualizar(Libro libro) {
        ListaEspera lista = listas.get(libro.getId());
        if (lista == null || lista.estaVacia()) {
            libro.eliminarObservador(this);
            return;
        }

        UsuarioRegular siguiente = lista.obtenerSiguiente();
        siguiente.recibirNotificacion("El libro " + libro.getTitulo() + " está disponible. Es tu turno.");
        solicitudes.stream()
                .filter(s -> s.getLibro().getId().equals(libro.getId())
                        && s.getUsuario().getId().equals(siguiente.getId())
                        && "EN ESPERA".equals(s.getEstado()))
                .findFirst().ifPresent(SolicitudEspera::notificar);

        if (lista.estaVacia()) libro.eliminarObservador(this);
    }
}
