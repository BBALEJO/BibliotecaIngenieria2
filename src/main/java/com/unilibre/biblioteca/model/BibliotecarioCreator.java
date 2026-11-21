
package com.unilibre.biblioteca.model;

public class BibliotecarioCreator extends UsuarioCreator {

    public BibliotecarioCreator(String id, String nombre, String correo,
                                String contrasenaHash) {
        super(id, nombre, correo, contrasenaHash);
    }

    @Override
    public Usuario crearUsuario() {
        return new Bibliotecario(
                getId(),
                getNombre(),
                getCorreo(),
                getContrasenaHash()
        );
    }
}
