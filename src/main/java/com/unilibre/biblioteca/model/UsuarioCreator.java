
package com.unilibre.biblioteca.model;

public abstract class UsuarioCreator {

    private String id;
    private String nombre;
    private String correo;
    private String contrasenaHash;

    public UsuarioCreator(String id, String nombre, String correo,
                          String contrasenaHash) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasenaHash = contrasenaHash;
    }

    public abstract Usuario crearUsuario();

    protected String getId() {
        return id;
    }

    protected String getNombre() {
        return nombre;
    }

    protected String getCorreo() {
        return correo;
    }

    protected String getContrasenaHash() {
        return contrasenaHash;
    }
}
