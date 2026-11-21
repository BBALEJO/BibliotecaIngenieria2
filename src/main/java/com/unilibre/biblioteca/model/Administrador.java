
package com.unilibre.biblioteca.model;

public class Administrador extends Usuario {

    public Administrador(String id, String nombre, String correo, String contrasenaHash) {
        super(id, nombre, correo, contrasenaHash);
    }

    @Override
    public String obtenerRol() {
        return "ADMINISTRADOR";
    }
}
