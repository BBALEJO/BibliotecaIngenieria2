package com.unilibre.biblioteca.ui;

import com.unilibre.biblioteca.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelListaEspera extends JPanel {
    private final BibliotecaDatos datos;
    private final JComboBox<Libro> cmbLibro = new JComboBox<>();
    private final JComboBox<UsuarioRegular> cmbUsuario = new JComboBox<>();
    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"Libro", "Usuario", "Correo", "Estado"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable tabla = new JTable(modelo);

    public PanelListaEspera(BibliotecaDatos datos) {
        this.datos = datos;
        setLayout(new BorderLayout(12, 12));
        setBackground(new Color(245, 245, 240));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Lista de espera");
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        add(titulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridLayout(2, 2, 10, 10));
        formulario.setBorder(BorderFactory.createTitledBorder("Registrar interesado"));
        formulario.add(new JLabel("Libro:"));
        formulario.add(cmbLibro);
        formulario.add(new JLabel("Usuario regular:"));
        formulario.add(cmbUsuario);

        JButton btnAgregar = new JButton("Agregar a lista de espera");
        JButton btnActualizar = new JButton("Actualizar datos");
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botones.add(btnAgregar);
        botones.add(btnActualizar);

        JPanel superior = new JPanel(new BorderLayout(8, 8));
        superior.add(formulario, BorderLayout.CENTER);
        superior.add(botones, BorderLayout.SOUTH);
        add(superior, BorderLayout.NORTH);

        tabla.setRowHeight(25);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnAgregar.addActionListener(e -> agregarInteresado());
        btnActualizar.addActionListener(e -> actualizarTodo());
        actualizarTodo();
    }

    public void refrescar() {
        actualizarTodo();
    }

    private void actualizarTodo() {
        cmbLibro.removeAllItems();
        for (Libro libro : datos.getLibros()) cmbLibro.addItem(libro);
        cmbUsuario.removeAllItems();
        for (Usuario usuario : datos.getUsuarios()) {
            if (usuario instanceof UsuarioRegular regular) cmbUsuario.addItem(regular);
        }
        actualizarTabla();
    }

    private void agregarInteresado() {
        Libro libro = (Libro) cmbLibro.getSelectedItem();
        UsuarioRegular usuario = (UsuarioRegular) cmbUsuario.getSelectedItem();
        if (libro == null || usuario == null) {
            JOptionPane.showMessageDialog(this, "Registra primero un libro y al menos un usuario regular.");
            return;
        }
        if (libro.consultarDisponibilidad()) {
            JOptionPane.showMessageDialog(this,
                    "El libro tiene ejemplares disponibles. Registra un préstamo en lugar de agregarlo a la lista de espera.");
            return;
        }
        try {
            datos.getServicioListaEspera().registrarInteresado(libro, usuario);
            actualizarTabla();
            JOptionPane.showMessageDialog(this,
                    "Usuario agregado. Cuando el libro vuelva a estar disponible, recibirá una notificación en la consola.");
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No se pudo agregar", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void actualizarTabla() {
        modelo.setRowCount(0);
        for (SolicitudEspera solicitud : datos.getServicioListaEspera().getSolicitudes()) {
            modelo.addRow(new Object[]{solicitud.getLibro().getTitulo(),
                    solicitud.getUsuario().getNombre(), solicitud.getUsuario().getCorreo(), solicitud.getEstado()});
        }
    }
}
