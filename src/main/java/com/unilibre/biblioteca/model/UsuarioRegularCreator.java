
package com.unilibre.biblioteca.model;

public class UsuarioRegularCreator extends UsuarioCreator {

    public UsuarioRegularCreator(String id, String nombre, String correo,
                                 String contrasenaHash) {
        super(id, nombre, correo, contrasenaHash);
    }

    @Override
    public Usuario crearUsuario() {
        return new UsuarioRegular(
                getId(),
                getNombre(),
                getCorreo(),
                getContrasenaHash()
        );
    }
}
