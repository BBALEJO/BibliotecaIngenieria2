
package com.unilibre.biblioteca.model;

public class Bibliotecario extends Usuario {

    public Bibliotecario(String id, String nombre, String correo, String contrasenaHash) {
        super(id, nombre, correo, contrasenaHash);
    }

    @Override
    public String obtenerRol() {
        return "BIBLIOTECARIO";
    }
}
