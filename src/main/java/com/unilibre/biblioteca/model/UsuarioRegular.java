
package com.unilibre.biblioteca.model;

public class UsuarioRegular extends Usuario {

    public UsuarioRegular(String id, String nombre, String correo,
                          String contrasenaHash) {
        super(id, nombre, correo, contrasenaHash);
    }

    @Override
    public String obtenerRol() {
        return "USUARIO_REGULAR";
    }

    public void recibirNotificacion(String mensaje) {
        System.out.println(
                "Notificacion para " + getNombre() + ": " + mensaje
        );
    }
}
