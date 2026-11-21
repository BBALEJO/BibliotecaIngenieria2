
package com.unilibre.biblioteca.ui;

import javax.swing.*;
import com.unilibre.biblioteca.model.BibliotecaDatos;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private JPanel panelContenido;
    private CardLayout administradorPaneles;
    private final BibliotecaDatos datos = new BibliotecaDatos();
    private final PanelMultas panelMultas;
    private final PanelListaEspera panelEspera;
    private final PanelInicio panelInicio;

    public VentanaPrincipal() {

        setTitle("Biblioteca Ingenieria 2");
        setSize(1200, 750);
        setMinimumSize(new Dimension(1000, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // MENU LATERAL
        JPanel menu = new JPanel();
        menu.setBackground(new Color(42, 62, 55));
        menu.setPreferredSize(new Dimension(230, 0));
        menu.setLayout(new GridLayout(9, 1, 5, 5));
        menu.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        JLabel titulo = new JLabel("BIBLIOTECA", SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        JButton inicio = crearBoton("Inicio");
        JButton libros = crearBoton("Libros");
        JButton usuarios = crearBoton("Usuarios");
        JButton prestamos = crearBoton("Prestamos");
        JButton multas = crearBoton("Multas");
        JButton espera = crearBoton("Lista de espera");
        JButton salir = crearBoton("Salir");

        menu.add(titulo);
        menu.add(inicio);
        menu.add(libros);
        menu.add(usuarios);
        menu.add(prestamos);
        menu.add(multas);
        menu.add(espera);
        menu.add(new JLabel());
        menu.add(salir);

        // ADMINISTRADOR DE PANELES
        administradorPaneles = new CardLayout();
        panelContenido = new JPanel(administradorPaneles);

        panelInicio = new PanelInicio(datos);
        panelContenido.add(panelInicio, "INICIO");
        panelContenido.add(new PanelLibros(datos), "LIBROS");

        panelContenido.add(new PanelUsuarios(datos), "USUARIOS");
        panelContenido.add(new PanelPrestamos(datos), "PRESTAMOS");
        panelMultas = new PanelMultas(datos);
        panelEspera = new PanelListaEspera(datos);
        panelContenido.add(panelMultas, "MULTAS");
        panelContenido.add(panelEspera, "ESPERA");

        // ACCIONES
        inicio.addActionListener(e -> { panelInicio.refrescar(); mostrarPanel("INICIO"); });
        libros.addActionListener(e -> mostrarPanel("LIBROS"));
        usuarios.addActionListener(e -> mostrarPanel("USUARIOS"));
        prestamos.addActionListener(e -> mostrarPanel("PRESTAMOS"));
        multas.addActionListener(e -> { panelMultas.refrescar(); mostrarPanel("MULTAS"); });
        espera.addActionListener(e -> { panelEspera.refrescar(); mostrarPanel("ESPERA"); });

        salir.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Deseas cerrar la aplicacion?",
                    "Salir",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcion == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

        add(menu, BorderLayout.WEST);
        add(panelContenido, BorderLayout.CENTER);
    }

    private JButton crearBoton(String texto) {

        JButton boton = new JButton(texto);
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(42, 62, 55));
        boton.setFont(new Font("Arial", Font.PLAIN, 15));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return boton;
    }

    private JPanel crearInicio() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 240));

        JLabel bienvenida = new JLabel(
                "<html><center><h1>Bienvenido a la Biblioteca</h1>"
                        + "<p>Sistema de gestion bibliotecaria</p></center></html>",
                SwingConstants.CENTER
        );

        panel.add(bienvenida, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelTemporal(String nombre) {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 240));

        JLabel mensaje = new JLabel(
                "<html><center><h1>" + nombre + "</h1>"
                        + "<p>Modulo en desarrollo</p></center></html>"
        );

        panel.add(mensaje);

        return panel;
    }

    private void mostrarPanel(String nombre) {
        administradorPaneles.show(panelContenido, nombre);
    }
}
