
package com.unilibre.biblioteca.ui;

import com.unilibre.biblioteca.model.Libro;
import com.unilibre.biblioteca.model.BibliotecaDatos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelLibros extends JPanel {

    private JTextField txtId;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtIsbn;
    private JTextField txtEjemplares;

    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;

    private final List<Libro> libros;
    private final BibliotecaDatos datos;

    public PanelLibros(BibliotecaDatos datos) {

        this.datos = datos;
        this.libros = datos.getLibros();

        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 245, 240));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Titulo del modulo
        JLabel titulo = new JLabel("Gestion de libros");
        titulo.setFont(new Font("Arial", Font.BOLD, 26));

        add(titulo, BorderLayout.NORTH);

        // Formulario
        JPanel formulario = new JPanel(new GridLayout(5, 2, 10, 10));
        formulario.setBackground(Color.WHITE);
        formulario.setBorder(BorderFactory.createTitledBorder("Datos del libro"));

        txtId = new JTextField();
        txtTitulo = new JTextField();
        txtAutor = new JTextField();
        txtIsbn = new JTextField();
        txtEjemplares = new JTextField();

        formulario.add(new JLabel("ID:"));
        formulario.add(txtId);

        formulario.add(new JLabel("Titulo:"));
        formulario.add(txtTitulo);

        formulario.add(new JLabel("Autor:"));
        formulario.add(txtAutor);

        formulario.add(new JLabel("ISBN:"));
        formulario.add(txtIsbn);

        formulario.add(new JLabel("Ejemplares disponibles (al registrar):"));
        formulario.add(txtEjemplares);

        // Botones
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        panelSuperior.setBackground(new Color(245, 245, 240));
        panelSuperior.add(formulario, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        // Tabla
        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Titulo", "Autor", "ISBN", "Disponibles"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaLibros = new JTable(modeloTabla);
        tablaLibros.setRowHeight(25);

        JScrollPane scrollTabla = new JScrollPane(tablaLibros);
        scrollTabla.setBorder(
                BorderFactory.createTitledBorder("Catalogo de libros")
        );

        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        panelCentral.setBackground(new Color(245, 245, 240));
        panelCentral.add(panelSuperior, BorderLayout.NORTH);
        panelCentral.add(scrollTabla, BorderLayout.CENTER);

        add(panelCentral, BorderLayout.CENTER);

        // Acciones de los botones
        btnRegistrar.addActionListener(e -> registrarLibro());
        btnEditar.addActionListener(e -> editarLibro());
        btnEliminar.addActionListener(e -> eliminarLibro());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        tablaLibros.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarLibroSeleccionado();
            }
        });
    }

    private void registrarLibro() {

        try {
            String id = txtId.getText().trim();
            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            String isbn = txtIsbn.getText().trim();
            int ejemplares = Integer.parseInt(txtEjemplares.getText().trim());

            if (id.isEmpty() || titulo.isEmpty()
                    || autor.isEmpty() || isbn.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Todos los campos son obligatorios.");
                return;
            }

            if (ejemplares < 0) {
                JOptionPane.showMessageDialog(this,
                        "Los ejemplares no pueden ser negativos.");
                return;
            }

            for (Libro libro : libros) {
                if (libro.getId().equals(id)) {
                    JOptionPane.showMessageDialog(this,
                            "Ya existe un libro con ese ID.");
                    return;
                }
            }

            Libro nuevoLibro = new Libro(
                    id, titulo, autor, isbn, ejemplares
            );

            libros.add(nuevoLibro);
            actualizarTabla();
            limpiarFormulario();

            JOptionPane.showMessageDialog(this,
                    "Libro registrado correctamente.");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Ingresa un numero valido de ejemplares.");
        }
    }

    private void editarLibro() {

        int fila = tablaLibros.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un libro de la tabla.");
            return;
        }

        try {
            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            String isbn = txtIsbn.getText().trim();

            if (titulo.isEmpty() || autor.isEmpty() || isbn.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Todos los campos son obligatorios.");
                return;
            }

            int indice = tablaLibros.convertRowIndexToModel(fila);
            Libro libroActual = libros.get(indice);
            // Se conserva la misma instancia para no romper préstamos ni observadores.
            libroActual.actualizarDatos(titulo, autor, isbn);

            actualizarTabla();
            limpiarFormulario();

            JOptionPane.showMessageDialog(this,
                    "Datos descriptivos actualizados. El ID y los ejemplares disponibles se conservaron.");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Ingresa un numero valido de ejemplares.");
        }
    }

    private void eliminarLibro() {

        int fila = tablaLibros.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un libro de la tabla.");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Estas seguro de eliminar este libro?",
                "Confirmar eliminacion",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {

            int indice = tablaLibros.convertRowIndexToModel(fila);
            Libro libro = libros.get(indice);
            boolean tienePrestamos = datosPrestamosAsociados(libro);
            boolean tieneEspera = servicioEsperaAsociado(libro);
            if (tienePrestamos || tieneEspera) {
                JOptionPane.showMessageDialog(this,
                        "No se puede eliminar: el libro tiene préstamos registrados o solicitudes pendientes en lista de espera.");
                return;
            }

            libros.remove(indice);

            actualizarTabla();
            limpiarFormulario();

            JOptionPane.showMessageDialog(this,
                    "Libro eliminado correctamente.");
        }
    }

    private boolean datosPrestamosAsociados(Libro libro) {
        return datos.getPrestamos().stream().anyMatch(p -> p.getLibro() == libro);
    }

    private boolean servicioEsperaAsociado(Libro libro) {
        return datos.getServicioListaEspera().tieneSolicitudesPendientes(libro.getId());
    }

    private void cargarLibroSeleccionado() {

        int fila = tablaLibros.getSelectedRow();

        if (fila != -1) {

            int indice = tablaLibros.convertRowIndexToModel(fila);
            Libro libro = libros.get(indice);

            txtId.setText(libro.getId());
            txtTitulo.setText(libro.getTitulo());
            txtAutor.setText(libro.getAutor());
            txtIsbn.setText(libro.getIsbn());

            txtEjemplares.setText(
                    String.valueOf(libro.getEjemplaresDisponibles())
            );
        }
    }

    private void actualizarTabla() {

        modeloTabla.setRowCount(0);

        for (Libro libro : libros) {

            modeloTabla.addRow(new Object[]{
                    libro.getId(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getIsbn(),
                    libro.getEjemplaresDisponibles()
            });
        }
    }

    private void limpiarFormulario() {

        txtId.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        txtIsbn.setText("");
        txtEjemplares.setText("");

        tablaLibros.clearSelection();
    }
}
