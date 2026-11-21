package com.unilibre.biblioteca.model;

public class SolicitudEspera {
    private final Libro libro;
    private final UsuarioRegular usuario;
    private String estado;

    public SolicitudEspera(Libro libro, UsuarioRegular usuario) {
        this.libro = libro;
        this.usuario = usuario;
        this.estado = "EN ESPERA";
    }

    public Libro getLibro() { return libro; }
    public UsuarioRegular getUsuario() { return usuario; }
    public String getEstado() { return estado; }
    public void notificar() { estado = "NOTIFICADO"; }
}
