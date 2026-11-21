
package com.unilibre.biblioteca.model;

public class AdministradorCreator extends UsuarioCreator {

    public AdministradorCreator(String id, String nombre, String correo,
                                String contrasenaHash) {
        super(id, nombre, correo, contrasenaHash);
    }

    @Override
    public Usuario crearUsuario() {
        return new Administrador(
                getId(),
                getNombre(),
                getCorreo(),
                getContrasenaHash()
        );
    }
}
