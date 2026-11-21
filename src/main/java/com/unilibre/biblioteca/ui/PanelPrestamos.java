package com.unilibre.biblioteca.ui;

import com.unilibre.biblioteca.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class PanelPrestamos extends JPanel {
    private final BibliotecaDatos datos;
    private final JComboBox<Libro> cmbLibro = new JComboBox<>();
    private final JComboBox<Usuario> cmbUsuario = new JComboBox<>();
    private final JTextField txtId = new JTextField();
    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"ID", "Usuario", "Libro", "Inicio", "Vencimiento", "Estado", "Renovaciones"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tabla = new JTable(modelo);

    public PanelPrestamos(BibliotecaDatos datos) {
        this.datos = datos;
        setLayout(new BorderLayout(12, 12));
        setBackground(new Color(245,245,240));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel titulo = new JLabel("Gestion de prestamos");
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        add(titulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridLayout(3,2,10,10));
        formulario.setBorder(BorderFactory.createTitledBorder("Registrar prestamo"));
        formulario.add(new JLabel("ID del prestamo:"));
        formulario.add(txtId);
        formulario.add(new JLabel("Usuario:"));
        formulario.add(cmbUsuario);
        formulario.add(new JLabel("Libro:"));
        formulario.add(cmbLibro);

        JButton btnRegistrar = new JButton("Registrar prestamo (15 dias)");
        JButton btnRenovar = new JButton("Renovar seleccionado");
        JButton btnDevolver = new JButton("Registrar devolucion");
        JButton btnActualizar = new JButton("Actualizar datos");
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        acciones.add(btnRegistrar); acciones.add(btnRenovar);
        acciones.add(btnDevolver); acciones.add(btnActualizar);

        JPanel superior = new JPanel(new BorderLayout(8,8));
        superior.add(formulario, BorderLayout.CENTER);
        superior.add(acciones, BorderLayout.SOUTH);
        add(superior, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnRegistrar.addActionListener(e -> registrar());
        btnRenovar.addActionListener(e -> ejecutarAccion(true));
        btnDevolver.addActionListener(e -> ejecutarAccion(false));
        btnActualizar.addActionListener(e -> actualizarTodo());
        actualizarTodo();
    }

    private void actualizarTodo() {
        cmbLibro.removeAllItems();
        for (Libro libro : datos.getLibros()) cmbLibro.addItem(libro);
        cmbUsuario.removeAllItems();
        for (Usuario usuario : datos.getUsuarios()) cmbUsuario.addItem(usuario);
        actualizarTabla();
    }

    private void registrar() {
        String id = txtId.getText().trim();
        Usuario usuario = (Usuario)cmbUsuario.getSelectedItem();
        Libro libro = (Libro)cmbLibro.getSelectedItem();
        if (id.isEmpty() || usuario == null || libro == null) {
            JOptionPane.showMessageDialog(this, "Registra primero libros y usuarios, y completa el ID.");
            return;
        }
        if (datos.getPrestamos().stream().anyMatch(p -> p.getId().equalsIgnoreCase(id))) {
            JOptionPane.showMessageDialog(this, "Ya existe un prestamo con ese ID.");
            return;
        }
        try {
            Prestamo prestamo = new Prestamo(id, usuario, libro, LocalDate.now().plusDays(15));
            datos.getPrestamos().add(prestamo);
            txtId.setText("");
            actualizarTodo();
            JOptionPane.showMessageDialog(this, "Prestamo registrado. Fecha limite: " + prestamo.getFechaLimite());
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No se pudo registrar", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void ejecutarAccion(boolean renovar) {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un prestamo de la tabla.");
            return;
        }
        String id = modelo.getValueAt(fila, 0).toString();
        Prestamo prestamo = datos.getPrestamos().stream()
                .filter(p -> p.getId().equals(id)).findFirst().orElse(null);
        if (prestamo == null) return;
        try {
            if (renovar) prestamo.renovar(); else prestamo.devolver();
            actualizarTabla();
            JOptionPane.showMessageDialog(this, renovar
                    ? "Prestamo renovado. Nueva fecha limite: " + prestamo.getFechaLimite()
                    : "Devolucion registrada correctamente.");
        } catch (IllegalStateException ex) {
            actualizarTabla();
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Operacion no permitida", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void actualizarTabla() {
        modelo.setRowCount(0);
        for (Prestamo p : datos.getPrestamos()) {
            modelo.addRow(new Object[]{p.getId(), p.getUsuario().getNombre(),
                    p.getLibro().getTitulo(), p.getFechaInicio(), p.getFechaLimite(),
                    p.getEstado().obtenerNombre(), p.getRenovaciones()});
        }
    }
}
