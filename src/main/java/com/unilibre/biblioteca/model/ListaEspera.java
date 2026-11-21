
package com.unilibre.biblioteca.model;

import java.util.LinkedList;
import java.util.Queue;

public class ListaEspera {

    private Queue<UsuarioRegular> usuarios;

    public ListaEspera() {
        this.usuarios = new LinkedList<>();
    }

    public void agregarUsuario(UsuarioRegular usuario) {
        usuarios.offer(usuario);
    }

    public UsuarioRegular obtenerSiguiente() {
        return usuarios.poll();
    }

    public boolean estaVacia() {
        return usuarios.isEmpty();
    }
}
