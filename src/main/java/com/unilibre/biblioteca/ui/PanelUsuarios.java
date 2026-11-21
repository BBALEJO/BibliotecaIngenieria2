
package com.unilibre.biblioteca.ui;

import com.unilibre.biblioteca.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;

public class PanelUsuarios extends JPanel {

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JTextField txtBuscar;

    private JComboBox<String> cmbRol;

    private JTable tabla;
    private DefaultTableModel modelo;

    private final List<Usuario> usuarios;

    public PanelUsuarios(BibliotecaDatos datos) {

        this.usuarios = datos.getUsuarios();

        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 245, 240));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Gestion de usuarios");
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        add(titulo, BorderLayout.NORTH);

        // FORMULARIO
        JPanel formulario = new JPanel(new GridLayout(5, 2, 10, 10));
        formulario.setBackground(Color.WHITE);
        formulario.setBorder(
                BorderFactory.createTitledBorder("Datos del usuario")
        );

        txtId = new JTextField();
        txtNombre = new JTextField();
        txtCorreo = new JTextField();
        txtContrasena = new JPasswordField();

        cmbRol = new JComboBox<>(new String[]{
                "Administrador",
                "Bibliotecario",
                "Usuario regular"
        });

        formulario.add(new JLabel("Identificacion:"));
        formulario.add(txtId);

        formulario.add(new JLabel("Nombre:"));
        formulario.add(txtNombre);

        formulario.add(new JLabel("Correo:"));
        formulario.add(txtCorreo);

        formulario.add(new JLabel("Contrasena:"));
        formulario.add(txtContrasena);

        formulario.add(new JLabel("Rol:"));
        formulario.add(cmbRol);

        // BOTONES
        JButton btnRegistrar = new JButton("Registrar usuario");
        JButton btnEliminar = new JButton("Eliminar usuario");
        JButton btnLimpiar = new JButton("Limpiar");

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botones.add(btnRegistrar);
        botones.add(btnEliminar);
        botones.add(btnLimpiar);

        // BUSQUEDA
        txtBuscar = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        JButton btnMostrarTodos = new JButton("Mostrar todos");

        JPanel busqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        busqueda.add(new JLabel("Buscar:"));
        busqueda.add(txtBuscar);
        busqueda.add(btnBuscar);
        busqueda.add(btnMostrarTodos);

        JPanel superior = new JPanel(new BorderLayout(10, 10));
        superior.setBackground(new Color(245, 245, 240));
        superior.add(formulario, BorderLayout.CENTER);
        superior.add(botones, BorderLayout.SOUTH);

        // TABLA
        modelo = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Correo", "Rol"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(
                BorderFactory.createTitledBorder("Usuarios registrados")
        );

        JPanel centro = new JPanel(new BorderLayout(10, 10));
        centro.setBackground(new Color(245, 245, 240));
        centro.add(superior, BorderLayout.NORTH);
        centro.add(scroll, BorderLayout.CENTER);
        centro.add(busqueda, BorderLayout.SOUTH);

        add(centro, BorderLayout.CENTER);

        // EVENTOS
        btnRegistrar.addActionListener(e -> registrarUsuario());
        btnEliminar.addActionListener(e -> eliminarUsuario());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnBuscar.addActionListener(e -> buscarUsuario());
        btnMostrarTodos.addActionListener(e -> actualizarTabla(usuarios));
    }

    private void registrarUsuario() {

        String id = txtId.getText().trim();
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());

        if (id.isEmpty() || nombre.isEmpty()
                || correo.isEmpty() || contrasena.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this, "Todos los campos son obligatorios."
            );
            return;
        }

        if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            JOptionPane.showMessageDialog(
                    this, "Ingresa un correo valido."
            );
            return;
        }

        for (Usuario usuario : usuarios) {

            if (usuario.getId().equals(id)) {
                JOptionPane.showMessageDialog(
                        this, "Ya existe un usuario con esa identificacion."
                );
                return;
            }

            if (usuario.getCorreo().equalsIgnoreCase(correo)) {
                JOptionPane.showMessageDialog(
                        this, "Ya existe un usuario con ese correo."
                );
                return;
            }
        }

        // FACTORY METHOD
        UsuarioCreator creador;

        switch (cmbRol.getSelectedIndex()) {

            case 0:
                creador = new AdministradorCreator(
                        id, nombre, correo, contrasena
                );
                break;

            case 1:
                creador = new BibliotecarioCreator(
                        id, nombre, correo, contrasena
                );
                break;

            default:
                creador = new UsuarioRegularCreator(
                        id, nombre, correo, contrasena
                );
                break;
        }

        Usuario nuevoUsuario = creador.crearUsuario();

        usuarios.add(nuevoUsuario);

        actualizarTabla(usuarios);
        limpiarFormulario();

        JOptionPane.showMessageDialog(
                this,
                "Usuario registrado correctamente.\\nRol: "
                        + nuevoUsuario.obtenerRol()
        );
    }

    private void eliminarUsuario() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(
                    this, "Selecciona un usuario de la tabla."
            );
            return;
        }

        String id = modelo.getValueAt(fila, 0).toString();

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Deseas eliminar este usuario?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {

            usuarios.removeIf(usuario -> usuario.getId().equals(id));

            actualizarTabla(usuarios);
            limpiarFormulario();
        }
    }

    private void buscarUsuario() {

        String criterio = txtBuscar.getText().trim().toLowerCase();

        List<Usuario> resultados = new ArrayList<>();

        for (Usuario usuario : usuarios) {

            if (usuario.getId().toLowerCase().contains(criterio)
                    || usuario.getNombre().toLowerCase().contains(criterio)) {

                resultados.add(usuario);
            }
        }

        actualizarTabla(resultados);
    }

    private void actualizarTabla(List<Usuario> lista) {

        modelo.setRowCount(0);

        for (Usuario usuario : lista) {

            modelo.addRow(new Object[]{
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getCorreo(),
                    usuario.obtenerRol()
            });
        }
    }

    private void limpiarFormulario() {

        txtId.setText("");
        txtNombre.setText("");
        txtCorreo.setText("");
        txtContrasena.setText("");
        txtBuscar.setText("");

        cmbRol.setSelectedIndex(0);
        tabla.clearSelection();
    }
}
