
package com.unilibre.biblioteca.model;

public abstract class Usuario {

    private String id;
    private String nombre;
    private String correo;
    private String contrasenaHash;

    public Usuario(String id, String nombre, String correo, String contrasenaHash) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasenaHash = contrasenaHash;
    }

    @Override
    public String toString() { return nombre + " - " + obtenerRol(); }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasenaHash() {
        return contrasenaHash;
    }

    public abstract String obtenerRol();
}
